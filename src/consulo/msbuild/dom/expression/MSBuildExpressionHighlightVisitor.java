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

package consulo.msbuild.dom.expression;

import org.jetbrains.annotations.NotNull;
import com.intellij.codeInsight.daemon.impl.HighlightInfo;
import com.intellij.codeInsight.daemon.impl.HighlightInfoType;
import com.intellij.codeInsight.daemon.impl.HighlightVisitor;
import com.intellij.codeInsight.daemon.impl.analysis.HighlightInfoHolder;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import consulo.annotations.RequiredReadAction;
import consulo.msbuild.dom.expression.lang.psi.MSBuildExpressionElementVisitor;
import consulo.msbuild.dom.expression.lang.psi.MSBuildExpressionFile;
import consulo.msbuild.dom.expression.lang.psi.MSBuildExpressionMacroReference;

/**
 * @author VISTALL
 * @since 16-Jun-17
 */
public class MSBuildExpressionHighlightVisitor extends MSBuildExpressionElementVisitor implements HighlightVisitor
{
	private HighlightInfoHolder myHighlightInfoHolder;

	@Override
	public boolean suitableForFile(@NotNull PsiFile psiFile)
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
	}

	@Override
	public void visit(@NotNull PsiElement psiElement)
	{
		psiElement.accept(this);
	}

	@Override
	public boolean analyze(@NotNull PsiFile psiFile, boolean b, @NotNull HighlightInfoHolder highlightInfoHolder, @NotNull Runnable runnable)
	{
		myHighlightInfoHolder = highlightInfoHolder;
		runnable.run();
		return true;
	}

	@NotNull
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
