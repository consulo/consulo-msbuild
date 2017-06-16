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

package consulo.msbuild.dom.expression.lang.psi.reference;

import org.jetbrains.annotations.NotNull;
import com.intellij.openapi.util.Comparing;
import com.intellij.openapi.util.TextRange;
import com.intellij.openapi.vfs.LocalFileSystem;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiDirectory;
import com.intellij.psi.PsiElementResolveResult;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiManager;
import com.intellij.psi.ResolveResult;
import com.intellij.psi.impl.source.resolve.reference.impl.providers.FileReference;
import com.intellij.psi.impl.source.resolve.reference.impl.providers.FileReferenceSet;
import consulo.msbuild.dom.expression.evaluate.MSBuildEvaluateContext;
import consulo.msbuild.dom.expression.evaluate.variable.MSBuildVariableProvider;

/**
 * @author VISTALL
 * @since 16-Jun-17
 */
public class MSBuildExpressionFileReference extends FileReference
{
	public MSBuildExpressionFileReference(@NotNull FileReferenceSet fileReferenceSet, TextRange range, int index, String text)
	{
		super(fileReferenceSet, range, index, text);
	}

	@NotNull
	@Override
	protected ResolveResult[] innerResolve(boolean caseSensitive, @NotNull PsiFile containingFile)
	{
		String decode = decode(getText());

		if(getIndex() == 0)
		{
			VirtualFile fileByPath = LocalFileSystem.getInstance().findFileByPath(decode);
			if(fileByPath != null)
			{
				PsiManager psiManager = PsiManager.getInstance(getFileReferenceSet().getElement().getProject());

				PsiDirectory directory = psiManager.findDirectory(fileByPath);
				if(directory != null)
				{
					return new ResolveResult[]{new PsiElementResolveResult(directory)};
				}
			}
		}
		return super.innerResolve(caseSensitive, containingFile);
	}

	@NotNull
	@Override
	public String decode(@NotNull String text)
	{
		String variable = text.substring(2, text.length() - 1);

		for(MSBuildVariableProvider provider : MSBuildVariableProvider.EP_NAME.getExtensions())
		{
			if(Comparing.equal(provider.getName(), variable))
			{
				String value = provider.evaluate(new MSBuildEvaluateContext(getElement()));
				if(value != null)
				{
					return value;
				}
			}
		}
		return text;
	}
}
