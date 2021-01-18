package consulo.msbuild.impl;

import com.intellij.openapi.application.Application;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.projectRoots.Sdk;
import com.intellij.openapi.roots.ModifiableRootModel;
import com.intellij.openapi.vfs.VirtualFile;
import consulo.msbuild.MSBuildProcessProvider;
import consulo.msbuild.MSBuildProjectCapability;
import consulo.msbuild.MSBuildReferencePath;

import java.util.List;
import java.util.Map;

/**
 * @author VISTALL
 * @since 16/01/2021
 */
public abstract class DotNetLanguageProjectCapability implements MSBuildProjectCapability
{
	protected void initializeDotNetCapability(Module module,
											  ModifiableRootModel rootModel,
											  VirtualFile projectFile,
											  MSBuildProcessProvider buildProcessProvider,
											  Sdk msBuildSdk, Map<String, String> properties,
											  List<? extends MSBuildReferencePath> referencePaths)
	{
		for(MSBuildProjectCapability capability : MSBuildProjectCapability.EP_NAME.getExtensionList(Application.get()))
		{
			if(DOTNET_CAPABILITY.equals(capability.getId()) && capability.isApplicable(buildProcessProvider))
			{
				capability.importModule(module, rootModel, projectFile, buildProcessProvider, msBuildSdk, properties, referencePaths);
			}
		}
	}
}
