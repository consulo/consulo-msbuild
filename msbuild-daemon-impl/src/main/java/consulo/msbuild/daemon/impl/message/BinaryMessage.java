package consulo.msbuild.daemon.impl.message;

import consulo.msbuild.daemon.impl.message.model.DataObject;
import consulo.msbuild.daemon.impl.message.model.EnumInt;
import consulo.util.lang.Couple;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import java.lang.reflect.*;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.StandardCharsets;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.BiConsumer;

/**
 * @author VISTALL
 * @since 12/12/2020
 */
public class BinaryMessage implements DataObject
{
	public final int Id;

	public final String Name;

	public final String Target;

	public final Map<String, Object> Arguments = new LinkedHashMap<>();

	public BinaryMessage(int id, @Nonnull DaemonMessage daemonMessage)
	{
		Id = id;
		Name = daemonMessage.getName();
		Target = daemonMessage.getTarget();

		fillArguments(daemonMessage, Arguments);
	}

	private void fillArguments(Object o, Map<String, Object> arguments)
	{
		for(Field field : o.getClass().getDeclaredFields())
		{
			try
			{
				if(!Modifier.isPublic(field.getModifiers()) || Modifier.isStatic(field.getModifiers()))
				{
					continue;
				}

				Object fieldValue = field.get(o);

				arguments.put(field.getName(), fieldValue);
			}
			catch(IllegalAccessException e)
			{
				throw new IllegalArgumentException(e);
			}
		}
	}

	public BinaryMessage(ByteBuffer buffer)
	{
		Id = buffer.getInt();

		Name = readString(buffer);

		Target = readString(buffer);

		int argsCount = buffer.getInt();

		for(int i = 0; i < argsCount; i++)
		{
			String argName = readString(buffer);

			Object argValue = readValue(buffer);

			Arguments.put(argName, argValue);
		}
	}

	@Nonnull
	@SuppressWarnings("unchecked")
	public <R extends DataObject> R toModel(DaemonMessage<R> consumer)
	{
		Type[] genericInterfaces = consumer.getClass().getGenericInterfaces();
		for(Type genericInterface : genericInterfaces)
		{
			if(genericInterface instanceof ParameterizedType && ((ParameterizedType) genericInterface).getRawType() == DaemonMessage.class)
			{
				return toModel((Class<R>) ((ParameterizedType) genericInterface).getActualTypeArguments()[0]);
			}
		}
		throw new IllegalArgumentException("Wrong type " + consumer.getClass());
	}

	@Nonnull
	@SuppressWarnings("unchecked")
	public <T> T toModel(Class<T> clazz)
	{
		if(clazz == BinaryMessage.class)
		{
			return (T) this;
		}

		return toModel(Arguments, clazz);
	}

	@Nonnull
	@SuppressWarnings("unchecked")
	private static <T> T toModel(Map<String, Object> arguments, Class<T> clazz)
	{
		try
		{
			T model = clazz.getDeclaredConstructor().newInstance();

			for(Field field : clazz.getDeclaredFields())
			{
				Object fieldValue = arguments.get(field.getName());
				if(fieldValue != null)
				{
					if(DataObject.class.isAssignableFrom(field.getType()))
					{
						fieldValue = toModel((Map<String, Object>) fieldValue, field.getType());
					}
					else if(field.getType() == TypedMap.class)
					{
						Couple<Class> types = TypedMap.getKeyValueTypes(clazz, field);

						if(types.getFirst() != String.class)
						{
							throw new UnsupportedOperationException(types.toString());
						}

						TypedMap map = new TypedMap(types.getFirst(), types.getSecond());

						Map<?, ?> mappedFieldValue = (Map) fieldValue;

						for(Map.Entry e : mappedFieldValue.entrySet())
						{
							String key = (String) e.getKey();
							Object value = e.getValue();

							Object convertedValue;
							if(map.getValueClass().isArray())
							{
								convertedValue = mapArray(map.getValueClass(), value);
							}
							else if(DataObject.class.isAssignableFrom(map.getValueClass()))
							{
								convertedValue = toModel((Map<String, Object>) value, map.getValueClass());
							}
							else
							{
								convertedValue = value;
							}

							map.put(key, convertedValue);
						}

						fieldValue = map;
					}
					else if(field.getType().isArray() && DataObject.class.isAssignableFrom(field.getType().getComponentType()))
					{
						fieldValue = mapArray(field.getType(), fieldValue);
					}

					field.set(model, fieldValue);
				}
			}
			return model;
		}
		catch(InstantiationException | InvocationTargetException | IllegalAccessException | NoSuchMethodException e)
		{
			throw new RuntimeException(e);
		}
	}

