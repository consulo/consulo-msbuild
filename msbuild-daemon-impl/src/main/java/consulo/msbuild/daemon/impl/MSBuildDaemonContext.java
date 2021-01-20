package consulo.msbuild.daemon.impl;

import com.intellij.util.containers.MultiMap;
import consulo.msbuild.daemon.impl.message.model.MSBuildEvaluatedItem;
import consulo.msbuild.daemon.impl.message.model.ProjectItem;
import consulo.msbuild.solution.model.WProject;

import javax.annotation.Nonnull;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author VISTALL
 * @since 01/01/2021
 */
public class MSBuildDaemonContext
{
	public static class PerProjectInfo
	{
		public int id;

		public WProject wProject;

		@Deprecated
		public MultiMap<String, String> oldItems = new MultiMap<>();

		public List<MSBuildEvaluatedItem> dependencies = new ArrayList<>();

		public MultiMap<String, MSBuildEvaluatedItem> items = new MultiMap<>();

		public Map<String, String> properties = new HashMap<>();

		public Set<String> targets = new LinkedHashSet<>();
	}

	private final Map<String, PerProjectInfo> myProjectData = new ConcurrentHashMap<>();

	public void registerProject(@Nonnull WProject wProject, int serverId)
	{
		PerProjectInfo info = new PerProjectInfo();
		info.id = serverId;
		info.wProject = wProject;
		myProjectData.put(wProject.getId(), info);
	}

	@Nonnull
	public Collection<PerProjectInfo> getInfos()
	{
		return myProjectData.values();
	}

	public int getRegisteredProjectId(@Nonnull WProject project)
	{
		return getInfo(project).id;
	}

	public void addProjectItems(@Nonnull WProject project, @Nonnull MSBuildEvaluatedItem[] evaluatedItems)
	{
		PerProjectInfo info = getInfo(project);

		for(MSBuildEvaluatedItem item : evaluatedItems)
		{
			info.items.putValue(item.getName(), item);
		}
	}

	public void updateOldProjectItems(@Nonnull WProject project, @Nonnull ProjectItem[] projectItems)
	{
		PerProjectInfo info = getInfo(project);
		info.oldItems.clear();

		for(ProjectItem projectItem : projectItems)
		{
			info.oldItems.putValue(projectItem.ItemType, projectItem.EvaluatedInclude);
		}
	}

	public void updateProjectTargets(@Nonnull WProject project, @Nonnull String[] targets)
	{
		PerProjectInfo info = getInfo(project);
		info.targets.clear();

		Collections.addAll(info.targets, targets);
	}

	public void updateProjectDependencies(@Nonnull WProject project, @Nonnull MSBuildEvaluatedItem[] items)
	{
		PerProjectInfo info = getInfo(project);
		info.dependencies.clear();

		Collections.addAll(info.dependencies, items);
	}

	public Map<String, String> getProperties(@Nonnull WProject project)
	{
		PerProjectInfo info = getInfo(project);
		return info.properties;
	}

	private PerProjectInfo getInfo(@Nonnull WProject project)
	{
		PerProjectInfo projectInfo = myProjectData.get(project.getId());
		if(projectInfo == null)
		{
			throw new IllegalArgumentException("Project is not registered at server : " + project.getName());
		}
		return projectInfo;
	}
}
