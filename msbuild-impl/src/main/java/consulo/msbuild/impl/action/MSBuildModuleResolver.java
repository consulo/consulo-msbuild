package consulo.msbuild.impl.action;

import consulo.annotation.access.RequiredReadAction;
import consulo.annotation.component.ExtensionImpl;
import consulo.module.Module;
import consulo.module.content.NewFileModuleResolver;
import consulo.project.Project;
import consulo.virtualFileSystem.VirtualFile;
import consulo.virtualFileSystem.fileType.FileType;
import jakarta.inject.Inject;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

/**
 * @author VISTALL
 * @since 2018-08-15
 */
@ExtensionImpl
public class MSBuildModuleResolver implements NewFileModuleResolver
{
	private final Project myProject;

	@Inject
	public MSBuildModuleResolver(Project project)
	{
		myProject = project;
	}

	@Nullable
	@Override
	@RequiredReadAction
	public Module resolveModule(@Nonnull VirtualFile file, @Nonnull FileType fileType)
	{
		// TODO vistall
//		MSBuildSolutionManager solutionManager = MSBuildSolutionManager.getInstance(psiDirectory.getProject());
//		if(!solutionManager.isEnabled())
//		{
//			return null;
//		}
//
//		ModuleManager manager = ModuleManager.getInstance(psiDirectory.getProject());
//
//		WSolution solution = solutionManager.getSolution();
//
//		for(Module module : ArrayUtil.reverseArray(manager.getModules()))
//		{
//			Map.Entry<String, MSBuildSolutionManager.ProjectOptions> entry = solutionManager.getOptionsByModuleName(module.getName());
//			if(entry == null)
//			{
//				continue;
//			}
//
//			WProject wProject = solution.findProjectByName(entry.getKey());
//			if(wProject == null)
//			{
//				continue;
//			}
//
//			VirtualFile virtualFile = wProject.getVirtualFile();
//			if(virtualFile == null)
//			{
//				continue;
//			}
//
//			VirtualFile parent = virtualFile.getParent();
//			assert parent != null;
//
//			if(VfsUtil.isAncestor(parent, psiDirectory.getVirtualFile(), false))
//			{
//				return module;
//			}
//		}

		return null;
	}
}
