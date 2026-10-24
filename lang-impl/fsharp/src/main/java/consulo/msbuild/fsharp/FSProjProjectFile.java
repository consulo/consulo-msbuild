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

import consulo.annotation.component.ExtensionImpl;
import consulo.msbuild.MSBuildProjectFile;
import jakarta.annotation.Nonnull;

import java.util.Set;

/**
 * {@code .fsproj}. MSBuild reports no capability for F# projects (unlike {@code CSharp} and {@code VB}), so the project file
 * kind adds it.
 *
 * @author VISTALL
 * @since 2026-10-25
 */
@ExtensionImpl
public class FSProjProjectFile implements MSBuildProjectFile {
    @Nonnull
    @Override
    public String getExtension() {
        return "fsproj";
    }

    @Nonnull
    @Override
    public Set<String> getCapabilities() {
        return Set.of(BaseFSharpProjectCapability.FSHARP_CAPABILITY);
    }
}
