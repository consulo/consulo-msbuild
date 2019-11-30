package consulo.msbuild.action;

import com.intellij.ide.actions.CreateFileFromTemplateAction;
import com.intellij.openapi.fileTypes.FileType;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.ModuleManager;
import com.intellij.openapi.vfs.VfsUtil;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiDirectory;
import com.intellij.util.ArrayUtil;
import consulo.annotation.access.RequiredReadAction;
import consulo.msbuild.MSBuildSolutionManager;
import consulo.msbuild.solution.model.WProject;
import consulo.msbuild.solution.model.WSolution;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Map;

/**
 * @author VISTALL
 * @since 2018-08-15
 */
public class MSBuildModuleResolver implements CreateFileFromTemplateAction.ModuleResolver
{
	@Nullable
	@Override
	@RequiredReadAction
	public Module resolveModule(@Nonnull PsiDirectory psiDirectory, @Nonnull FileType fileType)
	{
		MSBuildSolutionManager solutionManager = MSBuildSolutionManager.getInstance(psiDirectory.getProject());
		if(!solutionManager.isEnabled())
		{
			return null;
		}

		ModuleManager manager = ModuleManager.getInstance(psiDirectory.getProject());

		WSolution solution = solutionManager.getSolution();

		for(Module module : ArrayUtil.reverseArray(manager.getModules()))
		{
			Map.Entry<String, MSBuildSolutionManager.ProjectOptions> entry = solutionManager.getOptionsByModuleName(module.getName());
			if(entry == null)
			{
				continue;
			}

			WProject wProject = solution.findProjectByName(entry.getKey());
			if(wProject == null)
			{
				continue;
			}

			VirtualFile virtualFile = wProject.getVirtualFile();
			if(virtualFile == null)
			{
				continue;
			}

			VirtualFile parent = virtualFile.getParent();
			assert parent != null;

			if(VfsUtil.isAncestor(parent, psiDirectory.getVirtualFile(), false))
			{
				return module;
			}
		}

		return null;
	}
}
