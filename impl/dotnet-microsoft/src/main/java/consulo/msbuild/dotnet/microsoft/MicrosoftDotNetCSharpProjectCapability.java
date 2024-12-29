package consulo.msbuild.dotnet.microsoft;

import consulo.annotation.component.ExtensionImpl;
import consulo.msbuild.MSBuildProcessProvider;
import consulo.msbuild.csharp.BaseCSharpProjectCapability;

import jakarta.annotation.Nonnull;

/**
 * @author VISTALL
 * @since 21/01/2021
 */
@ExtensionImpl
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
