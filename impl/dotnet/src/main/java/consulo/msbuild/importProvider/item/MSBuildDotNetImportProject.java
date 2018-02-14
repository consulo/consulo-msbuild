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

package consulo.msbuild.importProvider.item;

import javax.annotation.Nonnull;

import consulo.msbuild.MSBuildSolutionManager;
import consulo.msbuild.importProvider.MSBuildModuleImportContext;
import consulo.msbuild.solution.reader.SlnProject;

/**
 * @author VISTALL
 * @since 30-Jan-17
 */
public class MSBuildDotNetImportProject extends MSBuildImportProject
{
	private MSBuildDotNetImportTarget myTarget;
	private MSBuildModuleImportContext myContext;

	public MSBuildDotNetImportProject(SlnProject projectInfo, MSBuildModuleImportContext context, MSBuildDotNetImportTarget target)
	{
		super(projectInfo);
		myContext = context;

		setTarget(target);
	}

	public MSBuildDotNetImportTarget getTarget()
	{
		return myTarget;
	}

	public void setTarget(@Nonnull MSBuildDotNetImportTarget target)
	{
		myTarget = target;

		MSBuildSolutionManager.ProjectOptions options = myContext.getOptions(myProjectInfo.Name);
		options.target = target.name();
	}
}
