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

package consulo.msbuild.impl.dom.extender;

import consulo.annotation.component.ExtensionImpl;
import consulo.msbuild.dom.Property;
import consulo.msbuild.dom.PropertyGroup;
import consulo.xml.util.xml.reflect.DomExtender;
import consulo.xml.util.xml.reflect.DomExtensionsRegistrar;

import jakarta.annotation.Nonnull;

/**
 * @author VISTALL
 * @since 03-Feb-17
 */
@ExtensionImpl
public class MSBuildPropertyGroupDomExtender extends DomExtender<PropertyGroup>
{
	@Nonnull
	@Override
	public Class<PropertyGroup> getElementClass()
	{
		return PropertyGroup.class;
	}

	@Override
	public void registerExtensions(@Nonnull PropertyGroup propertyGroup, @Nonnull DomExtensionsRegistrar domExtensionsRegistrar)
	{
		domExtensionsRegistrar.registerCustomChildrenExtension(Property.class);
	}
}
