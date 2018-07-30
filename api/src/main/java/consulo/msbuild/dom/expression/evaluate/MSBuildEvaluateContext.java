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

package consulo.msbuild.dom.expression.evaluate;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.ModuleUtilCore;
import com.intellij.openapi.util.NullableLazyValue;
import com.intellij.psi.PsiElement;
import consulo.annotations.RequiredReadAction;
import consulo.msbuild.dom.expression.evaluate.variable.MSBuildVariableProvider;
import consulo.msbuild.module.extension.MSBuildRootExtension;

/**
 * @author VISTALL
 * @since 16-Jun-17
 */
public class MSBuildEvaluateContext
{
	@Nonnull
	public static MSBuildEvaluateContext from(@Nonnull PsiElement element)
	{
		return new MSBuildEvaluateContext(element, null);
	}

	@Nonnull
	public static MSBuildEvaluateContext from(@Nonnull PsiElement element, @Nonnull MSBuildRootExtension buildRootExtension)
	{
		return new MSBuildEvaluateContext(element, buildRootExtension);
	}

	private PsiElement myElement;

	private NullableLazyValue<Module> myModuleValue = NullableLazyValue.of(() -> ModuleUtilCore.findModuleForPsiElement(myElement));

	private NullableLazyValue<MSBuildRootExtension<?>> myModuleExtensionValue;

	private final Map<String, MSBuildVariableProvider> myVariables = new HashMap<>();

	private final Map<Class<? extends MSBuildVariableProvider>, String> myVariableValues = new HashMap<>();

	private MSBuildEvaluateContext(@Nonnull PsiElement element, @Nullable MSBuildRootExtension buildRootExtension)
	{
		myElement = element;

		for(MSBuildVariableProvider provider : MSBuildVariableProvider.EP_NAME.getExtensions())
		{
			myVariables.put(provider.getName(), provider);
		}

		if(buildRootExtension != null)
		{
			myModuleExtensionValue = NullableLazyValue.of(() -> buildRootExtension);
		}
		else
		{
			myModuleExtensionValue = NullableLazyValue.of(() ->
			{
				Module module = getModule();
				if(module == null)
				{
					return null;
				}
				return ModuleUtilCore.getExtension(module, MSBuildRootExtension.class);
			});
		}
	}

	public void predefineVariable(@Nonnull Class<? extends MSBuildVariableProvider> variableClazz, String value)
	{
		myVariableValues.put(variableClazz, value);
	}

	public Map<String, MSBuildVariableProvider> getVariables()
	{
		return myVariables;
	}

	@Nullable
	@RequiredReadAction
	public String evaluateUnsafe(@Nonnull Class<? extends MSBuildVariableProvider> variableClazz) throws MSBuildEvaluatioException
	{
		String value = myVariableValues.get(variableClazz);
		if(value != null)
		{
			return value;
		}

		for(MSBuildVariableProvider provider : MSBuildVariableProvider.EP_NAME.getExtensions())
		{
			if(provider.getClass() == variableClazz)
			{
				return provider.evaluateUnsafe(this);
			}
		}
		return null;
	}

	@RequiredReadAction
	@Nullable
	public Module getModule()
	{
		return myModuleValue.getValue();
	}

	@Nullable
	@RequiredReadAction
	public MSBuildRootExtension<?> getModuleExtension()
	{
		return myModuleExtensionValue.getValue();
	}
}
