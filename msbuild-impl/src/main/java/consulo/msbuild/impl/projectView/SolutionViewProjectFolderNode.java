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

import consulo.application.AllIcons;
import consulo.msbuild.solution.model.WProject;
import consulo.project.Project;
import consulo.project.ui.view.tree.AbstractTreeNode;
import consulo.project.ui.view.tree.ProjectViewNode;
import consulo.project.ui.view.tree.ViewSettings;
import consulo.ui.ex.tree.PresentationData;
import consulo.virtualFileSystem.VirtualFile;

import jakarta.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author VISTALL
 * @since 30-Jan-17
 */
public class SolutionViewProjectFolderNode extends ProjectViewNode<WProject> implements SolutionViewProjectNodeBase
{
	private List<AbstractTreeNode> myChildren = new ArrayList<>();

	public SolutionViewProjectFolderNode(Project project, WProject wProject, ViewSettings viewSettings)
	{
		super(project, wProject, viewSettings);
	}

	public void addChildren(AbstractTreeNode node)
	{
		myChildren.add(node);
	}

	@Override
	public int getWeight()
	{
		return 4;
	}

	@Override
	public boolean contains(@Nonnull VirtualFile file)
	{
		return false;
	}

	@Nonnull
	@Override
	public Collection<? extends AbstractTreeNode> getChildren()
	{
		return myChildren;
	}

	@Override
	protected void update(PresentationData presentation)
	{
		presentation.setPresentableText(getValue().getName());
		presentation.setIcon(AllIcons.Nodes.ModuleGroup);
	}

	@Override
	public String getProjectId()
	{
		return getValue().getId();
	}
}
