package consulo.msbuild.impl.compiler;

import consulo.annotation.component.ExtensionImpl;
import consulo.application.progress.ProgressIndicator;
import consulo.application.progress.ProgressIndicatorProvider;
import consulo.compiler.CompileContextEx;
import consulo.compiler.CompileDriver;
import consulo.compiler.CompilerRunner;
import consulo.compiler.ExitException;
import consulo.localize.LocalizeValue;
import consulo.module.extension.ModuleExtensionHelper;
import consulo.msbuild.daemon.impl.MSBuildDaemonService;
import consulo.msbuild.daemon.impl.step.DaemonStepQueue;
import consulo.msbuild.daemon.impl.step.InitializeProjectStep;
import consulo.msbuild.daemon.impl.step.RunTargetProjectStep;
import consulo.msbuild.icon.MSBuildIconGroup;
import consulo.msbuild.module.extension.MSBuildSolutionModuleExtension;
import consulo.msbuild.solution.model.WProject;
import consulo.project.Project;
import consulo.ui.image.Image;
import jakarta.annotation.Nonnull;
import jakarta.inject.Inject;

/**
 * @author VISTALL
 * @since 2018-02-06
 */
@ExtensionImpl
public class MSBuildCompilerRunner implements CompilerRunner {
    private final Project myProject;

    @Inject
    public MSBuildCompilerRunner(Project project) {
        myProject = project;
    }

    @Nonnull
    @Override
    public LocalizeValue getName() {
        return LocalizeValue.localizeTODO("MSBuild");
    }

    @Nonnull
    @Override
    public Image getBuildIcon() {
        return MSBuildIconGroup.msbuildbuild();
    }

    @Override
    public boolean isAvailable() {
        return ModuleExtensionHelper.getInstance(myProject).hasModuleExtension(MSBuildSolutionModuleExtension.class);
    }

    @Override
    public boolean build(CompileDriver compileDriver, CompileContextEx context, boolean isRebuild, boolean forceCompile, boolean onlyCheckStatus) throws ExitException {
        MSBuildSolutionModuleExtension<?> solutionExtension = MSBuildSolutionModuleExtension.getSolutionModuleExtension(context.getProject());

        if (solutionExtension == null) {
            return false;
        }

        DaemonStepQueue steps = new DaemonStepQueue();

        for (WProject wProject : solutionExtension.getValidProjects()) {
            steps.join(new InitializeProjectStep(wProject));
        }

        for (WProject project : solutionExtension.getValidProjects()) {
            steps.join(new RunTargetProjectStep(project, "Build", false));
        }

        MSBuildDaemonService daemonService = MSBuildDaemonService.getInstance(context.getProject());

        ProgressIndicator indicator = ProgressIndicatorProvider.getGlobalProgressIndicator();

        daemonService.runSteps(steps, indicator, LocalizeValue.localizeTODO("Build")).getResultSync();

        return true;
    }
}
