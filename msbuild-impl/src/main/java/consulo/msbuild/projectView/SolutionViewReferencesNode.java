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

import javax.annotation.Nonnull;

import com.intellij.icons.AllIcons;
import com.intellij.ide.projectView.PresentationData;
import com.intellij.ide.projectView.ProjectViewNode;
import com.intellij.ide.projectView.ViewSettings;
import com.intellij.ide.util.treeView.AbstractTreeNode;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;

/**
 * @author VISTALL
 * @since 29-Jan-17
 */
public class SolutionViewReferencesNode extends ProjectViewNode<consulo.msbuild.dom.Project>
{
	public SolutionViewReferencesNode(Project project, consulo.msbuild.dom.Project msProject, ViewSettings viewSettings)
	{
		super(project, msProject, viewSettings);
	}

	@Override
	public int getWeight()
	{
		return -200;
	}

	@Override
	public boolean shouldDrillDownOnEmptyElement()
	{
		return true;
	}

	@Override
	public boolean isAlwaysShowPlus()
	{
		return false;
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
		presentation.setPresentableText("References");
		presentation.setIcon(AllIcons.Nodes.PpLibFolder);
	}
}
