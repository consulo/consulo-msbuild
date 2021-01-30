package consulo.msbuild.daemon.impl.step;

import consulo.msbuild.daemon.impl.MSBuildDaemonContext;
import consulo.msbuild.daemon.impl.message.model.RunProjectResponse;
import consulo.msbuild.solution.model.WProject;

import javax.annotation.Nonnull;

/**
 * @author VISTALL
 * @since 01/01/2021
 */
public class RestoreDependenciesStep extends BaseRunProjectStep
{
	public RestoreDependenciesStep(WProject wProject)
	{
		super(wProject, new String[0], new String[] {"Restore"});
	}

	@Nonnull
	@Override
	public String getProjectStepText()
	{
		return "Restoring ''{0}'' Dependencies";
	}

	@Override
	public void handleResponse(@Nonnull MSBuildDaemonContext context, @Nonnull RunProjectResponse runProjectResponse)
	{

	}
}
