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

import javax.annotation.Nonnull;
import consulo.msbuild.dom.expression.evaluate.MSBuildEvaluateContext;
import consulo.msbuild.dom.expression.evaluate.variable.MSBuildVariableProvider;

/**
 * @author VISTALL
 * @since 17-Jun-17
 */
public class MSBuildToolsPath extends MSBuildVariableProvider
{
	@Override
	public String evaluateUnsafe(@Nonnull MSBuildEvaluateContext context) throws Exception
	{
		return context.evaluateUnsafe(MSBuildExtensionsPath.class) + "\\" + context.evaluateUnsafe(MSBuildToolsVersion.class) + "\\bin";
	}
}
