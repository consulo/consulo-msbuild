package consulo.msbuild;

import consulo.annotation.component.ComponentScope;
import consulo.annotation.component.TopicAPI;

/**
 * @author VISTALL
 * @since 20/01/2021
 */
@TopicAPI(ComponentScope.PROJECT)
public interface MSBuildProjectListener
{
	void projectsReloaded();
}
