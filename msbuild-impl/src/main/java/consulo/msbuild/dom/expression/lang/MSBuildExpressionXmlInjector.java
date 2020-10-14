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

import com.intellij.lang.injection.MultiHostInjector;
import com.intellij.lang.injection.MultiHostRegistrar;
import com.intellij.psi.ElementManipulators;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiLanguageInjectionHost;
import com.intellij.psi.xml.*;
import com.intellij.util.xml.DomElement;
import com.intellij.util.xml.DomFileElement;
import com.intellij.util.xml.DomManager;
import com.intellij.util.xml.GenericAttributeValue;
import consulo.lang.LanguageVersion;
import consulo.msbuild.dom.Project;
import consulo.msbuild.dom.annotation.ExpressionFragment;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * @author VISTALL
 * @since 02-Feb-17
 */
public class MSBuildExpressionXmlInjector implements MultiHostInjector
{
	@Override
	public void injectLanguages(@Nonnull MultiHostRegistrar registrar, @Nonnull PsiElement host)
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

				ExpressionFragment annotation;
				if(domElement instanceof GenericAttributeValue && (annotation = domElement.getAnnotation(ExpressionFragment.class)) != null)
				{
					LanguageVersion ver = annotation.path() ? MSBuildExpressionLanguage.PathVersion.INSTANCE : MSBuildExpressionLanguage.ExpressionVersion.INSTANCE;

					registrar.startInjecting(ver).addPlace(null, null, (PsiLanguageInjectionHost) host, ElementManipulators.getValueTextRange(host)).doneInjecting();
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
