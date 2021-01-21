package consulo.msbuild.dotnet.mono.module.extension;

import com.intellij.execution.ExecutionException;
import com.intellij.execution.configurations.GeneralCommandLine;
import com.intellij.execution.configurations.RunProfile;
import com.intellij.openapi.projectRoots.Sdk;
import com.intellij.openapi.projectRoots.SdkType;
import com.intellij.xdebugger.XDebugSession;
import consulo.dotnet.compiler.DotNetMacroUtil;
import consulo.dotnet.debugger.DotNetDebugProcessBase;
import consulo.dotnet.execution.DebugConnectionInfo;
import consulo.dotnet.mono.debugger.MonoDebugProcess;
import consulo.mono.dotnet.module.extension.MonoDotNetModuleExtension;
import consulo.mono.dotnet.sdk.MonoSdkType;
import consulo.msbuild.module.extension.MSBuildBaseDotNetModuleExtension;
import consulo.roots.ModuleRootLayer;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * @author VISTALL
 * @since 21/01/2021
 */
public class MSBuildMonoDotNetModuleExtension extends MSBuildBaseDotNetModuleExtension<MSBuildMonoDotNetModuleExtension>
{
	public MSBuildMonoDotNetModuleExtension(@Nonnull String id, @Nonnull ModuleRootLayer moduleRootLayer)
	{
		super(id, moduleRootLayer);
	}

	@Nonnull
	@Override
	public Class<? extends SdkType> getSdkTypeClass()
	{
		return MonoSdkType.class;
	}

	@Nonnull
	@Override
	public DotNetDebugProcessBase createDebuggerProcess(@Nonnull XDebugSession session, @Nonnull RunProfile runProfile, @Nonnull DebugConnectionInfo debugConnectionInfo)
	{
		return new MonoDebugProcess(session, runProfile, debugConnectionInfo);
	}

	@Nonnull
	@Override
	public GeneralCommandLine createDefaultCommandLine(@Nonnull Sdk sdk, @Nullable DebugConnectionInfo debugConnectionInfo) throws ExecutionException
	{
		String fileName = DotNetMacroUtil.expandOutputFile(this);

		return MonoDotNetModuleExtension.createDefaultCommandLineImpl(sdk, debugConnectionInfo, fileName);
	}

	@Nonnull
	@Override
	public String getDebugFileExtension()
	{
		return "pdb";
	}
}
