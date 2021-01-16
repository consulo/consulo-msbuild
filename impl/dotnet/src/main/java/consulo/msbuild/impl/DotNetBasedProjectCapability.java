package consulo.msbuild.impl;

import com.intellij.openapi.util.io.FileUtil;
import com.intellij.openapi.vfs.LocalFileSystem;
import com.intellij.openapi.vfs.VirtualFile;
import consulo.annotation.access.RequiredReadAction;
import consulo.dotnet.DotNetTarget;
import consulo.dotnet.module.extension.DotNetModuleExtension;
import consulo.msbuild.MSBuildProjectCapability;
import consulo.msbuild.module.extension.MSBuildMutableDotNetModuleExtension;
import consulo.roots.ModifiableModuleRootLayer;
import consulo.roots.impl.ExcludedContentFolderTypeProvider;

import java.io.File;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * @author VISTALL
 * @since 16/01/2021
 */
public abstract class DotNetBasedProjectCapability implements MSBuildProjectCapability
{
	@RequiredReadAction
	private void handleProperty(Map.Entry<String, String> entry, VirtualFile projectFile, MSBuildMutableDotNetModuleExtension moduleExtension, ModifiableModuleRootLayer rootLayer)
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
