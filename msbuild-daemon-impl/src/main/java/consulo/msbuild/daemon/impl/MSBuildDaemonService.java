package consulo.msbuild.daemon.impl;

import consulo.annotation.component.ComponentScope;
import consulo.annotation.component.ServiceAPI;
import consulo.annotation.component.ServiceImpl;
import consulo.application.AccessRule;
import consulo.application.Application;
import consulo.application.WriteAction;
import consulo.application.progress.ProgressIndicator;
import consulo.application.progress.Task;
import consulo.container.boot.ContainerPathManager;
import consulo.content.bundle.Sdk;
import consulo.disposer.Disposable;
import consulo.localize.LocalizeValue;
import consulo.logging.Logger;
import consulo.module.ModifiableModuleModel;
import consulo.module.Module;
import consulo.module.ModuleManager;
import consulo.module.content.ModuleRootManager;
import consulo.module.content.layer.ModifiableRootModel;
import consulo.msbuild.MSBuildProcessProvider;
import consulo.msbuild.MSBuildProjectCapability;
import consulo.msbuild.MSBuildProjectListener;
import consulo.msbuild.MSBuildWorkspaceData;
import consulo.msbuild.daemon.impl.logging.MSBuildLoggingSession;
import consulo.msbuild.daemon.impl.message.DaemonConnection;
import consulo.msbuild.daemon.impl.message.model.InitializeRequest;
import consulo.msbuild.daemon.impl.message.model.LogMessage;
import consulo.msbuild.daemon.impl.message.model.MSBuildEvaluatedItem;
import consulo.msbuild.daemon.impl.network.MSBuildServerThread;
import consulo.msbuild.daemon.impl.network.MSBuildSocketThread;
import consulo.msbuild.daemon.impl.step.*;
import consulo.msbuild.module.extension.MSBuildSolutionModuleExtension;
import consulo.msbuild.solution.model.WProject;
import consulo.process.ProcessHandler;
import consulo.process.ProcessHandlerBuilder;
import consulo.process.cmd.GeneralCommandLine;
import consulo.process.event.ProcessEvent;
import consulo.process.event.ProcessListener;
import consulo.project.Project;
import consulo.util.concurrent.AsyncResult;
import consulo.util.dataholder.Key;
import consulo.util.io.FilePermissionCopier;
import consulo.util.io.FileUtil;
import consulo.util.io.NetUtil;
import consulo.virtualFileSystem.VirtualFile;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
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
@ServiceAPI(ComponentScope.PROJECT)
@ServiceImpl
public class MSBuildDaemonService implements Disposable {
    @Nonnull
    public static MSBuildDaemonService getInstance(@Nonnull Project project) {
        return project.getInstance(MSBuildDaemonService.class);
    }

    private static final Logger LOG = Logger.getInstance(MSBuildDaemonService.class);

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
    public MSBuildDaemonService(Project project) {
        myProject = project;
    }

    public boolean isBusy() {
        return myBusy.get();
    }

    /**
     * Start daemon service, with initialSteps.
     * Initial steps required to impl {@link DaemonStep#refill(MSBuildDaemonService, DaemonStepQueue)}
     * and call {@link #fillDefaultSteps(DaemonStepQueue)}  when ready
     */
    @Nonnull
    public AsyncResult<MSBuildDaemonContext> externalUpdate(@Nonnull DaemonStep... initialSteps) {
        MSBuildSolutionModuleExtension<?> solutionExtension = getSolutionExtension();
        if (solutionExtension == null) {
            return AsyncResult.rejected();
        }

        DaemonStepQueue queue = new DaemonStepQueue();
        for (DaemonStep initialStep : initialSteps) {
            queue.join(initialStep);
        }

        return runSteps(queue, null, LocalizeValue.localizeTODO("Update")).doWhenDone(this::createModules);
    }

    @Nonnull
    public AsyncResult<MSBuildDaemonContext> forceUpdate() {
        MSBuildSolutionModuleExtension<?> solutionExtension = getSolutionExtension();
        if (solutionExtension == null) {
            return AsyncResult.rejected();
        }

        DaemonStepQueue queue = new DaemonStepQueue();

        fillDefaultSteps(queue);

        return runSteps(queue, null, LocalizeValue.localizeTODO("Update")).doWhenDone(this::createModules);
    }

