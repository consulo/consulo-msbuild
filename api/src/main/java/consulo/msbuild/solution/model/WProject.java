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

package consulo.msbuild.solution.model;

import com.intellij.openapi.application.ReadAction;
import com.intellij.openapi.util.io.FileUtil;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiManager;
import com.intellij.psi.xml.XmlFile;
import com.intellij.util.xml.DomFileElement;
import com.intellij.util.xml.DomManager;
import consulo.annotation.DeprecationInfo;
import consulo.msbuild.MSBuildGUID;
import consulo.msbuild.dom.Project;
import consulo.msbuild.solution.reader.SlnProject;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * @author VISTALL
 * @since 30-Jan-17
 */
public class WProject
{
	public static enum FailReason
	{
		not_supported,
		project_not_found
	}

	private SlnProject myProject;

	private Project myDomProject;

	private VirtualFile myFile;

	private FailReason myFailReason;

	public WProject(com.intellij.openapi.project.Project project, VirtualFile solutionVirtualFile, SlnProject slnProject)
	{
		myProject = slnProject;

		if(!StringUtil.isEmpty(slnProject.FilePath) && !MSBuildGUID.SolutionFolder.equals(slnProject.TypeGuid))
		{
			VirtualFile parent = solutionVirtualFile.getParent();

			myFile = parent.findFileByRelativePath(FileUtil.toSystemIndependentName(slnProject.FilePath));
			if(myFile == null)
			{
				myFailReason = FailReason.project_not_found;
				return;
			}

			PsiFile psiFile = ReadAction.compute(() -> PsiManager.getInstance(project).findFile(myFile));

			if(psiFile instanceof XmlFile)
			{
				DomFileElement<Project> fileElement = ReadAction.compute(() -> DomManager.getDomManager(project).getFileElement((XmlFile) psiFile, consulo.msbuild.dom.Project.class));
				if(fileElement != null)
				{
					myDomProject = fileElement.getRootElement();
					return;
				}
			}
			myFailReason = FailReason.not_supported;
		}
	}

	public WProject(com.intellij.openapi.project.Project project, VirtualFile projectFile, String projectUUID)
	{
		myProject = new SlnProject();
		myProject.Id = "{" + projectUUID + "}";
		myProject.TypeGuid = "{" + "fake id" + "}";
		myProject.Name = projectFile.getName();

		myFile = projectFile;

		PsiFile psiFile = ReadAction.compute(() -> PsiManager.getInstance(project).findFile(projectFile));

		if(psiFile instanceof XmlFile)
		{
			DomFileElement<Project> fileElement = ReadAction.compute(() -> DomManager.getDomManager(project).getFileElement((XmlFile) psiFile, consulo.msbuild.dom.Project.class));
			if(fileElement != null)
			{
				myDomProject = fileElement.getRootElement();
				return;
			}
		}
		myFailReason = FailReason.not_supported;
	}

	@Deprecated
	@DeprecationInfo("For now there no sense of using this method, we don't support xml validation")
	public Project getDomProject()
	{
		return myDomProject;
	}

	@Nullable
	public FailReason getFailReason()
	{
		return myFailReason;
	}

	@Nonnull
	public String getTypeGUID()
	{
		return myProject.TypeGuid;
	}

	@Nonnull
	public String getId()
	{
		return myProject.Id;
	}

	public String getName()
	{
		return myProject.Name;
	}

	@Nullable
	public VirtualFile getVirtualFile()
	{
		return myFile;
	}
}
