package consulo.msbuild.dotnet.core;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.intellij.execution.ExecutionException;
import com.intellij.execution.configurations.GeneralCommandLine;
import com.intellij.openapi.projectRoots.Sdk;
import com.intellij.openapi.projectRoots.SdkType;
import consulo.dotnet.core.bundle.DotNetCoreBundleType;
import consulo.dotnet.execution.DebugConnectionInfo;
import consulo.msbuild.compiler.MSBuildCompileContext;
import consulo.msbuild.importProvider.item.MSBuildDotNetImportTarget;
import consulo.msbuild.module.extension.MSBuildDotNetModuleExtension;

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

	@Nonnull
	@Override
	public GeneralCommandLine createDefaultCommandLine(MSBuildDotNetModuleExtension moduleExtension, @Nonnull Sdk sdk, @Nullable DebugConnectionInfo debugConnectionInfo) throws ExecutionException
	{
		return null;
	}

	@Override
	public void build(MSBuildCompileContext context)
	{

	}
}
