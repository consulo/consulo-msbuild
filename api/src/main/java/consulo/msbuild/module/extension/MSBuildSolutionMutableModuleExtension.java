package consulo.msbuild.module.extension;

import consulo.module.extension.MutableModuleExtension;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

/**
 * @author VISTALL
 * @since 18/01/2021
 */
public interface MSBuildSolutionMutableModuleExtension<T extends MSBuildSolutionModuleExtension<T>> extends MSBuildSolutionModuleExtension<T>, MutableModuleExtension<T>
{
	void setSolutionFileUrl(@Nullable String url);

	void setProjectFileUrl(@Nullable String url);

	void setProjectUUID(@Nullable String uuid);

	void setProcessProviderId(@Nonnull String id);

	void setSdkName(@Nonnull String name);
}
