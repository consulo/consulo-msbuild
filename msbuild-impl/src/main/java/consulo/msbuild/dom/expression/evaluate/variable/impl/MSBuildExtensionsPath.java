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

package consulo.msbuild.dom.expression.evaluate.variable.impl;

import com.intellij.openapi.projectRoots.Sdk;
import com.intellij.openapi.projectRoots.SdkTable;
import consulo.annotation.access.RequiredReadAction;
import consulo.msbuild.MSBuildSolutionManager;
import consulo.msbuild.dom.expression.evaluate.MSBuildEvaluateContext;
import consulo.msbuild.dom.expression.evaluate.variable.MSBuildVariableProvider;

import javax.annotation.Nonnull;

/**
 * @author VISTALL
 * @since 16-Jun-17
 */
public class MSBuildExtensionsPath extends MSBuildVariableProvider
{
	private final SdkTable mySdkTable;

	public MSBuildExtensionsPath(SdkTable sdkTable)
	{
		mySdkTable = sdkTable;
	}

	@RequiredReadAction
	@Override
	public String evaluateUnsafe(@Nonnull MSBuildEvaluateContext context)
	{
		MSBuildSolutionManager msBuildSolutionManager = MSBuildSolutionManager.getInstance(context.getProject());
		if(!msBuildSolutionManager.isEnabled())
		{
			return null;
		}

		String msBuildBundleName = msBuildSolutionManager.getMSBuildBundleName();
		if(msBuildBundleName == null)
		{
			return null;
		}

		Sdk sdk = mySdkTable.findSdk(msBuildBundleName);
	
		if(sdk != null)
		{
			return sdk.getHomePath();
		}

		return null;
	}
}
