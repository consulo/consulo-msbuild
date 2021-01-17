package consulo.msbuild.csharp;

import com.intellij.openapi.module.Module;
import com.intellij.openapi.roots.ModifiableRootModel;
import com.intellij.openapi.vfs.VirtualFile;
import consulo.msbuild.MSBuildReferencePath;
import consulo.msbuild.csharp.module.extension.MSBuildCSharpMutableModuleExtension;
import consulo.msbuild.impl.DotNetBasedProjectCapability;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Map;

/**
 * @author VISTALL
 * @since 16/01/2021
 */
public class MSBuildCSharpProjectCapability extends DotNetBasedProjectCapability
{
	@Nonnull
	@Override
	public String getId()
	{
		return "CSharp";
	}

	@Override
	public void importModule(Module module, ModifiableRootModel rootModel, VirtualFile projectFile, Map<String, String> properties, List<? extends MSBuildReferencePath> referencePaths)
	{
		initializeDotNetCapability(module, rootModel, projectFile, properties, referencePaths);

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
