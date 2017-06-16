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

import com.intellij.psi.TokenType;
import com.intellij.psi.tree.IElementType;
import consulo.msbuild.dom.expression.lang.MSBuildExpressionLanguage;

/**
 * @author VISTALL
 * @since 16-Jun-17
 */
public interface MSBuildExpressionTokens extends TokenType
{
	IElementType TEXT = new IElementType("TEXT", MSBuildExpressionLanguage.INSTANCE);

	IElementType SINGLE_QUOTE = new IElementType("SINGLE_QUOTE", MSBuildExpressionLanguage.INSTANCE);

	IElementType MACRO_START = new IElementType("MACRO_OPEN", MSBuildExpressionLanguage.INSTANCE);

	IElementType MACRO_STOP = new IElementType("MACRO_STOP", MSBuildExpressionLanguage.INSTANCE);

	IElementType MACRO_NAME = new IElementType("MACRO_NAME", MSBuildExpressionLanguage.INSTANCE);
}