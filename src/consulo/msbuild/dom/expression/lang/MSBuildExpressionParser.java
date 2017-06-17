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

package consulo.msbuild.dom.expression.lang;

import org.jetbrains.annotations.NotNull;
import com.intellij.lang.ASTNode;
import com.intellij.lang.PsiBuilder;
import com.intellij.lang.PsiBuilderUtil;
import com.intellij.lang.PsiParser;
import com.intellij.psi.tree.IElementType;
import consulo.lang.LanguageVersion;
import consulo.msbuild.dom.expression.lang.psi.MSBuildExpressionElements;
import consulo.msbuild.dom.expression.lang.psi.MSBuildExpressionTokens;

/**
 * @author VISTALL
 * @since 02-Feb-17
 */
public class MSBuildExpressionParser implements PsiParser
{
	@NotNull
	@Override
	public ASTNode parse(@NotNull IElementType root, @NotNull PsiBuilder builder, @NotNull LanguageVersion languageVersion)
	{
		PsiBuilder.Marker last = null;

		PsiBuilder.Marker mark = builder.mark();
		while(!builder.eof())
		{
			if(builder.getTokenType() == MSBuildExpressionTokens.MACRO_START)
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
				last = macroMarker;
			}
			/*else if(builder.getTokenType() == MSBuildExpressionTokens.PATH_SEPARATOR)
			{
				PsiBuilder.Marker marker = last == null ? builder.mark() : last.precede();


			} */
			else
			{
				builder.advanceLexer();
				last = null;
			}
		}
		mark.done(root);
		return builder.getTreeBuilt();
	}
}
