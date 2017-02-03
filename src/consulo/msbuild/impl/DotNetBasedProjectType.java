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

import java.util.List;

import org.jetbrains.annotations.NotNull;
import com.intellij.openapi.application.ReadAction;
import consulo.dotnet.roots.orderEntry.DotNetLibraryOrderEntryImpl;
import consulo.msbuild.MSBuildProjectType;
import consulo.msbuild.dom.ItemGroup;
import consulo.msbuild.dom.Project;
import consulo.msbuild.dom.Reference;
import consulo.roots.ModifiableModuleRootLayer;
import consulo.roots.impl.ModuleRootLayerImpl;

/**
 * @author VISTALL
 * @since 03-Feb-17
 */
public abstract class DotNetBasedProjectType implements MSBuildProjectType
{
	public abstract void setupModuleImpl(@NotNull ModifiableModuleRootLayer modifiableRootModel);

	@Override
	public final void setupModule(Project domProject, @NotNull ModifiableModuleRootLayer rootLayer)
	{
		setupModuleImpl(rootLayer);

		rootLayer.addOrderEntry(new DotNetLibraryOrderEntryImpl((ModuleRootLayerImpl) rootLayer, "mscorlib"));

		ReadAction.run(() ->
		{
			List<ItemGroup> itemGroups = domProject.getItemGroups();
			for(ItemGroup itemGroup : itemGroups)
			{
				List<Reference> references = itemGroup.getReferences();
				for(Reference reference : references)
				{
					String stringValue = reference.getInclude().getStringValue();
					if(stringValue != null)
					{
						rootLayer.addOrderEntry(new DotNetLibraryOrderEntryImpl((ModuleRootLayerImpl) rootLayer, stringValue));
					}
				}
			}
		});
	}
}
