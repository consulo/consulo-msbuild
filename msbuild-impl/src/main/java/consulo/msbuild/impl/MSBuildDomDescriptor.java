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

package consulo.msbuild.impl;

import consulo.annotation.component.ExtensionImpl;
import consulo.application.Application;
import consulo.component.util.Iconable;
import consulo.msbuild.MSBuildProjectFile;
import consulo.msbuild.dom.Project;
import consulo.msbuild.icon.MSBuildIconGroup;
import consulo.ui.image.Image;
import consulo.util.lang.StringUtil;
import consulo.virtualFileSystem.VirtualFile;
import consulo.xml.psi.xml.XmlFile;
import consulo.xml.util.xml.DomFileDescription;
import jakarta.inject.Inject;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * @author VISTALL
 * @since 28-Jan-17
 */
@ExtensionImpl
public class MSBuildDomDescriptor extends DomFileDescription<Project>
{
	private final Application myApplication;

	@Inject
	public MSBuildDomDescriptor(Application application)
	{
		super(Project.class, "Project");
		myApplication = application;

		registerNamespacePolicy("", "http://schemas.microsoft.com/developer/msbuild/2003");
	}

	@Override
	public boolean isMyFile(@Nonnull XmlFile file)
	{
		VirtualFile virtualFile = file.getVirtualFile();
		if(virtualFile == null)
		{
			return false;
		}

		for(String projectFileExtension : MSBuildProjectFile.listAll(myApplication))
		{
			if(StringUtil.equalsIgnoreCase(virtualFile.getExtension(), projectFileExtension))
			{
				return true;
			}
		}

		return false;
	}

	@Nullable
	@Override
	public Image getFileIcon(@Iconable.IconFlags int flags)
	{
		return MSBuildIconGroup.msbuildtoolwindow();
	}
}
