package consulo.msbuild.toolWindow.actions;

import consulo.application.dumb.DumbAware;
import consulo.localize.LocalizeValue;
import consulo.msbuild.toolWindow.MSBuildToolWindowKeys;
import consulo.platform.base.icon.PlatformIconGroup;
import consulo.project.Project;
import consulo.project.ProjectPropertiesComponent;
import consulo.ui.ex.action.AnActionEvent;
import consulo.ui.ex.action.ToggleAction;
import consulo.ui.ex.awt.tree.StructureTreeModel;

import javax.annotation.Nonnull;

/**
 * @author VISTALL
 * @since 20/01/2021
 */
public class FilterTargetsAction extends ToggleAction implements DumbAware
{
	private static final String KEY = "msbuild.filter.action";

	public static boolean isFiltered(@Nonnull Project project)
	{
		return ProjectPropertiesComponent.getInstance(project).getBoolean(KEY, true);
	}

	public FilterTargetsAction()
	{
		super(LocalizeValue.localizeTODO("Show All Targets"), LocalizeValue.empty(), PlatformIconGroup.generalFilter());
	}

	@Override
	public boolean isSelected(@Nonnull AnActionEvent e)
	{
		Project project = e.getData(Project.KEY);
		return project != null && isFiltered(project);
	}

	@Override
	public void setSelected(@Nonnull AnActionEvent e, boolean state)
	{
		ProjectPropertiesComponent.getInstance(e.getData(Project.KEY)).setValue(KEY, state, true);

		StructureTreeModel<?> model = e.getRequiredData(MSBuildToolWindowKeys.TREE_STRUCTURE);

		model.invalidate();
	}
}
