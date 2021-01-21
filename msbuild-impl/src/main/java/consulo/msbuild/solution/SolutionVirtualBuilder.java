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

package consulo.msbuild.solution;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.io.FileUtilRt;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.util.containers.ContainerUtil;
import consulo.annotation.access.RequiredReadAction;
import consulo.logging.Logger;
import consulo.msbuild.MSBuildEvaluatedItem;
import consulo.msbuild.MSBuildWorkspaceData;
import consulo.msbuild.solution.model.WProject;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author VISTALL
 * @since 29-Jan-17
 */
public class SolutionVirtualBuilder
{
	private static final Logger LOG = Logger.getInstance(SolutionVirtualBuilder.class);

	@RequiredReadAction
	public static SolutionVirtualDirectory build(@Nonnull WProject wProject, @Nonnull Project project, @Nonnull VirtualFile baseDir)
	{
		SolutionVirtualDirectory root = new SolutionVirtualDirectory("", null);

		VirtualFile projectFile = wProject.getVirtualFile();
		if(projectFile != null)
		{
			root.myChildren.put(projectFile.getName(), new SolutionVirtualFile(projectFile.getName(), root, null, projectFile));
		}

		MSBuildWorkspaceData workspaceData = MSBuildWorkspaceData.getInstance(project);

		Collection<? extends MSBuildEvaluatedItem> items = new ArrayList<>(workspaceData.getItems(wProject.getId()));

		addAll(items, baseDir, root);

		return root;
	}

	private static void addAll(Collection<? extends MSBuildEvaluatedItem> list, @Nonnull VirtualFile baseDir, @Nonnull SolutionVirtualDirectory root)
	{
		for(MSBuildEvaluatedItem simpleItem : list)
		{
			String pathName = simpleItem.getItemSpec();
			if(pathName == null)
			{
				continue;
			}

			String presentationPath = pathName;

			List<String> split = StringUtil.split(presentationPath, "\\");

			SolutionVirtualDirectory target = root;
			for(int i = 0; i < (split.size() - 1); i++)
			{
				target = target.createOrGetDirectory(split.get(i));
			}

			VirtualFile file = baseDir.findFileByRelativePath(FileUtilRt.toSystemIndependentName(pathName));

			String name = ContainerUtil.getLastItem(split);
			if(consulo.util.lang.StringUtil.isEmpty(name))
			{
				LOG.error("Relative path is empty, unknown item: " + baseDir.getPath() + ", child: " + pathName);
				continue;
			}

			SolutionVirtualFile virtualFile = new SolutionVirtualFile(name, target, null, file);

			target.myChildren.put(name, virtualFile);

//			String autoGenValue = simpleItem.getAutoGen().getStringValue();
//			virtualFile.setGenerated(Comparing.equal(autoGenValue, "True"));

			String subTypeString = simpleItem.getMetadata().get("SubType");
			virtualFile.setSubType(subTypeString == null ? null : SolutionVirtualFileSubType.find(subTypeString));

//			String dependentUpon = simpleItem.getDependentUpon().getStringValue();
//			virtualFile.setDependentUpon(dependentUpon);
//
//			String generator = simpleItem.getGenerator().getStringValue();
//			if(generator != null)
//			{
//				virtualFile.setGenerator(generator);
//				SolutionVirtualFileSubType subType = virtualFile.getSubType();
//				if(subType == null)
//				{
//					virtualFile.setSubType(SolutionVirtualFileSubType.__generator);
//				}
//			}
		}
	}
}
