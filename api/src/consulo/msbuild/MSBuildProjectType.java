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

import gnu.trove.THashMap;
import gnu.trove.THashSet;

import java.util.Collections;
import java.util.Map;
import java.util.Set;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import com.intellij.openapi.extensions.ExtensionPointName;
import com.intellij.openapi.util.NotNullLazyValue;
import consulo.msbuild.dom.Project;
import consulo.msbuild.importProvider.MSBuildModuleImportContext;
import consulo.msbuild.importProvider.item.MSBuildImportProject;
import consulo.msbuild.solution.reader.SlnProject;
import consulo.roots.ModifiableModuleRootLayer;

/**
 * @author VISTALL
 * @since 28-Jan-17
 */
public interface MSBuildProjectType
{
	String PROPS_EXT = "props";
	String TARGETS_EXT = "targets";

	ExtensionPointName<MSBuildProjectTypeEP<MSBuildProjectType>> EP_NAME = ExtensionPointName.create("consulo.msbuild.projectType");

	NotNullLazyValue<Set<String>> ourExtensionsValue = NotNullLazyValue.createValue(() ->
	{
		Set<String> set = new THashSet<>();
		set.add(PROPS_EXT);
		set.add(TARGETS_EXT);

		for(MSBuildProjectTypeEP<MSBuildProjectType> ep : EP_NAME.getExtensions())
		{
			set.add(ep.getExt());
		}
		return set.isEmpty() ? Collections.<String>emptySet() : set;
	});

	NotNullLazyValue<Map<String, MSBuildProjectType>> ourTypeByGUIDMapValue = NotNullLazyValue.createValue(() ->
	{
		Map<String, MSBuildProjectType> map = new THashMap<>();
		for(MSBuildProjectTypeEP<MSBuildProjectType> ep : EP_NAME.getExtensions())
		{
			MSBuildProjectType instance = ep.getInstance();
			if(instance == null)
			{
				continue;
			}
			map.put(ep.getGuid(), instance);
		}
		return map;
	});

	@Nullable
	static MSBuildProjectType getProjectType(@NotNull String guid)
	{
		return ourTypeByGUIDMapValue.getValue().get(guid);
	}

	@NotNull
	static Set<String> getExtensions()
	{
		return ourExtensionsValue.getValue();
	}

	void setupModule(@NotNull Project domProject, @Nullable MSBuildSolutionManager.ProjectOptions projectOptions, @NotNull ModifiableModuleRootLayer modifiableRootModel);

	@NotNull
	MSBuildImportProject createImportItem(SlnProject project, MSBuildModuleImportContext context);
}
