package consulo.msbuild.dotnet.mono;

import consulo.annotation.component.ExtensionImpl;
import consulo.msbuild.MSBuildProcessProvider;
import consulo.msbuild.csharp.BaseCSharpProjectCapability;

import jakarta.annotation.Nonnull;

/**
 * @author VISTALL
 * @since 21/01/2021
 */
@ExtensionImpl
public class MonoDotNetCSharpProjectCapability extends BaseCSharpProjectCapability
{
	@Override
	public boolean isApplicable(@Nonnull MSBuildProcessProvider provider)
	{
		return provider instanceof MonoMSBuildProcessProvider;
	}

	@Nonnull
	@Override
	public String getExtensionId()
	{
		return "csharp-mono-by-msbuild";
	}
}
