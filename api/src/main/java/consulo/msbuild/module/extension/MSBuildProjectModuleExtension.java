package consulo.msbuild.module.extension;

import consulo.module.extension.ModuleExtension;
import consulo.msbuild.compiler.MSBuildCompileContext;

/**
 * @author VISTALL
 * @since 2018-02-06
 *
 * Module Extension for each MSBuild Project inside solution
 */
public interface MSBuildProjectModuleExtension<T extends MSBuildProjectModuleExtension<T>> extends ModuleExtension<T>
{
	@Deprecated
	void build(MSBuildCompileContext context);

	@Deprecated
	String getConfiguration();

	@Deprecated
	String getPlatform();
}
