package consulo.msbuild.daemon.impl.message.model;

/**
 * @author VISTALL
 * @since 13/12/2020
 */
public enum MSBuildVerbosity implements EnumInt
{
	Quiet,
	Minimal,
	Normal,
	Detailed,
	Diagnostic;

	@Override
	public int getValue()
	{
		return ordinal();
	}
}
