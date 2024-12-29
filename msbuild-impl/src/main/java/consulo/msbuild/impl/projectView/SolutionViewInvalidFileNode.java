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
import consulo.msbuild.impl.solution.SolutionVirtualFile;
import consulo.project.Project;
import consulo.project.ui.view.tree.AbstractTreeNode;
import consulo.project.ui.view.tree.ProjectViewNode;
import consulo.project.ui.view.tree.ViewSettings;
import consulo.ui.ex.tree.PresentationData;
import consulo.virtualFileSystem.VirtualFile;

import jakarta.annotation.Nonnull;
import java.util.Collection;
import java.util.Collections;

/**
 * @author VISTALL
 * @since 28-Jan-17
 */
public class SolutionViewInvalidFileNode extends ProjectViewNode<SolutionVirtualFile>
{
	public SolutionViewInvalidFileNode(Project project, SolutionVirtualFile solutionVirtualFile, ViewSettings viewSettings)
	{
		super(project, solutionVirtualFile, viewSettings);
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
		return Collections.emptyList();
	}

	@Override
	protected void update(PresentationData presentation)
	{
		presentation.setPresentableText(getValue().getName());
		presentation.setIcon(AllIcons.Toolbar.Unknown);
	}
}
