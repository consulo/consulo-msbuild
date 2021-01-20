package consulo.msbuild.csharp;

import com.intellij.openapi.module.Module;
import com.intellij.openapi.projectRoots.Sdk;
import com.intellij.openapi.roots.ModifiableRootModel;
import com.intellij.openapi.vfs.VirtualFile;
import consulo.msbuild.MSBuildProcessProvider;
import consulo.msbuild.MSBuildEvaluatedItem;
import consulo.msbuild.csharp.module.extension.MSBuildCSharpMutableModuleExtension;
import consulo.msbuild.impl.DotNetLanguageProjectCapability;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author VISTALL
 * @since 16/01/2021
 * <p>
 * "LangVersion" -> "8.0"
 */
public class MSBuildCSharpProjectCapability extends DotNetLanguageProjectCapability
{
	@Nonnull
	@Override
	public String getId()
	{
		return CSHARP_CAPABILITY;
	}

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
		initializeDotNetCapability(module, rootModel, projectFile, buildProcessProvider, msBuildSdk, properties, referencePaths, targets);

		MSBuildCSharpMutableModuleExtension csharpExtension = rootModel.getExtensionWithoutCheck("msbuild-csharp");
		assert csharpExtension != null;

		csharpExtension.setEnabled(true);
	}

	@Override
	public int getWeight()
	{
		return 100;
	}
}
