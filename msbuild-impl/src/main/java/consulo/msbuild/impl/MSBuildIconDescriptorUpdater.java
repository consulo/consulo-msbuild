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

import consulo.annotation.access.RequiredReadAction;
import consulo.annotation.component.ExtensionImpl;
import consulo.language.icon.IconDescriptor;
import consulo.language.icon.IconDescriptorUpdater;
import consulo.language.psi.PsiElement;
import consulo.language.psi.PsiFile;
import consulo.module.extension.ModuleExtensionHelper;
import consulo.msbuild.icon.MSBuildIconGroup;
import consulo.msbuild.module.extension.MSBuildSolutionModuleExtension;
import jakarta.inject.Inject;

import jakarta.annotation.Nonnull;

/**
 * @author VISTALL
 * @since 29-Jan-17
 */
@ExtensionImpl
public class MSBuildIconDescriptorUpdater implements IconDescriptorUpdater
{
	private final ModuleExtensionHelper myModuleExtensionHelper;

	@Inject
	public MSBuildIconDescriptorUpdater(ModuleExtensionHelper moduleExtensionHelper)
	{
		myModuleExtensionHelper = moduleExtensionHelper;
	}

	@RequiredReadAction
	@Override
	public void updateIcon(@Nonnull IconDescriptor iconDescriptor, @Nonnull PsiElement element, int flags)
	{
		if(element instanceof PsiFile)
		{
			if(!myModuleExtensionHelper.hasModuleExtension(MSBuildSolutionModuleExtension.class))
			{
				return;
			}

			if(MSBuildGeneratedSourcesFilter.isGeneratedFile(((PsiFile) element).getVirtualFile(), element.getProject()))
			{
				iconDescriptor.addLayerIcon(MSBuildIconGroup.generatedmark());
			}
		}
	}
}
