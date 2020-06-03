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

import java.util.Objects;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.intellij.openapi.projectRoots.Sdk;
import consulo.bundle.BundleHolder;
import consulo.bundle.ui.BundleBox;
import consulo.disposer.Disposable;
import consulo.dotnet.module.extension.DotNetMutableModuleExtension;
import consulo.module.extension.MutableModuleInheritableNamedPointer;
import consulo.msbuild.importProvider.item.MSBuildDotNetImportTarget;
import consulo.msbuild.module.extension.resolve.MSBuildBundleInfo;
import consulo.roots.ModuleRootLayer;
import consulo.ui.Component;
import consulo.ui.Label;
import consulo.ui.annotation.RequiredUIAccess;
import consulo.ui.layout.VerticalLayout;
import consulo.ui.util.LabeledComponents;

/**
 * @author VISTALL
 * @since 02-Feb-17
 */
public class MSBuildMutableDotNetModuleExtension extends MSBuildDotNetModuleExtension implements DotNetMutableModuleExtension<MSBuildDotNetModuleExtension>
{
	public MSBuildMutableDotNetModuleExtension(@Nonnull String id, @Nonnull ModuleRootLayer moduleRootLayer)
	{
		super(id, moduleRootLayer);
	}

	@RequiredUIAccess
	@Nullable
	@Override
	public Component createConfigurationComponent(@Nonnull Disposable disposable, @Nonnull Runnable runnable)
	{
		VerticalLayout vertical = VerticalLayout.create();
		vertical.add(LabeledComponents.left("Target", Label.create(myImportTarget.getPresentableName())));
		vertical.add(LabeledComponents.left("Configuration", Label.create(myConfiguration)));
		vertical.add(LabeledComponents.left("Platform", Label.create(myPlatform)));
		vertical.add(LabeledComponents.left("Output Dir", Label.create(getOutputDir())));
		vertical.add(LabeledComponents.left("Debug Symbols", Label.create(String.valueOf(myAllowDebugInfo))));
		vertical.add(LabeledComponents.left("Variables", Label.create(String.join(",", myVariables))));

		BundleBox bundleBox = new BundleBox(BundleHolder.EMPTY, null, false);

		for(MSBuildBundleInfo bundleInfo : myImportTarget.getBundleInfoList())
		{
			bundleBox.addCustomBundleItem(bundleInfo.getId(), bundleInfo.getName(), bundleInfo.getIcon());
		}

		bundleBox.setSelectedBundle(getBundleInfo().getId());

		vertical.add(LabeledComponents.leftFilled("SDK", bundleBox));
		return vertical;
	}

	public void setImportTarget(MSBuildDotNetImportTarget target)
	{
		myImportTarget = target;
	}

	@Nonnull
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
	public boolean isModified(@Nonnull MSBuildDotNetModuleExtension msBuildModuleExtension)
	{
		return super.isModifiedImpl(msBuildModuleExtension) ||
				!Objects.equals(myConfiguration, msBuildModuleExtension.getConfiguration()) ||
				!Objects.equals(myPlatform, msBuildModuleExtension.getPlatform());
	}
}
