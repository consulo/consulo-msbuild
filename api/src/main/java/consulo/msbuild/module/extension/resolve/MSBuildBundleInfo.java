package consulo.msbuild.module.extension.resolve;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import consulo.msbuild.importProvider.item.MSBuildImportTarget;
import consulo.msbuild.module.extension.MSBuildRootExtension;
import consulo.ui.image.Image;

/**
 * @author VISTALL
 * @since 2018-06-22
 */
public interface MSBuildBundleInfo
{
	@Nonnull
	String getId();

	@Nonnull
	String getName();

	@Nonnull
	Image getIcon();

	@Nullable
	Object resolveSdk(@Nonnull MSBuildImportTarget target, @Nonnull MSBuildRootExtension<?> rootExtension);
}
