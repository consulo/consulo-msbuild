package consulo.msbuild.toolWindow;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.SimpleToolWindowPanel;
import com.intellij.ui.DoubleClickListener;
import com.intellij.ui.ScrollPaneFactory;
import com.intellij.ui.tree.AsyncTreeModel;
import com.intellij.ui.tree.StructureTreeModel;
import com.intellij.ui.treeStructure.Tree;
import com.intellij.util.ui.tree.TreeUtil;
import consulo.disposer.Disposable;
import consulo.msbuild.MSBuildProjectListener;
import consulo.msbuild.daemon.impl.MSBuildDaemonService;
import consulo.msbuild.toolWindow.nodes.TargetNodeDescriptor;
import consulo.util.dataholder.Key;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.swing.tree.TreePath;
import java.awt.event.MouseEvent;

/**
 * @author VISTALL
 * @since 01/01/2021
 */
public class MSBuildToolWindowPanel extends SimpleToolWindowPanel implements Disposable
{
	private final Tree myTree;
	private final StructureTreeModel<MSBuildTreeStructure> myModel;

	public MSBuildToolWindowPanel(@Nonnull Project project)
	{
		super(true, true);

		myModel = new StructureTreeModel<>(new MSBuildTreeStructure(project), this);
		myTree = new Tree(new AsyncTreeModel(myModel, this));
		new DoubleClickListener()
		{
			@Override
			protected boolean onDoubleClick(MouseEvent mouseEvent)
			{
				TreePath selectionPath = myTree.getSelectionPath();
				if(selectionPath == null)
				{
					return false;
				}
				Object userObject = TreeUtil.getLastUserObject(selectionPath);
				if(userObject instanceof TargetNodeDescriptor)
				{
					MSBuildDaemonService.getInstance(project).runTarget(((TargetNodeDescriptor) userObject).getValue());
					return true;
				}
				return false;
			}
		}.installOn(myTree);

		setContent(ScrollPaneFactory.createScrollPane(myTree, true));

		project.getMessageBus().connect().subscribe(MSBuildProjectListener.TOPIC, () -> myModel.invalidate());
	}

	@Nullable
	@Override
	public Object getData(@Nonnull Key<?> dataId)
	{
		if(dataId == MSBuildToolWindowKeys.TREE_STRUCTURE)
		{
			return myModel;
		}
		return super.getData(dataId);
	}

	public StructureTreeModel<MSBuildTreeStructure> getModel()
	{
		return myModel;
	}

	@Override
	public void dispose()
	{

	}
}
