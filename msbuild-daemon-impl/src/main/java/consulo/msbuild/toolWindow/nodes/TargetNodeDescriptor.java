package consulo.msbuild.toolWindow.nodes;

import consulo.annotation.access.RequiredReadAction;
import consulo.platform.base.icon.PlatformIconGroup;
import consulo.project.Project;
import consulo.project.ui.view.tree.AbstractTreeNode;
import consulo.ui.ex.SimpleTextAttributes;
import consulo.ui.ex.tree.PresentationData;

import jakarta.annotation.Nonnull;
import java.util.Collection;
import java.util.List;

/**
 * @author VISTALL
 * @since 19/01/2021
 */
public class TargetNodeDescriptor extends AbstractTreeNode<String>
{
	public TargetNodeDescriptor(Project project, @Nonnull String value)
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
	public boolean isAlwaysLeaf()
	{
		return true;
	}

	@Override
	protected void update(PresentationData presentationData)
	{
		presentationData.addText(getValue(), SimpleTextAttributes.REGULAR_ATTRIBUTES);
		presentationData.setIcon(PlatformIconGroup.nodesTask());
	}
}
