package consulo.msbuild.toolWindow.nodes;

import com.intellij.ide.projectView.PresentationData;
import com.intellij.ide.util.treeView.AbstractTreeNode;
import com.intellij.openapi.project.Project;
import consulo.annotation.access.RequiredReadAction;
import consulo.msbuild.MSBuildSolutionManager;
import consulo.msbuild.solution.model.WProject;

import javax.annotation.Nonnull;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author VISTALL
 * @since 01/01/2021
 */
public class RootNodeDescriptor extends AbstractTreeNode<Object>
{
	public RootNodeDescriptor(Project project, @Nonnull Object value)
	{
		super(project, value);
	}

	@RequiredReadAction
	@Nonnull
	@Override
	public Collection<? extends AbstractTreeNode> getChildren()
	{
		MSBuildSolutionManager solutionManager = MSBuildSolutionManager.getInstance(myProject);
		if(!solutionManager.isEnabled())
		{
			return List.of();
		}

		Collection<WProject> projects = solutionManager.getSolution().getProjects();

		return projects.stream().map(wProject -> new ProjectNodeDescriptor(myProject, wProject)).collect(Collectors.toList());
	}

	@Override
	protected void update(PresentationData presentationData)
	{

	}
}
