package consulo.msbuild.module.extension;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import consulo.module.extension.ModuleExtension;
import consulo.msbuild.compiler.MSBuildCompileContext;
import consulo.msbuild.importProvider.item.MSBuildImportTarget;
import consulo.msbuild.module.extension.resolve.MSBuildBundleInfo;

/**
 * @author VISTALL
 * @since 2018-02-06
 */
public interface MSBuildRootExtension<T extends MSBuildRootExtension<T>> extends ModuleExtension<T>
{
	@Nonnull
	MSBuildBundleInfo getBundleInfo();

	@Nullable
	Object resolveAutoSdk();

	@Nonnull
	MSBuildImportTarget getImportTarget();

	void build(MSBuildCompileContext context);

	String getConfiguration();

	void setConfiguration(String configuration);

	String getPlatform();

	void setPlatform(String platform);
}
