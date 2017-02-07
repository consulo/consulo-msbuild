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
import org.jetbrains.annotations.Nullable;
import com.intellij.openapi.application.ReadAction;
import com.intellij.openapi.util.text.StringUtil;
import consulo.dotnet.roots.orderEntry.DotNetLibraryOrderEntryImpl;
import consulo.msbuild.MSBuildProjectType;
import consulo.msbuild.MSBuildSolutionManager;
import consulo.msbuild.dom.ItemGroup;
import consulo.msbuild.dom.Project;
import consulo.msbuild.dom.ProjectReference;
import consulo.msbuild.dom.Property;
import consulo.msbuild.dom.PropertyGroup;
import consulo.msbuild.dom.Reference;
import consulo.msbuild.dom.walk.Walker;
import consulo.msbuild.importProvider.MSBuildModuleImportContext;
import consulo.msbuild.importProvider.item.MSBuildDotNetImportProject;
import consulo.msbuild.importProvider.item.MSBuildDotNetImportTarget;
import consulo.msbuild.importProvider.item.MSBuildImportProject;
import consulo.msbuild.module.extension.MSBuildMutableDotNetModuleExtension;
import consulo.msbuild.solution.reader.SlnProject;
import consulo.roots.ModifiableModuleRootLayer;
import consulo.roots.impl.ModuleRootLayerImpl;

/**
 * @author VISTALL
 * @since 03-Feb-17
 */
public abstract class DotNetBasedProjectType implements MSBuildProjectType
{
	public abstract void setupModuleImpl(@NotNull ModifiableModuleRootLayer modifiableRootModel);

	@NotNull
	@Override
	public MSBuildImportProject createImportItem(SlnProject project, MSBuildModuleImportContext context)
	{
		return new MSBuildDotNetImportProject(project, context, MSBuildDotNetImportTarget._NET);
	}

	private static MSBuildDotNetImportTarget findTarget(@Nullable MSBuildSolutionManager.ProjectOptions projectOptions)
	{
		String value = projectOptions == null ? null : projectOptions.target;
		if(value == null)
		{
			return MSBuildDotNetImportTarget._NET;
		}

		return StringUtil.parseEnum(value, MSBuildDotNetImportTarget._NET, MSBuildDotNetImportTarget.class);
	}

	@Override
	public final void setupModule(@NotNull Project domProject, @Nullable MSBuildSolutionManager.ProjectOptions projectOptions, @NotNull ModifiableModuleRootLayer rootLayer)
	{
		// setup .NET extension
		MSBuildMutableDotNetModuleExtension moduleExtension = rootLayer.getExtensionWithoutCheck(MSBuildMutableDotNetModuleExtension.class);
		assert moduleExtension != null;
		moduleExtension.setEnabled(true);
		moduleExtension.setTarget(findTarget(projectOptions));

		setupModuleImpl(rootLayer);

		rootLayer.addOrderEntry(new DotNetLibraryOrderEntryImpl((ModuleRootLayerImpl) rootLayer, "mscorlib"));

		ReadAction.run(() ->
		{
			Walker walker = new Walker(domProject);

			walker.walk(PropertyGroup.class, propertyGroup ->
			{
				//TODO [VISTALL] STUB
				String stringValue = propertyGroup.getCondition().getStringValue();
				if(stringValue != null)
				{
					return;
				}

				List<Property> propertries = propertyGroup.getPropertries();
				for(Property propertry : propertries)
				{
					String xmlElementName = propertry.getXmlElementName();

					switch(xmlElementName)
					{
						case "TargetFrameworkVersion":
							break;
					}
				}
			});

			walker.walk(ItemGroup.class, itemGroup ->
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

				List<ProjectReference> projectReferences = itemGroup.getProjectReferences();
				for(ProjectReference projectReference : projectReferences)
				{
					String name = projectReference.getName().getStringValue();
					if(name == null)
					{
						continue;
					}
					rootLayer.addInvalidModuleEntry(name);
				}
			});
		});
	}
}
