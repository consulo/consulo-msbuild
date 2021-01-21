package consulo.msbuild.toolWindow.actions;

import com.intellij.ide.util.PropertiesComponent;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.ToggleAction;
import com.intellij.openapi.project.DumbAware;
import com.intellij.openapi.project.Project;
import com.intellij.ui.tree.StructureTreeModel;
import consulo.localize.LocalizeValue;
import consulo.msbuild.toolWindow.MSBuildToolWindowKeys;
import consulo.platform.base.icon.PlatformIconGroup;

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
		return PropertiesComponent.getInstance(project).getBoolean(KEY, true);
	}

	public FilterTargetsAction()
	{
		super(LocalizeValue.localizeTODO("Show All Targets"), LocalizeValue.empty(), PlatformIconGroup.generalFilter());
	}

	@Override
	public boolean isSelected(@Nonnull AnActionEvent e)
	{
		Project project = e.getProject();
		return project != null && isFiltered(project);
	}

	@Override
	public void setSelected(@Nonnull AnActionEvent e, boolean state)
	{
		PropertiesComponent.getInstance(e.getProject()).setValue(KEY, state, true);

		StructureTreeModel<?> model = e.getRequiredData(MSBuildToolWindowKeys.TREE_STRUCTURE);

		model.invalidate();
	}
}
