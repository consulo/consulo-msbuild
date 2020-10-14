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

package consulo.msbuild.evaluate;

import com.intellij.openapi.application.AccessToken;
import com.intellij.openapi.projectRoots.Sdk;
import com.intellij.openapi.util.io.FileUtil;
import com.intellij.openapi.vfs.LocalFileSystem;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiManager;
import com.intellij.psi.xml.XmlElement;
import com.intellij.psi.xml.XmlFile;
import com.intellij.psi.xml.XmlTag;
import com.intellij.util.xml.DomElement;
import com.intellij.util.xml.DomFileElement;
import com.intellij.util.xml.DomManager;
import consulo.annotation.access.RequiredReadAction;
import consulo.logging.Logger;
import consulo.msbuild.dom.*;
import consulo.msbuild.dom.expression.evaluate.MSBuildExpressionEvaluator;
import consulo.msbuild.module.extension.MSBuildRootExtension;
import consulo.util.lang.StringUtil;

import javax.annotation.Nonnull;
import java.io.File;
import java.util.function.Consumer;

/**
 * @author VISTALL
 * @since 03-Feb-17
 * <p>
 * TODO [VISTALL] walk in Import tags
 */
public class MSBuildProjectEvaluator
{
	private static final Logger LOG = Logger.getInstance(MSBuildProjectEvaluator.class);

	private final Project myProject;

	public MSBuildProjectEvaluator(Project project)
	{
		myProject = project;
	}

	@RequiredReadAction
	private MSBuildProjectEvaluateResult evaluate()
	{
		XmlElement xmlElement = myProject.getXmlElement();
		MSBuildEvaluateContext context = MSBuildEvaluateContext.from(xmlElement);

		MSBuildExpressionEvaluator expressionEvaluator = MSBuildExpressionEvaluator.create();

		walkIntoSdk(context, expressionEvaluator, "Sdk.props");
		walkIntoSdk(context, expressionEvaluator, "Sdk.targets");

		evaluateProject(myProject, context, expressionEvaluator);

		return new MSBuildProjectEvaluateResult(context.getVariableValues());
	}

	@RequiredReadAction
	private void evaluateProject(Project projectElement, MSBuildEvaluateContext context, MSBuildExpressionEvaluator expressionEvaluator)
	{
		XmlElement xmlElement = projectElement.getXmlElement();
		try(AccessToken ignored = context.processFile(xmlElement.getContainingFile().getVirtualFile()))
		{
			for(XmlTag xmlTag : ((XmlTag) xmlElement).getSubTags())
			{
				DomManager manager = DomManager.getDomManager(xmlTag.getProject());

				DomElement domElement = manager.getDomElement(xmlTag);

				if(domElement instanceof PropertyGroup)
				{
					if(validate((ElementWithCondition) domElement, context, expressionEvaluator))
					{
						for(Property property : ((PropertyGroup) domElement).getProperties())
						{
							if(validate(property, context, expressionEvaluator))
							{
								context.putVariableValue(property.getXmlElementName(), property.getText());
							}
						}
					}
				}
				else if(domElement instanceof Import)
				{
					if(validate((ElementWithCondition) domElement, context, expressionEvaluator))
					{
						String value = ((Import) domElement).getProject().getValue();

						if(StringUtil.isEmptyOrSpaces(value))
						{
							continue;
						}

						String evalutedProjectPath = expressionEvaluator.evaluatePath(value, context);
						if(evalutedProjectPath != null)
						{
							if(FileUtil.isAbsolute(evalutedProjectPath))
							{
								walkIntoFile(new File(evalutedProjectPath), context, expressionEvaluator);
							}
							else
							{
								String currentDirrectory = context.getCurrentFile().getParent().getPath();
								walkIntoFile(new File(currentDirrectory, evalutedProjectPath), context, expressionEvaluator);
							}
						}
					}
				}
				else if(domElement instanceof SimpleItem)
				{
					context.addProjectItem((SimpleItem) domElement);
				}
			}
		}
	}

	private boolean validate(ElementWithCondition element, MSBuildEvaluateContext context, MSBuildExpressionEvaluator expressionEvaluator)
	{
		String value = element.getCondition().getValue();

		if(value == null || expressionEvaluator.evaluate(value, context, Boolean.class) == Boolean.TRUE)
		{
			return true;
		}
		return false;
	}

	@RequiredReadAction
	@SuppressWarnings("unchecked")
	public <T extends DomElement> void walk(@Nonnull Class<T> clazz, @Nonnull Consumer<T> consumer)
	{
		//TODO [VISTALL] stub!
		evaluate();
	}

	@RequiredReadAction
	private void walkIntoSdk(MSBuildEvaluateContext context, MSBuildExpressionEvaluator expressionEvaluator, String fileName)
	{
		String sdkValue = myProject.getSdk().getValue();
		if(StringUtil.isEmpty(sdkValue))
		{
			return;
		}

		MSBuildRootExtension<?> moduleExtension = context.getModuleExtension();

		Object o = moduleExtension == null ? null : moduleExtension.resolveAutoSdk();

		if(!(o instanceof Sdk))
		{
			return;
		}

		String homePath = ((Sdk) o).getHomePath();

		File sdkFile = new File(homePath, "Sdks/" + sdkValue + "/Sdk/" + fileName);

		walkIntoFile(sdkFile, context, expressionEvaluator);
	}

	@RequiredReadAction
	private void walkIntoFile(File ioFile, MSBuildEvaluateContext context, MSBuildExpressionEvaluator expressionEvaluator)
	{
		if(ioFile.exists())
		{
			VirtualFile file = LocalFileSystem.getInstance().refreshAndFindFileByIoFile(ioFile);
			if(file == null)
			{
				return;
			}

			com.intellij.openapi.project.Project project = context.getModule().getProject();
			PsiManager manager = PsiManager.getInstance(project);

			PsiFile psiFile = manager.findFile(file);
			if(!(psiFile instanceof XmlFile))
			{
				return;
			}

			DomFileElement<Project> fileElement = DomManager.getDomManager(project).getFileElement((XmlFile) psiFile, Project.class);
			if(fileElement == null)
			{
				return;
			}

			Project rootElement = fileElement.getRootElement();

			evaluateProject(rootElement, context, expressionEvaluator);
		}
		else
		{
			LOG.warn("Try to walk not existen file: " + ioFile.getPath());
		}
	}
}
