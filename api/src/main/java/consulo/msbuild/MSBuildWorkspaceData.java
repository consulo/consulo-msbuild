package consulo.msbuild;

import consulo.annotation.component.ComponentScope;
import consulo.annotation.component.ServiceAPI;
import consulo.annotation.component.ServiceImpl;
import consulo.component.persist.PersistentStateComponent;
import consulo.component.persist.State;
import consulo.component.persist.Storage;
import consulo.component.persist.StoragePathMacros;
import consulo.project.Project;
import consulo.util.xml.serializer.XmlSerializerUtil;
import jakarta.inject.Singleton;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.*;

/**
 * @author VISTALL
 * @since 19/01/2021
 */
@State(name = "MSBuildSolutionManager", storages = @Storage(StoragePathMacros.WORKSPACE_FILE))
@Singleton
@ServiceAPI(ComponentScope.PROJECT)
@ServiceImpl
public class MSBuildWorkspaceData implements PersistentStateComponent<MSBuildWorkspaceData.State>
{
	public static MSBuildWorkspaceData getInstance(@Nonnull Project project)
	{
		return project.getInstance(MSBuildWorkspaceData.class);
	}

	public static class State
	{
		public Map<String, ProjectInfo> projectInfos = new LinkedHashMap<>();
	}

	public static class ProjectInfo
	{
		public Set<String> targets = new LinkedHashSet<>();

		public List<ProjectItemInfo> items = new LinkedList<>();
	}

	public static class ProjectItemInfo implements MSBuildEvaluatedItem
	{
		public String name;

		public String itemSpec;

		public Map<String, String> metadata = new LinkedHashMap<>();

		@Nonnull
		@Override
		public String getName()
		{
			return name;
		}

		@Nonnull
		@Override
		public String getItemSpec()
		{
			return itemSpec;
		}

		@Nonnull
		@Override
		public Map<String, String> getMetadata()
		{
			return metadata;
		}
	}

	private State myState = new State();

	@Nonnull
	public Set<String> getTargets(@Nonnull String projectId)
	{
		ProjectInfo projectInfo = myState.projectInfos.get(projectId);
		if(projectInfo == null)
		{
			return Set.of();
		}
		return projectInfo.targets;
	}

	public void setTargets(@Nonnull String projectId, @Nonnull Set<String> targets)
	{
		ProjectInfo projectInfo = myState.projectInfos.computeIfAbsent(projectId, (it) -> new ProjectInfo());

		projectInfo.targets.clear();
		projectInfo.targets.addAll(targets);
	}

	public void setItems(@Nonnull String projectId, @Nonnull Collection<? extends MSBuildEvaluatedItem> items)
	{
		ProjectInfo projectInfo = myState.projectInfos.computeIfAbsent(projectId, (it) -> new ProjectInfo());
		projectInfo.items.clear();

		for(MSBuildEvaluatedItem item : items)
		{
			ProjectItemInfo projectItemInfo = new ProjectItemInfo();
			projectItemInfo.metadata.putAll(item.getMetadata());
			projectItemInfo.itemSpec = item.getItemSpec();
			projectItemInfo.name = item.getName();

			projectInfo.items.add(projectItemInfo);
		}
	}

	@Nonnull
	public Collection<? extends MSBuildEvaluatedItem> getItems(@Nonnull String projectId)
	{
		ProjectInfo info = myState.projectInfos.get(projectId);
		return info == null ? List.of() : info.items;
	}

	@Nullable
	@Override
	public MSBuildWorkspaceData.State getState()
	{
		return myState;
	}

	@Override
	public void loadState(MSBuildWorkspaceData.State state)
	{
		XmlSerializerUtil.copyBean(state, myState);
	}
}
