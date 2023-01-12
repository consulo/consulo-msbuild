/*
 * Copyright 2013-2017 consulo.io
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package consulo.msbuild.impl.projectView;

import consulo.annotation.access.RequiredReadAction;
import consulo.application.AllIcons;
import consulo.language.psi.PsiFile;
import consulo.language.psi.PsiManager;
import consulo.msbuild.impl.solution.SolutionVirtualBuilder;
import consulo.msbuild.impl.solution.SolutionVirtualDirectory;
import consulo.msbuild.impl.solution.SolutionVirtualFile;
import consulo.msbuild.impl.solution.SolutionVirtualItem;
import consulo.msbuild.solution.model.WProject;
import consulo.project.Project;
import consulo.project.ui.view.tree.AbstractTreeNode;
import consulo.project.ui.view.tree.ProjectViewNode;
import consulo.project.ui.view.tree.ViewSettings;
import consulo.ui.annotation.RequiredUIAccess;
import consulo.ui.ex.SimpleTextAttributes;
import consulo.ui.ex.tree.PresentationData;
import consulo.virtualFileSystem.VirtualFile;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.function.Supplier;

/**
 * @author VISTALL
 * @since 28-Jan-17
 */
public class SolutionViewProjectNode extends ProjectViewNode<WProject> implements SolutionViewProjectNodeBase
{
	public SolutionViewProjectNode(Project project, WProject wProject, ViewSettings viewSettings)
	{
		super(project, wProject, viewSettings);
		myName = getValue().getName();
	}

	@Override
	public boolean contains(@Nonnull VirtualFile file)
	{
		return false;
	}

	@Override
	public String getProjectId()
	{
		return getValue().getId();
	}

	@Nonnull
	@Override
	@RequiredUIAccess
	public Collection<? extends AbstractTreeNode> getChildren()
	{
		VirtualFile projectFile = getValue().getVirtualFile();
		VirtualFile parent = projectFile.getParent();
		if(parent == null)
		{
			return Collections.emptyList();
		}

		SolutionVirtualDirectory directory = SolutionVirtualBuilder.build(getValue(), myProject, parent);

		Collection<AbstractTreeNode> nodes = buildNodes(myProject, directory::getChildren, getSettings(), false);
		nodes.add(new SolutionViewReferencesNode(myProject, getValue().getDomProject(), getSettings()));
		return nodes;
	}

	@Nonnull
	@RequiredReadAction
	public static Collection<AbstractTreeNode> buildNodes(Project project,
			Supplier<Collection<SolutionVirtualItem>> values,
			ViewSettings settings,
			boolean restrictPatcher)
	{
		Collection<SolutionVirtualItem> items = values.get();
		Collection<AbstractTreeNode> list = new ArrayList<>(items.size());
		for(SolutionVirtualItem item : items)
		{
			if(item instanceof SolutionVirtualDirectory)
			{
				list.add(new SolutionViewDirectoryNode(project, (SolutionVirtualDirectory) item, settings));
			}
			else if(item instanceof SolutionVirtualFile)
			{
				VirtualFile virtualFile = ((SolutionVirtualFile) item).getVirtualFile();
				if(virtualFile == null)
				{
					list.add(new SolutionViewInvalidFileNode(project, (SolutionVirtualFile) item, settings));
				}
				else
				{
					PsiFile file = PsiManager.getInstance(project).findFile(virtualFile);
					assert file != null;
					list.add(new SolutionViewFileNode(project, file, settings, (SolutionVirtualFile) item, restrictPatcher));
				}
			}
		}
		return list;
	}

	@Override
	protected void update(PresentationData presentation)
	{
		presentation.setIcon(AllIcons.Nodes.Module);
		presentation.addText(getValue().getName(), SimpleTextAttributes.REGULAR_BOLD_ATTRIBUTES);
	}
}
