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

package consulo.msbuild.impl.dom.expression.evaluate.variable.impl;

import consulo.annotation.access.RequiredReadAction;
import consulo.annotation.component.ExtensionImpl;
import consulo.content.bundle.Sdk;
import consulo.content.bundle.SdkTable;
import consulo.msbuild.dom.expression.evaluate.MSBuildEvaluateContext;
import consulo.msbuild.dom.expression.evaluate.variable.MSBuildVariableProvider;
import consulo.msbuild.module.extension.MSBuildSolutionModuleExtension;
import consulo.util.lang.Version;
import jakarta.inject.Inject;

import jakarta.annotation.Nonnull;

/**
 * @author VISTALL
 * @since 16-Jun-17
 */
@ExtensionImpl
public class MSBuildToolsVersion extends MSBuildVariableProvider
{
	private final SdkTable mySdkTable;

	@Inject
	public MSBuildToolsVersion(SdkTable sdkTable)
	{
		mySdkTable = sdkTable;
	}

	@RequiredReadAction
	@Override
	public String evaluateUnsafe(@Nonnull MSBuildEvaluateContext context)
	{
		MSBuildSolutionModuleExtension<?> solutionModuleExtension = MSBuildSolutionModuleExtension.getSolutionModuleExtension(context.getProject());
		if(solutionModuleExtension == null)
		{
			return null;
		}

		String msBuildBundleName = solutionModuleExtension.getSdkName();
		if(msBuildBundleName == null)
		{
			return null;
		}

		Sdk sdk = mySdkTable.findSdk(msBuildBundleName);
		if(sdk != null)
		{
			Version version = Version.parseVersion(sdk.getVersionString());

			if(version == null)
			{
				return null;
			}

			return version.major + "." + "0";
		}

		return null;
	}
}
