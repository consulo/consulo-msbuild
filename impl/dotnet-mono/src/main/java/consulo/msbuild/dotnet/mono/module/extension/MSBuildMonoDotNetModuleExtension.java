package consulo.msbuild.dotnet.mono.module.extension;

import consulo.content.bundle.Sdk;
import consulo.content.bundle.SdkType;
import consulo.dotnet.compiler.DotNetMacroUtil;
import consulo.dotnet.debugger.impl.DotNetDebugProcessBase;
import consulo.dotnet.mono.debugger.MonoDebugProcess;
import consulo.dotnet.util.DebugConnectionInfo;
import consulo.execution.configuration.RunProfile;
import consulo.execution.debug.XDebugSession;
import consulo.module.content.layer.ModuleRootLayer;
import consulo.mono.dotnet.module.extension.MonoDotNetModuleExtension;
import consulo.mono.dotnet.sdk.MonoSdkType;
import consulo.msbuild.dotnet.impl.module.extension.MSBuildBaseDotNetModuleExtension;
import consulo.process.ExecutionException;
import consulo.process.cmd.GeneralCommandLine;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

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
