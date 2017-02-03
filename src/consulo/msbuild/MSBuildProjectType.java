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

package consulo.msbuild;

import gnu.trove.THashSet;

import java.util.Collections;
import java.util.Set;

import org.jetbrains.annotations.NotNull;
import com.intellij.openapi.extensions.ExtensionPointName;
import com.intellij.openapi.util.NotNullLazyValue;

/**
 * @author VISTALL
 * @since 28-Jan-17
 */
public interface MSBuildProjectType
{
	@Deprecated
	public static class Default implements MSBuildProjectType
	{
	}

	ExtensionPointName<MSBuildProjectTypeEP<MSBuildProjectType>> EP_NAME = ExtensionPointName.create("consulo.msbuild.projectType");

	NotNullLazyValue<Set<String>> ourExtensionsValue = new NotNullLazyValue<Set<String>>()
	{
		@NotNull
		@Override
		protected Set<String> compute()
		{
			Set<String> set = new THashSet<>();
			for(MSBuildProjectTypeEP<MSBuildProjectType> ep : EP_NAME.getExtensions())
			{
				set.add(ep.getExt());
			}
			return set.isEmpty() ? Collections.<String>emptySet() : set;
		}
	};

	@NotNull
	static Set<String> getExtensions()
	{
		return ourExtensionsValue.getValue();
	}
}
