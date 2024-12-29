package consulo.msbuild;

import jakarta.annotation.Nonnull;
import java.util.Map;

/**
 * @author VISTALL
 * @since 17/01/2021
 */
public interface MSBuildEvaluatedItem
{
	@Nonnull
	String getName();

	@Nonnull
	String getItemSpec();

	@Nonnull
	Map<String, String> getMetadata();
}
