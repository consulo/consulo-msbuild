package consulo.msbuild.daemon.impl.message.model;

import consulo.msbuild.daemon.impl.message.TypedMap;

/**
 * @author VISTALL
 * @since 13/12/2020
 */
public class MSBuildEvaluatedItem implements DataObject
{
	public TypedMap<String, String> Metadata = new TypedMap<>(String.class, String.class);

	public String ItemSpec;

	public String Name;
}
