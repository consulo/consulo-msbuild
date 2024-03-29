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

package consulo.msbuild.impl.dom.expression.lang.lexer;

import consulo.language.ast.TokenSet;
import consulo.language.lexer.MergingLexerAdapter;
import consulo.msbuild.impl.dom.expression.lang.psi.MSBuildExpressionTokens;

/**
 * @author VISTALL
 * @since 17-Jun-17
 */
public class MSBuildExpressionMergeLexer extends MergingLexerAdapter
{
	public MSBuildExpressionMergeLexer()
	{
		super(new MSBuildExpressionLexer(), TokenSet.create(MSBuildExpressionTokens.TEXT));
	}
}
