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
import org.jetbrains.annotations.Nullable;
import com.intellij.psi.ElementManipulators;
import com.intellij.psi.InjectedLanguagePlaces;
import com.intellij.psi.LanguageInjector;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiLanguageInjectionHost;
import com.intellij.psi.xml.XmlAttribute;
import com.intellij.psi.xml.XmlAttributeValue;
import com.intellij.psi.xml.XmlFile;
import com.intellij.psi.xml.XmlTag;
import com.intellij.psi.xml.XmlText;
import com.intellij.util.xml.DomElement;
import com.intellij.util.xml.DomFileElement;
import com.intellij.util.xml.DomManager;
import com.intellij.util.xml.GenericAttributeValue;
import consulo.annotations.RequiredReadAction;
import consulo.msbuild.dom.ExpressionFragment;
import consulo.msbuild.dom.Project;

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
			PsiFile containingFile = host.getContainingFile();

			if(containingFile instanceof XmlFile)
			{
				DomFileElement<Project> fileElement = DomManager.getDomManager(host.getProject()).getFileElement((XmlFile) containingFile, Project.class);
				if(fileElement == null)
				{
					return;
				}

				DomElement domElement = getDomElement(host);
				if(domElement == null)
				{
					return;
				}

				if(domElement instanceof GenericAttributeValue && domElement.getAnnotation(ExpressionFragment.class) != null)
				{
					injectionPlacesRegistrar.addPlace(MSBuildExpressionLanguage.INSTANCE, ElementManipulators.getValueTextRange(host), null, null);
				}
			}
		}
	}

	@Nullable
	public static DomElement getDomElement(PsiElement element)
	{
		if(element instanceof XmlAttributeValue)
		{
			PsiElement parent = element.getParent();
			if(!(parent instanceof XmlAttribute))
			{
				return null;
			}
			return DomManager.getDomManager(element.getProject()).getDomElement((XmlAttribute) parent);
		}
		else if(element instanceof XmlText)
		{
			return DomManager.getDomManager(element.getProject()).getDomElement((XmlTag) element.getParent());
		}
		return null;
	}
}
