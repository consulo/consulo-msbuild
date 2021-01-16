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

import com.intellij.openapi.application.Application;
import com.intellij.openapi.util.Iconable;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.xml.XmlFile;
import com.intellij.util.xml.DomFileDescription;
import consulo.msbuild.dom.Project;
import consulo.ui.image.Image;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Objects;

/**
 * @author VISTALL
 * @since 28-Jan-17
 */
public class MSBuildDomDescriptor extends DomFileDescription<Project>
{
	public MSBuildDomDescriptor()
	{
		super(Project.class, "Project");

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

		for(MSBuildProjectFileEP projectFileEP : MSBuildProjectFileEP.EP_NAME.getExtensionList(Application.get()))
		{
			if(Objects.equals(virtualFile.getExtension(), projectFileEP.extension))
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
		return MSBuildIcons.Msbuild;
	}
}
