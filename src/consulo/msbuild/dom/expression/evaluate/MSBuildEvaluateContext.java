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

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.ModuleUtilCore;
import com.intellij.psi.PsiElement;
import consulo.annotations.RequiredReadAction;
import consulo.msbuild.dom.expression.evaluate.variable.MSBuildVariableProvider;

/**
 * @author VISTALL
 * @since 16-Jun-17
 */
public class MSBuildEvaluateContext
{
	private PsiElement myElement;

	private Module myModule;

	public MSBuildEvaluateContext(@NotNull PsiElement element)
	{
		myElement = element;
	}

	@Nullable
	public String evaluateUnsafe(@NotNull Class<? extends MSBuildVariableProvider> variableClazz) throws Exception
	{
		MSBuildVariableProvider provider = MSBuildVariableProvider.findProvider(variableClazz.getSimpleName());
		if(provider == null)
		{
			throw new IllegalArgumentException();
		}

		return provider.evaluateUnsafe(this);
	}

	@RequiredReadAction
	@Nullable
	public Module getModule()
	{
		if(myModule == null)
		{
			myModule = ModuleUtilCore.findModuleForPsiElement(myElement);
		}
		return myModule;
	}
}
