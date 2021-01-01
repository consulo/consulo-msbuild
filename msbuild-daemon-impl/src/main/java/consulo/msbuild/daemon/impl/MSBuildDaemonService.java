package consulo.msbuild.daemon.impl;

import com.intellij.execution.configurations.GeneralCommandLine;
import com.intellij.execution.process.OSProcessHandler;
import com.intellij.execution.process.ProcessAdapter;
import com.intellij.execution.process.ProcessEvent;
import com.intellij.openapi.progress.Task;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.projectRoots.Sdk;
import com.intellij.openapi.projectRoots.SdkTable;
import com.intellij.openapi.util.io.FileUtil;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.util.io.BaseOutputReader;
import consulo.container.boot.ContainerPathManager;
import consulo.container.plugin.PluginManager;
import consulo.disposer.Disposable;
import consulo.msbuild.MSBuildSolutionManager;
import consulo.msbuild.bundle.MSBuildBundleType;
import consulo.msbuild.daemon.impl.message.DaemonConnection;
import consulo.msbuild.daemon.impl.message.DaemonMessage;
import consulo.msbuild.daemon.impl.message.WithLenghtReaderDecoder;
import consulo.msbuild.daemon.impl.message.model.*;
import consulo.msbuild.daemon.impl.step.*;
import consulo.msbuild.solution.model.WProject;
import consulo.net.util.NetUtil;
import consulo.platform.Platform;
import consulo.util.dataholder.Key;
import consulo.util.jdom.JDOMUtil;
import consulo.util.lang.StringUtil;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import org.jdom.Document;
import org.jdom.Element;

import javax.annotation.Nonnull;
import java.io.File;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author VISTALL
 * @since 12/12/2020
 * <p>
 * <p>
 * Contains some code parts from MonoDeveloper (MIT)
 */
@Singleton
public class MSBuildDaemonService implements Disposable
{
	private MSBuildDaemonHandler myHandler;

	@Nonnull
	public static MSBuildDaemonService getInstance(@Nonnull Project project)
	{
		return project.getInstance(MSBuildDaemonService.class);
	}

	public static final int VERSION = 6;

	private final Project myProject;

	private int myPort = -1;
	private EventLoopGroup bossGroup;
	private EventLoopGroup workerGroup;

	private final Map<String, Integer> myProjectIdsToIds = new ConcurrentHashMap<>();

	private DaemonConnection myConnection;

	@Inject
	public MSBuildDaemonService(Project project)
	{
		myProject = project;
	}

	public void forceUpdate()
	{
		MSBuildSolutionManager solutionManager = MSBuildSolutionManager.getInstance(myProject);
		if(!solutionManager.isEnabled())
		{
			return;
		}

		List<DaemonStep> steps = new ArrayList<>();
		Collection<WProject> projects = solutionManager.getSolution().getProjects();
		for(WProject wProject : projects)
		{
			steps.add(new InitializeProjectStep(wProject));
		}

		for(WProject project : projects)
		{
			steps.add(new AnalyzeProjectItemsStep(project));
		}

		for(WProject project : projects)
		{
			steps.add(new RestoreDependenciesStep(project));
		}

		for(WProject project : projects)
		{
			steps.add(new AnalyzeDependenciesStep(project));
		}

		for(WProject project : projects)
		{
			steps.add(new ListTargetsStep(project));
		}

		runSteps(steps);
	}

