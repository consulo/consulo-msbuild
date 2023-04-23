package consulo.msbuild.daemon.impl.step;

import consulo.msbuild.daemon.impl.MSBuildDaemonContext;
import consulo.msbuild.daemon.impl.message.model.LoadProjectRequest;
import consulo.msbuild.daemon.impl.message.model.LoadProjectResponse;
import consulo.msbuild.solution.model.WProject;

import javax.annotation.Nonnull;

/**
 * @author VISTALL
 * @since 01/01/2021
 */
public class InitializeProjectStep extends PerProjectRemoteDaemonStep<LoadProjectRequest, LoadProjectResponse>
{
	public InitializeProjectStep(WProject wProject)
	{
		super(wProject);
	}

	@Nonnull
	@Override
	public String getProjectStepText()
	{
		return "Initialize ''{0}'' Project";
	}

	@Nonnull
	@Override
	public LoadProjectRequest prepareRequest(@Nonnull MSBuildDaemonContext context)
	{
		LoadProjectRequest request = new LoadProjectRequest();
		request.ProjectFile = myWProject.getVirtualFile().getPresentableUrl();
		return request;
	}

	@Override
	public void handleResponse(@Nonnull MSBuildDaemonContext context, @Nonnull LoadProjectResponse projectResponse)
	{
		context.registerProject(myWProject, projectResponse.ProjectId);
	}
}
