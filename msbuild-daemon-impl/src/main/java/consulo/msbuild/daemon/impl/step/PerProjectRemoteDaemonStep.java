package consulo.msbuild.daemon.impl.step;

import consulo.component.util.localize.BundleBase;
import consulo.localize.LocalizeValue;
import consulo.msbuild.daemon.impl.MSBuildDaemonContext;
import consulo.msbuild.daemon.impl.message.DaemonMessage;
import consulo.msbuild.daemon.impl.message.model.DataObject;
import consulo.msbuild.daemon.impl.message.model.ProjectConfigurationInfo;
import consulo.msbuild.solution.model.WProject;

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
		ProjectConfigurationInfo conf = new ProjectConfigurationInfo();
		conf.Configuration = "Debug";
		conf.Platform = "AnyCPU";
		conf.ProjectFile = myWProject.getVirtualFile().getPresentableUrl();
		conf.ProjectGuid = myWProject.getId();

		return new ProjectConfigurationInfo[]{conf};
	}
}
