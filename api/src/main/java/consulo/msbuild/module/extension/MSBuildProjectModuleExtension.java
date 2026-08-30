package consulo.msbuild.module.extension;

import consulo.module.extension.ModuleExtension;

/**
 * @author VISTALL
 * @since 2018-02-06
 *
 * Module Extension for each MSBuild Project inside solution
 */
public interface MSBuildProjectModuleExtension<T extends MSBuildProjectModuleExtension<T>> extends ModuleExtension<T>
{
	@Deprecated
	String getConfiguration();

	@Deprecated
	String getPlatform();
}
