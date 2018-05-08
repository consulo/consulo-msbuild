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

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.intellij.openapi.util.Iconable;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.xml.XmlFile;
import com.intellij.util.xml.DomFileDescription;
import consulo.msbuild.dom.Project;
import consulo.ui.image.Image;

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
		return virtualFile != null && MSBuildProjectType.getExtensions().contains(virtualFile.getExtension());
	}

	@Nullable
	@Override
	public Image getFileIcon(@Iconable.IconFlags int flags)
	{
		return MSBuildIcons.Msbuild;
	}
}
