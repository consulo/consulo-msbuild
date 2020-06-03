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

package consulo.msbuild.csharp.module.extension;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import consulo.csharp.module.extension.CSharpSimpleMutableModuleExtension;
import consulo.disposer.Disposable;
import consulo.roots.ModuleRootLayer;
import consulo.ui.Component;
import consulo.ui.annotation.RequiredUIAccess;

/**
 * @author VISTALL
 * @since 03-Feb-17
 */
public class MSBuildCSharpMutableModuleExtension extends MSBuildCSharpModuleExtension implements CSharpSimpleMutableModuleExtension<MSBuildCSharpModuleExtension>
{
	public MSBuildCSharpMutableModuleExtension(@Nonnull String id, @Nonnull ModuleRootLayer layer)
	{
		super(id, layer);
	}

	@RequiredUIAccess
	@Nullable
	@Override
	public Component createConfigurationComponent(@Nonnull Disposable disposable, @Nonnull Runnable runnable)
	{
		return null;
	}

	@Override
	public void setEnabled(boolean b)
	{
		myIsEnabled = b;
	}

	@Override
	public boolean isModified(@Nonnull MSBuildCSharpModuleExtension extension)
	{
		return myIsEnabled != extension.isEnabled();
	}
}