	@SuppressWarnings("unchecked")
	private static Object mapArray(Class arrayType, Object arrayValue)
	{
		Class componentType = arrayType.getComponentType();

		int length = Array.getLength(arrayValue);

		Object newArray = Array.newInstance(componentType, length);

		for(int i = 0; i < length; i++)
		{
			Array.set(newArray, i, toModel((Map<String, Object>) Array.get(arrayValue, i), componentType));
		}

		return newArray;
	}

	public byte[] toByteArray()
	{
		ByteBuffer byteBuffer = ByteBuffer.allocateDirect(65535).order(ByteOrder.LITTLE_ENDIAN);

		byteBuffer.putInt(Id);

		writeString(byteBuffer, Name);

		writeString(byteBuffer, Target);

		byteBuffer.putInt(Arguments.size());

		for(Map.Entry<String, Object> entry : Arguments.entrySet())
		{
			writeString(byteBuffer, entry.getKey());

			Object value = entry.getValue();
			writeValue(byteBuffer, value);
		}

		int position = byteBuffer.position();

		byteBuffer.position(0);

		byte[] array = new byte[position];

		byteBuffer.get(array);

		return array;
	}

	private void writeValue(@Nonnull ByteBuffer byteBuffer, @Nullable Object value)
	{
		if(value == null)
		{
			byteBuffer.put((byte) TypeCode.Null.ordinal());
			return;
		}

		if(value instanceof String)
		{
			byteBuffer.put((byte) TypeCode.String.ordinal());
			writeString(byteBuffer, (String) value);
		}
		else if(value instanceof Integer)
		{
			byteBuffer.put((byte) TypeCode.Int32.ordinal());
			byteBuffer.putInt((Integer) value);
		}
		else if(value instanceof Long)
		{
			byteBuffer.put((byte) TypeCode.Int64.ordinal());
			byteBuffer.putLong((Long) value);
		}
		else if(value.getClass().isArray())
		{
			byteBuffer.put((byte) TypeCode.Array.ordinal());
			writeArray(byteBuffer, value.getClass().getComponentType(), value);
		}
		else if(value instanceof EnumInt)
		{
			byteBuffer.put((byte) TypeCode.Int64.ordinal());
			byteBuffer.putLong(((EnumInt) value).getValue());
		}
		else if(value instanceof Boolean)
		{
			byteBuffer.put((byte) TypeCode.Boolean.ordinal());
			byteBuffer.put(((Boolean) value) ? (byte) 1 : 0);
		}
		else if(value instanceof TypedMap)
		{
			byteBuffer.put((byte) TypeCode.Map.ordinal());

			TypedMap map = (TypedMap) value;

			Class keyClass = map.getKeyClass();

			Class valueClass = map.getValueClass();

			writeArray(byteBuffer, keyClass, map.keySet().toArray(i -> Array.newInstance(keyClass, i)));

			writeArray(byteBuffer, valueClass, map.values().toArray(i -> Array.newInstance(valueClass, i)));
		}
		else
		{
			if(!(value instanceof DataObject))
			{
				throw new IllegalArgumentException(value.getClass().getName());
			}

			TypedMap<String, Object> arguments = new TypedMap<>(String.class, Object.class);
			fillArguments(value, arguments);
			writeValue(byteBuffer, arguments);
		}
	}

	private void writeArray(ByteBuffer byteBuffer, Class typeClass, Object array)
	{
		int rank = 1; // TODO [VISTALL] not supported deep array

		int length = Array.getLength(array);

		byteBuffer.putInt(rank);

		byteBuffer.putInt(length);

		if(rank == 1)
		{
			if(typeClass == String.class)
			{
				byteBuffer.put((byte) TypeCode.String.ordinal());
				forEachArrayElement(byteBuffer, array, (b, o) -> writeString(b, (String) o));
			}
			else
			{
				byteBuffer.put((byte) TypeCode.Object.ordinal());

				forEachArrayElement(byteBuffer, array, this::writeValue);
			}
		}
		else
		{
			throw new UnsupportedOperationException();
		}
	}

	private void forEachArrayElement(ByteBuffer byteBuffer, Object array, BiConsumer<ByteBuffer, Object> writer)
	{
		for(int i = 0; i < Array.getLength(array); i++)
		{
			writer.accept(byteBuffer, Array.get(array, i));
		}
	}

