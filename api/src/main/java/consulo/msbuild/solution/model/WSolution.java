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

package consulo.msbuild.solution.model;

import consulo.logging.Logger;
import consulo.msbuild.solution.reader.SlnFile;
import consulo.msbuild.solution.reader.SlnProject;
import consulo.msbuild.solution.reader.SlnSection;
import consulo.project.Project;
import consulo.util.lang.Comparing;
import consulo.virtualFileSystem.VirtualFile;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author VISTALL
 * @since 30-Jan-17
 * <p>
 * solution wrapper
 */
public class WSolution
{
	public static final String SECTION_NESTED_PROJECTS = "NestedProjects";

	private static final Logger LOG = Logger.getInstance(WSolution.class);

	@Nonnull
	public static WSolution build(@Nonnull Project project, @Nonnull VirtualFile solutionVirtualFile)
	{
		SlnFile slnFile = new SlnFile();
		try (LineNumberReader reader = new LineNumberReader(new InputStreamReader(solutionVirtualFile.getInputStream(), StandardCharsets.UTF_8)))
		{
			slnFile.Read(reader);
		}
		catch(IOException e)
		{
			LOG.error(e);
		}

		return new WSolution(project, solutionVirtualFile, slnFile);
	}

	private final SlnFile myFile;

	private List<WProject> myProjects = new ArrayList<>();

	public WSolution(@Nonnull Project project, @Nonnull VirtualFile solutionVirtualFile, @Nonnull SlnFile file)
	{
		myFile = file;
		for(SlnProject slnProject : file.getProjects())
		{
			myProjects.add(new WProject(project, solutionVirtualFile, slnProject));
		}
	}

	@Nullable
	public SlnSection getSection(String id)
	{
		return myFile.getSections().GetSection(id);
	}

	public Collection<WProject> getProjects()
	{
		return myProjects;
	}

	@Nullable
	public WProject findProjectByName(String name)
	{
		for(WProject project : myProjects)
		{
			if(Comparing.equal(name, project.getName()))
			{
				return project;
			}
		}
		return null;
	}
}
