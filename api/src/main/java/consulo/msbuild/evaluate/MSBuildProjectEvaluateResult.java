package consulo.msbuild.evaluate;

import javax.annotation.Nonnull;
import java.util.Map;

/**
 * @author VISTALL
 * @since 2020-06-24
 */
public class MSBuildProjectEvaluateResult
{
	private final Map<String, String> myProperties;

	public MSBuildProjectEvaluateResult(Map<String, String> properties)
	{
		myProperties = properties;
	}

	@Nonnull
	public Map<String, String> getProperties()
	{
		return myProperties;
	}
}
