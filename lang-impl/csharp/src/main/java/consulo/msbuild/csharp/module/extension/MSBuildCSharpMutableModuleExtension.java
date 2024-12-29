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

import consulo.csharp.base.module.extension.CSharpConfigurationPanel;
import consulo.csharp.module.extension.CSharpMutableModuleExtension;
import consulo.disposer.Disposable;
import consulo.localize.LocalizeValue;
import consulo.module.content.layer.ModuleRootLayer;
import consulo.module.extension.swing.SwingMutableModuleExtension;
import consulo.ui.Component;
import consulo.ui.Label;
import consulo.ui.annotation.RequiredUIAccess;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import javax.swing.*;

/**
 * @author VISTALL
 * @since 03-Feb-17
 */
public class MSBuildCSharpMutableModuleExtension extends MSBuildCSharpModuleExtension implements CSharpMutableModuleExtension<MSBuildCSharpModuleExtension>, SwingMutableModuleExtension
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
		return Label.create(LocalizeValue.localizeTODO("Unsupported UI"));
	}

	@RequiredUIAccess
	@Nullable
	@Override
	public JComponent createConfigurablePanel(@Nonnull Disposable disposable, @Nonnull Runnable runnable)
	{
		return new CSharpConfigurationPanel(this);
	}

	@Override
	public void setEnabled(boolean b)
	{
		myIsEnabled = b;
	}

	@Override
	public boolean isModified(@Nonnull MSBuildCSharpModuleExtension extension)
	{
		return isModifiedImpl(extension);
	}
}
