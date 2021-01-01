package consulo.msbuild.toolWindow;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.Condition;
import consulo.msbuild.MSBuildSolutionManager;

/**
 * @author VISTALL
 * @since 01/01/2021
 */
public class MSBuildToolWindowCondition implements Condition<Project>
{
	@Override
	public boolean value(Project project)
	{
		return MSBuildSolutionManager.getInstance(project).isEnabled();
	}
}
