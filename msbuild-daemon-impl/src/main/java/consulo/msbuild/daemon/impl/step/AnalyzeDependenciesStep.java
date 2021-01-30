package consulo.msbuild.daemon.impl.step;

import consulo.msbuild.MSBuildProcessProvider;
import consulo.msbuild.daemon.impl.MSBuildDaemonContext;
import consulo.msbuild.daemon.impl.message.model.MSBuildEvaluatedItem;
import consulo.msbuild.daemon.impl.message.model.MSBuildResult;
import consulo.msbuild.daemon.impl.message.model.RunProjectResponse;
import consulo.msbuild.solution.model.WProject;

import javax.annotation.Nonnull;

/**
 * @author VISTALL
 * @since 01/01/2021
 */
public class AnalyzeDependenciesStep extends BaseRunProjectStep
{
	public AnalyzeDependenciesStep(WProject wProject, MSBuildProcessProvider provider)
	{
		super(wProject, new String[]{
				"ReferencePath",
				"_DependenciesDesignTime"
		}, provider.getDependencyTargets());
	}

	@Nonnull
	@Override
	public String getProjectStepText()
	{
		return "Analyzing ''{0}'' Dependencies";
	}

	@Override
	public void handleResponse(@Nonnull MSBuildDaemonContext context, @Nonnull RunProjectResponse runProjectResponse)
	{
		MSBuildResult result = runProjectResponse.Result;

		MSBuildEvaluatedItem[] paths = result.items.get("ReferencePath");
		if(paths == null)
		{
			return;
		}

		context.updateProjectDependencies(myWProject, paths);

//		for(MSBuildEvaluatedItem item : paths)
//		{
//			String filename = item.Metadata.get("Filename");
//			String extension = item.Metadata.get("Extension");
//			String fullPath = item.Metadata.get("FullPath");
//
//			//System.out.println();
//		}
	}
}
