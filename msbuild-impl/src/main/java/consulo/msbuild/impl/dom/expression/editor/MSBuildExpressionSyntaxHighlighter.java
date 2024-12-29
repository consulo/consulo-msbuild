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

import consulo.codeEditor.DefaultLanguageHighlighterColors;
import consulo.colorScheme.TextAttributesKey;
import consulo.language.ast.IElementType;
import consulo.language.editor.highlight.SyntaxHighlighterBase;
import consulo.language.lexer.Lexer;
import consulo.msbuild.impl.dom.expression.lang.lexer.MSBuildExpressionMergeLexer;
import consulo.msbuild.impl.dom.expression.lang.psi.MSBuildExpressionTokens;

import jakarta.annotation.Nonnull;
import java.util.HashMap;
import java.util.Map;

/**
 * @author VISTALL
 * @since 16-Jun-17
 */
public class MSBuildExpressionSyntaxHighlighter extends SyntaxHighlighterBase
{
	private static final Map<IElementType, TextAttributesKey> ourKeys = new HashMap<>();

	static
	{
		safeMap(ourKeys, MSBuildExpressionTokens.MACRO_OPEN, DefaultLanguageHighlighterColors.KEYWORD);
		safeMap(ourKeys, MSBuildExpressionTokens.MACRO_STOP, DefaultLanguageHighlighterColors.KEYWORD);
		safeMap(ourKeys, MSBuildExpressionTokens.PATH_SEPARATOR, DefaultLanguageHighlighterColors.KEYWORD);
	}

	@Nonnull
	@Override
	public Lexer getHighlightingLexer()
	{
		return new MSBuildExpressionMergeLexer();
	}

	@Nonnull
	@Override
	public TextAttributesKey[] getTokenHighlights(IElementType iElementType)
	{
		return pack(ourKeys.get(iElementType));
	}
}
