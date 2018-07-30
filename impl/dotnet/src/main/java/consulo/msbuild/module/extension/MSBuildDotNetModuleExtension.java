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

package consulo.msbuild.module.extension;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.intellij.execution.ExecutionException;
import com.intellij.execution.configurations.GeneralCommandLine;
import com.intellij.execution.configurations.RunProfile;
import com.intellij.openapi.projectRoots.Sdk;
import com.intellij.openapi.projectRoots.SdkType;
import com.intellij.xdebugger.XDebugSession;
import consulo.annotations.RequiredReadAction;
import consulo.dotnet.debugger.DotNetDebugProcessBase;
import consulo.dotnet.debugger.DotNetModuleExtensionWithDebug;
import consulo.dotnet.execution.DebugConnectionInfo;
import consulo.dotnet.module.extension.BaseDotNetModuleExtension;
import consulo.dotnet.module.extension.DotNetModuleExtension;
import consulo.dotnet.sdk.DotNetSdkType;
import consulo.msbuild.compiler.MSBuildCompileContext;
import consulo.msbuild.importProvider.item.MSBuildDotNetImportTarget;
import consulo.msbuild.importProvider.item.MSBuildImportTarget;
import consulo.msbuild.importProvider.item.UnknownBuildDotNetImportTarget;
import consulo.msbuild.module.extension.resolve.MSBuildBundleInfo;
import consulo.roots.ModuleRootLayer;

/**
 * @author VISTALL
 * @since 02-Feb-17
 */
public class MSBuildDotNetModuleExtension extends BaseDotNetModuleExtension<MSBuildDotNetModuleExtension> implements MSBuildRootExtension<MSBuildDotNetModuleExtension>,
		DotNetModuleExtension<MSBuildDotNetModuleExtension>, DotNetModuleExtensionWithDebug
{
	protected MSBuildDotNetImportTarget myImportTarget = UnknownBuildDotNetImportTarget.INSTANCE;
	protected String myPlatform;
	protected String myConfiguration;

	public MSBuildDotNetModuleExtension(@Nonnull String id, @Nonnull ModuleRootLayer moduleRootLayer)
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
	public MSBuildImportTarget getImportTarget()
	{
		return myImportTarget;
	}

	@Override
	public void build(MSBuildCompileContext context)
	{
		myImportTarget.build(context);
	}

	@Override
	public String getConfiguration()
	{
		return myConfiguration;
	}

	@Override
	public void setConfiguration(String configuration)
	{
		myConfiguration = configuration;
	}

	@Override
	public String getPlatform()
	{
		return myPlatform;
	}

	@Override
	public void setPlatform(String platform)
	{
		myPlatform = platform;
	}

	@Nonnull
	@Override
	public Class<? extends SdkType> getSdkTypeClass()
	{
		return DotNetSdkType.class;
	}

	@RequiredReadAction
	@Override
	public void commit(@Nonnull MSBuildDotNetModuleExtension mutableModuleExtension)
	{
		super.commit(mutableModuleExtension);
		myImportTarget = mutableModuleExtension.myImportTarget;
		myPlatform = mutableModuleExtension.myPlatform;
		myConfiguration = mutableModuleExtension.myConfiguration;
	}

	@Nonnull
	@Override
	public MSBuildBundleInfo getBundleInfo()
	{
		return myImportTarget.getBundleInfoList().get(0);
	}

	@Nullable
	@Override
	public Object resolveAutoSdk()
	{
		return myImportTarget.resolveAutoSdk(this);
	}

	@Nonnull
	@Override
	public String getDebugFileExtension()
	{
		return "pdb";
	}

	@Nonnull
	@Override
	public GeneralCommandLine createDefaultCommandLine(@Nonnull Sdk sdk, @Nullable DebugConnectionInfo debugConnectionInfo) throws ExecutionException
	{
		return myImportTarget.createDefaultCommandLine(this, sdk, debugConnectionInfo);
	}

	@Nonnull
	@Override
	public DotNetDebugProcessBase createDebuggerProcess(@Nonnull XDebugSession session, @Nonnull RunProfile runProfile, @Nonnull DebugConnectionInfo debugConnectionInfo)
	{
		return myImportTarget.createDebuggerProcess(this, session, runProfile, debugConnectionInfo);
	}
}
