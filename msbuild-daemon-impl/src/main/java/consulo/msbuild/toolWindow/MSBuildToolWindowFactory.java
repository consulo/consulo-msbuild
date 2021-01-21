package consulo.msbuild.toolWindow;

import com.intellij.openapi.actionSystem.AnSeparator;
import com.intellij.openapi.project.DumbAware;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowFactory;
import com.intellij.openapi.wm.ex.ToolWindowEx;
import com.intellij.ui.content.Content;
import com.intellij.ui.content.ContentManager;
import consulo.msbuild.toolWindow.actions.FilterTargetsAction;
import consulo.msbuild.toolWindow.actions.RefreshProjectsAction;
import consulo.ui.annotation.RequiredUIAccess;

import javax.annotation.Nonnull;

/**
 * @author VISTALL
 * @since 01/01/2021
 */
public class MSBuildToolWindowFactory implements ToolWindowFactory, DumbAware
{
	@RequiredUIAccess
	@Override
	public void createToolWindowContent(@Nonnull Project project, @Nonnull ToolWindow toolWindow)
	{
		ContentManager contentManager = toolWindow.getContentManager();

		MSBuildToolWindowPanel panel = new MSBuildToolWindowPanel(project);

		Content content = contentManager.getFactory().createContent(panel.getComponent(), null, true);
		content.setDisposer(panel);
		contentManager.addContent(content);

		((ToolWindowEx)toolWindow).setTitleActions(new RefreshProjectsAction(), AnSeparator.create(), new FilterTargetsAction());
	}
}
