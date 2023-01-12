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

import consulo.language.inject.MultiHostInjector;
import consulo.language.inject.MultiHostRegistrar;
import consulo.language.psi.ElementManipulators;
import consulo.language.psi.PsiElement;
import consulo.language.psi.PsiFile;
import consulo.language.psi.PsiLanguageInjectionHost;
import consulo.language.version.LanguageVersion;
import consulo.msbuild.dom.Project;
import consulo.msbuild.dom.annotation.ExpressionFragment;
import consulo.xml.psi.xml.*;
import consulo.xml.util.xml.DomElement;
import consulo.xml.util.xml.DomFileElement;
import consulo.xml.util.xml.DomManager;
import consulo.xml.util.xml.GenericAttributeValue;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * @author VISTALL
 * @since 02-Feb-17
 */
public abstract class MSBuildBaseExpressionXmlInjector implements MultiHostInjector
{
	@Override
	public void injectLanguages(@Nonnull MultiHostRegistrar registrar, @Nonnull PsiElement host)
	{
		if(Boolean.TRUE)
		{
			// FIXME [VISTALL] disable for now
			return;
		}

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
