package consulo.msbuild.daemon.impl.step;

import consulo.msbuild.daemon.impl.MSBuildDaemonContext;
import consulo.msbuild.daemon.impl.message.model.GetProjectItemsRequest;
import consulo.msbuild.daemon.impl.message.model.GetProjectItemsResponse;
import consulo.msbuild.daemon.impl.message.model.ProjectItem;
import consulo.msbuild.solution.model.WProject;

import javax.annotation.Nonnull;

/**
 * @author VISTALL
 * @since 01/01/2021
 *
 * Project capacities
 *
 * https://github.com/microsoft/VSProjectSystem/blob/master/doc/overview/project_capabilities.md
 */
public class AnalyzeProjectItemsStep extends PerProjectDaemonStep<GetProjectItemsRequest, GetProjectItemsResponse>
{
	public AnalyzeProjectItemsStep(WProject wProject)
	{
		super(wProject);
	}

	@Nonnull
	@Override
	public String getProjectStepText()
	{
		return "Analyzing ''{0}'' Project Items";
	}

	@Nonnull
	@Override
	public GetProjectItemsRequest prepareRequest(@Nonnull MSBuildDaemonContext context)
	{
		GetProjectItemsRequest request = new GetProjectItemsRequest();
		request.Configurations = buildProjectConfigurationInfo(context);
		request.ProjectId = context.getRegisteredProjectId(myWProject);
		return request;
	}

	@Override
	public void handleResponse(@Nonnull MSBuildDaemonContext context, @Nonnull GetProjectItemsResponse getProjectItemsResponse)
	{
		if(getProjectItemsResponse.Items == null)
		{
			return;
		}

		context.updateProjectItems(myWProject, getProjectItemsResponse.Items);

		for(ProjectItem item : getProjectItemsResponse.Items)
		{
			System.out.println(item.ItemType + " " + item.EvaluatedInclude);
		}
	}
}