    public void fillDefaultSteps(@Nonnull DaemonStepQueue queue) {
        MSBuildSolutionModuleExtension<?> solutionExtension = getSolutionExtension();
        if (solutionExtension == null) {
            return;
        }

        Collection<WProject> projects = solutionExtension.getValidProjects();
        for (WProject wProject : projects) {
            queue.join(new InitializeProjectStep(wProject));
        }

        for (WProject project : projects) {
            queue.join(new AnalyzeProjectItemsStep(project));
        }

        for (WProject project : projects) {
            queue.join(new AnalyzeOldProjectItemsStep(project));
        }

        for (WProject project : projects) {
            queue.join(new AnalyzePropertiesStep(project));
        }

        for (WProject project : projects) {
            queue.join(new RestoreDependenciesStep(project));
        }

        MSBuildProcessProvider provider = findBuildProcessProvider(solutionExtension);

        for (WProject project : projects) {
            queue.join(new AnalyzeDependenciesStep(project, provider));
        }

        for (WProject project : projects) {
            queue.join(new ListTargetsStep(project));
        }
    }

    public void runTarget(String target) {
        MSBuildSolutionModuleExtension<?> solutionExtension = getSolutionExtension();
        if (solutionExtension == null) {
            return;
        }
        DaemonStepQueue steps = new DaemonStepQueue();
        Collection<WProject> projects = solutionExtension.getValidProjects();
        for (WProject wProject : projects) {
            steps.join(new InitializeProjectStep(wProject));
        }

        for (WProject project : projects) {
            steps.join(new RunTargetProjectStep(project, target, false));
        }

        runSteps(steps, null, LocalizeValue.of(target));
    }

    @Nonnull
    public AsyncResult<MSBuildDaemonContext> runSteps(DaemonStepQueue queue, @Nullable ProgressIndicator indicator, @Nonnull LocalizeValue loggingGroup) {
        MSBuildSolutionModuleExtension<?> solutionExtension = getSolutionExtension();
        if (solutionExtension == null) {
            throw new UnsupportedOperationException();
        }

        if (!myBusy.compareAndSet(false, true)) {
            return AsyncResult.rejected();
        }

        AsyncResult<MSBuildDaemonContext> runStepResult = AsyncResult.undefined();

        if (indicator != null) {
            indicator.setText("Starting Daemon Service...");

            runInBackground(indicator, solutionExtension, queue, runStepResult, loggingGroup);
        }
        else {
            Task.Backgroundable.queue(myProject, "Starting Daemon Service...", i -> runInBackground(i, solutionExtension, queue, runStepResult, loggingGroup));
        }

        return runStepResult;
    }

    private void runInBackground(@Nonnull ProgressIndicator indicator,
                                 MSBuildSolutionModuleExtension<?> solutionExtension,
                                 DaemonStepQueue queue,
                                 AsyncResult<MSBuildDaemonContext> runStepResult,
                                 @Nonnull LocalizeValue loggingGroup) {
        try {
            MSBuildProcessProvider buildProcessProvider = findBuildProcessProvider(solutionExtension);

            Sdk msBuildSdk = buildProcessProvider.findBundle(solutionExtension.getSdkName());

            // not connected to server
            if (myConnection == null) {
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
                message.CultureName = buildProcessProvider.getLocaleForProcess();
                message.BinDir = buildProcessProvider.getBinDir(msBuildSdk).getAbsolutePath();

                fillGlobalProperties(message.GlobalProperties, message.BinDir, msBuildSdk, buildProcessProvider);

                myConnection.sendWithResponse(message).getResultSync();
            }

            MSBuildDaemonContext context = new MSBuildDaemonContext(buildProcessProvider, msBuildSdk);

            MSBuildLoggingSession loggingSession = null;
            DaemonStep wantLogging = queue.find(DaemonStep::wantLogging);
            if (wantLogging != null) {
                loggingSession = newLoggingSession(loggingGroup);

                loggingSession.start(indicator);
            }

            boolean[] foundListTargetsStep = new boolean[1];

            final MSBuildLoggingSession finalLoggingSession = loggingSession;
            queue.eat(step ->
            {
                indicator.setTextValue(step.getStepText());

                step.execute(context, myConnection, finalLoggingSession);

                step.refill(this, queue);

                if (step instanceof ListTargetsStep) {
                    foundListTargetsStep[0] = true;
                }
            });

            if (foundListTargetsStep[0]) {
                MSBuildWorkspaceData msBuildWorkspaceData = MSBuildWorkspaceData.getInstance(myProject);
                for (MSBuildDaemonContext.PerProjectInfo projectInfo : context.getInfos()) {
                    msBuildWorkspaceData.setTargets(projectInfo.wProject.getId(), projectInfo.targets);
                }
            }

            if (loggingSession != null) {
                loggingSession.stop();

                myLoggingSessions.remove(loggingSession.getId());
                loggingSession.disposeWithTree();
            }

            runStepResult.setDone(context);
        }
        catch (Exception e) {
            LOG.warn(e);

            runStepResult.rejectWithThrowable(e);
        }
        finally {
            myBusy.compareAndSet(true, false);
        }
    }

