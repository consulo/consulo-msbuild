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

package consulo.msbuild.dom;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import consulo.xml.util.xml.DomElement;
import consulo.xml.util.xml.GenericAttributeValue;
import consulo.xml.util.xml.NameStrategy;
import consulo.xml.util.xml.NameStrategyForAttributes;
import consulo.xml.util.xml.TagValue;
import consulo.msbuild.dom.annotation.ExpressionFragment;

/**
 * @author VISTALL
 * @since 03-Feb-17
 */
@NameStrategy(MSBuildNameStrategy.class)
@NameStrategyForAttributes(MSBuildNameStrategy.class)
public interface Property extends DomElement
{
	@Nonnull
	@ExpressionFragment
	GenericAttributeValue<String> getCondition();

	@Nullable
	@TagValue
	String getText();
}
