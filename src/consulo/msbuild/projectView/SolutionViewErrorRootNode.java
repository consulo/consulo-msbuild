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

package consulo.msbuild.projectView;

import java.util.Collection;
import java.util.Collections;

import org.jetbrains.annotations.NotNull;
import com.intellij.ide.projectView.PresentationData;
import com.intellij.ide.projectView.ProjectViewNode;
import com.intellij.ide.projectView.ViewSettings;
import com.intellij.ide.util.treeView.AbstractTreeNode;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import consulo.msbuild.MSBuildIcons;

/**
 * @author VISTALL
 * @since 26-Mar-17
 */
public class SolutionViewErrorRootNode extends ProjectViewNode<Project>
{
	public SolutionViewErrorRootNode(Project project, ViewSettings viewSettings)
	{
		super(project, project, viewSettings);
	}

	@Override
	public boolean contains(@NotNull VirtualFile file)
	{
		return false;
	}

	@NotNull
	@Override
	public Collection<? extends AbstractTreeNode> getChildren()
	{
		return Collections.emptyList();
	}

	@Override
	protected void update(PresentationData presentation)
	{
		presentation.setIcon(MSBuildIcons.VisualStudio);
		presentation.setPresentableText("Solution");
	}
}
