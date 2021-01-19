package consulo.msbuild.dotnet.microsoft.module.extension;

import com.intellij.execution.ExecutionException;
import com.intellij.execution.configurations.GeneralCommandLine;
import com.intellij.execution.configurations.RunProfile;
import com.intellij.openapi.projectRoots.Sdk;
import com.intellij.openapi.projectRoots.SdkType;
import com.intellij.xdebugger.XDebugSession;
import consulo.dotnet.compiler.DotNetMacroUtil;
import consulo.dotnet.debugger.DotNetDebugProcessBase;
import consulo.dotnet.debugger.DotNetModuleExtensionWithDebug;
import consulo.dotnet.execution.DebugConnectionInfo;
import consulo.dotnet.microsoft.debugger.MicrosoftDebugProcess;
import consulo.dotnet.module.extension.BaseDotNetModuleExtension;
import consulo.dotnet.module.extension.DotNetModuleExtension;
import consulo.microsoft.dotnet.module.extension.MicrosoftDotNetModuleExtension;
import consulo.microsoft.dotnet.sdk.MicrosoftDotNetSdkType;
import consulo.msbuild.compiler.MSBuildCompileContext;
import consulo.msbuild.module.extension.MSBuildProjectModuleExtension;
import consulo.roots.ModuleRootLayer;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * @author VISTALL
 * @since 19/01/2021
 */
public class MSBuildMicrosoftDotNetModuleExtension extends BaseDotNetModuleExtension<MSBuildMicrosoftDotNetModuleExtension> implements DotNetModuleExtension<MSBuildMicrosoftDotNetModuleExtension>,
		DotNetModuleExtensionWithDebug, MSBuildProjectModuleExtension<MSBuildMicrosoftDotNetModuleExtension>
{
	public MSBuildMicrosoftDotNetModuleExtension(@Nonnull String id, @Nonnull ModuleRootLayer moduleRootLayer)
	{
		super(id, moduleRootLayer);
	}

	@Override
	public boolean isSupportCompilation()
	{
		return false;
	}

	@Nonnull
	@Override
	public Class<? extends SdkType> getSdkTypeClass()
	{
		return MicrosoftDotNetSdkType.class;
	}

	@Override
	public void build(MSBuildCompileContext context)
	{

	}

	@Override
	public String getConfiguration()
	{
		return null;
	}

	@Override
	public String getPlatform()
	{
		return null;
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
