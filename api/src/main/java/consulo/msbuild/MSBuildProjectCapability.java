package consulo.msbuild;

import consulo.annotation.component.ComponentScope;
import consulo.annotation.component.ExtensionAPI;
import consulo.content.bundle.Sdk;
import consulo.module.Module;
import consulo.module.content.layer.ModifiableRootModel;
import consulo.virtualFileSystem.VirtualFile;

import jakarta.annotation.Nonnull;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author VISTALL
 * @since 16/01/2021
 */
@ExtensionAPI(ComponentScope.APPLICATION)
public interface MSBuildProjectCapability
{
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
