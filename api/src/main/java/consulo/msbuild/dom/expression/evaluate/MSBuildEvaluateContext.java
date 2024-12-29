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

import consulo.annotation.access.RequiredReadAction;
import consulo.application.util.NullableLazyValue;
import consulo.language.psi.PsiElement;
import consulo.language.util.ModuleUtilCore;
import consulo.module.Module;
import consulo.msbuild.dom.expression.evaluate.variable.MSBuildVariableProvider;
import consulo.msbuild.module.extension.MSBuildProjectModuleExtension;
import consulo.project.Project;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;

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
	public static MSBuildEvaluateContext from(@Nonnull PsiElement element, @Nonnull MSBuildProjectModuleExtension buildRootExtension)
	{
		return new MSBuildEvaluateContext(element, buildRootExtension);
	}

	private PsiElement myElement;

	private NullableLazyValue<Module> myModuleValue = NullableLazyValue.of(() -> ModuleUtilCore.findModuleForPsiElement(myElement));

	private NullableLazyValue<MSBuildProjectModuleExtension<?>> myModuleExtensionValue;

	private final Map<String, MSBuildVariableProvider> myVariables = new HashMap<>();

	private final Map<Class<? extends MSBuildVariableProvider>, String> myVariableValues = new HashMap<>();

	private MSBuildEvaluateContext(@Nonnull PsiElement element, @Nullable MSBuildProjectModuleExtension buildRootExtension)
	{
		myElement = element;

		for(MSBuildVariableProvider provider : element.getProject().getApplication().getExtensionPoint(MSBuildVariableProvider.class))
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
				return ModuleUtilCore.getExtension(module, MSBuildProjectModuleExtension.class);
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

		for(MSBuildVariableProvider provider : myElement.getProject().getApplication().getExtensionPoint(MSBuildVariableProvider.class))
		{
			if(provider.getClass() == variableClazz)
			{
				return provider.evaluateUnsafe(this);
			}
		}
		return null;
	}

	@Nonnull
	public Project getProject()
	{
		return myElement.getProject();
	}

	@RequiredReadAction
	@Nullable
	public Module getModule()
	{
		return myModuleValue.getValue();
	}

	@Nullable
	@RequiredReadAction
	public MSBuildProjectModuleExtension<?> getModuleExtension()
	{
		return myModuleExtensionValue.getValue();
	}
}
