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

import org.jetbrains.annotations.NotNull;
import com.intellij.openapi.projectRoots.SdkType;
import consulo.dotnet.module.extension.BaseDotNetSimpleModuleExtension;
import consulo.msbuild.bundle.MSBuildBundleType;
import consulo.roots.ModuleRootLayer;

/**
 * @author VISTALL
 * @since 02-Feb-17
 */
public class MSBuildModuleExtension extends BaseDotNetSimpleModuleExtension<MSBuildModuleExtension>
{
	public MSBuildModuleExtension(@NotNull String id, @NotNull ModuleRootLayer moduleRootLayer)
	{
		super(id, moduleRootLayer);
	}

	@NotNull
	@Override
	public Class<? extends SdkType> getSdkTypeClass()
	{
		return MSBuildBundleType.class;
	}
}
