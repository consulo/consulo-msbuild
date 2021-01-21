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
import com.intellij.openapi.roots.ModifiableRootModel;
import com.intellij.openapi.roots.ModuleRootManager;
import com.intellij.openapi.util.io.FileUtil;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.util.io.BaseOutputReader;
import consulo.application.AccessRule;
import consulo.container.boot.ContainerPathManager;
import consulo.disposer.Disposable;
import consulo.msbuild.MSBuildProcessProvider;
import consulo.msbuild.MSBuildProjectCapability;
import consulo.msbuild.MSBuildProjectListener;
import consulo.msbuild.MSBuildWorkspaceData;
import consulo.msbuild.daemon.impl.logging.MSBuildLoggingSession;
import consulo.msbuild.daemon.impl.message.DaemonConnection;
import consulo.msbuild.daemon.impl.message.DaemonMessage;
import consulo.msbuild.daemon.impl.message.model.DataObject;
import consulo.msbuild.daemon.impl.message.model.InitializeRequest;
import consulo.msbuild.daemon.impl.message.model.LogMessage;
import consulo.msbuild.daemon.impl.message.model.MSBuildEvaluatedItem;
import consulo.msbuild.daemon.impl.network.MSBuildServerThread;
import consulo.msbuild.daemon.impl.network.MSBuildSocketThread;
import consulo.msbuild.daemon.impl.step.*;
import consulo.msbuild.module.extension.MSBuildSolutionModuleExtension;
import consulo.msbuild.solution.model.WProject;
import consulo.net.util.NetUtil;
import consulo.util.concurrent.AsyncResult;
import consulo.util.dataholder.Key;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

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
	@Nonnull
	public static MSBuildDaemonService getInstance(@Nonnull Project project)
	{
		return project.getInstance(MSBuildDaemonService.class);
	}

	public static final int VERSION = 9;

	private final Project myProject;

	private int myPort = -1;

	private MSBuildServerThread myServerThread;

	private AsyncResult<MSBuildSocketThread> mySocketResult = AsyncResult.undefined();

	private DaemonConnection myConnection;

	private AtomicInteger myLoggers = new AtomicInteger();

	private Map<Integer, MSBuildLoggingSession> myLoggingSessions = new ConcurrentHashMap<>();

	private AtomicBoolean myBusy = new AtomicBoolean(false);

	@Inject
	public MSBuildDaemonService(Project project)
	{
		myProject = project;
	}

	public boolean isBusy()
	{
		return myBusy.get();
	}

	@Nonnull
	public AsyncResult<MSBuildDaemonContext> forceUpdate()
	{
		MSBuildSolutionModuleExtension<?> solutionExtension = getSolutionExtension();
		if(solutionExtension == null)
		{
			return AsyncResult.rejected();
		}

		List<DaemonStep> steps = new ArrayList<>();
		Collection<WProject> projects = solutionExtension.getProjects();
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
			steps.add(new AnalyzeOldProjectItemsStep(project));
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

		return runSteps(steps).doWhenDone(context -> createModules(context));
	}

	public void runTarget(String target)
	{
		MSBuildSolutionModuleExtension<?> solutionExtension = getSolutionExtension();
		if(solutionExtension == null)
		{
			return;
		}
		List<DaemonStep> steps = new ArrayList<>();
		Collection<WProject> projects = solutionExtension.getProjects();
		for(WProject wProject : projects)
		{
			steps.add(new InitializeProjectStep(wProject));
		}

		for(WProject project : projects)
		{
			steps.add(new RunTargetProjectStep(project, target));
		}

		runSteps(steps);
	}

	@Nonnull
	public AsyncResult<MSBuildDaemonContext> runSteps(List<DaemonStep> steps)
	{
		MSBuildSolutionModuleExtension<?> solutionExtension = getSolutionExtension();
		if(solutionExtension == null)
		{
			throw new UnsupportedOperationException();
		}

		if(!myBusy.compareAndSet(false, true))
		{
			return AsyncResult.rejected();
		}

		AsyncResult<MSBuildDaemonContext> runStepResult = AsyncResult.undefined();

		Task.Backgroundable.queue(myProject, "Starting Daemon Service...", indicator ->
		{
			try
			{
				MSBuildProcessProvider buildProcessProvider = findBuildProcessProvider(solutionExtension);

				Sdk msBuildSdk = buildProcessProvider.findBundle(solutionExtension.getSdkName());

				// not connected to server
				if(myConnection == null)
				{
					indicator.setText2("Preparing daemon process...");

					initializeServer();

					indicator.setText2("Starting daemon process...");

					startServer(msBuildSdk, buildProcessProvider);

					MSBuildSocketThread socketThread = mySocketResult.getResultSync();

					myConnection = new DaemonConnection(socketThread);
					socketThread.setDaemonConnection(myConnection);

					socketThread.waitForConnect();

					InitializeRequest message = new InitializeRequest();
					message.IdeProcessId = (int) ProcessHandle.current().pid();
					message.CultureName = Locale.getDefault().toString();
					message.BinDir = buildProcessProvider.getBinDir(msBuildSdk).getAbsolutePath();

					fillGlobalProperties(message.GlobalProperties, message.BinDir, msBuildSdk, buildProcessProvider);

					myConnection.sendWithResponse(message).getResultSync();
				}

				MSBuildDaemonContext context = new MSBuildDaemonContext(buildProcessProvider, msBuildSdk);

				MSBuildLoggingSession loggingSession = null;
				for(DaemonStep step : steps)
				{
					if(step instanceof BaseRunProjectStep)
					{
						if(((BaseRunProjectStep) step).wantLogging())
						{
							loggingSession = newLoggingSession();
							break;
						}
					}
				}

				for(DaemonStep step : steps)
				{
					indicator.setText(step.getStepText());

					DaemonMessage request = step.prepareRequest(context);

					myConnection.prepareLogging(request, step, loggingSession);

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

				for(DaemonStep step : steps)
				{
					if(step instanceof ListTargetsStep)
					{
						MSBuildWorkspaceData msBuildWorkspaceData = MSBuildWorkspaceData.getInstance(myProject);
						for(MSBuildDaemonContext.PerProjectInfo projectInfo : context.getInfos())
						{
							msBuildWorkspaceData.setTargets(projectInfo.wProject.getId(), projectInfo.targets);
							break;
						}
					}
				}

				if(loggingSession != null)
				{
					myLoggingSessions.remove(loggingSession.getId());
					final MSBuildLoggingSession finalLoggingSession = loggingSession;
					Application.get().getLastUIAccess().give(() -> finalLoggingSession.flush());
					loggingSession.disposeWithTree();
				}

				runStepResult.setDone(context);
			}
			catch(Exception e)
			{
				runStepResult.rejectWithThrowable(e);
			}
			finally
			{
				myBusy.compareAndSet(true, false);
			}
		});

		return runStepResult;
	}

	private void createModules(MSBuildDaemonContext context)
	{
		MSBuildProcessProvider buildProcessProvider = context.getBuildProcessProvider();
		Sdk msBuildSdk = context.getMSBuildSdk();

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

				importModule(finalModuleByName, rootModel, info, buildProcessProvider, msBuildSdk);

				WriteAction.runAndWait(rootModel::commit);
			}

			WriteAction.runAndWait(moduleModel::commit);

			myProject.getMessageBus().syncPublisher(MSBuildProjectListener.TOPIC).projectsReloaded();
		});
	}

	private void importModule(Module module, ModifiableRootModel rootModel, MSBuildDaemonContext.PerProjectInfo info, MSBuildProcessProvider buildProcessProvider, Sdk msBuildSdk)
	{
		VirtualFile projectFile = info.wProject.getVirtualFile();
		// project file found
		if(projectFile == null)
		{
			return;
		}

		MSBuildWorkspaceData workspaceData = MSBuildWorkspaceData.getInstance(myProject);

		VirtualFile parent = projectFile.getParent();
		assert parent != null;

		List<MSBuildEvaluatedItem> all = new ArrayList<>();

		for(String item : AnalyzeProjectItemsStep.ITEMS)
		{
			Collection<MSBuildEvaluatedItem> items = info.items.get(item);
			all.addAll(items);

			for(MSBuildEvaluatedItem evaluatedItem : items)
			{
				VirtualFile file = parent.findFileByRelativePath(evaluatedItem.getItemSpec());
				if(file != null)
				{
					rootModel.addSingleContentEntry(file);
				}
			}
		}

		rootModel.addSingleContentEntry(projectFile);

		workspaceData.setItems(info.wProject.getId(), all);

		// FIXME [VISTALL] rewrite it! old items are deprecated
		Collection<String> projectCapacility = info.oldItems.get("ProjectCapability");

		List<MSBuildProjectCapability> capabilities = new ArrayList<>();
		for(MSBuildProjectCapability capability : MSBuildProjectCapability.EP_NAME.getExtensionList(myProject.getApplication()))
		{
			if(projectCapacility.contains(capability.getId()) && capability.isApplicable(buildProcessProvider))
			{
				capabilities.add(capability);
			}
		}

		capabilities.sort((o1, o2) -> Integer.compareUnsigned(o1.getWeight(), o2.getWeight()));

		for(MSBuildProjectCapability capability : capabilities)
		{
			capability.importModule(module, rootModel, projectFile, buildProcessProvider, msBuildSdk, info.properties, info.dependencies, info.targets);
		}
	}

	@Nonnull
	private MSBuildProcessProvider findBuildProcessProvider(@Nonnull MSBuildSolutionModuleExtension<?> solutionModuleExtension)
	{
		for(MSBuildProcessProvider msBuildProcessProvider : MSBuildProcessProvider.EP_NAME.getExtensionList(Application.get()))
		{
			if(Objects.equals(solutionModuleExtension.getProcessProviderId(), msBuildProcessProvider.getId()))
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

	private void fillGlobalProperties(Map<String, String> dictionary, String binDir, Sdk msBuildSdk, MSBuildProcessProvider buildProcessProvider)
	{
		// This causes build targets to behave how they should inside an IDE, instead of in a command-line process
		dictionary.put("BuildingInsideVisualStudio", "true");

		// We don't have host compilers in MD, and this is set to true by some of the MS targets
		// which causes it to always run the CoreCompile task if BuildingInsideVisualStudio is also
		// true, because the VS in-process compiler would take care of the deps tracking
		dictionary.put("UseHostCompilerIfAvailable", "false");

		MSBuildSolutionModuleExtension<?> solutionExtension = getSolutionExtension();
		if(solutionExtension == null)
		{
			return;
		}

		VirtualFile solutionFile = solutionExtension.getSolutionFile();

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
				myConnection = null;

				if(!mySocketResult.isProcessed())
				{
					mySocketResult.reject("Process terminated");
				}
			}

			@Override
			public void onTextAvailable(ProcessEvent processEvent, Key key)
			{
				//System.out.println("> " + processEvent.getText().trim());
			}
		});
		handler.startNotify();
	}

	private void initializeServer() throws Exception
	{
		myPort = NetUtil.findAvailableSocketPort();

		myServerThread = new MSBuildServerThread(this, myPort, it -> mySocketResult.setDone(it));
	}

	@Nullable
	private MSBuildSolutionModuleExtension<?> getSolutionExtension()
	{
		return MSBuildSolutionModuleExtension.getSolutionModuleExtension(myProject);
	}

	public MSBuildLoggingSession newLoggingSession()
	{
		int id = myLoggers.incrementAndGet();
		MSBuildLoggingSession session = new MSBuildLoggingSession(id, myProject);
		myLoggingSessions.put(id, session);
		return session;
	}

	public void acceptLogMessage(LogMessage logMessage)
	{
		MSBuildLoggingSession session = myLoggingSessions.get(logMessage.LoggerId);
		if(session != null)
		{
			session.acceptMessage(logMessage);
		}
	}

	@Override
	public void dispose()
	{
		if(myServerThread != null)
		{
			myServerThread.shutdown();
		}
	}
}
