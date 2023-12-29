package consulo.msbuild.module.extension;

import consulo.application.AccessRule;
import consulo.language.util.ModuleUtilCore;
import consulo.module.Module;
import consulo.module.ModuleManager;
import consulo.module.extension.ModuleExtension;
import consulo.msbuild.solution.model.WProject;
import consulo.project.Project;
import consulo.virtualFileSystem.VirtualFile;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Collection;
import java.util.Objects;

/**
 * @author VISTALL
 * @since 18/01/2021
 */
public interface MSBuildSolutionModuleExtension<T extends ModuleExtension<T>> extends ModuleExtension<T>
{
	@Nullable
	static MSBuildSolutionModuleExtension<?> getSolutionModuleExtension(@Nonnull Project project)
	{
		Module[] modules = AccessRule.read(() -> ModuleManager.getInstance(project).getModules());
		assert modules != null;

		VirtualFile baseDir = project.getBaseDir();

		assert baseDir != null;

		for(Module module : modules)
		{
			VirtualFile moduleDir = module.getModuleDir();
			if(Objects.equals(baseDir, moduleDir))
			{
				return AccessRule.read(() -> ModuleUtilCore.getExtension(module, MSBuildSolutionModuleExtension.class));
			}
		}

		return null;
	}

	@Nullable
	String getSdkName();

	/**
	 * File URL to solution file. If it's not null - {@link #getProjectUrl()} will always return null
	 */
	@Nullable
	String getSolutionFileUrl();

	@Nullable
	String getProjectUrl();

	@Nullable
	VirtualFile getSolutionFile();

	@Nullable
	VirtualFile getProjectFile();

	@Nullable
	String getProjectUUID();

	@Nonnull
	Collection<WProject> getProjects();

	@Nonnull
	default Collection<WProject> getValidProjects()
	{
		return getProjects()
				.stream()
				.filter(wProject -> !wProject.isSolutionFolder())
				.filter(wProject -> wProject.getFailReason() == null)
				.toList();
	}

	@Nonnull
	String getProcessProviderId();
}
