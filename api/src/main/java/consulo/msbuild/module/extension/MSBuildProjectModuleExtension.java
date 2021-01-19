package consulo.msbuild.module.extension;

import consulo.module.extension.ModuleExtension;
import consulo.msbuild.compiler.MSBuildCompileContext;

import javax.annotation.Nonnull;
import java.util.Set;

/**
 * @author VISTALL
 * @since 2018-02-06
 *
 * Module Extension for each MSBuild Project inside solution
 */
public interface MSBuildProjectModuleExtension<T extends MSBuildProjectModuleExtension<T>> extends ModuleExtension<T>
{
	void build(MSBuildCompileContext context);

	@Nonnull
	Set<String> getTargets();

	@Deprecated
	String getConfiguration();

	@Deprecated
	String getPlatform();
}
