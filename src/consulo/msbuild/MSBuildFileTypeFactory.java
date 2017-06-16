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

import org.jetbrains.annotations.NotNull;
import com.intellij.ide.highlighter.XmlFileType;
import com.intellij.openapi.fileTypes.FileTypeConsumer;
import com.intellij.openapi.fileTypes.FileTypeFactory;

/**
 * @author VISTALL
 * @since 28-Jan-17
 */
public class MSBuildFileTypeFactory extends FileTypeFactory
{
	@Override
	public void createFileTypes(@NotNull FileTypeConsumer consumer)
	{
		consumer.consume(VisualStudioSolutionFileType.INSTANCE);
		consumer.consume(XmlFileType.INSTANCE, "resx;settings;config");
		consumer.consume(XmlFileType.INSTANCE, MSBuildProjectType.PROPS_EXT);
		consumer.consume(XmlFileType.INSTANCE, MSBuildProjectType.TARGET_EXT);

		for(String ext : MSBuildProjectType.getExtensions())
		{
			consumer.consume(XmlFileType.INSTANCE, ext);
		}
	}
}
