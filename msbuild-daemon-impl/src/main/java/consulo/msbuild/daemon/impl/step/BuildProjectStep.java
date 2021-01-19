package consulo.msbuild.daemon.impl.step;

import consulo.msbuild.daemon.impl.MSBuildDaemonContext;
import consulo.msbuild.daemon.impl.message.model.MSBuildVerbosity;
import consulo.msbuild.daemon.impl.message.model.RunProjectRequest;
import consulo.msbuild.daemon.impl.message.model.RunProjectResponse;
import consulo.msbuild.solution.model.WProject;

import javax.annotation.Nonnull;

/**
 * @author VISTALL
 * @since 19/01/2021
 */
public class BuildProjectStep extends BaseRunProjectStep
{
	public BuildProjectStep(WProject wProject)
	{
		super(wProject, new String[0], new String[] {"Build"});
	}

	@Nonnull
	@Override
	public String getProjectStepText()
	{
		return "Building ''{0}'' Project";
	}

	@Nonnull
	@Override
	public RunProjectRequest prepareRequest(@Nonnull MSBuildDaemonContext context)
	{
		RunProjectRequest request = super.prepareRequest(context);
		request.Verbosity = MSBuildVerbosity.Normal;
		return request;
	}

	@Override
	public void handleResponse(@Nonnull MSBuildDaemonContext context, @Nonnull RunProjectResponse runProjectResponse)
	{

	}
}
