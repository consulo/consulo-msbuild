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

package consulo.msbuild.impl.solution;

import jakarta.annotation.Nonnull;

/**
 * @author VISTALL
 * @since 28-Jan-17
 */
public class SolutionVirtualDirectory extends SolutionVirtualCompositeItem
{
	public SolutionVirtualDirectory(String name, SolutionVirtualDirectory parent)
	{
		super(name, parent, null);
	}

	@Nonnull
	public SolutionVirtualDirectory createOrGetDirectory(@Nonnull String name)
	{
		return (SolutionVirtualDirectory) myChildren.computeIfAbsent(name, s -> new SolutionVirtualDirectory(name, this));
	}
}
