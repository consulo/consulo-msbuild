package consulo.msbuild.dotnet.microsoft.module.extension;

import com.intellij.execution.ExecutionException;
import com.intellij.execution.configurations.GeneralCommandLine;
import com.intellij.execution.configurations.RunProfile;
import com.intellij.openapi.projectRoots.Sdk;
import com.intellij.openapi.projectRoots.SdkType;
import com.intellij.xdebugger.XDebugSession;
import consulo.dotnet.compiler.DotNetMacroUtil;
import consulo.dotnet.debugger.DotNetDebugProcessBase;
import consulo.dotnet.execution.DebugConnectionInfo;
import consulo.dotnet.microsoft.debugger.MicrosoftDebugProcess;
import consulo.microsoft.dotnet.module.extension.MicrosoftDotNetModuleExtension;
import consulo.microsoft.dotnet.sdk.MicrosoftDotNetSdkType;
import consulo.msbuild.module.extension.MSBuildBaseDotNetModuleExtension;
import consulo.roots.ModuleRootLayer;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * @author VISTALL
 * @since 19/01/2021
 */
public class MSBuildMicrosoftDotNetModuleExtension extends MSBuildBaseDotNetModuleExtension<MSBuildMicrosoftDotNetModuleExtension>
{
	public MSBuildMicrosoftDotNetModuleExtension(@Nonnull String id, @Nonnull ModuleRootLayer moduleRootLayer)
	{
		super(id, moduleRootLayer);
	}

	@Nonnull
	@Override
	public Class<? extends SdkType> getSdkTypeClass()
	{
		return MicrosoftDotNetSdkType.class;
	}

	@Nonnull
	@Override
	public DotNetDebugProcessBase createDebuggerProcess(@Nonnull XDebugSession session, @Nonnull RunProfile runProfile, @Nonnull DebugConnectionInfo debugConnectionInfo)
	{
		return new MicrosoftDebugProcess(session, runProfile, debugConnectionInfo);
	}

	@Nonnull
	@Override
	public GeneralCommandLine createDefaultCommandLine(@Nonnull Sdk sdk, @Nullable DebugConnectionInfo debugConnectionInfo) throws ExecutionException
	{
		String fileName = DotNetMacroUtil.expandOutputFile(this);

		return MicrosoftDotNetModuleExtension.createRunCommandLineImpl(fileName, debugConnectionInfo, sdk);
	}

	@Nonnull
	@Override
	public String getDebugFileExtension()
	{
		return "pdb";
	}
}
