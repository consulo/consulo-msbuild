package consulo.msbuild.dotnet.microsoft;

import javax.annotation.Nonnull;

import com.intellij.openapi.projectRoots.SdkType;
import consulo.microsoft.dotnet.sdk.MicrosoftDotNetSdkType;
import consulo.msbuild.importProvider.item.MSBuildDotNetImportTarget;

/**
 * @author VISTALL
 * @since 2018-06-22
 */
public class MicrosoftDotNetImportTarget extends MSBuildDotNetImportTarget
{
	public MicrosoftDotNetImportTarget()
	{
		super(".NET", "microsoft-dotnet");
	}

	@Nonnull
	@Override
	public SdkType getSdkType()
	{
		return MicrosoftDotNetSdkType.getInstance();
	}
}
