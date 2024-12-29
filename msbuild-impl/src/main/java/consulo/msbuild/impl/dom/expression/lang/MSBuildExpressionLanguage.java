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

import consulo.language.Language;
import consulo.language.version.LanguageVersion;

import jakarta.annotation.Nonnull;

/**
 * @author VISTALL
 * @since 16-Jun-17
 */
public class MSBuildExpressionLanguage extends Language
{
	public static class PathVersion extends LanguageVersion
	{
		public static final PathVersion INSTANCE = new PathVersion();

		public PathVersion()
		{
			super("PATH", "Path", MSBuildExpressionLanguage.INSTANCE);
		}
	}

	public static class ExpressionVersion extends LanguageVersion
	{
		public static final ExpressionVersion INSTANCE = new ExpressionVersion();

		public ExpressionVersion()
		{
			super("EXPRESSION", "Expression", MSBuildExpressionLanguage.INSTANCE);
		}
	}

	public static final MSBuildExpressionLanguage INSTANCE = new MSBuildExpressionLanguage();

	private MSBuildExpressionLanguage()
	{
		super("MSBuildExpression");
	}

	@Nonnull
	@Override
	protected LanguageVersion[] findVersions()
	{
		return new LanguageVersion[]{
				ExpressionVersion.INSTANCE,
				PathVersion.INSTANCE
		};
	}
}
