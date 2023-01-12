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

package consulo.msbuild.impl.codeInspection;

import consulo.annotation.component.ExtensionImpl;
import consulo.msbuild.dom.Project;
import consulo.xml.util.xml.highlighting.BasicDomElementsInspection;

import javax.annotation.Nonnull;

/**
 * @author VISTALL
 * @since 28-Jan-17
 */
@ExtensionImpl
public class MSBuildDomElementsInspection extends BasicDomElementsInspection<Project>
{
	public MSBuildDomElementsInspection()
	{
		super(Project.class);
	}

	@Override
	public boolean isEnabledByDefault()
	{
		return true;
	}

	@Nonnull
	@Override
	public String getGroupDisplayName()
	{
		return "MSBuild";
	}

	@Nonnull
	@Override
	public String getDisplayName()
	{
		return "MSBuild validation";
	}
}
