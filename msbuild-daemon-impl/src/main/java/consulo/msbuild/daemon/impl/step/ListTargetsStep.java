package consulo.msbuild.daemon.impl.step;

import consulo.msbuild.daemon.impl.MSBuildDaemonContext;
import consulo.msbuild.daemon.impl.message.model.GetTargetsRequest;
import consulo.msbuild.daemon.impl.message.model.GetTargetsResponse;
import consulo.msbuild.solution.model.WProject;

import jakarta.annotation.Nonnull;

/**
 * @author VISTALL
 * @since 01/01/2021
 */
public class ListTargetsStep extends PerProjectRemoteDaemonStep<GetTargetsRequest, GetTargetsResponse>
{
	public ListTargetsStep(WProject wProject)
	{
		super(wProject);
	}

	@Nonnull
	@Override
	public String getProjectStepText()
	{
		return "Getting ''{0}'' targets";
	}

	@Nonnull
	@Override
	public GetTargetsRequest prepareRequest(@Nonnull MSBuildDaemonContext context)
	{
		GetTargetsRequest request = new GetTargetsRequest();
		request.Configurations = buildProjectConfigurationInfo(context);
		request.ProjectId = context.getRegisteredProjectId(myWProject);
		return request;
	}

	@Override
	public void handleResponse(@Nonnull MSBuildDaemonContext context, @Nonnull GetTargetsResponse response)
	{
		context.updateProjectTargets(myWProject, response.Targets);
	}
}
