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

import com.intellij.codeInsight.daemon.impl.HighlightInfo;
import com.intellij.codeInsight.daemon.impl.HighlightInfoType;
import com.intellij.codeInsight.daemon.impl.HighlightVisitor;
import com.intellij.codeInsight.daemon.impl.analysis.HighlightInfoHolder;
import com.intellij.openapi.editor.DefaultLanguageHighlighterColors;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import consulo.annotation.access.RequiredReadAction;
import consulo.msbuild.dom.expression.lang.psi.MSBuildExpressionElementVisitor;
import consulo.msbuild.dom.expression.lang.psi.MSBuildExpressionFile;
import consulo.msbuild.dom.expression.lang.psi.MSBuildExpressionMacroReference;
import consulo.msbuild.dom.expression.lang.psi.MSBuildLightMacroValue;

import javax.annotation.Nonnull;

/**
 * @author VISTALL
 * @since 16-Jun-17
 */
public class MSBuildExpressionHighlightVisitor extends MSBuildExpressionElementVisitor implements HighlightVisitor
{
	private HighlightInfoHolder myHighlightInfoHolder;

	@Override
	public boolean suitableForFile(@Nonnull PsiFile psiFile)
	{
		return psiFile instanceof MSBuildExpressionFile;
	}

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

	@Nonnull
	@Override
	public HighlightVisitor clone()
	{
		return new MSBuildExpressionHighlightVisitor();
	}

	@Override
	public int order()
	{
		return 0;
	}
}
