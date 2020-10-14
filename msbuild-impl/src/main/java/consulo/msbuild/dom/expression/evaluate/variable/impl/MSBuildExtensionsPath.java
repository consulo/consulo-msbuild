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

package consulo.msbuild.dom.expression.evaluate.variable.impl;

import com.intellij.openapi.module.ModuleUtilCore;
import com.intellij.openapi.projectRoots.Sdk;
import consulo.annotation.access.RequiredReadAction;
import consulo.msbuild.evaluate.MSBuildEvaluateContext;
import consulo.msbuild.dom.expression.evaluate.variable.MSBuildVariableProvider;
import consulo.msbuild.module.extension.MSBuildRootExtension;

import javax.annotation.Nonnull;

/**
 * @author VISTALL
 * @since 16-Jun-17
 */
public class MSBuildExtensionsPath extends MSBuildVariableProvider
{
	@RequiredReadAction
	@Override
	public String evaluateUnsafe(@Nonnull MSBuildEvaluateContext context)
	{
		MSBuildRootExtension extension = ModuleUtilCore.getExtension(context.getModule(), MSBuildRootExtension.class);
		if(extension == null)
		{
			return null;
		}

		Object o = extension.getBundleInfo().resolveSdk(extension.getImportTarget(), extension);
		if(o instanceof Sdk)
		{
			return ((Sdk) o).getHomePath();
		}

		return null;
	}
}
