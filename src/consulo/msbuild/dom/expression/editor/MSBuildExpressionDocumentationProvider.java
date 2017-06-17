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

package consulo.msbuild.dom.expression.editor;

import java.util.List;

import org.jetbrains.annotations.Nullable;
import com.intellij.lang.documentation.DocumentationProvider;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiManager;
import consulo.msbuild.dom.expression.lang.psi.MSBuildLightMacroValue;

/**
 * @author VISTALL
 * @since 17-Jun-17
 */
public class MSBuildExpressionDocumentationProvider implements DocumentationProvider
{
	@Nullable
	@Override
	public String getQuickNavigateInfo(PsiElement element, PsiElement originalElement)
	{
		if(element instanceof MSBuildLightMacroValue)
		{
			return ((MSBuildLightMacroValue) element).getValue();
		}
		return null;
	}

	@Nullable
	@Override
	public List<String> getUrlFor(PsiElement element, PsiElement originalElement)
	{
		return null;
	}

	@Nullable
	@Override
	public String generateDoc(PsiElement element, @Nullable PsiElement originalElement)
	{
		return null;
	}

	@Nullable
	@Override
	public PsiElement getDocumentationElementForLookupItem(PsiManager psiManager, Object object, PsiElement element)
	{
		return null;
	}

	@Nullable
	@Override
	public PsiElement getDocumentationElementForLink(PsiManager psiManager, String link, PsiElement context)
	{
		return null;
	}
}
