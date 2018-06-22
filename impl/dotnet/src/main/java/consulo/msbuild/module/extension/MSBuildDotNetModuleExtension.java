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

import com.intellij.openapi.projectRoots.SdkType;
import consulo.annotations.RequiredReadAction;
import consulo.dotnet.module.extension.BaseDotNetSimpleModuleExtension;
import consulo.dotnet.sdk.DotNetSdkType;
import consulo.msbuild.importProvider.item.MSBuildDotNetImportTarget;
import consulo.msbuild.importProvider.item.MSBuildImportTarget;
import consulo.msbuild.importProvider.item.UnknownBuildDotNetImportTarget;
import consulo.msbuild.module.extension.resolve.MSBuildBundleInfo;
import consulo.roots.ModuleRootLayer;

/**
 * @author VISTALL
 * @since 02-Feb-17
 */
public class MSBuildDotNetModuleExtension extends BaseDotNetSimpleModuleExtension<MSBuildDotNetModuleExtension> implements MSBuildRootExtension<MSBuildDotNetModuleExtension>
{
	protected MSBuildDotNetImportTarget myTarget = UnknownBuildDotNetImportTarget.INSTANCE;

	public MSBuildDotNetModuleExtension(@Nonnull String id, @Nonnull ModuleRootLayer moduleRootLayer)
	{
		super(id, moduleRootLayer);
	}

	@Nonnull
	@Override
	public MSBuildImportTarget getImportTarget()
	{
		return myTarget;
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
		myTarget = mutableModuleExtension.myTarget;
	}

	@Nonnull
	@Override
	public MSBuildBundleInfo getBundleInfo()
	{
		return myTarget.getBundleInfoList().get(0);
	}

	@Nullable
	@Override
	public Object resolveAutoSdk()
	{
		return myTarget.resolveAutoSdk(this);
	}
}
