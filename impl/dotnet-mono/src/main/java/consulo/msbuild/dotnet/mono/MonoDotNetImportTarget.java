package consulo.msbuild.dotnet.mono;

import java.io.File;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.intellij.execution.ExecutionException;
import com.intellij.execution.configurations.GeneralCommandLine;
import com.intellij.execution.configurations.RunProfile;
import com.intellij.execution.process.CapturingProcessHandler;
import com.intellij.openapi.projectRoots.Sdk;
import com.intellij.openapi.projectRoots.SdkType;
import com.intellij.util.ArrayUtil;
import com.intellij.xdebugger.XDebugSession;
import consulo.dotnet.compiler.DotNetMacroUtil;
import consulo.dotnet.debugger.DotNetDebugProcessBase;
import consulo.dotnet.execution.DebugConnectionInfo;
import consulo.dotnet.mono.debugger.MonoDebugProcess;
import consulo.dotnet.sdk.DotNetVersion;
import consulo.mono.dotnet.module.extension.MonoDotNetModuleExtension;
import consulo.mono.dotnet.sdk.MonoSdkType;
import consulo.msbuild.compiler.MSBuildCompileContext;
import consulo.msbuild.compiler.MSBuildReporter;
import consulo.msbuild.importProvider.item.MSBuildDotNetImportTarget;
import consulo.msbuild.module.extension.MSBuildDotNetModuleExtension;
import consulo.msbuild.module.extension.MSBuildRootExtension;

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
	public GeneralCommandLine createDefaultCommandLine(@Nonnull MSBuildDotNetModuleExtension moduleExtension,
													   @Nonnull Sdk sdk,
													   @Nullable DebugConnectionInfo debugConnectionInfo) throws ExecutionException
	{
		String fileName = DotNetMacroUtil.expandOutputFile(moduleExtension);

		return MonoDotNetModuleExtension.createDefaultCommandLineImpl(sdk, debugConnectionInfo, fileName);
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

	@Nullable
	@Override
	public Object resolveAutoSdk(@Nonnull MSBuildDotNetModuleExtension moduleExtension)
	{
		return moduleExtension.getSdk();
	}

	@Nonnull
	public static String getMonoExecutable(@Nonnull String sdkPath, @Nonnull String exePath)
	{
		DotNetVersion[] versions = ArrayUtil.reverseArray(DotNetVersion.values());

		File originalSdkHome = new File(sdkPath);

		File parentFile = originalSdkHome.getParentFile();

		for(DotNetVersion version : versions)
		{
			File sdkVersionPath = new File(parentFile, version.getPresentableName() + "/" + exePath);

			if(sdkVersionPath.exists())
			{
				return sdkVersionPath.getPath();
			}
		}

		return sdkPath;
	}

	@Override
	public void build(MSBuildCompileContext context)
	{
		MSBuildRootExtension extension = context.getExtension();

		Object o = extension.resolveAutoSdk();

		Sdk sdk = (Sdk) o;

		if(sdk == null)
		{
			throw new IllegalArgumentException("SDK not found");
		}

		String monoExecutable = getMonoExecutable(sdk.getHomePath(), "xbuild.exe");

		GeneralCommandLine commandLine = new GeneralCommandLine(MonoSdkType.getInstance().getExecutable(sdk));

		commandLine.addParameter(monoExecutable);

		context.addDefaultArguments(commandLine::addParameter);

		try
		{
			CapturingProcessHandler processHandler = new CapturingProcessHandler(commandLine);

			processHandler.runProcess();

			MSBuildReporter.report(context);
		}
		catch(ExecutionException e)
		{
			throw new IllegalArgumentException(e);
		}
	}
}
