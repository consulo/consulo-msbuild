package consulo.msbuild.daemon.impl;

import com.intellij.util.containers.MultiMap;
import consulo.msbuild.daemon.impl.message.model.ProjectItem;
import consulo.msbuild.solution.model.WProject;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author VISTALL
 * @since 01/01/2021
 */
public class MSBuildDaemonContext
{
	private static class PerProjectInfo
	{
		public int id;

		public MultiMap<String, String> items = new MultiMap<>();

		public Map<String, String> properties = new HashMap<>();
	}

	private final Map<String, PerProjectInfo> myProjectData = new ConcurrentHashMap<>();

	public void registerProject(@Nonnull WProject wProject, int serverId)
	{
		PerProjectInfo info = new PerProjectInfo();
		info.id = serverId;
		myProjectData.put(wProject.getId(), info);
	}

	public int getRegisteredProjectId(@Nonnull WProject project)
	{
		return getInfo(project).id;
	}

	public void updateProjectItems(@Nonnull WProject project, @Nonnull ProjectItem[] projectItems)
	{
		PerProjectInfo info = getInfo(project);
		info.items.clear();

		for(ProjectItem projectItem : projectItems)
		{
			info.items.putValue(projectItem.ItemType, projectItem.EvaluatedInclude);
		}
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
