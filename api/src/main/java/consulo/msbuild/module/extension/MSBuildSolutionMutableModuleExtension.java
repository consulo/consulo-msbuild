package consulo.msbuild.module.extension;

import consulo.module.extension.MutableModuleExtension;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * @author VISTALL
 * @since 18/01/2021
 */
public interface MSBuildSolutionMutableModuleExtension<T extends MSBuildSolutionModuleExtension<T>> extends MSBuildSolutionModuleExtension<T>, MutableModuleExtension<T>
{
	void setSolutionFileUrl(@Nullable String url);

	void setProcessProviderId(@Nonnull String id);

	void setSdkName(@Nonnull String name);
}
