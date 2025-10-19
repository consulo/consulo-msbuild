package consulo.msbuild.csharp;

import consulo.content.bundle.Sdk;
import consulo.module.Module;
import consulo.module.content.layer.ModifiableRootModel;
import consulo.msbuild.MSBuildEvaluatedItem;
import consulo.msbuild.MSBuildProcessProvider;
import consulo.msbuild.csharp.module.extension.MSBuildCSharpMutableModuleExtension;
import consulo.msbuild.dotnet.impl.DotNetLanguageProjectCapability;
import consulo.virtualFileSystem.VirtualFile;
import jakarta.annotation.Nonnull;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author VISTALL
 * @since 16/01/2021
 * <p>
 * "LangVersion" -> "8.0"
 */
public abstract class BaseCSharpProjectCapability extends DotNetLanguageProjectCapability {
    @Nonnull
    @Override
    public String getId() {
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
                             Set<String> targets) {
        initializeDotNetCapability(module, rootModel, projectFile, buildProcessProvider, msBuildSdk, properties, referencePaths, targets);

        MSBuildCSharpMutableModuleExtension csharpExtension = rootModel.getExtensionWithoutCheck(getExtensionId());

        assert csharpExtension != null;

        csharpExtension.setEnabled(true);

        csharpExtension.setAllowUnsafeCode(properties.getOrDefault("AllowUnsafeBlocks", "false").equalsIgnoreCase("true"));
    }

    @Override
    public abstract boolean isApplicable(@Nonnull MSBuildProcessProvider provider);

    @Nonnull
    public abstract String getExtensionId();

    @Override
    public int getWeight() {
        return 100;
    }
}
