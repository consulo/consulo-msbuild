package consulo.msbuild;

import com.intellij.openapi.application.Application;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.projectRoots.Sdk;
import com.intellij.openapi.roots.ModifiableRootModel;
import com.intellij.openapi.vfs.VirtualFile;
import consulo.extensions.StrictExtensionPointName;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author VISTALL
 * @since 16/01/2021
 */
public interface MSBuildProjectCapability
{
	StrictExtensionPointName<Application, MSBuildProjectCapability> EP_NAME = StrictExtensionPointName.forApplication("consulo.msbuild.projectCapability");

	// Internal Capablity, which is not provided by MSBuild for .NET
	String DOTNET_CAPABILITY = "_DotNet";

	String CSHARP_CAPABILITY = "CSharp";

	@Nonnull
	String getId();

	void importModule(Module module,
					  ModifiableRootModel rootModel,
					  VirtualFile projectFile,
					  MSBuildProcessProvider buildProcessProvider,
					  Sdk msBuildSdk,
					  Map<String, String> properties,
					  List<? extends MSBuildEvaluatedItem> referencePaths,
					  Set<String> targets);

	default boolean isApplicable(@Nonnull MSBuildProcessProvider provider)
	{
		return true;
	}

	default int getWeight()
	{
		return 0;
	}
}
