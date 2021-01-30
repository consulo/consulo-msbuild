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

package consulo.msbuild.roots;

import com.google.common.base.Objects;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.roots.GeneratedSourcesFilter;
import com.intellij.openapi.vfs.VirtualFile;
import consulo.annotation.access.RequiredReadAction;
import consulo.msbuild.module.extension.MSBuildSolutionModuleExtension;
import consulo.msbuild.solution.SolutionVirtualBuilder;
import consulo.msbuild.solution.SolutionVirtualDirectory;
import consulo.msbuild.solution.SolutionVirtualFile;
import consulo.msbuild.solution.model.WProject;
import consulo.util.lang.ref.SimpleReference;

import javax.annotation.Nonnull;

/**
 * @author VISTALL
 * @since 29-Jan-17
 */
public class MSBuildGeneratedSourcesFilter extends GeneratedSourcesFilter
{
	@RequiredReadAction
	@Override
	public boolean isGeneratedSource(@Nonnull VirtualFile virtualFile, @Nonnull Project project)
	{
		return isGeneratedFile(virtualFile, project);
	}

	@RequiredReadAction
	public static boolean isGeneratedFile(@Nonnull VirtualFile virtualFile, @Nonnull Project project)
	{
		MSBuildSolutionModuleExtension<?> solutionModuleExtension = MSBuildSolutionModuleExtension.getSolutionModuleExtension(project);
		if(solutionModuleExtension == null)
		{
			return false;
		}

		for(WProject wProject : solutionModuleExtension.getProjects())
		{
			VirtualFile projectFile = wProject.getVirtualFile();
			if(projectFile == null)
			{
				continue;
			}

			SolutionVirtualDirectory directory = SolutionVirtualBuilder.build(wProject, project, projectFile.getParent());

			SimpleReference<Boolean> ref = SimpleReference.create(Boolean.FALSE);
			directory.visitRecursive(solutionVirtualItem ->
			{
				if(solutionVirtualItem instanceof SolutionVirtualFile)
				{
					SolutionVirtualFile file = (SolutionVirtualFile) solutionVirtualItem;
					if(Objects.equal(virtualFile, file.getVirtualFile()))
					{
						ref.set(file.isGenerated());
						return false;
					}
				}

				return true;
			});

			if(ref.get())
			{
				return true;
			}
		}
		return false;
	}
}
