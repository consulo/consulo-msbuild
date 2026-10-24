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
package consulo.msbuild.fsharp;

import consulo.content.bundle.Sdk;
import consulo.module.Module;
import consulo.module.content.layer.ModifiableRootModel;
import consulo.module.extension.MutableModuleExtension;
import consulo.msbuild.MSBuildEvaluatedItem;
import consulo.msbuild.MSBuildProcessProvider;
import consulo.msbuild.dotnet.impl.DotNetLanguageProjectCapability;
import consulo.virtualFileSystem.VirtualFile;
import jakarta.annotation.Nonnull;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * F# projects: the .NET part (references, framework) and the F# module extension of the MSBuild process provider.
 *
 * @author VISTALL
 * @since 2026-10-25
 */
public abstract class BaseFSharpProjectCapability extends DotNetLanguageProjectCapability {
    public static final String FSHARP_CAPABILITY = "FSharp";

    @Nonnull
    @Override
    public String getId() {
        return FSHARP_CAPABILITY;
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

        MutableModuleExtension<?> fsharpExtension = rootModel.getExtensionWithoutCheck(getExtensionId());
        if (fsharpExtension != null) {
            fsharpExtension.setEnabled(true);
        }
    }

    @Override
    public abstract boolean isApplicable(@Nonnull MSBuildProcessProvider provider);

    /**
     * F# module extension of the process provider, a child of its .NET extension.
     */
    @Nonnull
    public abstract String getExtensionId();

    @Override
    public int getWeight() {
        return 100;
    }
}
