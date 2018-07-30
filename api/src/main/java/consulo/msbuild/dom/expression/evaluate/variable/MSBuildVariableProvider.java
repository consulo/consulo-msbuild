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

package consulo.msbuild.dom.expression.evaluate.variable;

import gnu.trove.THashMap;

import java.util.Map;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.intellij.openapi.extensions.ExtensionPointName;
import com.intellij.openapi.util.NotNullLazyValue;
import consulo.annotations.RequiredReadAction;
import consulo.msbuild.dom.expression.evaluate.MSBuildEvaluateContext;
import consulo.msbuild.dom.expression.evaluate.MSBuildEvaluatioException;

/**
 * @author VISTALL
 * @since 16-Jun-17
 */
public abstract class MSBuildVariableProvider
{
	public static final ExtensionPointName<MSBuildVariableProvider> EP_NAME = ExtensionPointName.create("consulo.msbuild.variableProvider");

	private static NotNullLazyValue<Map<String, MSBuildVariableProvider>> ourValue = NotNullLazyValue.createValue(() ->
	{
		Map<String, MSBuildVariableProvider> map = new THashMap<>();
		for(MSBuildVariableProvider provider : EP_NAME.getExtensions())
		{
			map.put(provider.getName(), provider);
		}
		return map;
	});

	@Nullable
	public static MSBuildVariableProvider findProvider(String name)
	{
		return ourValue.getValue().get(name);
	}

	private final String myName;

	public MSBuildVariableProvider()
	{
		myName = getClass().getSimpleName();
	}

	public MSBuildVariableProvider(String name)
	{
		myName = name;
	}

	@Nonnull
	public String getName()
	{
		return myName;
	}

	@Nullable
	public String evaluate(@Nonnull MSBuildEvaluateContext context)
	{
		try
		{
			return evaluateUnsafe(context);
		}
		catch(Exception ignored)
		{
		}
		return null;
	}

	@Nullable
	@RequiredReadAction
	public abstract String evaluateUnsafe(@Nonnull MSBuildEvaluateContext context) throws MSBuildEvaluatioException;
}
