package consulo.msbuild.dotnet.impl;

import consulo.content.bundle.Sdk;
import consulo.module.Module;
import consulo.module.content.layer.ModifiableRootModel;
import consulo.msbuild.MSBuildEvaluatedItem;
import consulo.msbuild.MSBuildProcessProvider;
import consulo.msbuild.MSBuildProjectCapability;
import consulo.virtualFileSystem.VirtualFile;

import java.util.List;
import java.util.Map;
import java.util.Set;

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
											  List<? extends MSBuildEvaluatedItem> referencePaths,
											  Set<String> targets)
	{
		for(MSBuildProjectCapability capability : module.getApplication().getExtensionList(MSBuildProjectCapability.class))
		{
			if(DOTNET_CAPABILITY.equals(capability.getId()) && capability.isApplicable(buildProcessProvider))
			{
				capability.importModule(module, rootModel, projectFile, buildProcessProvider, msBuildSdk, properties, referencePaths, targets);
			}
		}
	}
}
