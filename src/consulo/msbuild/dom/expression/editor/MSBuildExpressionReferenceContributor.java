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

package consulo.msbuild.dom.expression.editor;

import org.jetbrains.annotations.NotNull;
import com.intellij.openapi.util.TextRange;
import com.intellij.patterns.XmlPatterns;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiReference;
import com.intellij.psi.PsiReferenceContributor;
import com.intellij.psi.PsiReferenceProvider;
import com.intellij.psi.PsiReferenceRegistrar;
import com.intellij.psi.impl.source.resolve.reference.impl.providers.FileReference;
import com.intellij.psi.impl.source.resolve.reference.impl.providers.FileReferenceSet;
import com.intellij.psi.xml.XmlFile;
import com.intellij.util.ProcessingContext;
import com.intellij.util.xml.DomElement;
import com.intellij.util.xml.DomFileElement;
import com.intellij.util.xml.DomManager;
import com.intellij.util.xml.GenericAttributeValue;
import consulo.msbuild.dom.PathReferenceSet;
import consulo.msbuild.dom.Project;
import consulo.msbuild.dom.expression.lang.MSBuildExpressionXmlInjector;
import consulo.msbuild.dom.expression.lang.psi.reference.MSBuildExpressionFileReference;

/**
 * @author VISTALL
 * @since 16-Jun-17
 */
public class MSBuildExpressionReferenceContributor extends PsiReferenceContributor
{
	@Override
	public void registerReferenceProviders(PsiReferenceRegistrar registrar)
	{
		registrar.registerReferenceProvider(XmlPatterns.xmlAttributeValue(), new PsiReferenceProvider()
		{
			@NotNull
			@Override
			public PsiReference[] getReferencesByElement(@NotNull PsiElement element, @NotNull ProcessingContext context)
			{
				PsiFile containingFile = element.getContainingFile();
				DomFileElement<Project> fileElement = DomManager.getDomManager(element.getProject()).getFileElement((XmlFile) containingFile, Project.class);
				if(fileElement == null)
				{
					return PsiReference.EMPTY_ARRAY;
				}

				DomElement domElement = MSBuildExpressionXmlInjector.getDomElement(element);
				if(domElement == null)
				{
					return PsiReference.EMPTY_ARRAY;
				}

				if(domElement instanceof GenericAttributeValue && domElement.getAnnotation(PathReferenceSet.class) != null)
				{
					FileReferenceSet set = new FileReferenceSet(element)
					{
						@Override
						public FileReference createFileReference(TextRange range, int index, String text)
						{
							if(text.startsWith("$(") && text.endsWith(")"))
							{
								return new MSBuildExpressionFileReference(this, range, index, text);
							}
							return super.createFileReference(range, index, text);
						}

						@Override
						public boolean isAbsolutePathReference()
						{
							return true;
						}

					/*	@Override
						protected boolean isSoft()
						{
							return true;
						}*/

						@Override
						public String getSeparatorString()
						{
							return "\\";
						}
					};
					return set.getAllReferences();
				}
				return PsiReference.EMPTY_ARRAY;
			}
		});
	}
}
