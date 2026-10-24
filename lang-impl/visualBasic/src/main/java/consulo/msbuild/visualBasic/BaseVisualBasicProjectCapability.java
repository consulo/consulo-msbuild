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
package consulo.msbuild.visualBasic;

import consulo.content.bundle.Sdk;
import consulo.module.Module;
import consulo.module.content.layer.ModifiableRootModel;
import consulo.msbuild.MSBuildEvaluatedItem;
import consulo.msbuild.MSBuildProcessProvider;
import consulo.msbuild.dotnet.impl.DotNetLanguageProjectCapability;
import consulo.msbuild.visualBasic.module.extension.MSBuildVisualBasicMutableModuleExtension;
import consulo.virtualFileSystem.VirtualFile;
import consulo.visualBasic.dotnet.module.VisualBasicDefineConstants;
import consulo.visualBasic.dotnet.module.extension.VisualBasicCompareMode;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Visual Basic projects: the .NET part (references, framework) and the Visual Basic module extension of the MSBuild
 * process provider, with the project settings: {@code RootNamespace}, {@code Option*}, {@code DefineConstants} and
 * {@code Import} items.
 *
 * @author VISTALL
 * @since 2026-10-25
 */
public abstract class BaseVisualBasicProjectCapability extends DotNetLanguageProjectCapability {
    public static final String VISUAL_BASIC_CAPABILITY = "VB";

    public static final String IMPORT_ITEM = "Import";

    @Nonnull
    @Override
    public String getId() {
        return VISUAL_BASIC_CAPABILITY;
    }

    @Override
    public void importModule(Module module,
                             ModifiableRootModel rootModel,
                             VirtualFile projectFile,
                             MSBuildProcessProvider buildProcessProvider,
                             Sdk msBuildSdk,
                             Map<String, String> properties,
                             List<? extends MSBuildEvaluatedItem> referencePaths,
                             Set<String> targets) {
        initializeDotNetCapability(module, rootModel, projectFile, buildProcessProvider, msBuildSdk, properties, referencePaths, targets);

        MSBuildVisualBasicMutableModuleExtension extension = rootModel.getExtensionWithoutCheck(getExtensionId());
        if (extension == null) {
            return;
        }
        extension.setEnabled(true);
        extension.setRootNamespace(properties.getOrDefault("RootNamespace", ""));
        extension.setOptionStrict(isOn(properties.get("OptionStrict"), false));
        extension.setOptionExplicit(isOn(properties.get("OptionExplicit"), true));
        extension.setOptionInfer(isOn(properties.get("OptionInfer"), true));
        extension.setOptionCompare("Text".equalsIgnoreCase(properties.get("OptionCompare")) ? VisualBasicCompareMode.TEXT : VisualBasicCompareMode.BINARY);

        // FinalDefineConstants adds the ones of the project system: CONFIG, TRACE, _MyType, PLATFORM...
        String defineConstants = properties.getOrDefault("FinalDefineConstants", properties.getOrDefault("DefineConstants", ""));
        extension.getConstants().clear();
        extension.getConstants().putAll(VisualBasicDefineConstants.parse(defineConstants));
    }

    @Override
    public void importItems(Module module,
                            ModifiableRootModel rootModel,
                            VirtualFile projectFile,
                            Map<String, String> properties,
                            List<? extends MSBuildEvaluatedItem> items) {
        MSBuildVisualBasicMutableModuleExtension extension = rootModel.getExtensionWithoutCheck(getExtensionId());
        if (extension == null) {
            return;
        }
        extension.getImports().clear();
        for (MSBuildEvaluatedItem item : items) {
            if (IMPORT_ITEM.equals(item.getName())) {
                extension.getImports().add(item.getItemSpec());
            }
        }
    }

    private static boolean isOn(@Nullable String value, boolean defaultValue) {
        if (value == null || value.isBlank()) {
            return defaultValue;
        }
        return value.equalsIgnoreCase("On") || value.equalsIgnoreCase("true");
    }

    @Override
    public abstract boolean isApplicable(@Nonnull MSBuildProcessProvider provider);

    /**
     * Visual Basic module extension of the process provider, a child of its .NET extension.
     */
    @Nonnull
    public abstract String getExtensionId();

    @Override
    public int getWeight() {
        return 100;
    }
}
