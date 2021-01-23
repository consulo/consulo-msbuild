package consulo.msbuild.module.extension;

import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.ModuleManager;
import com.intellij.openapi.module.ModuleUtilCore;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import consulo.application.AccessRule;
import consulo.module.extension.ModuleExtension;
import consulo.msbuild.solution.model.WProject;

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
	String getProcessProviderId();
}
