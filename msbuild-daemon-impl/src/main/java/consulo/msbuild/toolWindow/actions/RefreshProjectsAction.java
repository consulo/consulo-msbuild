package consulo.msbuild.toolWindow.actions;

import consulo.msbuild.daemon.impl.MSBuildDaemonService;
import consulo.platform.base.icon.PlatformIconGroup;
import consulo.project.Project;
import consulo.ui.annotation.RequiredUIAccess;
import consulo.ui.ex.action.AnActionEvent;
import consulo.ui.ex.action.DumbAwareAction;

import jakarta.annotation.Nonnull;

/**
 * @author VISTALL
 * @since 01/01/2021
 */
public class RefreshProjectsAction extends DumbAwareAction
{
	public RefreshProjectsAction()
	{
		super("Refresh Projects", null, PlatformIconGroup.actionsRefresh());
	}

	@RequiredUIAccess
	@Override
	public void actionPerformed(@Nonnull AnActionEvent e)
	{
		MSBuildDaemonService.getInstance(e.getData(Project.KEY)).forceUpdate();
	}

	@RequiredUIAccess
	@Override
	public void update(@Nonnull AnActionEvent e)
	{
		Project project = e.getData(Project.KEY);
		if(project == null)
		{
			return;
		}

		MSBuildDaemonService buildDaemonService = MSBuildDaemonService.getInstance(project);
		e.getPresentation().setEnabled(!buildDaemonService.isBusy());
	}
}
