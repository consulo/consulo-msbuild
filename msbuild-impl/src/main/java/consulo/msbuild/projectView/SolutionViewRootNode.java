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

import com.intellij.ide.projectView.PresentationData;
import com.intellij.ide.projectView.ProjectViewNode;
import com.intellij.ide.projectView.ViewSettings;
import com.intellij.ide.util.treeView.AbstractTreeNode;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import consulo.annotation.access.RequiredReadAction;
import consulo.msbuild.MSBuildGUID;
import consulo.msbuild.solution.model.WProject;
import consulo.msbuild.solution.model.WSolution;
import consulo.msbuild.solution.reader.SlnSection;
import consulo.platform.base.icon.PlatformIconGroup;
import consulo.ui.annotation.RequiredUIAccess;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.*;

/**
 * @author VISTALL
 * @since 28-Jan-17
 */
public class SolutionViewRootNode extends ProjectViewNode<Project>
{
	@Nullable
	private final WSolution myWSolution;

	@Nullable
	private final VirtualFile mySolutionFile;

	private final Collection<WProject> myProjects;

	@RequiredReadAction
	public SolutionViewRootNode(Project project, @Nullable VirtualFile solutionFile, Collection<WProject> projects, ViewSettings viewSettings)
	{
		super(project, project, viewSettings);
		mySolutionFile = solutionFile;
		myProjects = projects;
		myWSolution = solutionFile == null ? null : WSolution.build(project, solutionFile);
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
		Map<String, AbstractTreeNode> nodes = new HashMap<>(myProjects.size());
		for(WProject wProject : myProjects)
		{
			AbstractTreeNode node;
			if(MSBuildGUID.SolutionFolder.equals(wProject.getTypeGUID()))
			{
				node = new SolutionViewProjectFolderNode(myProject, wProject, getSettings());
			}
			else
			{
				consulo.msbuild.dom.Project domProject = wProject.getDomProject();
				if(domProject == null)
				{
					node = new SolutionViewInvalidProjectNode(myProject, wProject, getSettings());
				}
				else
				{
					node = new SolutionViewProjectNode(myProject, wProject, getSettings());
				}
			}

			nodes.put(wProject.getId(), node);
		}

		if(myWSolution != null)
		{
			SlnSection section = myWSolution.getSection(WSolution.SECTION_NESTED_PROJECTS);
			if(section != null)
			{
				Set<String> toRemove = new HashSet<>();
				for(Map.Entry<String, String> entry : section.getProperties().getValues().entrySet())
				{
					AbstractTreeNode left = nodes.get(entry.getKey());
					AbstractTreeNode right = nodes.get(entry.getValue());

					if(right == null)
					{
						continue;
					}

					((SolutionViewProjectFolderNode) right).addChildren(left);

					toRemove.add(((SolutionViewProjectNodeBase) left).getProjectId());
				}

				for(String id : toRemove)
				{
					nodes.remove(id);
				}
			}
		}
		return nodes.values();
	}

	@Override
	protected void update(PresentationData presentation)
	{
		presentation.setIcon(PlatformIconGroup.nodesFolder());

		if(mySolutionFile != null)
		{
			String solName = String.format("Solution '%s' (%s project(s))", mySolutionFile.getNameWithoutExtension(), myWSolution.getProjects().size());

			presentation.setPresentableText(solName);
		}
		else
		{
			presentation.setPresentableText("Solution");
		}
	}
}
