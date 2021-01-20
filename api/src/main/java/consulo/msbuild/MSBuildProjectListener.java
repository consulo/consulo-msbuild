package consulo.msbuild;

import com.intellij.util.messages.Topic;

/**
 * @author VISTALL
 * @since 20/01/2021
 */
public interface MSBuildProjectListener
{
	Topic<MSBuildProjectListener> TOPIC = Topic.create("MSBuildProjectListener", MSBuildProjectListener.class);

	void projectsReloaded();
}
