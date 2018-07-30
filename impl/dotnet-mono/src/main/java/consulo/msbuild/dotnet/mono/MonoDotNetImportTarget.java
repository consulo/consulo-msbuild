package consulo.msbuild.dotnet.mono;

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
import consulo.dotnet.mono.debugger.MonoDebugProcess;
import consulo.mono.dotnet.sdk.MonoSdkType;
import consulo.msbuild.compiler.MSBuildCompileContext;
import consulo.msbuild.importProvider.item.MSBuildDotNetImportTarget;
import consulo.msbuild.module.extension.MSBuildDotNetModuleExtension;

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

	@Nonnull
	@Override
	public GeneralCommandLine createDefaultCommandLine(MSBuildDotNetModuleExtension moduleExtension, @Nonnull Sdk sdk, @Nullable DebugConnectionInfo debugConnectionInfo) throws ExecutionException
	{
		return null;
	}

	@Nonnull
	@Override
	public DotNetDebugProcessBase createDebuggerProcess(@Nonnull MSBuildDotNetModuleExtension moduleExtension,
														@Nonnull XDebugSession session,
														@Nonnull RunProfile runProfile,
														@Nonnull DebugConnectionInfo debugConnectionInfo)
	{
		return new MonoDebugProcess(session, runProfile, debugConnectionInfo);
	}

	@Override
	public void build(MSBuildCompileContext context)
	{

	}
}
