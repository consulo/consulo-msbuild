/*
 * Copyright 2013-2026 consulo.io
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
package consulo.msbuild.visualBasic.module.extension;

import consulo.annotation.component.ExtensionImpl;
import consulo.visualBasic.dotnet.icon.VisualBasicIconGroup;
import consulo.visualBasic.dotnet.localize.VisualBasicLocalize;
import consulo.localize.LocalizeValue;
import consulo.module.content.layer.ModuleExtensionProvider;
import consulo.module.content.layer.ModuleRootLayer;
import consulo.module.extension.ModuleExtension;
import consulo.module.extension.MutableModuleExtension;
import consulo.ui.image.Image;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

/**
 * Visual Basic of modules imported by the .NET (Core) MSBuild provider: a child of its .NET extension ({@code dotnet-core-by-msbuild}).
 *
 * @author VISTALL
 * @since 2026-10-25
 */
@ExtensionImpl
public class DotNetCoreVisualBasicModuleExtensionProvider implements ModuleExtensionProvider<MSBuildVisualBasicModuleExtension> {
    @Nonnull
    @Override
    public String getId() {
        return "dotnet-core-visualBasic-by-msbuild";
    }

    @Nullable
    @Override
    public String getParentId() {
        return "dotnet-core-by-msbuild";
    }

    @Override
    public boolean isSystemOnly() {
        return true;
    }

    @Nonnull
    @Override
    public LocalizeValue getName() {
        return VisualBasicLocalize.languageName();
    }

    @Nonnull
    @Override
    public Image getIcon() {
        return VisualBasicIconGroup.visualbasic();
    }

    @Nonnull
    @Override
    public ModuleExtension<MSBuildVisualBasicModuleExtension> createImmutableExtension(@Nonnull ModuleRootLayer moduleRootLayer) {
        return new MSBuildVisualBasicModuleExtension(getId(), moduleRootLayer);
    }

    @Nonnull
    @Override
    public MutableModuleExtension<MSBuildVisualBasicModuleExtension> createMutableExtension(@Nonnull ModuleRootLayer moduleRootLayer) {
        return new MSBuildVisualBasicMutableModuleExtension(getId(), moduleRootLayer);
    }
}
