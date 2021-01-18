package consulo.msbuild.impl;

import com.intellij.openapi.application.WriteAction;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.projectRoots.Sdk;
import com.intellij.openapi.roots.ModifiableRootModel;
import com.intellij.openapi.roots.libraries.Library;
import com.intellij.openapi.roots.libraries.LibraryTable;
import com.intellij.openapi.util.io.FileUtil;
import com.intellij.openapi.vfs.LocalFileSystem;
import com.intellij.openapi.vfs.VirtualFile;
import consulo.dotnet.DotNetTarget;
import consulo.dotnet.module.extension.DotNetModuleExtension;
import consulo.dotnet.module.extension.DotNetMutableModuleExtension;
import consulo.msbuild.MSBuildProcessProvider;
import consulo.msbuild.MSBuildProjectCapability;
import consulo.msbuild.MSBuildReferencePath;
import consulo.roots.ModifiableModuleRootLayer;
import consulo.roots.impl.ExcludedContentFolderTypeProvider;
import consulo.roots.types.BinariesOrderRootType;
import consulo.vfs.util.ArchiveVfsUtil;

import javax.annotation.Nonnull;
import java.io.File;
import java.util.Collections;
import java.util.List;
import java.util.Map;

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
							 List<? extends MSBuildReferencePath> referencePaths)
	{
		DotNetMutableModuleExtension<?> dotNetExtension = rootModel.getExtensionWithoutCheck(getMutableExtensionClass());
		assert dotNetExtension != null;
		dotNetExtension.setEnabled(true);

		for(Map.Entry<String, String> entry : properties.entrySet())
		{
			handleProperty(entry, projectFile, dotNetExtension, rootModel);
		}

		LibraryTable moduleLibraryTable = rootModel.getModuleLibraryTable();
		LibraryTable.ModifiableModel modifiableLibraryModel = moduleLibraryTable.getModifiableModel();

		for(MSBuildReferencePath referencePath : referencePaths)
		{
			String fullPath = referencePath.getMetadata().get("FullPath");

			VirtualFile dllFile = LocalFileSystem.getInstance().refreshAndFindFileByIoFile(new File(fullPath));
			if(dllFile == null)
			{
				continue;
			}

			VirtualFile dllArchive = ArchiveVfsUtil.getArchiveRootForLocalFile(dllFile);
			if(dllArchive == null)
			{
				continue;
			}

			Library library = modifiableLibraryModel.createLibrary(null);

			Library.ModifiableModel modifiableModel = library.getModifiableModel();
			modifiableModel.addRoot(dllArchive, BinariesOrderRootType.getInstance());
			modifiableModel.commit();
		}

		postInitialize(dotNetExtension, properties, buildProcessProvider, msBuildSdk);

		WriteAction.runAndWait(modifiableLibraryModel::commit);
	}

	protected void postInitialize(DotNetMutableModuleExtension<?> dotNetExtension, Map<String, String> properties, MSBuildProcessProvider buildProcessProvider, Sdk msBuildSdk)
	{
	}

	private void handleProperty(Map.Entry<String, String> entry, VirtualFile projectFile, DotNetMutableModuleExtension<?> moduleExtension, ModifiableModuleRootLayer rootLayer)
	{
		String propertyName = entry.getKey();

		String propertyValue = entry.getValue();

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
				String path = null;
				if(new File(propertyValue).isAbsolute())
				{
					moduleExtension.setOutputDir(path = propertyValue);
				}
				else
				{
					VirtualFile parent = projectFile.getParent();
					assert parent != null;
					moduleExtension.setOutputDir(path = (parent.getPath() + File.separator + FileUtil.toSystemDependentName(propertyValue)));
				}

				VirtualFile file = LocalFileSystem.getInstance().findFileByPath(path);
				if(file != null)
				{
					rootLayer.addContentEntry(file).addFolder(file, ExcludedContentFolderTypeProvider.getInstance());
				}
				break;
			}
			case "TargetFrameworkVersion":
			{

				break;
			}
		}
	}
}
