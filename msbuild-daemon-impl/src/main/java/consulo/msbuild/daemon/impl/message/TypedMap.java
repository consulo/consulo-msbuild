package consulo.msbuild.daemon.impl.message;

import consulo.util.lang.Couple;

import jakarta.annotation.Nonnull;
import java.lang.reflect.Field;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author VISTALL
 * @since 13/12/2020
 */
public class TypedMap<K, V> extends LinkedHashMap<K, V>
{
	private static Map<Field, Couple<Class>> ourKeyValueTypeInfo = new ConcurrentHashMap<>();

	@Nonnull
	public static Couple<Class> getKeyValueTypes(@Nonnull Class<?> ownerClass, Field field)
	{
		return ourKeyValueTypeInfo.computeIfAbsent(field, it -> {
			try
			{
				Object stubNewInstance = ownerClass.newInstance();

				TypedMap typedMap = (TypedMap) field.get(stubNewInstance);

				return Couple.of(typedMap.getKeyClass(), typedMap.getValueClass());
			}
			catch(InstantiationException | IllegalAccessException e)
			{
				throw new RuntimeException(e);
			}
		});
	}

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
