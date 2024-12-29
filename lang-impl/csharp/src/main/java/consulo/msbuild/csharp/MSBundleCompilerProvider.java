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

package consulo.msbuild.csharp;

import consulo.annotation.component.ExtensionImpl;
import consulo.content.bundle.SdkType;
import consulo.csharp.base.compiler.CSharpCompilerUtil;
import consulo.csharp.compiler.CSharpCompilerProvider;
import consulo.csharp.compiler.MSBaseDotNetCompilerOptionsBuilder;
import consulo.csharp.module.extension.CSharpModuleExtension;
import consulo.dotnet.compiler.DotNetCompileFailedException;
import consulo.dotnet.module.extension.DotNetModuleExtension;
import consulo.dotnet.module.extension.DotNetSimpleModuleExtension;
import consulo.msbuild.bundle.MSBuildBundleType;
import consulo.virtualFileSystem.VirtualFile;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

/**
 * @author VISTALL
 * @since 08.06.2015
 */
@ExtensionImpl(id = "msbuild", order = "after ms-internal")
public class MSBundleCompilerProvider extends CSharpCompilerProvider
{
	@Nullable
	@Override
	public SdkType getBundleType(@Nonnull DotNetSimpleModuleExtension<?> moduleExtension)
	{
		// hack - due we can known about MSBuild plugin or Microsoft .NET, double depends option file is not supported
		if(moduleExtension.getId().equals("microsoft-dotnet"))
		{
			return MSBuildBundleType.getInstance();
		}
		return null;
	}

	@Override
	public void setupCompiler(@Nonnull DotNetModuleExtension<?> netExtension,
			@Nonnull CSharpModuleExtension<?> csharpExtension,
			@Nonnull MSBaseDotNetCompilerOptionsBuilder builder,
			@Nullable VirtualFile compilerSdkHome) throws DotNetCompileFailedException
	{
		if(compilerSdkHome == null)
		{
			throw new DotNetCompileFailedException("Compiler path is not resolved");
		}

		VirtualFile compilerPath = compilerSdkHome.findFileByRelativePath("bin/Roslyn/" + CSharpCompilerUtil.COMPILER_NAME);
		if(compilerPath == null)
		{
			compilerPath = compilerSdkHome.findFileByRelativePath("bin/" + CSharpCompilerUtil.COMPILER_NAME);
		}

		setExecutable(csharpExtension, builder, compilerPath);
	}
}
