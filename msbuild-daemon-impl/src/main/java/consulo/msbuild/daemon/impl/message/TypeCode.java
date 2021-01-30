package consulo.msbuild.daemon.impl.message;

/**
 * @author VISTALL
 * @since 12/12/2020
 */
public enum TypeCode
{
	NeverUsed,
	Array,
	String,
	Object,
	Map,
	Null,
	Boolean,
	Single,
	Double,
	Int16,
	Int32,
	Int64,
	Byte,
	DateTime,
	TimeSpan;

	public static final TypeCode[] VALUES = values();
}
