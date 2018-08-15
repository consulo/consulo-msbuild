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

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.inject.Inject;
import javax.inject.Singleton;

import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import com.intellij.openapi.components.StoragePathMacros;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.SimpleModificationTracker;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.openapi.vfs.VirtualFileManager;
import com.intellij.psi.util.CachedValue;
import com.intellij.psi.util.CachedValueProvider;
import com.intellij.psi.util.CachedValuesManager;
import com.intellij.util.xmlb.XmlSerializerUtil;
import consulo.msbuild.solution.model.WSolution;
import consulo.msbuild.synchronize.MSBuildSynchronizeFileListener;

/**
 * @author VISTALL
 * @since 28-Jan-17
 */
@State(name = "MSBuildSolutionManager", storages = @Storage(file = StoragePathMacros.PROJECT_CONFIG_DIR + "/msbuild.xml"))
@Singleton
public class MSBuildSolutionManager extends SimpleModificationTracker implements PersistentStateComponent<MSBuildSolutionManager.State>
{
	public final static class ProjectOptions
	{
		public String target;

		public String moduleName;
	}

	protected static class State
	{
		public boolean enabled;
		public String url;

		public Map<String, ProjectOptions> projects = new HashMap<>();
	}

	@Nonnull
	public static MSBuildSolutionManager getInstance(@Nonnull Project project)
	{
		return ServiceManager.getService(project, MSBuildSolutionManager.class);
	}

	private State myState = new State();
	private final Project myProject;

	private CachedValue<WSolution> myCachedValue;

	@Inject
	public MSBuildSolutionManager(Project project, VirtualFileManager virtualFileManager)
	{
		myProject = project;
		myCachedValue = CachedValuesManager.getManager(project).createCachedValue(() -> CachedValueProvider.Result.create(WSolution.build(myProject, getSolutionFile()), this), false);

		virtualFileManager.addVirtualFileListener(new MSBuildSynchronizeFileListener(project, this), project);
	}

	@Nullable
	@Override
	public State getState()
	{
		return myState;
	}

	@Override
	public void loadState(State state)
	{
		XmlSerializerUtil.copyBean(state, myState);
	}

	public void putOptions(String projectName, ProjectOptions projectOptions)
	{
		myState.projects.put(projectName, projectOptions);
	}

	/**
	 * Return options by project name (from solution files)
	 */
	@Nullable
	public ProjectOptions getOptionsByProjectName(@Nonnull String name)
	{
		return myState.projects.get(name);
	}

	/**
	 * Return options by module name (consulo project structure)
	 */
	@Nullable
	public Map.Entry<String, ProjectOptions> getOptionsByModuleName(@Nonnull String name)
	{
		for(Map.Entry<String, ProjectOptions> entry : myState.projects.entrySet())
		{
			if(name.equals(entry.getValue().moduleName))
			{
				return entry;
			}
		}

		return null;
	}

	@Nonnull
	public WSolution getSolution()
	{
		return myCachedValue.getValue();
	}

	public void setEnabled(boolean enabled)
	{
		myState.enabled = enabled;
	}

	public boolean isEnabled()
	{
		return myState.enabled;
	}

	public void setUrl(@Nonnull VirtualFile virtualFile)
	{
		myState.url = virtualFile.getUrl();
	}

	@Nullable
	public VirtualFile getSolutionFile()
	{
		return myState.enabled ? VirtualFileManager.getInstance().findFileByUrl(myState.url) : null;
	}
}
