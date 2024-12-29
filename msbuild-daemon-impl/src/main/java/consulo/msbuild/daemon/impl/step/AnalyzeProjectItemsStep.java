package consulo.msbuild.daemon.impl.step;

import consulo.msbuild.daemon.impl.MSBuildDaemonContext;
import consulo.msbuild.daemon.impl.message.model.MSBuildEvaluatedItem;
import consulo.msbuild.daemon.impl.message.model.RunProjectResponse;
import consulo.msbuild.solution.model.WProject;

import jakarta.annotation.Nonnull;

/**
 * @author VISTALL
 * @since 20/01/2021
 *
 * Replacement of AnalyzeOldProjectItemsStep - it's load also metadata, which required for bulding solution view
 */
public class AnalyzeProjectItemsStep extends BaseRunProjectStep
{
	public static final String[] ITEMS = {"None", "Compile", "EmbeddedResource", "Resource", "Item"};

	public AnalyzeProjectItemsStep(WProject wProject)
	{
		super(wProject, ITEMS, new String[]{"_GenerateRestoreProjectSpec"});
	}

	@Nonnull
	@Override
	public String getProjectStepText()
	{
		return "Analyzing ''{0}'' Project Items";
	}

	@Override
	public void handleResponse(@Nonnull MSBuildDaemonContext context, @Nonnull RunProjectResponse runProjectResponse)
	{
		for(String item : ITEMS)
		{
			MSBuildEvaluatedItem[] items = runProjectResponse.Result.items.get(item);
			if(items == null)
			{
				continue;
			}

			context.addProjectItems(myWProject, items);
		}
	}
}
