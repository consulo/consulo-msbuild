package consulo.msbuild.dotnet.microsoft;

import consulo.msbuild.MSBuildProcessProvider;
import consulo.msbuild.csharp.BaseCSharpProjectCapability;

import javax.annotation.Nonnull;

/**
 * @author VISTALL
 * @since 21/01/2021
 */
public class MicrosoftDotNetCSharpProjectCapability extends BaseCSharpProjectCapability
{
	@Override
	public boolean isApplicable(@Nonnull MSBuildProcessProvider provider)
	{
		return MSBuildProcessProvider.DEFAULT_ID.equals(provider.getId());
	}

	@Nonnull
	@Override
	public String getExtensionId()
	{
		return "dotnet-csharp-by-msbuild";
	}
}
