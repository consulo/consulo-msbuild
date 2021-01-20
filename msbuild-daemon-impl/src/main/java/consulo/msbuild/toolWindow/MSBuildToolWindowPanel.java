package consulo.msbuild.toolWindow;

import com.intellij.openapi.actionSystem.ActionGroup;
import com.intellij.openapi.actionSystem.ActionManager;
import com.intellij.openapi.actionSystem.ActionToolbar;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.SimpleToolWindowPanel;
import com.intellij.ui.DoubleClickListener;
import com.intellij.ui.JBColor;
import com.intellij.ui.ScrollPaneFactory;
import com.intellij.ui.tree.AsyncTreeModel;
import com.intellij.ui.tree.StructureTreeModel;
import com.intellij.ui.treeStructure.Tree;
import com.intellij.util.ui.JBUI;
import com.intellij.util.ui.tree.TreeUtil;
import consulo.disposer.Disposable;
import consulo.msbuild.daemon.impl.MSBuildDaemonService;
import consulo.msbuild.toolWindow.actions.FilterTargetsAction;
import consulo.msbuild.toolWindow.actions.RefreshProjectsAction;
import consulo.msbuild.toolWindow.nodes.TargetNodeDescriptor;
import consulo.util.dataholder.Key;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.swing.*;
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
		//tree.setRootVisible(false);

		setContent(ScrollPaneFactory.createScrollPane(myTree, true));

		ActionGroup.Builder builder = ActionGroup.newImmutableBuilder();
		builder.add(new RefreshProjectsAction());
		builder.add(new FilterTargetsAction());

		ActionToolbar toolbar = ActionManager.getInstance().createActionToolbar("MSBuildToolWindow", builder.build(), true);
		toolbar.setTargetComponent(this);

		JComponent component = toolbar.getComponent();
		component.setBorder(JBUI.Borders.customLine(JBColor.border(), 0, 0, 1, 0));
		setToolbar(component);
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

	@Override
	public void dispose()
	{

	}
}
