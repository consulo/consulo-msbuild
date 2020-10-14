package consulo.msbuild.dotnet.core;

import com.intellij.execution.ExecutionException;
import com.intellij.execution.configurations.GeneralCommandLine;
import com.intellij.execution.configurations.RunProfile;
import com.intellij.openapi.projectRoots.Sdk;
import com.intellij.openapi.projectRoots.SdkTable;
import com.intellij.openapi.projectRoots.SdkType;
import com.intellij.openapi.util.Version;
import com.intellij.xdebugger.XDebugSession;
import consulo.dotnet.core.bundle.DotNetCoreBundleType;
import consulo.dotnet.debugger.DotNetDebugProcessBase;
import consulo.dotnet.execution.DebugConnectionInfo;
import consulo.msbuild.compiler.MSBuildCompileContext;
import consulo.msbuild.importProvider.item.MSBuildDotNetImportTarget;
import consulo.msbuild.module.extension.MSBuildDotNetModuleExtension;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;

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

	@Nullable
	@Override
	public Object resolveAutoSdk(@Nonnull MSBuildDotNetModuleExtension moduleExtension)
	{
		List<Sdk> sdksOfType = SdkTable.getInstance().getSdksOfType(getSdkType());
		if(sdksOfType.isEmpty())
		{
			return null;
		}
		Collections.sort(sdksOfType, (o1, o2) ->
		{
			Version v1 = parse(o1.getVersionString());
			Version v2 = parse(o2.getVersionString());
			return v2.compareTo(v1);
		});
		return sdksOfType.get(0);
	}

	@Nonnull
	private static Version parse(@Nullable String version)
	{
		if(version == null)
		{
			return new Version(0, 0, 0);
		}
		Version parsedVersion = Version.parseVersion(version);
		return parsedVersion == null ? new Version(0, 0, 0) : parsedVersion;
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
		return null;
	}

	@Override
	public void build(MSBuildCompileContext context)
	{

	}
}
