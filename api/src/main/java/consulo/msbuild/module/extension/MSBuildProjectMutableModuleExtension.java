package consulo.msbuild.module.extension;

/**
 * @author VISTALL
 * @since 18/01/2021
 */
public interface MSBuildProjectMutableModuleExtension<T extends MSBuildProjectModuleExtension<T>> extends MSBuildProjectModuleExtension<T>
{
	void setPlatform(String platform);

	void setConfiguration(String configuration);
}
