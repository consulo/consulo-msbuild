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

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.intellij.openapi.projectRoots.Sdk;
import com.intellij.openapi.projectRoots.SdkTable;
import com.intellij.openapi.projectRoots.SdkType;
import com.intellij.openapi.roots.libraries.Library;
import com.intellij.openapi.util.Comparing;
import com.intellij.openapi.util.Pair;
import com.intellij.openapi.util.io.FileUtil;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.openapi.vfs.VfsUtil;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.openapi.vfs.VirtualFileManager;
import com.intellij.util.containers.ContainerUtil;
import com.intellij.util.io.URLUtil;
import com.intellij.util.xml.GenericAttributeValue;
import consulo.annotations.RequiredReadAction;
import consulo.application.AccessRule;
import consulo.dotnet.DotNetTarget;
import consulo.dotnet.dll.DotNetModuleFileType;
import consulo.dotnet.module.extension.DotNetModuleExtension;
import consulo.dotnet.roots.orderEntry.DotNetLibraryOrderEntryImpl;
import consulo.module.extension.MutableModuleInheritableNamedPointer;
import consulo.msbuild.MSBuildProjectType;
import consulo.msbuild.MSBuildSolutionManager;
import consulo.msbuild.dom.ItemGroup;
import consulo.msbuild.dom.Project;
import consulo.msbuild.dom.ProjectReference;
import consulo.msbuild.dom.Property;
import consulo.msbuild.dom.PropertyGroup;
import consulo.msbuild.dom.Reference;
import consulo.msbuild.dom.expression.evaluate.MSBuildEvaluateContext;
import consulo.msbuild.dom.expression.evaluate.MSBuildEvaluator;
import consulo.msbuild.dom.expression.evaluate.variable.impl.MSBuildConfiguration;
import consulo.msbuild.dom.expression.evaluate.variable.impl.MSBuildPlatform;
import consulo.msbuild.dom.walk.Walker;
import consulo.msbuild.importProvider.MSBuildModuleImportContext;
import consulo.msbuild.importProvider.item.MSBuildDotNetImportProject;
import consulo.msbuild.importProvider.item.MSBuildDotNetImportTarget;
import consulo.msbuild.importProvider.item.MSBuildImportProject;
import consulo.msbuild.importProvider.item.UnknownBuildDotNetImportTarget;
import consulo.msbuild.module.extension.MSBuildMutableDotNetModuleExtension;
import consulo.msbuild.solution.reader.SlnProject;
import consulo.roots.ModifiableModuleRootLayer;
import consulo.roots.impl.ModuleRootLayerImpl;
import consulo.roots.types.BinariesOrderRootType;

/**
 * @author VISTALL
 * @since 03-Feb-17
 */
public abstract class DotNetBasedProjectType implements MSBuildProjectType
{
	public abstract void setupModuleImpl(@Nonnull ModifiableModuleRootLayer modifiableRootModel);

	@Nonnull
	@Override
	public MSBuildImportProject createImportItem(SlnProject project, MSBuildModuleImportContext context)
	{
		return new MSBuildDotNetImportProject(project, context, MSBuildDotNetImportTarget.EP_NAME.getExtensions()[0]);
	}

	@Override
	public boolean isAvaliable()
	{
		return MSBuildDotNetImportTarget.EP_NAME.getExtensions().length > 0;
	}

	private static MSBuildDotNetImportTarget findTarget(@Nullable MSBuildSolutionManager.ProjectOptions projectOptions)
	{
		String value = projectOptions == null ? null : projectOptions.target;
		if(value == null)
		{
			return UnknownBuildDotNetImportTarget.INSTANCE;
		}

		return MSBuildDotNetImportTarget.findById(value);
	}

