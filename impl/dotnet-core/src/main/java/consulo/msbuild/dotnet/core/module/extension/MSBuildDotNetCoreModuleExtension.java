package consulo.msbuild.dotnet.core.module.extension;

import com.intellij.execution.ExecutionException;
import com.intellij.execution.configurations.GeneralCommandLine;
import com.intellij.execution.configurations.RunProfile;
import com.intellij.openapi.projectRoots.Sdk;
import com.intellij.openapi.projectRoots.SdkType;
import com.intellij.xdebugger.XDebugSession;
import consulo.dotnet.compiler.DotNetMacroUtil;
import consulo.dotnet.core.bundle.DotNetCoreBundleType;
import consulo.dotnet.debugger.DotNetDebugProcessBase;
import consulo.dotnet.debugger.DotNetModuleExtensionWithDebug;
import consulo.dotnet.execution.DebugConnectionInfo;
import consulo.dotnet.module.extension.BaseDotNetModuleExtension;
import consulo.dotnet.module.extension.DotNetModuleExtension;
import consulo.msbuild.compiler.MSBuildCompileContext;
import consulo.msbuild.module.extension.MSBuildProjectModuleExtension;
import consulo.roots.ModuleRootLayer;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * @author VISTALL
 * @since 18/01/2021
 */
public class MSBuildDotNetCoreModuleExtension extends BaseDotNetModuleExtension<MSBuildDotNetCoreModuleExtension> implements DotNetModuleExtension<MSBuildDotNetCoreModuleExtension>,
		DotNetModuleExtensionWithDebug, MSBuildProjectModuleExtension<MSBuildDotNetCoreModuleExtension>
{
	public MSBuildDotNetCoreModuleExtension(@Nonnull String id, @Nonnull ModuleRootLayer moduleRootLayer)
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
		return DotNetCoreBundleType.class;
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
	public DotNetDebugProcessBase createDebuggerProcess(@Nonnull XDebugSession xDebugSession, @Nonnull RunProfile runProfile, @Nonnull DebugConnectionInfo debugConnectionInfo)
	{
		return null;
	}

	@Nonnull
	@Override
	public GeneralCommandLine createDefaultCommandLine(@Nonnull Sdk sdk, @Nullable DebugConnectionInfo debugConnectionInfo) throws ExecutionException
	{
		if(debugConnectionInfo != null)
		{
			throw new ExecutionException("Debug not supported");
		}

		GeneralCommandLine commandLine = new GeneralCommandLine();
		commandLine.setExePath(DotNetCoreBundleType.getExecutablePath(sdk.getHomePath()).getAbsolutePath());

		String file = DotNetMacroUtil.expandOutputFile(this);

		commandLine.addParameter(file);
		return commandLine;
	}

	@Nonnull
	@Override
	public String getDebugFileExtension()
	{
		return "pdb";
	}
}
