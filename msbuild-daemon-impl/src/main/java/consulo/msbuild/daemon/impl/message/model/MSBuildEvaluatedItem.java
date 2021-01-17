package consulo.msbuild.daemon.impl.message.model;

import consulo.msbuild.MSBuildReferencePath;
import consulo.msbuild.daemon.impl.message.TypedMap;

import javax.annotation.Nonnull;
import java.util.Map;

/**
 * @author VISTALL
 * @since 13/12/2020
 */
public class MSBuildEvaluatedItem implements DataObject, MSBuildReferencePath
{
	public TypedMap<String, String> Metadata = new TypedMap<>(String.class, String.class);

	public String ItemSpec;

	public String Name;

	@Nonnull
	@Override
	public String getName()
	{
		return Name;
	}

	@Nonnull
	@Override
	public Map<String, String> getMetadata()
	{
		return Metadata;
	}
}
