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

package consulo.msbuild.impl.dom.expression.lang.psi;

import consulo.annotation.access.RequiredReadAction;
import consulo.annotation.access.RequiredWriteAction;
import consulo.document.util.TextRange;
import consulo.language.ast.ASTNode;
import consulo.language.impl.psi.ASTWrapperPsiElement;
import consulo.language.psi.PsiElement;
import consulo.language.psi.PsiElementVisitor;
import consulo.language.psi.PsiReference;
import consulo.language.psi.resolve.ResolveCache;
import consulo.language.util.IncorrectOperationException;
import consulo.msbuild.dom.expression.evaluate.MSBuildEvaluateContext;
import consulo.msbuild.dom.expression.evaluate.variable.MSBuildVariableProvider;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

/**
 * @author VISTALL
 * @since 16-Jun-17
 */
public class MSBuildExpressionMacroReference extends ASTWrapperPsiElement implements PsiReference
{
	public MSBuildExpressionMacroReference(@Nonnull ASTNode node)
	{
		super(node);
	}

	@RequiredReadAction
	@Override
	public PsiElement getElement()
	{
		return findNotNullChildByType(MSBuildExpressionTokens.MACRO_NAME);
	}

	@RequiredReadAction
	@Nonnull
	@Override
	public TextRange getRangeInElement()
	{
		PsiElement element = getElement();
		return new TextRange(0, element.getTextLength());
	}

	@Override
	public PsiReference getReference()
	{
		return this;
	}

	@RequiredReadAction
	@Nullable
	@Override
	public PsiElement resolve()
	{
		return ResolveCache.getInstance(getProject()).resolveWithCaching(this, (reference, incompleteCode) ->
		{
			String text = getText();
			MSBuildVariableProvider provider = MSBuildVariableProvider.findProvider(getProject().getApplication(), text);
			if(provider != null)
			{
				String value = provider.evaluate(MSBuildEvaluateContext.from(this));
				if(value != null)
				{
					return new MSBuildLightMacroValue(getProject(), text, value);
				}
			}
			return null;
		}, false, true);
	}

	@RequiredReadAction
	@Nonnull
	@Override
	public String getCanonicalText()
	{
		return getText();
	}

	@RequiredWriteAction
	@Override
	public PsiElement handleElementRename(String newElementName) throws IncorrectOperationException
	{
		return null;
	}

	@RequiredWriteAction
	@Override
	public PsiElement bindToElement(@Nonnull PsiElement element) throws IncorrectOperationException
	{
		return null;
	}

	@RequiredReadAction
	@Override
	public boolean isReferenceTo(PsiElement element)
	{
		return element.isEquivalentTo(resolve());
	}

	@RequiredReadAction
	@Override
	public boolean isSoft()
	{
		return true;
	}

	@Override
	public void accept(@Nonnull PsiElementVisitor visitor)
	{
		if(visitor instanceof MSBuildExpressionElementVisitor)
		{
			((MSBuildExpressionElementVisitor) visitor).visitMacroReference(this);
		}
		else
		{
			visitor.visitElement(this);
		}
	}
}
