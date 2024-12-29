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

import consulo.annotation.component.ExtensionImpl;
import consulo.language.Language;
import consulo.language.editor.highlight.SyntaxHighlighter;
import consulo.language.editor.highlight.SyntaxHighlighterFactory;
import consulo.msbuild.impl.dom.expression.lang.MSBuildExpressionLanguage;
import consulo.project.Project;
import consulo.virtualFileSystem.VirtualFile;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

/**
 * @author VISTALL
 * @since 16-Jun-17
 */
@ExtensionImpl
public class MSBuildExpressionSyntaxHighlighterFactory extends SyntaxHighlighterFactory
{
	@Nonnull
	@Override
	public SyntaxHighlighter getSyntaxHighlighter(@Nullable Project project, @Nullable VirtualFile virtualFile)
	{
		return new MSBuildExpressionSyntaxHighlighter();
	}

	@Nonnull
	@Override
	public Language getLanguage()
	{
		return MSBuildExpressionLanguage.INSTANCE;
	}
}
