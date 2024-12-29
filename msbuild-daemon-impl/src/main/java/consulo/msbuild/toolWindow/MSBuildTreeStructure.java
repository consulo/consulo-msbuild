package consulo.msbuild.toolWindow;

import consulo.msbuild.toolWindow.nodes.RootNodeDescriptor;
import consulo.project.Project;
import consulo.project.ui.view.tree.AbstractTreeStructureBase;
import consulo.project.ui.view.tree.TreeStructureProvider;
import consulo.util.lang.ObjectUtil;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import java.util.List;

/**
 * @author VISTALL
 * @since 01/01/2021
 */
public class MSBuildTreeStructure extends AbstractTreeStructureBase
{
	private RootNodeDescriptor myRootNodeDescriptor;

	public MSBuildTreeStructure(Project project)
	{
		super(project);

		myRootNodeDescriptor = new RootNodeDescriptor(project, ObjectUtil.NULL);
	}

	@Nonnull
	@Override
	public Object getRootElement()
	{
		return myRootNodeDescriptor;
	}

	@Nullable
	@Override
	public List<TreeStructureProvider> getProviders()
	{
		return null;
	}

	@Override
	public void commit()
	{

	}

	@Override
	public boolean hasSomethingToCommit()
	{
		return false;
	}
}
