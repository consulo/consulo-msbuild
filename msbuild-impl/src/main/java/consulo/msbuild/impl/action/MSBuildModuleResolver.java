package consulo.msbuild.impl.action;

import consulo.annotation.access.RequiredReadAction;
import consulo.annotation.component.ExtensionImpl;
import consulo.module.Module;
import consulo.module.ModuleManager;
import consulo.module.content.NewFileModuleResolver;
import consulo.module.extension.ModuleExtensionHelper;
import consulo.msbuild.MSBuildWorkspaceData;
import consulo.msbuild.module.extension.MSBuildSolutionModuleExtension;
import consulo.project.Project;
import consulo.virtualFileSystem.LocalFileSystem;
import consulo.virtualFileSystem.VirtualFile;
import consulo.virtualFileSystem.fileType.FileType;
import consulo.virtualFileSystem.util.VirtualFileUtil;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import jakarta.inject.Inject;

import java.util.Collection;

/**
 * @author VISTALL
 * @since 2018-08-15
 */
@ExtensionImpl
public class MSBuildModuleResolver implements NewFileModuleResolver {
    private final Project myProject;
    private final ModuleExtensionHelper myModuleExtensionHelper;

    @Inject
    public MSBuildModuleResolver(Project project, ModuleExtensionHelper moduleExtensionHelper) {
        myProject = project;
        myModuleExtensionHelper = moduleExtensionHelper;
    }

    @Nullable
    @Override
    @RequiredReadAction
    public Module resolveModule(@Nonnull VirtualFile file, @Nonnull FileType fileType) {
        if (!myModuleExtensionHelper.hasModuleExtension(MSBuildSolutionModuleExtension.class)) {
            return null;
        }

        MSBuildWorkspaceData workspaceData = MSBuildWorkspaceData.getInstance(myProject);

        Collection<? extends MSBuildWorkspaceData.ProjectInfo> projectInfos = workspaceData.getProjectInfos();

        if (projectInfos.isEmpty()) {
            return null;
        }

        for (MSBuildWorkspaceData.ProjectInfo projectInfo : projectInfos) {
            String projectDir = projectInfo.properties.get("ProjectDir");

            VirtualFile projectDirFile = LocalFileSystem.getInstance().findFileByPathIfCached(projectDir);
            if (projectDirFile == null) {
                continue;
            }

            if (VirtualFileUtil.isAncestor(projectDirFile, file, false) && projectInfo.moduleName != null) {
                Module module = ModuleManager.getInstance(myProject).findModuleByName(projectInfo.moduleName);
                if (module != null) {
                    return module;
                }
            }
        }

        return null;
    }
}
