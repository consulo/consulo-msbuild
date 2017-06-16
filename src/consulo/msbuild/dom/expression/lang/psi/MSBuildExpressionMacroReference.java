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

package consulo.msbuild.dom.expression.lang.psi;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.PsiReference;
import com.intellij.util.IncorrectOperationException;
import consulo.annotations.RequiredReadAction;
import consulo.annotations.RequiredWriteAction;

/**
 * @author VISTALL
 * @since 16-Jun-17
 */
public class MSBuildExpressionMacroReference extends ASTWrapperPsiElement implements PsiReference
{
	public MSBuildExpressionMacroReference(@NotNull ASTNode node)
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
	@NotNull
	@Override
	public TextRange getRangeInElement()
	{
		PsiElement element = getElement();
		return new TextRange(0, element.getTextLength());
	}

	@RequiredReadAction
	@Nullable
	@Override
	public PsiElement resolve()
	{
		return null;
	}

	@RequiredReadAction
	@NotNull
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
	public PsiElement bindToElement(@NotNull PsiElement element) throws IncorrectOperationException
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
	public void accept(@NotNull PsiElementVisitor visitor)
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
