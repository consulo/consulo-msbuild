package consulo.msbuild.daemon.impl.step;

import consulo.application.Application;
import consulo.component.util.localize.BundleBase;
import consulo.localize.LocalizeValue;
import consulo.msbuild.daemon.impl.MSBuildDaemonContext;
import consulo.msbuild.daemon.impl.message.DaemonMessage;
import consulo.msbuild.daemon.impl.message.model.DataObject;
import consulo.msbuild.daemon.impl.message.model.ProjectConfigurationInfo;
import consulo.msbuild.MSBuildProjectFile;
import consulo.msbuild.solution.model.WProject;
import consulo.virtualFileSystem.VirtualFile;

import jakarta.annotation.Nonnull;

/**
 * @author VISTALL
 * @since 01/01/2021
 */
public abstract class PerProjectRemoteDaemonStep<Request extends DaemonMessage<Response>, Response extends DataObject> extends RemoteDaemonStep<Request, Response>
{
	protected WProject myWProject;

	protected PerProjectRemoteDaemonStep(WProject wProject)
	{
		myWProject = wProject;
	}

	@Nonnull
	@Override
	public LocalizeValue getStepText()
	{
		return LocalizeValue.localizeTODO(BundleBase.format(getProjectStepText(), myWProject.getName()));
	}

	@Nonnull
	public abstract String getProjectStepText();

	@Nonnull
	protected ProjectConfigurationInfo[] buildProjectConfigurationInfo(@Nonnull MSBuildDaemonContext context)
	{
		VirtualFile projectFile = myWProject.getVirtualFile();
		MSBuildProjectFile projectFileKind = MSBuildProjectFile.findByExtension(Application.get(), projectFile.getExtension());

		ProjectConfigurationInfo conf = new ProjectConfigurationInfo();
		conf.Configuration = projectFileKind != null ? projectFileKind.getConfiguration() : "Debug";
		conf.Platform = projectFileKind != null ? projectFileKind.getPlatform() : "AnyCPU";
		conf.ProjectFile = projectFile.getPresentableUrl();
		conf.ProjectGuid = myWProject.getId();

		return new ProjectConfigurationInfo[]{conf};
	}
}
