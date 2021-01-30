package consulo.msbuild.daemon.impl.step;

import consulo.msbuild.daemon.impl.MSBuildDaemonContext;
import consulo.msbuild.daemon.impl.message.model.GetPropertiesRequest;
import consulo.msbuild.daemon.impl.message.model.GetPropertiesResponse;
import consulo.msbuild.daemon.impl.message.model.Property;
import consulo.msbuild.solution.model.WProject;

import javax.annotation.Nonnull;
import java.util.Map;

/**
 * @author VISTALL
 * @since 16/01/2021
 */
public class AnalyzePropertiesStep extends PerProjectDaemonStep<GetPropertiesRequest, GetPropertiesResponse>
{
	public AnalyzePropertiesStep(WProject wProject)
	{
		super(wProject);
	}

	@Nonnull
	@Override
	public String getProjectStepText()
	{
		return "Analyzing ''{0}'' Properties";
	}

	@Nonnull
	@Override
	public GetPropertiesRequest prepareRequest(@Nonnull MSBuildDaemonContext context)
	{
		GetPropertiesRequest request = new GetPropertiesRequest();
		request.Configurations = buildProjectConfigurationInfo(context);
		request.ProjectId = context.getRegisteredProjectId(myWProject);
		return request;
	}

	@Override
	public void handleResponse(@Nonnull MSBuildDaemonContext context, @Nonnull GetPropertiesResponse response)
	{
		Map<String, String> map = context.getProperties(myWProject);

		for(Property property : response.Properties)
		{
			map.put(property.Key, property.Value);
		}
	}
}
