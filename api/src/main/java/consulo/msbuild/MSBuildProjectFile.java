package consulo.msbuild;

import consulo.annotation.component.ComponentScope;
import consulo.annotation.component.ExtensionAPI;
import consulo.application.Application;
import consulo.component.extension.ExtensionPointCacheKey;

import javax.annotation.Nonnull;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * @author VISTALL
 * @since 10/01/2023
 */
@ExtensionAPI(ComponentScope.APPLICATION)
public interface MSBuildProjectFile
{
	ExtensionPointCacheKey<MSBuildProjectFile, Set<String>> CACHE_KEY = ExtensionPointCacheKey.create("MSBuildProjectFile", walker ->
	{
		Set<String> extensions = new LinkedHashSet<>();
		walker.walk(file -> extensions.add(file.getExtension()));
		return extensions;
	});

	@Nonnull
	@Deprecated
	public static Set<String> listAll()
	{
		return listAll(Application.get());
	}

	@Nonnull
	public static Set<String> listAll(@Nonnull Application application)
	{
		return application.getExtensionPoint(MSBuildProjectFile.class).getOrBuildCache(MSBuildProjectFile.CACHE_KEY);
	}

	@Nonnull
	String getExtension();
}
