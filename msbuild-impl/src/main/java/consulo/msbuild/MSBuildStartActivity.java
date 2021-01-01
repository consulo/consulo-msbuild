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

package consulo.msbuild;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.startup.StartupActivity;
import consulo.msbuild.daemon.impl.MSBuildDaemonService;
import consulo.ui.UIAccess;

import javax.annotation.Nonnull;

/**
 * @author VISTALL
 * @since 02-Feb-17
 */
public class MSBuildStartActivity implements StartupActivity
{
	@Override
	public void runActivity(@Nonnull UIAccess uiAccess, @Nonnull Project project)
	{
//		MSBuildSolutionManager solutionManager = MSBuildSolutionManager.getInstance(project);
//		if(!solutionManager.isEnabled())
//		{
//			return;
//		}
//
//		Task.Backgroundable.queue(project, "Synchronize solution", indicator ->
//		{
//			ModifiableModuleModel modifiableModel = AccessRule.read(() -> ModuleManager.getInstance(project).getModifiableModel());
//
//			WSolution solution = AccessRule.read(solutionManager::getSolution);
//
//			for(Module module : modifiableModel.getModules())
//			{
//				if(!Comparing.equal(project.getBaseDir(), module.getModuleDir()))
//				{
//					modifiableModel.disposeModule(module);
//				}
//			}
//
//			Collection<WProject> projects = solution.getProjects();
//
//			for(WProject wProject : projects)
//			{
//				VirtualFile projectFile = wProject.getVirtualFile();
//				if(projectFile == null)
//				{
//					continue;
//				}
//
//				consulo.msbuild.dom.Project domProject = wProject.getDomProject();
//				if(domProject == null)
//				{
//					continue;
//				}
//
//				MSBuildSolutionManager.ProjectOptions projectOptions = solutionManager.getOptionsByProjectName(wProject.getName());
//				if(projectOptions != null)
//				{
//					projectOptions.moduleName = wProject.getName();
//				}
//
//				Module module = modifiableModel.newModule(wProject.getName(), null);
//
//				ModifiableRootModel modifiableRootModel = AccessRule.read(() -> ModuleRootManager.getInstance(module).getModifiableModel());
//
//				MSBuildProjectType projectType = MSBuildProjectType.getProjectType(wProject.getTypeGUID());
//				if(projectType != null)
//				{
//					projectType.setupModule(projectFile, domProject, projectOptions, (ModifiableModuleRootLayer) modifiableRootModel.getCurrentLayer());
//				}
//
//				SolutionVirtualDirectory directory = AccessRule.read(() -> SolutionVirtualBuilder.build(domProject, projectFile.getParent()));
//
//				directory.visitRecursive(solutionVirtualItem ->
//				{
//					if(solutionVirtualItem instanceof SolutionVirtualFile)
//					{
//						VirtualFile virtualFile = ((SolutionVirtualFile) solutionVirtualItem).getVirtualFile();
//						if(virtualFile != null)
//						{
//							modifiableRootModel.addContentEntry(virtualFile);
//						}
//					}
//
//					return true;
//				});
//
//				WriteCommandAction.runWriteCommandAction(project, modifiableRootModel::commit);
//			}
//
//			WriteCommandAction.runWriteCommandAction(project, modifiableModel::commit);
//
//			startDaemonService(project);
//		});
	}

	private void startDaemonService(Project project)
	{
		MSBuildDaemonService.getInstance(project).forceUpdate();
	}
}
