package consulo.msbuild.module.extension;

import consulo.module.extension.ModuleExtension;
import consulo.msbuild.compiler.MSBuildCompileContext;

/**
 * @author VISTALL
 * @since 2018-02-06
 */
public interface MSBuildRootExtension<T extends MSBuildRootExtension<T>> extends ModuleExtension<T>
{
	void build(MSBuildCompileContext context);

	String getConfiguration();

	void setConfiguration(String configuration);

	String getPlatform();

	void setPlatform(String platform);
}
