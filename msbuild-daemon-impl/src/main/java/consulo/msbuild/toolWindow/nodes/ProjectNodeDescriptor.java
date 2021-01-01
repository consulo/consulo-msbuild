package consulo.msbuild.toolWindow.nodes;

import com.intellij.ide.projectView.PresentationData;
import com.intellij.ide.util.treeView.AbstractTreeNode;
import com.intellij.openapi.project.Project;
import com.intellij.ui.SimpleTextAttributes;
import consulo.annotation.access.RequiredReadAction;
import consulo.msbuild.api.icon.MSBuildApiIconGroup;
import consulo.msbuild.solution.model.WProject;

import javax.annotation.Nonnull;
import java.util.Collection;
import java.util.List;

/**
 * @author VISTALL
 * @since 01/01/2021
 */
public class ProjectNodeDescriptor extends AbstractTreeNode<WProject>
{
	public ProjectNodeDescriptor(Project project, @Nonnull WProject value)
	{
		super(project, value);
	}

	@RequiredReadAction
	@Nonnull
	@Override
	public Collection<? extends AbstractTreeNode> getChildren()
	{
		return List.of();
	}

	@Override
	protected void update(PresentationData presentation)
	{
		presentation.addText(getValue().getName(), SimpleTextAttributes.REGULAR_BOLD_ATTRIBUTES);
		presentation.setIcon(MSBuildApiIconGroup.msbuild());
	}
}
