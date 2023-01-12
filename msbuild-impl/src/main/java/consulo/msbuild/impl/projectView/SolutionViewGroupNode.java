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
import consulo.msbuild.impl.solution.SolutionVirtualItem;
import consulo.project.Project;
import consulo.project.ui.view.tree.AbstractTreeNode;
import consulo.project.ui.view.tree.ProjectViewNode;
import consulo.project.ui.view.tree.ViewSettings;
import consulo.ui.annotation.RequiredUIAccess;
import consulo.ui.ex.tree.PresentationData;
import consulo.util.lang.Comparing;
import consulo.virtualFileSystem.VirtualFile;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author VISTALL
 * @since 29-Jan-17
 */
public class SolutionViewGroupNode extends ProjectViewNode<SolutionVirtualFile>
{
	private final List<SolutionVirtualItem> myItems = new ArrayList<>();

	public SolutionViewGroupNode(Project project, SolutionVirtualFile solutionVirtualGroup, ViewSettings viewSettings)
	{
		super(project, solutionVirtualGroup, viewSettings);
	}

	@Override
	public boolean contains(@Nonnull VirtualFile file)
	{
		return false;
	}

	@Nonnull
	@Override
	@RequiredUIAccess
	public Collection<? extends AbstractTreeNode> getChildren()
	{
		return SolutionViewProjectNode.buildNodes(myProject, () -> myItems, getSettings(), true);
	}

	@Override
	protected void update(PresentationData presentation)
	{
		SolutionVirtualFile value = getValue();

		switch(value.getSubType())
		{
			case Form:
				presentation.setIcon(AllIcons.FileTypes.UiForm);
				break;
			case Designer:
				presentation.setIcon(AllIcons.Actions.RealIntentionBulb);
				break;
			case __generator:
				if(Comparing.equal(value.getName(), "Settings.settings"))
				{
					presentation.setIcon(AllIcons.General.GearPlain);
				}
				break;
			case __unknown:
				presentation.setIcon(AllIcons.Toolbar.Unknown);
				break;
		}

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
		return !myItems.isEmpty();
	}

	public void addChildren(SolutionVirtualItem item)
	{
		myItems.add(item);
	}
}
