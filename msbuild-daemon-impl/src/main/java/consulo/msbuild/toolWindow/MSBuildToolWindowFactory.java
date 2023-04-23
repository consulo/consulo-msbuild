package consulo.msbuild.toolWindow;

import consulo.annotation.component.ExtensionImpl;
import consulo.application.dumb.DumbAware;
import consulo.localize.LocalizeValue;
import consulo.msbuild.icon.MSBuildIconGroup;
import consulo.msbuild.module.extension.MSBuildSolutionModuleExtension;
import consulo.msbuild.toolWindow.actions.FilterTargetsAction;
import consulo.msbuild.toolWindow.actions.RefreshProjectsAction;
import consulo.project.Project;
import consulo.project.ui.wm.ToolWindowFactory;
import consulo.ui.annotation.RequiredUIAccess;
import consulo.ui.ex.action.AnSeparator;
import consulo.ui.ex.content.Content;
import consulo.ui.ex.content.ContentManager;
import consulo.ui.ex.toolWindow.ToolWindow;
import consulo.ui.ex.toolWindow.ToolWindowAnchor;
import consulo.ui.image.Image;

import javax.annotation.Nonnull;

/**
 * @author VISTALL
 * @since 01/01/2021
 */
@ExtensionImpl
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

		toolWindow.setTitleActions(new RefreshProjectsAction(), AnSeparator.create(), new FilterTargetsAction());
	}

	@Nonnull
	@Override
	public String getId()
	{
		return "MSBuild";
	}

	@Nonnull
	@Override
	public ToolWindowAnchor getAnchor()
	{
		return ToolWindowAnchor.RIGHT;
	}

	@Nonnull
	@Override
	public Image getIcon()
	{
		return MSBuildIconGroup.msbuildtoolwindow();
	}

	@Nonnull
	@Override
	public LocalizeValue getDisplayName()
	{
		return LocalizeValue.of("MSBuild");
	}

	@Override
	public boolean canCloseContents()
	{
		return false;
	}

	@Override
	public boolean validate(@Nonnull Project project)
	{
		return MSBuildSolutionModuleExtension.getSolutionModuleExtension(project) != null;
	}
}
