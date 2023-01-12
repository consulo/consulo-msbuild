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

import consulo.annotation.access.RequiredReadAction;
import consulo.annotation.component.ComponentScope;
import consulo.annotation.component.ExtensionAPI;
import consulo.application.Application;
import consulo.component.extension.ExtensionPointCacheKey;
import consulo.component.extension.ExtensionPointName;
import consulo.msbuild.dom.expression.evaluate.MSBuildEvaluateContext;
import consulo.msbuild.dom.expression.evaluate.MSBuildEvaluatioException;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Map;

/**
 * @author VISTALL
 * @since 16-Jun-17
 */
@ExtensionAPI(ComponentScope.APPLICATION)
public abstract class MSBuildVariableProvider
{
	private static final ExtensionPointCacheKey<MSBuildVariableProvider, Map<String, MSBuildVariableProvider>> CACHE_KEY =
			ExtensionPointCacheKey.groupBy("MSBuildVariableProvider", MSBuildVariableProvider::getName);

	@Nullable
	public static MSBuildVariableProvider findProvider(Application application, String name)
	{
		return application.getExtensionPoint(MSBuildVariableProvider.class).getOrBuildCache(CACHE_KEY).get(name);
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
