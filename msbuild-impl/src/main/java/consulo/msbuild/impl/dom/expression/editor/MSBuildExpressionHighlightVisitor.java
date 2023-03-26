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

package consulo.msbuild.impl.dom.expression.editor;

import consulo.annotation.access.RequiredReadAction;
import consulo.codeEditor.DefaultLanguageHighlighterColors;
import consulo.language.editor.rawHighlight.HighlightInfo;
import consulo.language.editor.rawHighlight.HighlightInfoHolder;
import consulo.language.editor.rawHighlight.HighlightInfoType;
import consulo.language.editor.rawHighlight.HighlightVisitor;
import consulo.language.psi.PsiElement;
import consulo.language.psi.PsiFile;
import consulo.msbuild.impl.dom.expression.lang.psi.MSBuildExpressionElementVisitor;
import consulo.msbuild.impl.dom.expression.lang.psi.MSBuildExpressionMacroReference;
import consulo.msbuild.impl.dom.expression.lang.psi.MSBuildLightMacroValue;

import javax.annotation.Nonnull;

/**
 * @author VISTALL
 * @since 16-Jun-17
 */
public class MSBuildExpressionHighlightVisitor extends MSBuildExpressionElementVisitor implements HighlightVisitor
{
	private HighlightInfoHolder myHighlightInfoHolder;

	@RequiredReadAction
	@Override
	public void visitMacroReference(MSBuildExpressionMacroReference reference)
	{
		PsiElement resolved = reference.resolve();
		if(resolved == null)
		{
			String text = "'" + reference.getText() + "' is not resolved";
			myHighlightInfoHolder.add(HighlightInfo.newHighlightInfo(HighlightInfoType.WARNING).descriptionAndTooltip(text).range(reference).create());
		}
		else if(resolved instanceof MSBuildLightMacroValue)
		{
			myHighlightInfoHolder.add(HighlightInfo.newHighlightInfo(HighlightInfoType.INFORMATION).textAttributes(DefaultLanguageHighlighterColors.INSTANCE_FIELD).range(reference).create());
		}
	}

	@Override
	public void visit(@Nonnull PsiElement psiElement)
	{
		psiElement.accept(this);
	}

	@Override
	public boolean analyze(@Nonnull PsiFile psiFile, boolean b, @Nonnull HighlightInfoHolder highlightInfoHolder, @Nonnull Runnable runnable)
	{
		myHighlightInfoHolder = highlightInfoHolder;
		runnable.run();
		return true;
	}
}
