package consulo.msbuild;

import consulo.annotation.component.ComponentScope;
import consulo.annotation.component.ExtensionAPI;
import consulo.application.Application;
import consulo.component.extension.ExtensionPointCacheKey;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * @author VISTALL
 * @since 10/01/2023
 */
@ExtensionAPI(ComponentScope.APPLICATION)
public interface MSBuildProjectFile {
    ExtensionPointCacheKey<MSBuildProjectFile, Set<String>> CACHE_KEY = ExtensionPointCacheKey.create("MSBuildProjectFile", walker ->
    {
        Set<String> extensions = new LinkedHashSet<>();
        walker.walk(file -> extensions.add(file.getExtension()));
        return extensions;
    });

    @Nonnull
    @Deprecated
    public static Set<String> listAll() {
        return listAll(Application.get());
    }

    @Nonnull
    public static Set<String> listAll(@Nonnull Application application) {
        return application.getExtensionPoint(MSBuildProjectFile.class).getOrBuildCache(MSBuildProjectFile.CACHE_KEY);
    }

    @Nullable
    public static MSBuildProjectFile findByExtension(@Nonnull Application application, @Nullable String extension) {
        if (extension == null) {
            return null;
        }
        return application.getExtensionPoint(MSBuildProjectFile.class).findFirstSafe(file -> extension.equalsIgnoreCase(file.getExtension()));
    }

    @Nonnull
    String getExtension();

    @Nonnull
    default Set<String> getCapabilities() {
        return Set.of();
    }

    @Nonnull
    default Set<String> getItemTypes() {
        return Set.of();
    }

    @Nonnull
    default String getConfiguration() {
        return "Debug";
    }

    @Nonnull
    default String getPlatform() {
        return "AnyCPU";
    }
}
