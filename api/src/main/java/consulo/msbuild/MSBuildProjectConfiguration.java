package consulo.msbuild;

import jakarta.annotation.Nonnull;

/**
 * @author VISTALL
 * @since 2026-10-10
 */
public record MSBuildProjectConfiguration(@Nonnull String configuration, @Nonnull String platform) {
}
