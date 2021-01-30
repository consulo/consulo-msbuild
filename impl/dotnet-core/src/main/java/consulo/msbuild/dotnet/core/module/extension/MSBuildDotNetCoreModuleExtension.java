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
import consulo.dotnet.execution.DebugConnectionInfo;
import consulo.msbuild.module.extension.MSBuildBaseDotNetModuleExtension;
import consulo.roots.ModuleRootLayer;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.IOException;

/**
 * @author VISTALL
 * @since 18/01/2021
 */
public class MSBuildDotNetCoreModuleExtension extends MSBuildBaseDotNetModuleExtension<MSBuildDotNetCoreModuleExtension>
{
	public MSBuildDotNetCoreModuleExtension(@Nonnull String id, @Nonnull ModuleRootLayer moduleRootLayer)
	{
		super(id, moduleRootLayer);
	}

	@Nonnull
	@Override
	public Class<? extends SdkType> getSdkTypeClass()
	{
		return DotNetCoreBundleType.class;
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
		try
		{
			commandLine.setExePath(DotNetCoreBundleType.getExecutablePath(sdk.getHomePath()).getCanonicalPath());
			String file = DotNetMacroUtil.expandOutputFile(this);
			commandLine.addParameter(file);
		}
		catch(IOException e)
		{
			throw new ExecutionException(e);
		}
		return commandLine;
	}

	@Nonnull
	@Override
	public String getDebugFileExtension()
	{
		return "pdb";
	}
}
