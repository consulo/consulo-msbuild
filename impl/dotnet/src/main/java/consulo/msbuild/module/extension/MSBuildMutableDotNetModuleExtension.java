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
import org.jetbrains.annotations.Nullable;
import com.intellij.openapi.projectRoots.Sdk;
import consulo.dotnet.module.extension.DotNetSimpleMutableModuleExtension;
import consulo.module.extension.MutableModuleInheritableNamedPointer;
import consulo.msbuild.importProvider.item.MSBuildDotNetImportTarget;
import consulo.roots.ModuleRootLayer;
import consulo.ui.Component;
import consulo.ui.Label;
import consulo.ui.LabeledComponents;
import consulo.ui.RequiredUIAccess;
import consulo.ui.VerticalLayout;

/**
 * @author VISTALL
 * @since 02-Feb-17
 */
public class MSBuildMutableDotNetModuleExtension extends MSBuildDotNetModuleExtension implements DotNetSimpleMutableModuleExtension<MSBuildDotNetModuleExtension>
{
	public MSBuildMutableDotNetModuleExtension(@NotNull String id, @NotNull ModuleRootLayer moduleRootLayer)
	{
		super(id, moduleRootLayer);
	}

	@RequiredUIAccess
	@Nullable
	@Override
	public Component createConfigurationComponent(@NotNull Runnable updateOnCheck)
	{
		VerticalLayout vertical = VerticalLayout.create();
		vertical.add(LabeledComponents.left("Target", Label.create(myTarget.name())));
		return vertical;
	}

	public MSBuildDotNetModuleExtension setTarget(MSBuildDotNetImportTarget target)
	{
		myTarget = target;
		return this;
	}

	@NotNull
	@Override
	public MutableModuleInheritableNamedPointer<Sdk> getInheritableSdk()
	{
		return (MutableModuleInheritableNamedPointer<Sdk>) super.getInheritableSdk();
	}

	@Override
	public void setEnabled(boolean b)
	{
		myIsEnabled = b;
	}

	@Override
	public boolean isModified(@NotNull MSBuildDotNetModuleExtension msBuildModuleExtension)
	{
		return myIsEnabled != msBuildModuleExtension.isEnabled();
	}
}
