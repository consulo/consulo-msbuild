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
import com.intellij.openapi.projectRoots.SdkType;
import com.intellij.util.ArrayFactory;
import com.intellij.util.containers.ContainerUtil;
import consulo.module.extension.ModuleExtensionProviderEP;
import consulo.module.extension.impl.ModuleExtensionProviders;

/**
 * @author VISTALL
 * @since 30-Jan-17
 */
public enum MSBuildDotNetImportTarget
{
	_NET("microsoft-dotnet", "MICROSOFT_DOTNET_SDK"),
	Mono("mono-dotnet", "MONO_DOTNET_SDK"),
	_NET__Core("dotnet-core", "DOTNET_CORE_SDK");

	public static final MSBuildDotNetImportTarget[] EMPTY_ARRAY = new MSBuildDotNetImportTarget[0];

	public static ArrayFactory<MSBuildDotNetImportTarget> ARRAY_FACTORY = count -> count == 0 ? EMPTY_ARRAY : new MSBuildDotNetImportTarget[count];

	private final String myPresentableName;
	private final String myFrameworkExtensionId;
	private final String mySdkTypeId;

	MSBuildDotNetImportTarget(@Nonnull String frameworkExtensionId, @Nonnull String sdkTypeId)
	{
		myFrameworkExtensionId = frameworkExtensionId;
		mySdkTypeId = sdkTypeId;
		myPresentableName = name().replace("__", " ").replace("_", ".");
	}

	@Nullable
	public SdkType getSdkType()
	{
		return ContainerUtil.find(SdkType.EP_NAME.getExtensions(), it -> it.getName().equals(mySdkTypeId));
	}

	@Nonnull
	public static MSBuildDotNetImportTarget[] getAvailableTargets()
	{
		MSBuildDotNetImportTarget[] values = values();
		List<MSBuildDotNetImportTarget> list = new ArrayList<>(values.length);
		for(MSBuildDotNetImportTarget visualStudioImportTarget : values)
		{
			ModuleExtensionProviderEP providerEP = ModuleExtensionProviders.findProvider(visualStudioImportTarget.getFrameworkExtensionId());
			if(providerEP != null)
			{
				list.add(visualStudioImportTarget);
			}
		}
		return ContainerUtil.toArray(list, MSBuildDotNetImportTarget.ARRAY_FACTORY);
	}

	public String getFrameworkExtensionId()
	{
		return myFrameworkExtensionId;
	}

	@Override
	public String toString()
	{
		return myPresentableName;
	}
}
