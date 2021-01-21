package consulo.msbuild.toolWindow.actions;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.DumbAwareAction;
import com.intellij.openapi.project.Project;
import consulo.msbuild.daemon.impl.MSBuildDaemonService;
import consulo.platform.base.icon.PlatformIconGroup;
import consulo.ui.annotation.RequiredUIAccess;

import javax.annotation.Nonnull;

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
		MSBuildDaemonService.getInstance(e.getProject()).forceUpdate();
	}

	@RequiredUIAccess
	@Override
	public void update(@Nonnull AnActionEvent e)
	{
		Project project = e.getProject();
		if(project == null)
		{
			return;
		}

		MSBuildDaemonService buildDaemonService = MSBuildDaemonService.getInstance(e.getProject());
		e.getPresentation().setEnabled(!buildDaemonService.isBusy());
	}
}
