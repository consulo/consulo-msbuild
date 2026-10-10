package consulo.msbuild.daemon.impl.step;

import consulo.application.Application;
import consulo.component.util.localize.BundleBase;
import consulo.localize.LocalizeValue;
import consulo.msbuild.MSBuildProjectConfiguration;
import consulo.msbuild.MSBuildProjectFile;
import consulo.msbuild.daemon.impl.MSBuildDaemonContext;
import consulo.msbuild.daemon.impl.message.DaemonMessage;
import consulo.msbuild.daemon.impl.message.model.DataObject;
import consulo.msbuild.daemon.impl.message.model.ProjectConfigurationInfo;
import consulo.msbuild.solution.model.WProject;
import consulo.virtualFileSystem.VirtualFile;
import jakarta.annotation.Nonnull;

import java.util.ArrayList;
import java.util.List;

/**
 * @author VISTALL
 * @since 01/01/2021
 */
public abstract class PerProjectRemoteDaemonStep<Request extends DaemonMessage<Response>, Response extends DataObject> extends RemoteDaemonStep<Request, Response> {
    protected WProject myWProject;

    protected PerProjectRemoteDaemonStep(WProject wProject) {
        myWProject = wProject;
    }

    @Nonnull
    @Override
    public LocalizeValue getStepText() {
        return LocalizeValue.localizeTODO(BundleBase.format(getProjectStepText(), myWProject.getName()));
    }

    @Nonnull
    public abstract String getProjectStepText();

    @Nonnull
    protected ProjectConfigurationInfo[] buildProjectConfigurationInfo(@Nonnull MSBuildDaemonContext context) {
        List<ProjectConfigurationInfo> infos = new ArrayList<>();
        infos.add(buildProjectConfigurationInfo(myWProject));

        for (MSBuildDaemonContext.PerProjectInfo info : context.getInfos()) {
            WProject wProject = info.wProject;
            if (wProject.getId().equals(myWProject.getId()) || wProject.getVirtualFile() == null) {
                continue;
            }

            infos.add(buildProjectConfigurationInfo(wProject));
        }

        return infos.toArray(ProjectConfigurationInfo[]::new);
    }

    @Nonnull
    private static ProjectConfigurationInfo buildProjectConfigurationInfo(@Nonnull WProject wProject) {
        VirtualFile projectFile = wProject.getVirtualFile();
        MSBuildProjectFile projectFileKind = MSBuildProjectFile.findByExtension(Application.get(), projectFile.getExtension());

        MSBuildProjectConfiguration configuration = projectFileKind != null
            ? projectFileKind.getProjectConfiguration(projectFile)
            : new MSBuildProjectConfiguration("Debug", "AnyCPU");

        ProjectConfigurationInfo conf = new ProjectConfigurationInfo();
        conf.Configuration = configuration.configuration();
        conf.Platform = configuration.platform();
        conf.ProjectFile = projectFile.getPresentableUrl();
        conf.ProjectGuid = wProject.getId();
        return conf;
    }
}
