package consulo.msbuild;

import com.intellij.openapi.application.Application;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.roots.ModifiableRootModel;
import com.intellij.openapi.vfs.VirtualFile;
import consulo.extensions.StrictExtensionPointName;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Map;

/**
 * @author VISTALL
 * @since 16/01/2021
 */
public interface MSBuildProjectCapability
{
	StrictExtensionPointName<Application, MSBuildProjectCapability> EP_NAME = StrictExtensionPointName.forApplication("consulo.msbuild.projectCapability");

	@Nonnull
	String getId();

	void importModule(Module module, ModifiableRootModel rootModel, VirtualFile projectFile, Map<String, String> properties, List<? extends MSBuildReferencePath> referencePaths);

	default int getWeight()
	{
		return 0;
	}
}
