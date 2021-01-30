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

import com.intellij.ide.highlighter.XmlFileType;
import com.intellij.openapi.application.Application;
import com.intellij.openapi.fileTypes.FileTypeConsumer;
import com.intellij.openapi.fileTypes.FileTypeFactory;
import jakarta.inject.Inject;

import javax.annotation.Nonnull;

/**
 * @author VISTALL
 * @since 28-Jan-17
 */
public class MSBuildFileTypeFactory extends FileTypeFactory
{
	public static final String PROPS_EXT = "props";
	public static final String TARGETS_EXT = "targets";

	private final Application myApplication;

	@Inject
	public MSBuildFileTypeFactory(Application application)
	{
		myApplication = application;
	}

	@Override
	public void createFileTypes(@Nonnull FileTypeConsumer consumer)
	{
		consumer.consume(VisualStudioSolutionFileType.INSTANCE);
		consumer.consume(XmlFileType.INSTANCE, "resx;settings;config");
		consumer.consume(XmlFileType.INSTANCE, PROPS_EXT);
		consumer.consume(XmlFileType.INSTANCE, TARGETS_EXT);

		for(MSBuildProjectFileEP projectFileEP : MSBuildProjectFileEP.EP_NAME.getExtensionList(myApplication))
		{
			consumer.consume(XmlFileType.INSTANCE, projectFileEP.extension);
		}
	}
}
