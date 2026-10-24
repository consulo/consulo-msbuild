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
package consulo.msbuild.fsharp.module.extension;

import consulo.dotnet.compiler.DotNetCompileFailedException;
import consulo.dotnet.compiler.DotNetCompilerOptionsBuilder;
import consulo.fsharp.module.extension.FSharpModuleExtension;
import consulo.module.content.layer.ModuleRootLayer;
import consulo.module.content.layer.extension.ModuleExtensionBase;
import jakarta.annotation.Nonnull;

/**
 * F# of a module imported by MSBuild: MSBuild compiles it.
 *
 * @author VISTALL
 * @since 2026-10-25
 */
public class MSBuildFSharpModuleExtension extends ModuleExtensionBase<MSBuildFSharpModuleExtension>
    implements FSharpModuleExtension<MSBuildFSharpModuleExtension> {
    public MSBuildFSharpModuleExtension(@Nonnull String id, @Nonnull ModuleRootLayer moduleRootLayer) {
        super(id, moduleRootLayer);
    }

    @Nonnull
    @Override
    public DotNetCompilerOptionsBuilder createCompilerOptionsBuilder() throws DotNetCompileFailedException {
        throw new DotNetCompileFailedException("F# projects imported from MSBuild are built by MSBuild");
    }
}
