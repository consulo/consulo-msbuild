package consulo.msbuild.dotnet.impl;

import consulo.application.ReadAction;
import consulo.application.WriteAction;
import consulo.compiler.ModuleCompilerPathsManager;
import consulo.content.base.BinariesOrderRootType;
import consulo.content.base.ExcludedContentFolderTypeProvider;
import consulo.content.bundle.Sdk;
import consulo.content.library.Library;
import consulo.content.library.LibraryTable;
import consulo.dotnet.DotNetTarget;
import consulo.dotnet.dll.DotNetModuleFileType;
import consulo.dotnet.module.extension.DotNetModuleExtension;
import consulo.dotnet.module.extension.DotNetMutableModuleExtension;
import consulo.language.content.ProductionContentFolderTypeProvider;
import consulo.language.content.ProductionResourceContentFolderTypeProvider;
import consulo.language.content.TestContentFolderTypeProvider;
import consulo.language.content.TestResourceContentFolderTypeProvider;
import consulo.module.Module;
import consulo.module.content.layer.ModifiableModuleRootLayer;
import consulo.module.content.layer.ModifiableRootModel;
import consulo.msbuild.MSBuildEvaluatedItem;
import consulo.msbuild.MSBuildProcessProvider;
import consulo.msbuild.MSBuildProjectCapability;
import consulo.msbuild.module.extension.MSBuildSolutionModuleExtension;
import consulo.msbuild.solution.model.WProject;
import consulo.util.io.FileUtil;
import consulo.virtualFileSystem.LocalFileSystem;
import consulo.virtualFileSystem.StandardFileSystems;
import consulo.virtualFileSystem.VirtualFile;
import consulo.virtualFileSystem.VirtualFileManager;
import consulo.virtualFileSystem.archive.ArchiveFileSystem;

import jakarta.annotation.Nonnull;
import java.io.File;
import java.util.*;

/**
 * @author VISTALL
 * @since 18/01/2021
 * <p>
 * "Configurations" -> "Debug;Release"
 * "DefineConstants" -> "TRACE;DEBUG;NETCOREAPP;NETCOREAPP3_1;"
 * "PackagedShimOutputRootDirectory" -> "bin\Debug\netcoreapp3.1\"
 * "Configuration" -> "Debug"
 * "OutputPath" -> "bin\Debug\netcoreapp3.1\"
 * "ProjectName" -> "WindowsFormsApp1"
 * "AssemblyTitle" -> "WindowsFormsApp1"
 * "SDKReferenceDirectoryRoot" -> "C:\Users\VISTALL\AppData\Local\Microsoft SDKs;C:\Program Files (x86)\Microsoft SDKs"
 * "PlatformName" -> "AnyCPU"
 * "Platform" -> "Any CPU"
 * "OutDir" -> "bin\Debug\netcoreapp3.1\"
 * "AvailablePlatforms" -> "Any CPU,x86,x64"
 * "DebugSymbols" -> "true"
 * "ProjectDir" -> "C:\Users\VISTALL\Documents\VS\WindowsFormsApp1\"
 * <p>
 * "NoWarn" -> "1701;1702"
 */
public abstract class BaseDotNetProjectCapability implements MSBuildProjectCapability
{
	@Nonnull
	@Override
	public String getId()
	{
		return DOTNET_CAPABILITY;
	}

	@Override
	public abstract boolean isApplicable(@Nonnull MSBuildProcessProvider provider);

	@Nonnull
	protected abstract Class<? extends DotNetMutableModuleExtension> getMutableExtensionClass();

