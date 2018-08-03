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

import javax.annotation.Nonnull;
import javax.swing.Icon;

import com.intellij.icons.AllIcons;
import com.intellij.ide.projectView.PresentationData;
import com.intellij.ide.projectView.ProjectViewNode;
import com.intellij.ide.projectView.ViewSettings;
import com.intellij.ide.util.treeView.AbstractTreeNode;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.Comparing;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.openapi.vfs.VirtualFile;
import consulo.annotations.RequiredReadAction;
import consulo.msbuild.solution.SolutionVirtualDirectory;

/**
 * @author VISTALL
 * @since 28-Jan-17
 */
public class SolutionViewDirectoryNode extends ProjectViewNode<SolutionVirtualDirectory>
{
	public SolutionViewDirectoryNode(Project project, SolutionVirtualDirectory solutionVirtualDirectory, ViewSettings viewSettings)
	{
		super(project, solutionVirtualDirectory, viewSettings);
	}

	@Override
	public boolean contains(@Nonnull VirtualFile file)
	{
		return false;
	}

	@Override
	public int getWeight()
	{
		if(isPropertiesDirectory())
		{
			return -100;
		}
		return 0;
	}

	@Nonnull
	@Override
	@RequiredReadAction
	public Collection<? extends AbstractTreeNode> getChildren()
	{
		return SolutionViewProjectNode.buildNodes(myProject, getValue()::getChildren, getSettings(), false);
	}

	@Override
	protected void update(PresentationData presentation)
	{
		SolutionVirtualDirectory value = getValue();

		Icon icon = AllIcons.Nodes.TreeOpen;
		if(isPropertiesDirectory())
		{
			icon = AllIcons.General.Settings;
		}
		presentation.setIcon(icon);
		presentation.setPresentableText(getValue().getName());
	}

	@Override
	public boolean shouldDrillDownOnEmptyElement()
	{
		return true;
	}

	@Override
	public boolean isAlwaysShowPlus()
	{
		SolutionVirtualDirectory value = getValue();
		return !value.getChildren().isEmpty();
	}

	private boolean isPropertiesDirectory()
	{
		SolutionVirtualDirectory value = getValue();
		SolutionVirtualDirectory parent = value.getParent();
		return parent != null && StringUtil.isEmpty(parent.getName()) && Comparing.equal(value.getName(), "Properties");
	}
}
