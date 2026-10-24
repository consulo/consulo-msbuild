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

import consulo.annotation.component.ExtensionImpl;
import consulo.msbuild.MSBuildProcessProvider;
import jakarta.annotation.Nonnull;

/**
 * Visual Basic projects built by the .NET (Core) MSBuild provider.
 *
 * @author VISTALL
 * @since 2026-10-25
 */
@ExtensionImpl
public class DotNetCoreVisualBasicProjectCapability extends BaseVisualBasicProjectCapability {
    @Override
    public boolean isApplicable(@Nonnull MSBuildProcessProvider provider) {
        return "dotnet-core".equals(provider.getId());
    }

    @Nonnull
    @Override
    public String getExtensionId() {
        return "dotnet-core-visualBasic-by-msbuild";
    }
}