	private void runSteps(List<DaemonStep> steps)
	{
		Task.Backgroundable.queue(myProject, "Starting Daemon Service...", indicator ->
		{
			try
			{
				// not connected to server
				if(myConnection == null)
				{
					indicator.setText2("Preparing daemon process...");

					initializeServer();

					indicator.setText2("Starting daemon process...");

					Sdk msBuildSdk = getMSBuildSdk();

					startServer(msBuildSdk);

					myConnection = myHandler.connectAsync().getResultSync();

					InitializeRequest message = new InitializeRequest();
					message.IdeProcessId = (int) ProcessHandle.current().pid();
					message.CultureName = Locale.getDefault().toString();
					message.BinDir = new File(msBuildSdk.getHomePath(), "bin").getAbsolutePath();

					fillGlobalProperties(message.GlobalProperties, message.BinDir);

					myConnection.sendWithResponse(message).getResultSync();
				}

				MSBuildDaemonContext context = new MSBuildDaemonContext();

				for(DaemonStep step : steps)
				{
					indicator.setText(step.getStepText());

					DaemonMessage request = step.prepareRequest(context);

					DataObject object = (DataObject) myConnection.sendWithResponse(request).getResultSync();

					step.handleResponse(context, object);
				}
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		});
	}


	private static File prepareMSBuildBuider(@Nonnull Sdk sdk) throws Exception
	{
		VirtualFile homeDirectory = sdk.getHomeDirectory();

		int urlHashCode = Math.abs(homeDirectory.getPresentableUrl().hashCode());

		File msBuildSdkDir = new File(ContainerPathManager.get().getSystemPath(), "MSBuild/msbuild-" + urlHashCode);

		int ver = -1;
		File versionFile = new File(msBuildSdkDir, "version.txt");
		if(versionFile.exists())
		{
			ver = Integer.parseInt(FileUtil.loadFile(versionFile));
		}

		if(ver != VERSION)
		{
			FileUtil.delete(msBuildSdkDir);
		}

		File msBuildFile = new File(msBuildSdkDir, "Consulo.MSBuildBuilder.exe");
		if(msBuildFile.exists())
		{
			return msBuildFile;
		}

		FileUtil.createParentDirs(msBuildSdkDir);

		File net4Dir = new File(PluginManager.getPluginPath(MSBuildDaemonService.class), "net4");

		File originalExeFile = new File(net4Dir, "Consulo.MSBuildBuilder.exe");

		FileUtil.copy(originalExeFile, msBuildFile);

		File pdbFile = new File(net4Dir, "Consulo.MSBuildBuilder.pdb");
		if(pdbFile.exists())
		{
			File targetPdb = new File(msBuildSdkDir, pdbFile.getName());
			FileUtil.copy(pdbFile, targetPdb);
		}

		File msBuildBinDir = new File(sdk.getHomePath(), "Bin");

		File configFile = new File(msBuildBinDir, "MSBuild.exe.config");
		if(configFile.exists())
		{
			Element rootElement = JDOMUtil.load(configFile);

			Element msbuildToolsets = rootElement.getChild("msbuildToolsets");

			Element toolset = msbuildToolsets.getChild("toolset");

			// This is required for MSBuild to properly load the searchPaths element (@radical knows why)
			SetMSBuildConfigProperty(toolset, "MSBuildBinPath", msBuildBinDir.getAbsolutePath(), false, true);

			// this must match MSBuildBinPath w/MSBuild15
			SetMSBuildConfigProperty(toolset, "MSBuildToolsPath", msBuildBinDir.getAbsolutePath(), false, true);

			if(Platform.current().os().isWindows())
			{
				File extensionsPath = FileUtil.getParentFile(FileUtil.getParentFile(msBuildBinDir));
				File vsInstallRoot = FileUtil.getParentFile(extensionsPath);
				File devEnvDir = new File(vsInstallRoot, "Common7/IDE");

				SetMSBuildConfigProperty(toolset, "MSBuildExtensionsPath", extensionsPath.getAbsolutePath());
				SetMSBuildConfigProperty(toolset, "MSBuildExtensionsPath32", extensionsPath.getAbsolutePath());
				SetMSBuildConfigProperty(toolset, "MSBuildToolsPath", msBuildBinDir.getAbsolutePath());
				SetMSBuildConfigProperty(toolset, "MSBuildToolsPath32", msBuildBinDir.getAbsolutePath());
				SetMSBuildConfigProperty(toolset, "MSBuildToolsPath64", msBuildBinDir.getAbsolutePath());
				SetMSBuildConfigProperty(toolset, "VsInstallRoot", vsInstallRoot.getAbsolutePath());
				SetMSBuildConfigProperty(toolset, "DevEnvDir", devEnvDir + "\\");
				SetMSBuildConfigProperty(toolset, "NuGetRestoreTargets", new File(devEnvDir, "CommonExtensions\\Microsoft\\NuGet\\NuGet.targets").getAbsolutePath());

				File sdksPath = new File(extensionsPath, "Sdks");
				SetMSBuildConfigProperty(toolset, "MSBuildSDKsPath", sdksPath.getAbsolutePath());

				File roslynTargetsPath = new File(msBuildBinDir, "Roslyn");
				SetMSBuildConfigProperty(toolset, "RoslynTargetsPath", roslynTargetsPath.getAbsolutePath());

				File vcTargetsPath = new File(devEnvDir, "VC/VCTargets");
				SetMSBuildConfigProperty(toolset, "VCTargetsPath", vcTargetsPath.getAbsolutePath());
			}
			//			else
			//			{
			//				var path = MSBuildProjectService.GetProjectImportSearchPaths(runtime, false).FirstOrDefault(p = > p.Property == "MSBuildSDKsPath");
			//				if(path != null)
			//					SetMSBuildConfigProperty(toolset, path.Property, path.Path);
			//			}
			//
			//			var projectImportSearchPaths = toolset.Element("projectImportSearchPaths");
			//			if(projectImportSearchPaths != null)
			//			{
			//				var os = Platform.IsMac ? "osx" : Platform.IsWindows ? "windows" : "unix";
			//				XElement searchPaths = projectImportSearchPaths.Elements("searchPaths").FirstOrDefault(sp = > sp.Attribute("os") ?.Value == os);
			//				if(searchPaths == null)
			//				{
			//					searchPaths = new XElement("searchPaths");
			//					searchPaths.SetAttributeValue("os", os);
			//					projectImportSearchPaths.Add(searchPaths);
			//				}
			//				foreach(var path in MSBuildProjectService.GetProjectImportSearchPaths(runtime, false))
			//				SetMSBuildConfigProperty(searchPaths, path.Property, path.Path, append:true, insertBefore:false);
			//			}

			File targetConfigFile = new File(msBuildSdkDir, originalExeFile.getName() + ".config");

			JDOMUtil.writeDocument(new Document(rootElement), targetConfigFile, "\n");
		}

		FileUtil.writeToFile(versionFile, String.valueOf(VERSION));
		return msBuildFile;
	}

	private static void SetMSBuildConfigProperty(Element toolset, String name, String value)
	{
		SetMSBuildConfigProperty(toolset, name, value, true, false);
	}

	private static void SetMSBuildConfigProperty(Element toolset, String name, String value, boolean append, boolean insertBefore)
	{
		List<Element> properties = toolset.getChildren("property");

		for(Element property : properties)
		{
			String propName = property.getAttributeValue("name");

			if(name.equals(propName))
			{
				property.setAttribute("value", value);
				return;
			}
		}

		Element element = new Element("property").setAttribute("name", name).setAttribute("value", value);
		if(insertBefore)
		{
			toolset.addContent(0, element);
		}
		else
		{
			toolset.addContent(element);
		}
	}

	private Sdk getMSBuildSdk()
	{
		MSBuildSolutionManager solutionManager = MSBuildSolutionManager.getInstance(myProject);

		// TODO [VISTALL] improve logic
		String msBuildBundleName = solutionManager.getMSBuildBundleName();
		if(StringUtil.isEmptyOrSpaces(msBuildBundleName))
		{
			for(Sdk sdk : SdkTable.getInstance().getSdksOfType(MSBuildBundleType.getInstance()))
			{
				return sdk;
			}
		}
		else
		{
			return SdkTable.getInstance().findSdk(msBuildBundleName);
		}
		return null;
	}

	private void fillGlobalProperties(Map<String, String> dictionary, String binDir)
	{
		// This causes build targets to behave how they should inside an IDE, instead of in a command-line process
		dictionary.put("BuildingInsideVisualStudio", "true");

		// We don't have host compilers in MD, and this is set to true by some of the MS targets
		// which causes it to always run the CoreCompile task if BuildingInsideVisualStudio is also
		// true, because the VS in-process compiler would take care of the deps tracking
		dictionary.put("UseHostCompilerIfAvailable", "false");

		MSBuildSolutionManager solutionManager = MSBuildSolutionManager.getInstance(myProject);

		VirtualFile solutionFile = solutionManager.getSolutionFile();
		if(solutionFile == null)
		{
			return;
		}

		dictionary.put("SolutionPath", solutionFile.getPresentableUrl());
		dictionary.put("SolutionName", solutionFile.getNameWithoutExtension());
		dictionary.put("SolutionFilename", solutionFile.getName());
		dictionary.put("SolutionDir", solutionFile.getParent().getPresentableUrl() + "/");

		// When running the dev15 MSBuild from commandline or inside MSBuild, it sets "VSToolsPath" correctly. when running from MD, it falls back to a bad default. override it.
		if(Platform.current().os().isWindows())
		{
			String toolsVersion = "16.0";
			dictionary.put("VSToolsPath", Paths.get(binDir, "..", "..", "Microsoft", "VisualStudio", "v" + toolsVersion).toFile().getAbsolutePath());
		}
	}

	private void startServer(@Nonnull Sdk msBuildSdk) throws Exception
	{
		File exeFile = prepareMSBuildBuider(msBuildSdk);

		GeneralCommandLine c = new GeneralCommandLine();
		c.setExePath(exeFile.getPath());
		c.addParameter(String.valueOf(myPort));
		c.addParameter("true"); // debug

		OSProcessHandler handler = new OSProcessHandler(c)
		{
			@Nonnull
			@Override
			protected BaseOutputReader.Options readerOptions()
			{
				return BaseOutputReader.Options.forMostlySilentProcess();
			}
		};
		handler.addProcessListener(new ProcessAdapter()
		{
			@Override
			public void processTerminated(ProcessEvent processEvent)
			{

			}

			@Override
			public void onTextAvailable(ProcessEvent processEvent, Key key)
			{
				System.out.println("> " + processEvent.getText().trim());
			}
		});
		handler.startNotify();
	}

	private void initializeServer() throws Exception
	{
		myPort = NetUtil.findAvailableSocketPort();

		bossGroup = new NioEventLoopGroup();
		workerGroup = new NioEventLoopGroup();

		myHandler = new MSBuildDaemonHandler(MSBuildDaemonService.this);

		ServerBootstrap b = new ServerBootstrap();
		b.group(bossGroup, workerGroup)
				.channel(NioServerSocketChannel.class)
				.childHandler(new ChannelInitializer<SocketChannel>()
				{
					@Override
					public void initChannel(SocketChannel ch) throws Exception
					{
						ch.pipeline().addFirst(new WithLenghtReaderDecoder()).addLast(myHandler);
					}
				})
				.option(ChannelOption.SO_BACKLOG, 128)
				.childOption(ChannelOption.SO_KEEPALIVE, true);
		b.bind(myPort).sync();
	}

	@Override
	public void dispose()
	{
		workerGroup.shutdownGracefully();
		bossGroup.shutdownGracefully();
	}
}