    private void createModules(MSBuildDaemonContext context) {
        MSBuildProcessProvider buildProcessProvider = context.getBuildProcessProvider();
        Sdk msBuildSdk = context.getMSBuildSdk();

        Task.Backgroundable.queue(myProject, "Creating Project Structure...", indicator -> {
            Collection<MSBuildDaemonContext.PerProjectInfo> infos = context.getInfos();

            ModuleManager moduleManager = ModuleManager.getInstance(myProject);

            ModifiableModuleModel moduleModel = AccessRule.read(moduleManager::getModifiableModel);

            assert moduleModel != null;

            for (MSBuildDaemonContext.PerProjectInfo info : infos) {
                WProject wProject = info.wProject;

                Module moduleByName = moduleModel.findModuleByName(wProject.getName());
                if (moduleByName == null) {
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

            myProject.getMessageBus().syncPublisher(MSBuildProjectListener.class).projectsReloaded();
        });
    }

    private void importModule(Module module, ModifiableRootModel rootModel, MSBuildDaemonContext.PerProjectInfo info, MSBuildProcessProvider buildProcessProvider, Sdk msBuildSdk) {
        VirtualFile projectFile = info.wProject.getVirtualFile();
        // project file found
        if (projectFile == null) {
            return;
        }

        MSBuildWorkspaceData workspaceData = MSBuildWorkspaceData.getInstance(myProject);

        VirtualFile parentDir = projectFile.getParent();
        assert parentDir != null;

        List<MSBuildEvaluatedItem> evaluateData = new ArrayList<>();

        for (String item : AnalyzeProjectItemsStep.ITEMS) {
            Collection<MSBuildEvaluatedItem> items = info.items.get(item);
            evaluateData.addAll(items);

            for (MSBuildEvaluatedItem evaluatedItem : items) {
                String url = parentDir.getUrl() + "/" + FileUtil.toSystemIndependentName(evaluatedItem.getItemSpec());
                rootModel.addSingleContentEntry(url);
            }
        }

        rootModel.addSingleContentEntry(projectFile);

        workspaceData.updateData(info.wProject.getId(), module.getName(), evaluateData, info.properties);

        // FIXME [VISTALL] rewrite it! old items are deprecated
        Collection<String> projectCapacility = info.oldItems.get("ProjectCapability");

        List<MSBuildProjectCapability> capabilities = new ArrayList<>();
        for (MSBuildProjectCapability capability : myProject.getApplication().getExtensionList(MSBuildProjectCapability.class)) {
            if (projectCapacility.contains(capability.getId()) && capability.isApplicable(buildProcessProvider)) {
                capabilities.add(capability);
            }
        }

        capabilities.sort((o1, o2) -> Integer.compareUnsigned(o1.getWeight(), o2.getWeight()));

        for (MSBuildProjectCapability capability : capabilities) {
            capability.importModule(module, rootModel, projectFile, buildProcessProvider, msBuildSdk, info.properties, info.dependencies, info.targets);
        }
    }

    @Nonnull
    private MSBuildProcessProvider findBuildProcessProvider(@Nonnull MSBuildSolutionModuleExtension<?> solutionModuleExtension) {
        for (MSBuildProcessProvider msBuildProcessProvider : Application.get().getExtensionList(MSBuildProcessProvider.class)) {
            if (Objects.equals(solutionModuleExtension.getProcessProviderId(), msBuildProcessProvider.getId())) {
                return msBuildProcessProvider;
            }
        }

        throw new IllegalArgumentException("MSBuild provider not found");
    }

    private static File prepareMSBuildBuider(@Nonnull Sdk sdk, @Nonnull MSBuildProcessProvider buildProcessProvider) throws IOException {
        VirtualFile homeDirectory = sdk.getHomeDirectory();

        int urlHashCode = Math.abs(homeDirectory.getPresentableUrl().hashCode());

        File msBuildRunnerDir = new File(ContainerPathManager.get().getSystemPath(), "MSBuild/msbuild-" + urlHashCode);

        int ver = -1;
        File versionFile = new File(msBuildRunnerDir, "version.txt");
        if (versionFile.exists()) {
            ver = Integer.parseInt(Files.readString(versionFile.toPath()));
        }

        int requiredVersion = VERSION + buildProcessProvider.getVersion();

        if (ver != requiredVersion) {
            FileUtil.delete(msBuildRunnerDir);
        }

        File originalExeFile = buildProcessProvider.getTargetFile();

        String fileName = originalExeFile.getName();

        File msBuildFile = new File(msBuildRunnerDir, fileName);
        if (msBuildFile.exists()) {
            return msBuildFile;
        }

        FileUtil.createParentDirs(msBuildRunnerDir);

        FileUtil.copy(originalExeFile, msBuildFile, FilePermissionCopier.BY_NIO2);

        File parentDir = originalExeFile.getParentFile();

        String[] additionalPaths = buildProcessProvider.getAdditionalCopyExtensions();
        for (String additionalPath : additionalPaths) {
            File attachFile = new File(parentDir, FileUtil.getNameWithoutExtension(originalExeFile) + additionalPath);
            if (attachFile.exists()) {
                File targetPdb = new File(msBuildRunnerDir, attachFile.getName());
                FileUtil.copy(attachFile, targetPdb, FilePermissionCopier.BY_NIO2);
            }
        }

        buildProcessProvider.doAdditionalCopy(originalExeFile, msBuildRunnerDir, sdk);

        FileUtil.writeToFile(versionFile, String.valueOf(requiredVersion));

        return msBuildFile;
    }

    private void fillGlobalProperties(Map<String, String> dictionary, String binDir, Sdk msBuildSdk, MSBuildProcessProvider buildProcessProvider) {
        // This causes build targets to behave how they should inside an IDE, instead of in a command-line process
        dictionary.put("BuildingInsideVisualStudio", "true");

        // We don't have host compilers in MD, and this is set to true by some of the MS targets
        // which causes it to always run the CoreCompile task if BuildingInsideVisualStudio is also
        // true, because the VS in-process compiler would take care of the deps tracking
        dictionary.put("UseHostCompilerIfAvailable", "false");

        MSBuildSolutionModuleExtension<?> solutionExtension = getSolutionExtension();
        if (solutionExtension == null) {
            return;
        }

        VirtualFile solutionFile = solutionExtension.getSolutionFile();

        if (solutionFile == null) {
            return;
        }

        dictionary.put("SolutionPath", solutionFile.getPresentableUrl());
        dictionary.put("SolutionName", solutionFile.getNameWithoutExtension());
        dictionary.put("SolutionFilename", solutionFile.getName());
        dictionary.put("SolutionDir", solutionFile.getParent().getPresentableUrl() + "/");

        buildProcessProvider.fillGlobalProperties(msBuildSdk, dictionary);
    }

    private void startServer(@Nonnull Sdk msBuildSdk, @Nonnull MSBuildProcessProvider provider) throws Exception {
        File exeFile = prepareMSBuildBuider(msBuildSdk, provider);

        GeneralCommandLine commandLine = provider.buildCommandLine(msBuildSdk, exeFile, myPort);

        ProcessHandler handler = ProcessHandlerBuilder.create(commandLine).silentReader().build();
        handler.addProcessListener(new ProcessListener() {
            @Override
            public void processTerminated(ProcessEvent processEvent) {
                myConnection = null;

                if (!mySocketResult.isProcessed()) {
                    mySocketResult.reject("Process terminated");
                }
            }

            @Override
            public void onTextAvailable(ProcessEvent processEvent, Key key) {
                LOG.info("> " + processEvent.getText().trim());
            }
        });
        handler.startNotify();
    }

    private void initializeServer() throws Exception {
        myPort = NetUtil.findAvailableSocketPort();

        myServerThread = new MSBuildServerThread(this, myPort, it -> mySocketResult.setDone(it));
    }

    @Nullable
    private MSBuildSolutionModuleExtension<?> getSolutionExtension() {
        return MSBuildSolutionModuleExtension.getSolutionModuleExtension(myProject);
    }

    public MSBuildLoggingSession newLoggingSession(@Nonnull LocalizeValue loggingGroup) {
        int id = myLoggers.incrementAndGet();
        MSBuildLoggingSession session = new MSBuildLoggingSession(id, myProject, loggingGroup);
        myLoggingSessions.put(id, session);
        return session;
    }

    public void acceptLogMessage(LogMessage logMessage) {
        MSBuildLoggingSession session = myLoggingSessions.get(logMessage.LoggerId);
        if (session != null) {
            session.acceptMessage(logMessage);
        }
    }

    @Override
    public void dispose() {
        if (myServerThread != null) {
            myServerThread.shutdown();
        }
    }
}
