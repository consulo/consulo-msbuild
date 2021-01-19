package consulo.msbuild.toolWindow.nodes;

import com.intellij.ide.projectView.PresentationData;
import com.intellij.ide.util.treeView.AbstractTreeNode;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.ModuleManager;
import com.intellij.openapi.module.ModuleUtilCore;
import com.intellij.openapi.project.Project;
import com.intellij.ui.SimpleTextAttributes;
import consulo.annotation.access.RequiredReadAction;
import consulo.msbuild.api.icon.MSBuildApiIconGroup;
import consulo.msbuild.module.extension.MSBuildProjectModuleExtension;
import consulo.msbuild.solution.model.WProject;

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

		Module moduleByName = manager.findModuleByName(getValue().getName());
		if(moduleByName != null)
		{
			MSBuildProjectModuleExtension<?> extension = ModuleUtilCore.getExtension(moduleByName, MSBuildProjectModuleExtension.class);
			if(extension != null)
			{
				Set<String> targets = extension.getTargets();
				return targets.stream().sorted().filter(s -> s.charAt(0) != '_').map(o -> new TargetNodeDescriptor(myProject, o)).collect(Collectors.toList());
			}
		}

		return List.of();
	}

	@Override
	protected void update(PresentationData presentation)
	{
		presentation.addText(getValue().getName(), SimpleTextAttributes.REGULAR_BOLD_ATTRIBUTES);
		presentation.setIcon(MSBuildApiIconGroup.msbuild());
	}
}
