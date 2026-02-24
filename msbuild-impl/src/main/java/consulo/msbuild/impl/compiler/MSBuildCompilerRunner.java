package consulo.msbuild.impl.compiler;

import consulo.annotation.component.ExtensionImpl;
import consulo.application.progress.ProgressIndicator;
import consulo.application.progress.ProgressIndicatorProvider;
import consulo.build.ui.progress.BuildProgress;
import consulo.build.ui.progress.BuildProgressDescriptor;
import consulo.compiler.*;
import consulo.dataContext.DataContext;
import consulo.localize.LocalizeValue;
import consulo.module.extension.ModuleExtensionHelper;
import consulo.msbuild.daemon.impl.MSBuildDaemonContext;
import consulo.msbuild.daemon.impl.MSBuildDaemonService;
import consulo.msbuild.daemon.impl.message.model.MSBuildTargetResult;
import consulo.msbuild.daemon.impl.message.model.RunProjectResponse;
import consulo.msbuild.daemon.impl.step.DaemonStepQueue;
import consulo.msbuild.daemon.impl.step.InitializeProjectStep;
import consulo.msbuild.daemon.impl.step.RunTargetProjectStep;
import consulo.msbuild.icon.MSBuildIconGroup;
import consulo.msbuild.module.extension.MSBuildSolutionModuleExtension;
import consulo.msbuild.solution.model.WProject;
import consulo.virtualFileSystem.util.VirtualFileUtil;
import jakarta.annotation.Nonnull;
import jakarta.inject.Inject;

import java.io.File;

/**
 * @author VISTALL
 * @since 2018-02-06
 */
@ExtensionImpl
public class MSBuildCompilerRunner implements CompilerRunner {
    private static final YesResult YES = new YesResult(MSBuildIconGroup.msbuildbuild());

    private final ModuleExtensionHelper myModuleExtensionHelper;

    @Inject
    public MSBuildCompilerRunner(ModuleExtensionHelper moduleExtensionHelper) {
        myModuleExtensionHelper = moduleExtensionHelper;
    }

    @Nonnull
    @Override
    public LocalizeValue getName() {
        return LocalizeValue.localizeTODO("MSBuild");
    }

    @Nonnull
    @Override
    public Result checkAvailable(@Nonnull DataContext dataContext) {
        if (myModuleExtensionHelper.hasModuleExtension(MSBuildSolutionModuleExtension.class)) {
            return YES;
        }
        return NO;
    }


    @Override
    public boolean build(CompileDriver compileDriver,
                         CompileContextEx compileContext,
                         BuildProgress<BuildProgressDescriptor> buildProgress,
                         boolean isRebuild,
                         boolean forceCompile,
                         boolean onlyCheckStatus) throws ExitException {
        MSBuildSolutionModuleExtension<?> solutionExtension =
            MSBuildSolutionModuleExtension.getSolutionModuleExtension(compileContext.getProject());

        if (solutionExtension == null) {
            return false;
        }

        DaemonStepQueue steps = new DaemonStepQueue();

        for (WProject wProject : solutionExtension.getValidProjects()) {
            steps.join(new InitializeProjectStep(wProject));
        }

        for (WProject project : solutionExtension.getValidProjects()) {
            steps.join(new RunTargetProjectStep(project, "Build", false) {
                @Override
                public void handleResponse(@Nonnull MSBuildDaemonContext context, @Nonnull RunProjectResponse runProjectResponse) {
                    super.handleResponse(context, runProjectResponse);

                    if (runProjectResponse.Result == null || runProjectResponse.Result.errors == null) {
                        return;
                    }

                    for (MSBuildTargetResult error : runProjectResponse.Result.errors) {
                        CompilerMessageCategory category = CompilerMessageCategory.ERROR;
                        if (error.IsWarning) {
                            category = CompilerMessageCategory.WARNING;
                        }

                        String filePath = error.File;

                        String fileUrl = null;
                        if (filePath != null) {
                            File file = new File(filePath);
                            if (file.exists()) {
                                fileUrl = VirtualFileUtil.pathToUrl(filePath);
                            }
                        }
                        compileContext.addMessage(category, error.Message, fileUrl, error.LineNumber, error.ColumnNumber);
                    }
                }
            });
        }

        MSBuildDaemonService daemonService = MSBuildDaemonService.getInstance(compileContext.getProject());

        ProgressIndicator indicator = ProgressIndicatorProvider.getGlobalProgressIndicator();

        daemonService.runSteps(steps, indicator, LocalizeValue.localizeTODO("Build")).getResultSync();

        return true;
    }
}
