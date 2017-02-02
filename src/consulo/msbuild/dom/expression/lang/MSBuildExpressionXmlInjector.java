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
import com.intellij.psi.InjectedLanguagePlaces;
import com.intellij.psi.LanguageInjector;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiLanguageInjectionHost;
import com.intellij.psi.xml.XmlAttribute;
import com.intellij.psi.xml.XmlAttributeValue;
import com.intellij.psi.xml.XmlTag;
import com.intellij.psi.xml.XmlText;
import com.intellij.util.xml.DomElement;
import com.intellij.util.xml.DomManager;
import consulo.annotations.RequiredReadAction;

/**
 * @author VISTALL
 * @since 02-Feb-17
 */
public class MSBuildExpressionXmlInjector implements LanguageInjector
{
	@Override
	@RequiredReadAction
	public void getLanguagesToInject(@NotNull PsiLanguageInjectionHost host, @NotNull InjectedLanguagePlaces injectionPlacesRegistrar)
	{
		if(host instanceof XmlAttributeValue || host instanceof XmlText)
		{
			
		}
	}

	private static DomElement getDomElement(PsiElement element)
	{
		if(element instanceof XmlAttributeValue)
		{
			return DomManager.getDomManager(element.getProject()).getDomElement((XmlAttribute) element.getParent());
		}
		else if(element instanceof XmlText)
		{
			return DomManager.getDomManager(element.getProject()).getDomElement((XmlTag) element.getParent());
		}
		return null;
	}
}
