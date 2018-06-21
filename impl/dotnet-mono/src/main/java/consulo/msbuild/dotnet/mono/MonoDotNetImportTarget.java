package consulo.msbuild.dotnet.mono;

import javax.annotation.Nonnull;

import com.intellij.openapi.projectRoots.SdkType;
import consulo.mono.dotnet.sdk.MonoSdkType;
import consulo.msbuild.importProvider.item.MSBuildDotNetImportTarget;

/**
 * @author VISTALL
 * @since 2018-06-22
 */
public class MonoDotNetImportTarget extends MSBuildDotNetImportTarget
{
	public MonoDotNetImportTarget()
	{
		super("Mono", "mono-dotnet");
	}

	@Nonnull
	@Override
	public SdkType getSdkType()
	{
		return MonoSdkType.getInstance();
	}
}