	private void writeString(ByteBuffer byteBuffer, String string)
	{
		byte[] bytes = string.getBytes(StandardCharsets.UTF_8);
		byteBuffer.putInt(bytes.length);
		byteBuffer.put(bytes);
	}

	private Object readArray(ByteBuffer byteBuffer)
	{
		int rank = byteBuffer.getInt();

		int[] lenghts = new int[rank];
		for(int i = 0; i < rank; i++)
		{
			lenghts[i] = byteBuffer.getInt();
		}

		if(rank == 1)
		{
			TypeCode typeCode = TypeCode.VALUES[byteBuffer.get() & 0xFF];
			int lenght = lenghts[0];

			switch(typeCode)
			{
				default:
					throw new UnsupportedOperationException(typeCode.name());
					//				case Array:
					//					break;
				case String:
					String[] strArray = new String[lenght];
					for(int i = 0; i < lenght; i++)
					{
						strArray[i] = readString(byteBuffer);
					}
					return strArray;
				case Object:
					Object[] objArray = new Object[lenght];
					for(int i = 0; i < lenght; i++)
					{
						objArray[i] = readValue(byteBuffer);
					}
					return objArray;
				//				case Map:
				//					break;
				//				case Null:
				//					break;
				case Boolean:
					boolean[] boolArray = new boolean[lenght];
					for(int i = 0; i < lenght; i++)
					{
						boolArray[i] = byteBuffer.get() == 1;
					}
					return boolArray;
				case Single:
					float[] floatArray = new float[lenght];
					for(int i = 0; i < lenght; i++)
					{
						floatArray[i] = byteBuffer.getFloat();
					}
					return floatArray;
				case Double:
					double[] doubleArray = new double[lenght];
					for(int i = 0; i < lenght; i++)
					{
						doubleArray[i] = byteBuffer.getDouble();
					}
					return doubleArray;
				case Int16:
					short[] shortArray = new short[lenght];
					for(int i = 0; i < lenght; i++)
					{
						shortArray[i] = byteBuffer.getShort();
					}
					return shortArray;
				case Int32:
					int[] intArray = new int[lenght];
					for(int i = 0; i < lenght; i++)
					{
						intArray[i] = byteBuffer.getInt();
					}
					return intArray;
				case Int64:
					long[] longArray = new long[lenght];
					for(int i = 0; i < lenght; i++)
					{
						longArray[i] = byteBuffer.getLong();
					}
					return longArray;
				case Byte:
					byte[] byteArray = new byte[lenght];
					for(int i = 0; i < lenght; i++)
					{
						byteArray[i] = byteBuffer.get();
					}
					return byteArray;
				//				case DateTime:
				//					break;
				//				case TimeSpan:
				//					break;
			}
		}
		else
		{
			throw new UnsupportedOperationException();
		}
	}

	private Object readValue(ByteBuffer byteBuffer)
	{
		TypeCode typeCode = TypeCode.VALUES[byteBuffer.get() & 0xFF];

		switch(typeCode)
		{
			case NeverUsed:
			default:
				throw new UnsupportedOperationException(typeCode.name());
			case Array:
				return readArray(byteBuffer);
			case String:
				return readString(byteBuffer);
			//			case Object:
			//				break;
			case Map:
				Map<Object, Object> map = new LinkedHashMap<>();
				Object keys = readArray(byteBuffer);
				Object values = readArray(byteBuffer);

				int length = Array.getLength(keys);
				for(int i = 0; i < length; i++)
				{
					Object key = Array.get(keys, i);
					Object value = Array.get(values, i);
					map.put(key, value);
				}
				return map;
			case Null:
				return null;
			case Boolean:
				return byteBuffer.get() == 1;
			case Single:
				return byteBuffer.getFloat();
			case Double:
				return byteBuffer.getDouble();
			case Int16:
				return byteBuffer.getShort();
			case Int32:
				return byteBuffer.getInt();
			case Int64:
				return byteBuffer.getLong();
			case Byte:
				return byteBuffer.get();
			//			case DateTime:
			//				break;
			//			case TimeSpan:
			//				break;
		}
	}

	private String readString(ByteBuffer b)
	{
		int size = b.getInt();
		byte[] bytes = new byte[size];
		b.get(bytes);
		return new String(bytes, StandardCharsets.UTF_8);
	}

	@Override
	public String toString()
	{
		return "BinaryMessage{" +
				"Id=" + Id +
				", Name='" + Name + '\'' +
				", Target='" + Target + '\'' +
				", Arguments=" + Arguments +
				'}';
	}
}