	@Override
	public void importModule(Module module,
							 ModifiableRootModel rootModel,
							 VirtualFile projectFile,
							 MSBuildProcessProvider buildProcessProvider,
							 Sdk msBuildSdk,
							 Map<String, String> properties,
							 List<? extends MSBuildEvaluatedItem> referencePaths,
							 Set<String> targets)
	{
		DotNetMutableModuleExtension<?> extension = rootModel.getExtensionWithoutCheck(getMutableExtensionClass());
		assert extension != null;
		extension.setEnabled(true);

		for(Map.Entry<String, String> entry : properties.entrySet())
		{
			handleProperty(entry, projectFile, extension, rootModel);
		}

		LibraryTable moduleLibraryTable = rootModel.getModuleLibraryTable();
		LibraryTable.ModifiableModel modifiableLibraryModel = moduleLibraryTable.getModifiableModel();

		for(MSBuildEvaluatedItem referencePath : referencePaths)
		{
			String fullPath = referencePath.getMetadata().get("FullPath");

			String referenceSourceTarget = referencePath.getMetadata().get("ReferenceSourceTarget");
			if("ProjectReference".equals(referenceSourceTarget))
			{
				String msBuildSourceProjectFile = referencePath.getMetadata().get("MSBuildSourceProjectFile");

				VirtualFile refProjectFile = LocalFileSystem.getInstance().findFileByPath(msBuildSourceProjectFile);
				if(refProjectFile != null)
				{
					MSBuildSolutionModuleExtension<?> solutionModuleExtension = MSBuildSolutionModuleExtension.getSolutionModuleExtension(module.getProject());
					if(solutionModuleExtension != null)
					{
						for(WProject wProject : solutionModuleExtension.getValidProjects())
						{
							if(Objects.equals(wProject.getVirtualFile(), refProjectFile))
							{
								ReadAction.run(() -> rootModel.addInvalidModuleEntry(wProject.getName()));
							}
						}
					}
				}
			}
			else
			{
				String archiveUrl = VirtualFileManager.constructUrl(DotNetModuleFileType.PROTOCOL, FileUtil.toSystemIndependentName(fullPath) + ArchiveFileSystem.ARCHIVE_SEPARATOR);

				Library library = modifiableLibraryModel.createLibrary(null);

				Library.ModifiableModel modifiableModel = library.getModifiableModel();
				modifiableModel.addRoot(archiveUrl, BinariesOrderRootType.getInstance());
				modifiableModel.commit();
			}
		}

		postInitialize(extension, properties, buildProcessProvider, msBuildSdk);

		WriteAction.runAndWait(modifiableLibraryModel::commit);
	}

	protected void postInitialize(DotNetMutableModuleExtension<?> dotNetExtension, Map<String, String> properties, MSBuildProcessProvider buildProcessProvider, Sdk msBuildSdk)
	{
	}

	private void handleProperty(Map.Entry<String, String> entry, VirtualFile projectFile, DotNetMutableModuleExtension<?> moduleExtension, ModifiableModuleRootLayer rootLayer)
	{
		String propertyName = entry.getKey();

		String propertyValue = entry.getValue();

		VirtualFile parent = projectFile.getParent();
		switch(propertyName)
		{
			case "TargetFrameworkMonikerAssemblyAttributesPath": // obj/Debug/net8.0/.NETCoreApp,Version=v8.0.AssemblyAttributes.cs
			case "GeneratedGlobalUsingsFile": // obj/Debug/net8.0/untitled4.GlobalUsings.g.cs
			case "GeneratedAssemblyInfoFile": //obj/Debug/net8.0/untitled4.AssemblyInfo.cs
			{
				assert parent != null;
				String generatedSourceFile = parent.getPath() + File.separator + FileUtil.toSystemDependentName(propertyValue);
				String generatedSourceFileUrl = VirtualFileManager.constructUrl(StandardFileSystems.FILE_PROTOCOL, FileUtil.toSystemIndependentName(generatedSourceFile));

				rootLayer.addContentEntry(generatedSourceFileUrl).addFolder(generatedSourceFileUrl, ProductionContentFolderTypeProvider.getInstance());
				break;
			}
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
				String path = null;
				if(new File(propertyValue).isAbsolute())
				{
					moduleExtension.setOutputDir(path = propertyValue);
				}
				else
				{
					assert parent != null;
					moduleExtension.setOutputDir(path = (parent.getPath() + File.separator + FileUtil.toSystemDependentName(propertyValue)));
				}

				String url = VirtualFileManager.constructUrl(StandardFileSystems.FILE_PROTOCOL, FileUtil.toSystemIndependentName(path));

				rootLayer.addContentEntry(url).addFolder(url, ExcludedContentFolderTypeProvider.getInstance());

				ModuleCompilerPathsManager compilerPathsManager = ModuleCompilerPathsManager.getInstance(moduleExtension.getModule());
				compilerPathsManager.setInheritedCompilerOutput(false);
				compilerPathsManager.setExcludeOutput(true);
				
				compilerPathsManager.setCompilerOutputUrl(ProductionContentFolderTypeProvider.getInstance(), url);
				compilerPathsManager.setCompilerOutputUrl(ProductionResourceContentFolderTypeProvider.getInstance(), url);
				compilerPathsManager.setCompilerOutputUrl(TestContentFolderTypeProvider.getInstance(), url);
				compilerPathsManager.setCompilerOutputUrl(TestResourceContentFolderTypeProvider.getInstance(), url);
				break;
			}
			case "TargetFrameworkVersion":
			{
				break;
			}
		}
	}
}
