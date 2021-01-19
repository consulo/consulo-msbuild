package consulo.msbuild.toolWindow;

import com.intellij.icons.AllIcons;
import com.intellij.openapi.actionSystem.ActionGroup;
import com.intellij.openapi.actionSystem.ActionManager;
import com.intellij.openapi.actionSystem.ActionToolbar;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.DumbAwareAction;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.SimpleToolWindowPanel;
import com.intellij.ui.JBColor;
import com.intellij.ui.ScrollPaneFactory;
import com.intellij.ui.tree.AsyncTreeModel;
import com.intellij.ui.tree.StructureTreeModel;
import com.intellij.ui.treeStructure.Tree;
import com.intellij.util.ui.JBUI;
import consulo.disposer.Disposable;
import consulo.msbuild.daemon.impl.MSBuildDaemonService;
import consulo.msbuild.toolWindow.actions.RefreshProjectsAction;
import consulo.ui.annotation.RequiredUIAccess;

import javax.annotation.Nonnull;
import javax.swing.*;

/**
 * @author VISTALL
 * @since 01/01/2021
 */
public class MSBuildToolWindowPanel extends SimpleToolWindowPanel implements Disposable
{
	public MSBuildToolWindowPanel(@Nonnull Project project)
	{
		super(true, true);

		Tree tree = new Tree(new AsyncTreeModel(new StructureTreeModel<>(new MSBuildTreeStructure(project), this), this));
		tree.setRootVisible(false);

		setContent(ScrollPaneFactory.createScrollPane(tree, true));

		ActionGroup.Builder builder = ActionGroup.newImmutableBuilder();
		builder.add(new RefreshProjectsAction());
		builder.add(new DumbAwareAction("Build", null, AllIcons.Actions.Compile)
		{

			@RequiredUIAccess
			@Override
			public void actionPerformed(@Nonnull AnActionEvent e)
			{
				MSBuildDaemonService.getInstance(e.getProject()).build();
			}
		});

		ActionToolbar toolbar = ActionManager.getInstance().createActionToolbar("MSBuildToolWindow", builder.build(), true);

		JComponent component = toolbar.getComponent();
		component.setBorder(JBUI.Borders.customLine(JBColor.border(), 0, 0, 1, 0));
		setToolbar(component);
	}

	@Override
	public void dispose()
	{

	}
}
