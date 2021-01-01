package consulo.msbuild.dotnet.microsoft;

import com.intellij.execution.ExecutionException;
import com.intellij.execution.configurations.GeneralCommandLine;
import com.intellij.execution.configurations.RunProfile;
import com.intellij.openapi.projectRoots.Sdk;
import com.intellij.openapi.projectRoots.SdkTable;
import com.intellij.openapi.projectRoots.SdkType;
import com.intellij.openapi.util.Version;
import com.intellij.openapi.util.io.FileUtil;
import com.intellij.xdebugger.XDebugSession;
import consulo.dotnet.compiler.DotNetMacroUtil;
import consulo.dotnet.debugger.DotNetDebugProcessBase;
import consulo.dotnet.execution.DebugConnectionInfo;
import consulo.dotnet.microsoft.debugger.MicrosoftDebugProcess;
import consulo.microsoft.dotnet.module.extension.MicrosoftDotNetModuleExtension;
import consulo.microsoft.dotnet.sdk.MicrosoftDotNetSdkType;
import consulo.msbuild.bundle.MSBuildBundleType;
import consulo.msbuild.compiler.MSBuildCompileContext;
import consulo.msbuild.importProvider.item.MSBuildDotNetImportTarget;
import consulo.msbuild.module.extension.MSBuildDotNetModuleExtension;
import consulo.msbuild.module.extension.MSBuildRootExtension;
import consulo.msbuild.module.extension.resolve.AutomaticBundleInfo;
import consulo.msbuild.module.extension.resolve.DefaultBundleInfo;
import consulo.msbuild.module.extension.resolve.MSBuildBundleInfo;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author VISTALL
 * @since 2018-06-22
 */
public class MicrosoftDotNetImportTarget extends MSBuildDotNetImportTarget
{
	public MicrosoftDotNetImportTarget()
	{
		super(".NET Framework", "microsoft-dotnet");
	}

	@Nonnull
	@Override
	public List<MSBuildBundleInfo> getBundleInfoList()
	{
		List<MSBuildBundleInfo> list = new ArrayList<>();
		list.add(AutomaticBundleInfo.INSTANCE);
		for(Sdk sdk : SdkTable.getInstance().getSdksOfType(MSBuildBundleType.getInstance()))
		{
			list.add(new DefaultBundleInfo(sdk));
		}
		return list;
	}

	@Nullable
	@Override
	public Object resolveAutoSdk(@Nonnull MSBuildDotNetModuleExtension moduleExtension)
	{
		List<Sdk> sdksOfType = SdkTable.getInstance().getSdksOfType(MSBuildBundleType.getInstance());
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
	public SdkType getSdkType()
	{
		return MicrosoftDotNetSdkType.getInstance();
	}

	@Nonnull
	@Override
	public GeneralCommandLine createDefaultCommandLine(MSBuildDotNetModuleExtension moduleExtension, @Nonnull Sdk sdk, @Nullable DebugConnectionInfo debugConnectionInfo) throws ExecutionException
	{
		String fileName = DotNetMacroUtil.expandOutputFile(moduleExtension);

		return MicrosoftDotNetModuleExtension.createRunCommandLineImpl(fileName, debugConnectionInfo, sdk);
	}

	@Nonnull
	@Override
	public DotNetDebugProcessBase createDebuggerProcess(@Nonnull MSBuildDotNetModuleExtension moduleExtension,
														@Nonnull XDebugSession session,
														@Nonnull RunProfile runProfile,
														@Nonnull DebugConnectionInfo debugConnectionInfo)
	{
		return new MicrosoftDebugProcess(session, runProfile, debugConnectionInfo);
	}

	@Override
	public void build(MSBuildCompileContext context)
	{
		MSBuildRootExtension extension = context.getExtension();

		Object o = extension.resolveAutoSdk();

		assert o instanceof Sdk;

		Sdk sdk = (Sdk) o;

		GeneralCommandLine commandLine = new GeneralCommandLine(FileUtil.toSystemIndependentName(sdk.getHomePath() + "/bin/msbuild.exe"));

		context.addDefaultArguments(commandLine::addParameter);

		runWithLogging(context, commandLine);
	}
}
