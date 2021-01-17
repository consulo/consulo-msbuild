package consulo.msbuild.daemon.impl;

import com.intellij.execution.configurations.GeneralCommandLine;
import com.intellij.execution.process.OSProcessHandler;
import com.intellij.execution.process.ProcessAdapter;
import com.intellij.execution.process.ProcessEvent;
import com.intellij.openapi.application.Application;
import com.intellij.openapi.application.WriteAction;
import com.intellij.openapi.module.ModifiableModuleModel;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.ModuleManager;
import com.intellij.openapi.progress.Task;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.projectRoots.Sdk;
import com.intellij.openapi.projectRoots.SdkTable;
import com.intellij.openapi.roots.ModifiableRootModel;
import com.intellij.openapi.roots.ModuleRootManager;
import com.intellij.openapi.util.io.FileUtil;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.util.io.BaseOutputReader;
import consulo.application.AccessRule;
import consulo.container.boot.ContainerPathManager;
import consulo.disposer.Disposable;
import consulo.msbuild.MSBuildProcessProvider;
import consulo.msbuild.MSBuildSolutionManager;
import consulo.msbuild.bundle.MSBuildBundleType;
import consulo.msbuild.daemon.impl.message.DaemonConnection;
import consulo.msbuild.daemon.impl.message.DaemonMessage;
import consulo.msbuild.daemon.impl.message.WithLenghtReaderDecoder;
import consulo.msbuild.daemon.impl.message.model.DataObject;
import consulo.msbuild.daemon.impl.message.model.InitializeRequest;
import consulo.msbuild.daemon.impl.step.*;
import consulo.msbuild.solution.model.WProject;
import consulo.net.util.NetUtil;
import consulo.util.concurrent.AsyncResult;
import consulo.util.dataholder.Key;
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

