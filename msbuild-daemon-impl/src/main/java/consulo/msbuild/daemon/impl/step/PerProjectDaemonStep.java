package consulo.msbuild.daemon.impl.step;

import com.intellij.BundleBase;
import consulo.msbuild.daemon.impl.MSBuildDaemonContext;
import consulo.msbuild.daemon.impl.message.DaemonMessage;
import consulo.msbuild.daemon.impl.message.model.DataObject;
import consulo.msbuild.daemon.impl.message.model.ProjectConfigurationInfo;
import consulo.msbuild.solution.model.WProject;

import javax.annotation.Nonnull;

/**
 * @author VISTALL
 * @since 01/01/2021
 */
public abstract class PerProjectDaemonStep<Request extends DaemonMessage<Response>, Response extends DataObject> extends DaemonStep<Request, Response>
{
	protected WProject myWProject;

	protected PerProjectDaemonStep(WProject wProject)
	{
		myWProject = wProject;
	}

	@Override
	public String getStepText()
	{
		return BundleBase.format(getProjectStepText(), myWProject.getName());
	}

	@Nonnull
	public abstract String getProjectStepText();

	@Nonnull
	protected ProjectConfigurationInfo[] buildProjectConfigurationInfo(@Nonnull MSBuildDaemonContext context)
	{
		ProjectConfigurationInfo conf = new ProjectConfigurationInfo();
		conf.Configuration = "Debug";
		conf.Platform = "Any CPU";
		conf.ProjectFile = myWProject.getVirtualFile().getPresentableUrl();
		conf.ProjectGuid = myWProject.getId();

		return new ProjectConfigurationInfo[]{conf};
	}
}
