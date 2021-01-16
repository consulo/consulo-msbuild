package consulo.msbuild.csharp;

import consulo.msbuild.impl.DotNetBasedProjectCapability;

import javax.annotation.Nonnull;

/**
 * @author VISTALL
 * @since 16/01/2021
 */
public class MSBuildCSharpProjectCapability extends DotNetBasedProjectCapability
{
	@Nonnull
	@Override
	public String getId()
	{
		return "CSharp";
	}
}
