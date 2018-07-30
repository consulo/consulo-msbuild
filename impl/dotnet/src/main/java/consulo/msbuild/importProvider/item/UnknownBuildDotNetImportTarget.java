package consulo.msbuild.importProvider.item;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.intellij.execution.ExecutionException;
import com.intellij.execution.configurations.GeneralCommandLine;
import com.intellij.execution.configurations.RunProfile;
import com.intellij.openapi.projectRoots.Sdk;
import com.intellij.openapi.projectRoots.SdkType;
import com.intellij.xdebugger.XDebugSession;
import consulo.dotnet.debugger.DotNetDebugProcessBase;
import consulo.dotnet.execution.DebugConnectionInfo;
import consulo.msbuild.compiler.MSBuildCompileContext;
import consulo.msbuild.module.extension.MSBuildDotNetModuleExtension;

/**
 * @author VISTALL
 * @since 2018-06-22
 */
public class UnknownBuildDotNetImportTarget extends MSBuildDotNetImportTarget
{
	public static final UnknownBuildDotNetImportTarget INSTANCE = new UnknownBuildDotNetImportTarget();

	public UnknownBuildDotNetImportTarget()
	{
		super("??", "");
	}

	@Nonnull
	@Override
	public SdkType getSdkType()
	{
		throw new UnsupportedOperationException();
	}

	@Nonnull
	@Override
	public GeneralCommandLine createDefaultCommandLine(@Nonnull MSBuildDotNetModuleExtension moduleExtension, @Nonnull Sdk sdk, @Nullable DebugConnectionInfo debugConnectionInfo) throws ExecutionException
	{
		return new GeneralCommandLine();
	}

	@Nonnull
	@Override
	public DotNetDebugProcessBase createDebuggerProcess(@Nonnull MSBuildDotNetModuleExtension moduleExtension,
														@Nonnull XDebugSession session,
														@Nonnull RunProfile runProfile,
														@Nonnull DebugConnectionInfo debugConnectionInfo)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public void build(MSBuildCompileContext context)
	{
		
	}
}
