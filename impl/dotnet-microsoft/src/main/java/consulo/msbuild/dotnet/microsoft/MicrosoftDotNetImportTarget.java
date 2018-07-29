package consulo.msbuild.dotnet.microsoft;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.intellij.execution.ExecutionException;
import com.intellij.execution.configurations.GeneralCommandLine;
import com.intellij.execution.process.CapturingProcessHandler;
import com.intellij.execution.process.ProcessOutput;
import com.intellij.openapi.projectRoots.Sdk;
import com.intellij.openapi.projectRoots.SdkTable;
import com.intellij.openapi.projectRoots.SdkType;
import com.intellij.openapi.util.io.FileUtil;
import consulo.dotnet.execution.DebugConnectionInfo;
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

/**
 * @author VISTALL
 * @since 2018-06-22
 */
public class MicrosoftDotNetImportTarget extends MSBuildDotNetImportTarget
{
	public MicrosoftDotNetImportTarget()
	{
		super(".NET", "microsoft-dotnet");
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
		return MicrosoftDotNetModuleExtension.createRunCommandLineImpl(moduleExtension.getFileName(), debugConnectionInfo, sdk);
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

		try
		{
			CapturingProcessHandler processHandler = new CapturingProcessHandler(commandLine);

			ProcessOutput processOutput = processHandler.runProcess();

			for(String s : processOutput.getStdoutLines())
			{
				System.out.println(s);
			}
		}
		catch(ExecutionException e)
		{
			throw new IllegalArgumentException(e);
		}
	}
}
