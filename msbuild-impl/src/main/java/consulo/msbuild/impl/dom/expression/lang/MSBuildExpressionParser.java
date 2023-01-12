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

package consulo.msbuild.impl.dom.expression.lang;

import consulo.language.ast.ASTNode;
import consulo.language.ast.IElementType;
import consulo.language.parser.PsiBuilder;
import consulo.language.parser.PsiBuilderUtil;
import consulo.language.parser.PsiParser;
import consulo.language.version.LanguageVersion;
import consulo.msbuild.impl.dom.expression.lang.psi.MSBuildExpressionElements;
import consulo.msbuild.impl.dom.expression.lang.psi.MSBuildExpressionTokens;

import javax.annotation.Nonnull;

/**
 * @author VISTALL
 * @since 02-Feb-17
 */
public class MSBuildExpressionParser implements PsiParser
{
	@Nonnull
	@Override
	public ASTNode parse(@Nonnull IElementType root, @Nonnull PsiBuilder builder, @Nonnull LanguageVersion languageVersion)
	{
		PsiBuilder.Marker mark = builder.mark();

		parseInner(builder);

		while(!builder.eof())
		{
			PsiBuilder.Marker errorMarker = builder.mark();
			builder.advanceLexer();
			errorMarker.error("Unexpected symbol");
		}

		mark.done(root);

		return builder.getTreeBuilt();
	}

	private void parseInner(PsiBuilder builder)
	{
		IElementType tokenType = builder.getTokenType();
		if(tokenType == null)
		{
			return;
		}

		skip(builder, MSBuildExpressionTokens.TEXT);

		PsiBuilder.Marker inner = parseSimpleExpression(builder);
		if(inner != null)
		{
			skip(builder, MSBuildExpressionTokens.TEXT);

			tokenType = builder.getTokenType();

			if(tokenType == MSBuildExpressionTokens.EQEQ)
			{
				inner = inner.precede();

				builder.advanceLexer();

				parseInner(builder);

				inner.done(MSBuildExpressionElements.BINARY_EXPRESSION);
			}
			else if(tokenType == MSBuildExpressionTokens.LPAR)
			{
				inner = inner.precede();

				builder.advanceLexer();

				parseInner(builder);

				skip(builder, MSBuildExpressionTokens.TEXT);

				if(builder.getTokenType() == MSBuildExpressionTokens.RPAR)
				{
					builder.advanceLexer();
				}
				else
				{
					builder.error("Expected ')'");
				}

				inner.done(MSBuildExpressionElements.FUNCTION_CALL_EXPRESSION);
			}
		}
		else
		{
			builder.error("Unexpected symbol");
		}
	}

	private void skip(PsiBuilder builder, IElementType elementType)
	{
		while(!builder.eof())
		{
			if(builder.getTokenType() == elementType)
			{
				builder.advanceLexer();
			}
			else
			{
				break;
			}
		}
	}

	private PsiBuilder.Marker parseSimpleExpression(PsiBuilder builder)
	{
		IElementType tokenType = builder.getTokenType();

		if(tokenType == MSBuildExpressionTokens.MACRO_OPEN)
		{
			PsiBuilder.Marker macroMarker = builder.mark();

			builder.advanceLexer();

			if(builder.getTokenType() == MSBuildExpressionTokens.MACRO_NAME)
			{
				PsiBuilder.Marker refMarker = builder.mark();
				builder.advanceLexer();
				refMarker.done(MSBuildExpressionElements.MACRO_REFERENCE);
			}
			else
			{
				builder.error("Name expected");
			}

			if(!PsiBuilderUtil.expect(builder, MSBuildExpressionTokens.MACRO_STOP))
			{
				builder.error("')' expected");
			}
			macroMarker.done(MSBuildExpressionElements.MACRO);
			return macroMarker;
		}
		else if(tokenType == MSBuildExpressionTokens.SINGLE_QUOTE)
		{
			PsiBuilder.Marker otherMarker = builder.mark();
			builder.advanceLexer();

			skip(builder, MSBuildExpressionTokens.TEXT);

			while(!builder.eof())
			{
				if(builder.getTokenType() == MSBuildExpressionTokens.SINGLE_QUOTE)
				{
					break;
				}

				parseInner(builder);
			}

			if(builder.getTokenType() != MSBuildExpressionTokens.SINGLE_QUOTE)
			{
				builder.error("Not closed value");
			}
			else
			{
				builder.advanceLexer();
			}

			otherMarker.done(MSBuildExpressionElements.MERGED_VALUE);
			return otherMarker;
		}

		return null;
	}
}
