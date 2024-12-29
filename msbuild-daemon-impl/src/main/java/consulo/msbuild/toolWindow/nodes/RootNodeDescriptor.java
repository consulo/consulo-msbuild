package consulo.msbuild.toolWindow.nodes;

import consulo.annotation.access.RequiredReadAction;
import consulo.msbuild.module.extension.MSBuildSolutionModuleExtension;
import consulo.msbuild.solution.model.WProject;
import consulo.platform.base.icon.PlatformIconGroup;
import consulo.project.Project;
import consulo.project.ui.view.tree.AbstractTreeNode;
import consulo.ui.ex.tree.PresentationData;
import consulo.virtualFileSystem.VirtualFile;

import jakarta.annotation.Nonnull;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author VISTALL
 * @since 01/01/2021
 */
public class RootNodeDescriptor extends AbstractTreeNode<Object>
{
	public RootNodeDescriptor(Project project, @Nonnull Object value)
	{
		super(project, value);
	}

	@RequiredReadAction
	@Nonnull
	@Override
	public Collection<? extends AbstractTreeNode> getChildren()
	{
		MSBuildSolutionModuleExtension<?> extension = MSBuildSolutionModuleExtension.getSolutionModuleExtension(myProject);
		if(extension == null)
		{
			return List.of();
		}

		Collection<WProject> projects = extension.getProjects();

		return projects.stream().map(wProject -> new ProjectNodeDescriptor(myProject, wProject)).collect(Collectors.toList());
	}

	@Override
	protected void update(PresentationData presentationData)
	{
		presentationData.setIcon(PlatformIconGroup.nodesFolder());

		MSBuildSolutionModuleExtension<?> extension = MSBuildSolutionModuleExtension.getSolutionModuleExtension(myProject);
		if(extension == null)
		{
			return;
		}

		VirtualFile solutionFile = extension.getSolutionFile();

		if(solutionFile != null)
		{
			String solName = String.format("Solution '%s' (%s project(s))", solutionFile.getNameWithoutExtension(), extension.getProjects().size());

			presentationData.setPresentableText(solName);
		}
		else
		{
			presentationData.setPresentableText("Solution");
		}
	}
}
