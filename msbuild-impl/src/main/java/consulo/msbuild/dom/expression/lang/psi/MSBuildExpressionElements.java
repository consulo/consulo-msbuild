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

import com.intellij.psi.tree.IElementType;
import consulo.msbuild.dom.expression.lang.MSBuildExpressionLanguage;
import consulo.psi.tree.ElementTypeAsPsiFactory;

/**
 * @author VISTALL
 * @since 16-Jun-17
 */
public interface MSBuildExpressionElements
{
	IElementType MACRO = new ElementTypeAsPsiFactory("MACRO", MSBuildExpressionLanguage.INSTANCE, MSBuildExpressionMacro.class);

	IElementType MACRO_REFERENCE = new ElementTypeAsPsiFactory("MACRO_REFERENCE", MSBuildExpressionLanguage.INSTANCE, MSBuildExpressionMacroReference.class);

	IElementType MERGED_VALUE = new ElementTypeAsPsiFactory("MERGED_VALUE", MSBuildExpressionLanguage.INSTANCE, MSBuildMergedValue.class);

	IElementType BINARY_EXPRESSION = new ElementTypeAsPsiFactory("BINARY_EXPRESSION", MSBuildExpressionLanguage.INSTANCE, MSBuildBinaryExpression.class);

	IElementType FUNCTION_CALL_EXPRESSION = new ElementTypeAsPsiFactory("FUNCTION_CALL_EXPRESSION", MSBuildExpressionLanguage.INSTANCE, MSBuildFunctionCallExpression.class);
}
