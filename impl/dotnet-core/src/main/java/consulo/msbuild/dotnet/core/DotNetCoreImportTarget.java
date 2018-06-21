package consulo.msbuild.dotnet.core;

import javax.annotation.Nonnull;

import com.intellij.openapi.projectRoots.SdkType;
import consulo.dotnet.core.bundle.DotNetCoreBundleType;
import consulo.msbuild.importProvider.item.MSBuildDotNetImportTarget;

/**
 * @author VISTALL
 * @since 2018-06-22
 */
public class DotNetCoreImportTarget extends MSBuildDotNetImportTarget
{
	public DotNetCoreImportTarget()
	{
		super(".NET Core", "dotnet-core");
	}

	@Nonnull
	@Override
	public SdkType getSdkType()
	{
		return DotNetCoreBundleType.getInstance();
	}
}
