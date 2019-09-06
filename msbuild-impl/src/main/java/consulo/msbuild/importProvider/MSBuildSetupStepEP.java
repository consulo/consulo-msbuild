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

package consulo.msbuild.importProvider;

import com.intellij.openapi.extensions.AbstractExtensionPointBean;
import com.intellij.openapi.extensions.ExtensionPointName;
import com.intellij.openapi.util.NotNullLazyValue;
import com.intellij.util.xmlb.annotations.Attribute;
import consulo.msbuild.importProvider.item.MSBuildImportProject;
import consulo.ui.wizard.WizardStep;

import javax.annotation.Nonnull;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * @author VISTALL
 * @since 05-Feb-17
 */
public class MSBuildSetupStepEP extends AbstractExtensionPointBean
{
	public static final ExtensionPointName<MSBuildSetupStepEP> EP = ExtensionPointName.create("consulo.msbuild.setupStep");

	@Attribute("importProjectClass")
	public String importProjectClass;

	@Attribute("stepClass")
	public String stepClass;

	private final NotNullLazyValue<Class<? extends MSBuildImportProject>> myImportProjectClassValue = NotNullLazyValue.createValue(() ->
	{
		try
		{
			return findClass(importProjectClass);
		}
		catch(ClassNotFoundException e)
		{
			throw new RuntimeException(e);
		}
	});

	private final NotNullLazyValue<Constructor<? extends WizardStep<MSBuildModuleImportContext>>> myStepConstructorValue = NotNullLazyValue.createValue(() ->
	{
		try
		{
			Class<WizardStep<MSBuildModuleImportContext>> aClass = findClass(stepClass);
			return aClass.getConstructor(MSBuildModuleImportContext.class);
		}
		catch(ClassNotFoundException | NoSuchMethodException e)
		{
			throw new RuntimeException(e);
		}
	});

	@Nonnull
	public Class<? extends MSBuildImportProject> getImportProjectClass()
	{
		return myImportProjectClassValue.getValue();
	}

	@Nonnull
	public WizardStep<MSBuildModuleImportContext> createStep(MSBuildModuleImportContext context)
	{
		try
		{
			return myStepConstructorValue.getValue().newInstance(context);
		}
		catch(InstantiationException | IllegalAccessException | InvocationTargetException e)
		{
			throw new RuntimeException(e);
		}
	}
}
