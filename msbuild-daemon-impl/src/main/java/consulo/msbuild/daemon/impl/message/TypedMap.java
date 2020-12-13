package consulo.msbuild.daemon.impl.message;

import javax.annotation.Nonnull;
import java.util.LinkedHashMap;

/**
 * @author VISTALL
 * @since 13/12/2020
 */
public class TypedMap<K, V> extends LinkedHashMap<K, V>
{
	private final Class<K> myKeyClass;
	private final Class<V> myValueClass;

	public TypedMap(Class<K> keyClass, Class<V> valueClass)
	{
		myKeyClass = keyClass;
		myValueClass = valueClass;
	}

	@Nonnull
	public Class<K> getKeyClass()
	{
		return myKeyClass;
	}

	@Nonnull
	public Class<V> getValueClass()
	{
		return myValueClass;
	}
}
