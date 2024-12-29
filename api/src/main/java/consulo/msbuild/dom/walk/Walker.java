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

package consulo.msbuild.dom.walk;

import consulo.xml.util.xml.DomElement;
import consulo.annotation.access.RequiredReadAction;
import consulo.msbuild.dom.ItemGroup;
import consulo.msbuild.dom.Project;
import consulo.msbuild.dom.PropertyGroup;

import jakarta.annotation.Nonnull;
import java.util.List;
import java.util.function.Consumer;

/**
 * @author VISTALL
 * @since 03-Feb-17
 *
 * TODO [VISTALL] walk in Import tags
 */
public class Walker
{
	private final Project myProject;

	public Walker(Project project)
	{
		myProject = project;
	}

	@RequiredReadAction
	@SuppressWarnings("unchecked")
	public <T extends DomElement> void walk(@Nonnull Class<T> clazz, @Nonnull Consumer<T> consumer)
	{
		//TODO [VISTALL] stub!

		if(clazz == PropertyGroup.class)
		{
			List<PropertyGroup> propertyGroups = myProject.getPropertyGroups();
			for(PropertyGroup propertyGroup : propertyGroups)
			{
				consumer.accept((T) propertyGroup);
			}
		}
		else if(clazz == ItemGroup.class)
		{
			List<ItemGroup> itemGroups = myProject.getItemGroups();
			for(ItemGroup itemGroup : itemGroups)
			{
				consumer.accept((T) itemGroup);
			}
		}
	}
}
