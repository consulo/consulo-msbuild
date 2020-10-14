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

package consulo.msbuild.evaluate;

import com.intellij.openapi.application.AccessToken;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.ModuleUtilCore;
import com.intellij.openapi.util.NullableLazyValue;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiElement;
import consulo.annotation.access.RequiredReadAction;
import consulo.msbuild.dom.SimpleItem;
import consulo.msbuild.dom.expression.evaluate.MSBuildEvaluatioException;
import consulo.msbuild.dom.expression.evaluate.variable.MSBuildVariableProvider;
import consulo.msbuild.module.extension.MSBuildRootExtension;
import consulo.util.lang.ref.SoftReference;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.*;
import java.util.function.Supplier;

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

	private final PsiElement myElement;

	private final NullableLazyValue<Module> myModuleValue;

	private final NullableLazyValue<MSBuildRootExtension<?>> myModuleExtensionValue;

	private final Map<String, Supplier<String>> myVariableValues = new HashMap<>();

	private final Deque<VirtualFile> myFiles = new ArrayDeque<>();

	private final List<SimpleItem> myProjectItems = new ArrayList<>();

	private MSBuildEvaluateContext(@Nonnull PsiElement element, @Nullable MSBuildRootExtension buildRootExtension)
	{
		myElement = element;

		myModuleValue = NullableLazyValue.of(() -> ModuleUtilCore.findModuleForPsiElement(element));

		for(MSBuildVariableProvider provider : MSBuildVariableProvider.EP_NAME.getExtensionList())
		{
			myVariableValues.put(provider.getName(), () -> provider.evaluateUnsafe(this));
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

	public void addProjectItem(@Nonnull SimpleItem simpleItem)
	{
		myProjectItems.add(simpleItem);
	}

	@Nonnull
	public List<SimpleItem> getProjectItems()
	{
		return myProjectItems;
	}

	@Nonnull
	public VirtualFile getCurrentFile()
	{
		if(myFiles.isEmpty())
		{
			return myElement.getContainingFile().getVirtualFile();
		}
		return myFiles.getLast();
	}

	@Nonnull
	public AccessToken processFile(@Nonnull VirtualFile file)
	{
		myFiles.addLast(file);
		return new AccessToken()
		{
			@Override
			public void finish()
			{
				myFiles.remove(file);
			}
		};
	}

	@Nonnull
	public Map<String, String> getVariableValues()
	{
		Map<String, String> map = new HashMap<>();
		for(Map.Entry<String, Supplier<String>> entry : myVariableValues.entrySet())
		{
			String value = entry.getValue().get();
			if(value == null)
			{
				continue;
			}
			map.put(entry.getKey(), value);
		}
		return map;
	}

	@Nullable
	public String getVariableValue(@Nonnull String name)
	{
		Supplier<String> supplier = myVariableValues.get(name);
		return SoftReference.deref(supplier);
	}

	public void putVariableValue(@Nonnull String name, @Nullable String value)
	{
		myVariableValues.put(name, () -> value);
	}

	@Nullable
	@RequiredReadAction
	public String getValueVariable(@Nonnull Class<? extends MSBuildVariableProvider> variableClazz) throws MSBuildEvaluatioException
	{
		for(MSBuildVariableProvider provider : MSBuildVariableProvider.EP_NAME.getExtensionList())
		{
			if(provider.getClass() == variableClazz)
			{
				return getVariableValue(provider.getName());
			}
		}
		throw new UnsupportedOperationException();
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
