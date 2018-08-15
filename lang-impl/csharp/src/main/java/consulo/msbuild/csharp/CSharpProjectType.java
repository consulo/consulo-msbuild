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

package consulo.msbuild.csharp;

import javax.annotation.Nonnull;

import com.intellij.openapi.vfs.VirtualFile;
import consulo.csharp.lang.CSharpFileType;
import consulo.msbuild.csharp.module.extension.MSBuildCSharpMutableModuleExtension;
import consulo.msbuild.impl.DotNetBasedProjectType;
import consulo.msbuild.synchronize.MSBuildFileReferenceType;
import consulo.roots.ModifiableModuleRootLayer;

/**
 * @author VISTALL
 * @since 03-Feb-17
 */
public class CSharpProjectType extends DotNetBasedProjectType
{
	@Override
	public void setupModuleImpl(@Nonnull ModifiableModuleRootLayer rootLayer)
	{
		MSBuildCSharpMutableModuleExtension extension = rootLayer.getExtensionWithoutCheck(MSBuildCSharpMutableModuleExtension.class);

		assert extension != null;

		extension.setEnabled(true);
	}

	@Nonnull
	@Override
	public MSBuildFileReferenceType getFileReferenceType(@Nonnull VirtualFile virtualFile)
	{
		if(virtualFile.getFileType() == CSharpFileType.INSTANCE)
		{
			return MSBuildFileReferenceType.COMPILE;
		}
		return MSBuildFileReferenceType.NONE;
	}
}