	@Override
	@SuppressWarnings("RequiredXAction")
	public final void setupModule(@Nonnull VirtualFile projectFile,
								  @Nonnull Project domProject,
								  @Nullable MSBuildSolutionManager.ProjectOptions projectOptions,
								  @Nonnull ModifiableModuleRootLayer rootLayer)
	{
		// setup .NET extension
		MSBuildMutableDotNetModuleExtension moduleExtension = rootLayer.getExtensionWithoutCheck(MSBuildMutableDotNetModuleExtension.class);
		assert moduleExtension != null;
		moduleExtension.setEnabled(true);
		moduleExtension.setImportTarget(findTarget(projectOptions));

		setupModuleImpl(rootLayer);

		rootLayer.addOrderEntry(new DotNetLibraryOrderEntryImpl((ModuleRootLayerImpl) rootLayer, "mscorlib"));

		AccessRule.read(() ->
		{
			Walker walker = new Walker(domProject);

			walker.walk(PropertyGroup.class, propertyGroup ->
			{
				String condition = propertyGroup.getCondition().getStringValue();
				if(condition == null)
				{
					propertyGroup.getProperties().forEach(property -> handleDefaultProperty(property, projectFile, moduleExtension));
				}
				else
				{
					MSBuildEvaluateContext context = MSBuildEvaluateContext.from(propertyGroup.getXmlElement(), moduleExtension);

					MSBuildEvaluator evaluator = MSBuildEvaluator.create();

					if(evaluator.evaluate(condition, context, Boolean.class) == Boolean.TRUE)
					{
						propertyGroup.getProperties().forEach(property -> handleProperty(property, projectFile, moduleExtension));
					}
				}
			});

			walker.walk(ItemGroup.class, itemGroup ->
			{
				List<Reference> references = itemGroup.getReferences();
				for(Reference reference : references)
				{
					String hintPath = reference.getHintPath().getValue();
					if(hintPath != null)
					{
						File file = new File(hintPath);
						if(!file.isAbsolute())
						{
							file = new File(VfsUtil.virtualToIoFile(projectFile.getParent()), FileUtil.toSystemIndependentName(hintPath));
						}

						String fullPath = file.getPath();
						try
						{
							fullPath = file.getCanonicalPath();
						}
						catch(IOException ignored)
						{
						}

						String url = VirtualFileManager.constructUrl(DotNetModuleFileType.PROTOCOL, FileUtil.toSystemIndependentName(fullPath)) + URLUtil.ARCHIVE_SEPARATOR;

						Library library = rootLayer.getModuleLibraryTable().createLibrary(file.getName());
						Library.ModifiableModel modifiableModel = library.getModifiableModel();
						modifiableModel.addRoot(url, BinariesOrderRootType.getInstance());
						modifiableModel.commit();
					}
					else
					{
						String includeName = reference.getInclude().getStringValue();
						if(includeName != null)
						{
							rootLayer.addOrderEntry(new DotNetLibraryOrderEntryImpl((ModuleRootLayerImpl) rootLayer, includeName));
						}
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

	@RequiredReadAction
	private void handleDefaultProperty(Property property, VirtualFile projectFile, MSBuildMutableDotNetModuleExtension moduleExtension)
	{
		String xmlElementName = property.getXmlElementName();

		switch(xmlElementName)
		{
			case "Configuration":
			case "Platform":
			{
				MSBuildEvaluator evaluator = MSBuildEvaluator.create();

				MSBuildEvaluateContext context = MSBuildEvaluateContext.from(property.getXmlElement());
				java.util.function.Consumer<String> setter = null;
				switch(xmlElementName)
				{
					case "Configuration":
						context.predefineVariable(MSBuildConfiguration.class, "");
						setter = moduleExtension::setConfiguration;
						break;
					case "Platform":
						context.predefineVariable(MSBuildPlatform.class, "");
						setter = moduleExtension::setPlatform;
						break;
				}

				GenericAttributeValue<String> condition = property.getCondition();

				String value = condition.getValue();
				if(value != null)
				{
					Boolean evaluate = evaluator.evaluate(value, context, Boolean.class);
					if(evaluate == Boolean.TRUE)
					{
						setter.accept(property.getText());
					}
				}

				break;
			}
			default:
				handleProperty(property, projectFile, moduleExtension);
				break;
		}
	}

	@RequiredReadAction
	private void handleProperty(Property property, VirtualFile projectFile, MSBuildMutableDotNetModuleExtension moduleExtension)
	{
		String propertyName = property.getXmlElementName();

		String propertyValue = property.getText();

		switch(propertyName)
		{
			case "DebugSymbols":
				moduleExtension.setAllowDebugInfo(Boolean.parseBoolean(propertyValue));
				break;
			case "DefineConstants":
				List<String> variables = moduleExtension.getVariables();
				variables.clear();
				Collections.addAll(variables, propertyValue.split(";"));
				break;
			case "OutputType":
				switch(propertyValue)
				{
					case "Exe":
						moduleExtension.setTarget(DotNetTarget.EXECUTABLE);
						break;
					case "Library":
						moduleExtension.setTarget(DotNetTarget.LIBRARY);
						break;
				}
				break;
			case "AssemblyName":
				moduleExtension.setFileName(propertyValue + "." + DotNetModuleExtension.OUTPUT_FILE_EXT);
				break;
			case "OutputPath":
			{
				if(new File(propertyValue).isAbsolute())
				{
					moduleExtension.setOutputDir(propertyValue);
				}
				else
				{
					VirtualFile parent = projectFile.getParent();
					assert parent != null;
					moduleExtension.setOutputDir(parent.getPath() + File.separator + FileUtil.toSystemDependentName(propertyValue));
				}
				break;
			}
			case "TargetFrameworkVersion":
			{
				Pair<String, Sdk> sdkPair = findSdk((MSBuildDotNetImportTarget) moduleExtension.getImportTarget(), propertyValue);
				MutableModuleInheritableNamedPointer<Sdk> pointer = moduleExtension.getInheritableSdk();
				if(sdkPair.getSecond() != null)
				{
					pointer.set(null, sdkPair.getSecond());
				}
				else
				{
					pointer.set(null, sdkPair.getFirst());
				}
				break;
			}
		}
	}

	@Nonnull
	private Pair<String, Sdk> findSdk(@Nonnull MSBuildDotNetImportTarget target, @Nullable String sdkVersion)
	{
		if(sdkVersion == null)
		{
			return Pair.create("??", null);
		}

		if(StringUtil.startsWithChar(sdkVersion, 'v'))
		{
			sdkVersion = sdkVersion.substring(1, sdkVersion.length());
		}

		if(target == UnknownBuildDotNetImportTarget.INSTANCE)
		{
			return Pair.create(sdkVersion, null);
		}

		SdkType sdkType = target.getSdkType();

		SdkTable sdkTable = SdkTable.getInstance();
		List<Sdk> sdksOfType = sdkTable.getSdksOfType(sdkType);

		// we need sort predefined first
		ContainerUtil.sort(sdksOfType, new Comparator<Sdk>()
		{
			@Override
			public int compare(Sdk o1, Sdk o2)
			{
				return getWeight(o2) - getWeight(o1);
			}

			private int getWeight(Sdk sdk)
			{
				return sdk.isPredefined() ? 100 : 0;
			}
		});

		for(Sdk sdk : sdksOfType)
		{
			String versionString = sdk.getVersionString();
			if(Comparing.equal(versionString, sdkVersion))
			{
				return Pair.create(null, sdk);
			}
		}
		return Pair.create(sdkVersion, null);
	}
}
