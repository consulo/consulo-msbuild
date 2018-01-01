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

package consulo.msbuild.projectView.toolWindow;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.Condition;
import consulo.msbuild.MSBuildSolutionManager;

/**
 * @author VISTALL
 * @since 16-Nov-17
 */
public class SolutionToolWindowCondition implements Condition<Project>
{
	@Override
	public boolean value(Project project)
	{
		return MSBuildSolutionManager.getInstance(project).isEnabled();
	}
}
