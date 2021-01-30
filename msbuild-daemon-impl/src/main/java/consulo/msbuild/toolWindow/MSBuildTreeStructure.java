package consulo.msbuild.toolWindow;

import com.intellij.ide.projectView.TreeStructureProvider;
import com.intellij.ide.util.treeView.AbstractTreeStructureBase;
import com.intellij.openapi.project.Project;
import consulo.msbuild.toolWindow.nodes.RootNodeDescriptor;
import consulo.util.lang.ObjectUtil;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
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
