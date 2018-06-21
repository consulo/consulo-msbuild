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

import javax.annotation.Nonnull;

import com.intellij.openapi.extensions.ExtensionPointName;
import com.intellij.openapi.projectRoots.SdkType;

/**
 * @author VISTALL
 * @since 30-Jan-17
 */
public abstract class MSBuildDotNetImportTarget
{
	public static final ExtensionPointName<MSBuildDotNetImportTarget> EP_NAME = ExtensionPointName.create("consulo.msbuild.dotnet.importTarget");

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
	public String getFrameworkExtensionId()
	{
		return myFrameworkExtensionId;
	}

	@Nonnull
	public String getPresentableName()
	{
		return myPresentableName;
	}
}
