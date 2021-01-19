package consulo.msbuild;

import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import com.intellij.openapi.components.StoragePathMacros;
import com.intellij.openapi.project.Project;
import com.intellij.util.xmlb.XmlSerializerUtil;
import jakarta.inject.Singleton;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author VISTALL
 * @since 19/01/2021
 */
@State(name = "MSBuildSolutionManager", storages = @Storage(StoragePathMacros.WORKSPACE_FILE))
@Singleton
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