import javax.annotation.Nonnull;
import java.io.File;
import java.io.IOException;
import java.util.*;

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

	public static final int VERSION = 9;

	private final Project myProject;

	private int myPort = -1;
	private EventLoopGroup bossGroup;
	private EventLoopGroup workerGroup;

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
			steps.add(new AnalyzePropertiesStep(project));
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

					MSBuildProcessProvider buildProcessProvider = findBuildProcessProvider();

					MSBuildSolutionManager solutionManager = MSBuildSolutionManager.getInstance(myProject);

					Sdk msBuildSdk = buildProcessProvider.findBundle(solutionManager.getMSBuildBundleName());

					startServer(msBuildSdk, buildProcessProvider);

					myConnection = myHandler.connectAsync().getResultSync();

					InitializeRequest message = new InitializeRequest();
					message.IdeProcessId = (int) ProcessHandle.current().pid();
					message.CultureName = Locale.getDefault().toString();
					message.BinDir = buildProcessProvider.getBinDir(msBuildSdk).getAbsolutePath();

					fillGlobalProperties(message.GlobalProperties, message.BinDir, msBuildSdk, buildProcessProvider);

					myConnection.sendWithResponse(message).getResultSync();
				}

				MSBuildDaemonContext context = new MSBuildDaemonContext();

				for(DaemonStep step : steps)
				{
					indicator.setText(step.getStepText());

					DaemonMessage request = step.prepareRequest(context);

					AsyncResult<DataObject> result = myConnection.sendWithResponse(request);
					DataObject object = (DataObject) result.getResultSync();

					if(object != null)
					{
						step.handleResponse(context, object);
					}
					else
					{
						// todo error
						throw new IOException("failed");
					}
				}

				createModules(context);
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		});
	}

	private void createModules(MSBuildDaemonContext context)
	{
		Task.Backgroundable.queue(myProject, "Creating Project Structure...", indicator -> {
			Collection<MSBuildDaemonContext.PerProjectInfo> infos = context.getInfos();

			ModuleManager moduleManager = ModuleManager.getInstance(myProject);

			ModifiableModuleModel moduleModel = AccessRule.read(moduleManager::getModifiableModel);

			assert moduleModel != null;

			for(MSBuildDaemonContext.PerProjectInfo info : infos)
			{
				WProject wProject = info.wProject;

				Module moduleByName = moduleModel.findModuleByName(wProject.getName());
				if(moduleByName == null)
				{
					moduleByName = moduleModel.newModule(wProject.getName(), null);
				}

				final Module finalModuleByName = moduleByName;
				ModifiableRootModel rootModel = AccessRule.read(() -> ModuleRootManager.getInstance(finalModuleByName).getModifiableModel());
				assert rootModel != null;

				rootModel.removeAllLayers(true);
				
				importModule(finalModuleByName, rootModel, info);

				WriteAction.runAndWait(rootModel::commit);
			}

			WriteAction.runAndWait(moduleModel::commit);
		});
	}

	private void importModule(Module module, ModifiableRootModel rootModel, MSBuildDaemonContext.PerProjectInfo info)
	{
		VirtualFile projectFile = info.wProject.getVirtualFile();
		// project file found
		if(projectFile == null)
		{
			return;
		}

		VirtualFile parent = projectFile.getParent();
		assert parent != null;

		Collection<String> compileItems = info.items.get("Compile");
		for(String compileItem : compileItems)
		{
			VirtualFile file = parent.findFileByRelativePath(compileItem);
			if(file != null)
			{
				rootModel.addSingleContentEntry(file);
			}
		}
		// todo
	}

	@Nonnull
	private MSBuildProcessProvider findBuildProcessProvider()
	{
		MSBuildSolutionManager solutionManager = MSBuildSolutionManager.getInstance(myProject);

		for(MSBuildProcessProvider msBuildProcessProvider : MSBuildProcessProvider.EP_NAME.getExtensionList(Application.get()))
		{
			if(Objects.equals(solutionManager.getProviderId(), msBuildProcessProvider.getId()))
			{
				return msBuildProcessProvider;
			}
		}

		throw new IllegalArgumentException("MSBuild provider not found");
	}

	private static File prepareMSBuildBuider(@Nonnull Sdk sdk, @Nonnull MSBuildProcessProvider buildProcessProvider) throws IOException
	{
		VirtualFile homeDirectory = sdk.getHomeDirectory();

		int urlHashCode = Math.abs(homeDirectory.getPresentableUrl().hashCode());

		File msBuildRunnerDir = new File(ContainerPathManager.get().getSystemPath(), "MSBuild/msbuild-" + urlHashCode);

		int ver = -1;
		File versionFile = new File(msBuildRunnerDir, "version.txt");
		if(versionFile.exists())
		{
			ver = Integer.parseInt(FileUtil.loadFile(versionFile));
		}

		int requiredVersion = VERSION + buildProcessProvider.getVersion();

		if(ver != requiredVersion)
		{
			FileUtil.delete(msBuildRunnerDir);
		}

		File originalExeFile = buildProcessProvider.getTargetFile();

		String fileName = originalExeFile.getName();

		File msBuildFile = new File(msBuildRunnerDir, fileName);
		if(msBuildFile.exists())
		{
			return msBuildFile;
		}

		FileUtil.createParentDirs(msBuildRunnerDir);

		FileUtil.copy(originalExeFile, msBuildFile);

		File parentDir = originalExeFile.getParentFile();

		String[] additionalPaths = {".pdb"};
		for(String additionalPath : additionalPaths)
		{
			File attachFile = new File(parentDir, FileUtil.getNameWithoutExtension(originalExeFile) + additionalPath);
			if(attachFile.exists())
			{
				File targetPdb = new File(msBuildRunnerDir, attachFile.getName());
				FileUtil.copy(attachFile, targetPdb);
			}
		}

		buildProcessProvider.doAdditionalCopy(originalExeFile, msBuildRunnerDir, sdk);

		FileUtil.writeToFile(versionFile, String.valueOf(requiredVersion));

		return msBuildFile;
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

	private void fillGlobalProperties(Map<String, String> dictionary, String binDir, Sdk msBuildSdk, MSBuildProcessProvider buildProcessProvider)
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

		buildProcessProvider.fillGlobalProperties(msBuildSdk, dictionary);
	}

	private void startServer(@Nonnull Sdk msBuildSdk, @Nonnull MSBuildProcessProvider provider) throws Exception
	{
		File exeFile = prepareMSBuildBuider(msBuildSdk, provider);

		GeneralCommandLine commandLine = provider.buildCommandLine(msBuildSdk, exeFile, myPort);

		OSProcessHandler handler = new OSProcessHandler(commandLine)
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
