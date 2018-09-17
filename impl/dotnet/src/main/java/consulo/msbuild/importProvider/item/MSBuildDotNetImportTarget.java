/*
 * Copyright 2013-2017 consulo.io
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package consulo.msbuild.importProvider.item;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.intellij.execution.ExecutionException;
import com.intellij.execution.configurations.GeneralCommandLine;
import com.intellij.execution.configurations.RunProfile;
import com.intellij.execution.process.CapturingProcessHandler;
import com.intellij.execution.process.ProcessOutput;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.extensions.ExtensionPointName;
import com.intellij.openapi.projectRoots.Sdk;
import com.intellij.openapi.projectRoots.SdkType;
import com.intellij.xdebugger.XDebugSession;
import consulo.dotnet.debugger.DotNetDebugProcessBase;
import consulo.dotnet.execution.DebugConnectionInfo;
import consulo.msbuild.compiler.MSBuildCompileContext;
import consulo.msbuild.compiler.MSBuildReporter;
import consulo.msbuild.module.extension.MSBuildDotNetModuleExtension;
import consulo.msbuild.module.extension.resolve.AutomaticBundleInfo;
import consulo.msbuild.module.extension.resolve.MSBuildBundleInfo;

/**
 * @author VISTALL
 * @since 30-Jan-17
 */
public abstract class MSBuildDotNetImportTarget implements MSBuildImportTarget
{
	public static final ExtensionPointName<MSBuildDotNetImportTarget> EP_NAME = ExtensionPointName.create("consulo.msbuild.dotnet.importTarget");

	private static final Logger LOGGER = Logger.getInstance(MSBuildDotNetImportTarget.class);

	private final String myPresentableName;
	private final String myFrameworkExtensionId;

	protected MSBuildDotNetImportTarget(@Nonnull String name, @Nonnull String frameworkExtensionId)
	{
		myFrameworkExtensionId = frameworkExtensionId;
		myPresentableName = name;
	}

	@Nonnull
	public static MSBuildDotNetImportTarget findById(@Nonnull String id)
	{
		for(MSBuildDotNetImportTarget target : EP_NAME.getExtensions())
		{
			if(id.equals(target.getFrameworkExtensionId()))
			{
				return target;
			}
		}
		return UnknownBuildDotNetImportTarget.INSTANCE;
	}

	@Nonnull
	public abstract SdkType getSdkType();

	@Nonnull
	public abstract GeneralCommandLine createDefaultCommandLine(@Nonnull MSBuildDotNetModuleExtension moduleExtension,
																@Nonnull Sdk sdk,
																@Nullable DebugConnectionInfo debugConnectionInfo) throws ExecutionException;

	@Nonnull
	public abstract DotNetDebugProcessBase createDebuggerProcess(@Nonnull MSBuildDotNetModuleExtension moduleExtension,
																 @Nonnull XDebugSession session,
																 @Nonnull RunProfile runProfile,
																 @Nonnull DebugConnectionInfo debugConnectionInfo);

	@Nullable
	public Object resolveAutoSdk(@Nonnull MSBuildDotNetModuleExtension moduleExtension)
	{
		List<MSBuildBundleInfo> bundleInfoList = getBundleInfoList();
		for(MSBuildBundleInfo info : bundleInfoList)
		{
			if(info == AutomaticBundleInfo.INSTANCE)
			{
				continue;
			}

			return info.resolveSdk(this, moduleExtension);
		}

		return null;
	}

	public abstract void build(MSBuildCompileContext context);

	@Nonnull
	public List<MSBuildBundleInfo> getBundleInfoList()
	{
		List<MSBuildBundleInfo> list = new ArrayList<>();
		list.add(AutomaticBundleInfo.INSTANCE);
		return list;
	}

	@Nonnull
	public String getFrameworkExtensionId()
	{
		return myFrameworkExtensionId;
	}

	@Nonnull
	public String getPresentableName()
	{
		return myPresentableName;
	}

	@Override
	public String toString()
	{
		return myPresentableName;
	}

	protected void runWithLogging(MSBuildCompileContext context, GeneralCommandLine commandLine)
	{
		try
		{
			CapturingProcessHandler processHandler = new CapturingProcessHandler(commandLine);

			ProcessOutput processOutput = processHandler.runProcess();
			for(String line : processOutput.getStdoutLines())
			{
				LOGGER.info(line);
			}

			MSBuildReporter.report(context);
		}
		catch(ExecutionException e)
		{
			throw new IllegalArgumentException(e);
		}
	}
}
