package consulo.msbuild.toolWindow.nodes;

import consulo.annotation.access.RequiredReadAction;
import consulo.module.Module;
import consulo.module.ModuleManager;
import consulo.msbuild.MSBuildWorkspaceData;
import consulo.msbuild.icon.MSBuildIconGroup;
import consulo.msbuild.solution.model.WProject;
import consulo.msbuild.toolWindow.actions.FilterTargetsAction;
import consulo.project.Project;
import consulo.project.ui.view.tree.AbstractTreeNode;
import consulo.ui.ex.SimpleTextAttributes;
import consulo.ui.ex.tree.PresentationData;
import consulo.util.collection.ArrayUtil;

import javax.annotation.Nonnull;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author VISTALL
 * @since 01/01/2021
 */
public class ProjectNodeDescriptor extends AbstractTreeNode<WProject>
{
	private static final String[] ourCommonTargets = {
			"Clean",
			"Compile",
			"Restore",
			"Build"
	};

	public ProjectNodeDescriptor(Project project, @Nonnull WProject value)
	{
		super(project, value);
	}

	@RequiredReadAction
	@Nonnull
	@Override
	public Collection<? extends AbstractTreeNode> getChildren()
	{
		ModuleManager manager = ModuleManager.getInstance(myProject);
		MSBuildWorkspaceData workspaceData = MSBuildWorkspaceData.getInstance(myProject);

		Module moduleByName = manager.findModuleByName(getValue().getName());
		if(moduleByName != null)
		{
			Set<String> targets = workspaceData.getTargets(getValue().getId());
			if(FilterTargetsAction.isFiltered(myProject))
			{
				return targets.stream().sorted().filter(s -> ArrayUtil.contains(s, ourCommonTargets)).map(o -> new TargetNodeDescriptor(myProject, o)).collect(Collectors.toList());
			}
			else
			{
				return targets.stream().sorted().filter(s -> s.charAt(0) != '_').map(o -> new TargetNodeDescriptor(myProject, o)).collect(Collectors.toList());
			}
		}

		return List.of();
	}

	@Override
	protected void update(PresentationData presentation)
	{
		presentation.addText(getValue().getName(), SimpleTextAttributes.REGULAR_BOLD_ATTRIBUTES);
		presentation.setIcon(MSBuildIconGroup.msbuild());
	}
}
