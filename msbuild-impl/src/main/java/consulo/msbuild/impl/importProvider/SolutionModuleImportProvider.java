package consulo.msbuild.impl.importProvider;

import consulo.annotation.access.RequiredReadAction;
import consulo.annotation.component.ExtensionImpl;
import consulo.application.concurrent.coroutine.ReadLock;
import consulo.application.concurrent.coroutine.WriteLock;
import consulo.localize.LocalizeValue;
import consulo.module.ModifiableModuleModel;
import consulo.module.Module;
import consulo.module.content.ModuleRootManager;
import consulo.module.content.layer.ModifiableRootModel;
import consulo.module.creation.importing.ModuleImportProvider;
import consulo.msbuild.daemon.impl.MSBuildDaemonService;
import consulo.msbuild.icon.MSBuildIconGroup;
import consulo.msbuild.impl.VisualStudioSolutionFileType;
import consulo.msbuild.importProvider.SolutionModuleImportContext;
import consulo.msbuild.module.extension.MSBuildSolutionMutableModuleExtension;
import consulo.project.Project;
import consulo.project.ProjectRunOneService;
import consulo.project.startup.StartupActivity;
import consulo.project.startup.StartupManager;
import consulo.ui.ex.wizard.WizardStep;
import consulo.ui.image.Image;
import consulo.util.concurrent.coroutine.Coroutine;
import consulo.virtualFileSystem.LocalFileSystem;
import consulo.virtualFileSystem.VirtualFile;
import consulo.virtualFileSystem.fileType.FileTypeRegistry;
import consulo.virtualFileSystem.util.VirtualFileUtil;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

import java.io.File;
import java.util.function.Consumer;

/**
 * @author VISTALL
 * @since 01-Feb-17
 */
@ExtensionImpl
public class SolutionModuleImportProvider implements ModuleImportProvider<SolutionModuleImportContext> {
    @Nonnull
    @Override
    public SolutionModuleImportContext createContext(@Nullable Project project) {
        return new SolutionModuleImportContext(project);
    }

    @Nonnull
    @Override
    public LocalizeValue getName() {
        return LocalizeValue.localizeTODO("Visual Studio");
    }

    @Nonnull
    @Override
    public Image getIcon() {
        return MSBuildIconGroup.visualstudio();
    }

    @Override
    public boolean canImport(@Nonnull File fileOrDirectory) {
        if (fileOrDirectory.isDirectory()) {
            // special case. We don't provide importing Unity project via solution file
            if (new File(fileOrDirectory, "ProjectSettings/ProjectSettings.asset").exists()) {
                return false;
            }

            return findSingleSolutionFile(fileOrDirectory) != null;
        }
        else {
            return FileTypeRegistry.getInstance().getFileTypeByFileName(fileOrDirectory.getName()) == VisualStudioSolutionFileType.INSTANCE;
        }
    }

    @Override
    public String getPathToBeImported(@Nonnull VirtualFile file) {
        if (file.isDirectory()) {
            File solutionFile = findSingleSolutionFile(VirtualFileUtil.virtualToIoFile(file));
            if (solutionFile != null) {
                return solutionFile.getPath();
            }
        }
        return file.getPath();
    }

    public static File findSingleSolutionFile(File directory) {
        File firstSolution = null;
        if (directory.isDirectory()) {
            File[] files = directory.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (file.isFile()) {
                        if (FileTypeRegistry.getInstance().getFileTypeByFileName(file.getName()) == VisualStudioSolutionFileType.INSTANCE) {
                            // already found - return null
                            if (firstSolution != null) {
                                return null;
                            }

                            firstSolution = file;
                        }
                    }
                }
            }
        }
        return firstSolution;
    }

    @Override
    public void buildSteps(@Nonnull Consumer<WizardStep<SolutionModuleImportContext>> consumer, @Nonnull SolutionModuleImportContext context) {
        consumer.accept(new MSBuildProjectOrModuleNameStep<>(context));
    }

    @Override
    public Coroutine<Object, Object> process(@Nonnull SolutionModuleImportContext context,
                                             @Nonnull Project project,
                                             @Nonnull ModifiableModuleModel modifiableModuleModel,
                                             @Nonnull Consumer<Module> consumer) {
        return ReadLock.apply((i, continuation) -> {

                String fileToImport = context.getFileToImport();

                VirtualFile solutionFile = LocalFileSystem.getInstance().findFileByPath(fileToImport);
                assert solutionFile != null;

                VirtualFile parent = solutionFile.getParent();

                final ModifiableRootModel mainModuleModel = createModuleWithSingleContent(parent.getName(), parent, modifiableModuleModel);

                MSBuildSolutionMutableModuleExtension<?> solExtension = mainModuleModel.getExtensionWithoutCheck(context.getProvider().getSolutionModuleExtensionId());
                assert solExtension != null;
                solExtension.setEnabled(true);
                solExtension.setSolutionFileUrl(solutionFile.getUrl());
                solExtension.setSdkName(context.getMSBuildBundleName());
                solExtension.setProcessProviderId(context.getProvider().getId());

                consumer.accept(mainModuleModel.getModule());

                project.getInstance(ProjectRunOneService.class).register(MSBuildRunOnceExtension.ID, new MSBuildRunOnceExtension.Data(""));

                return mainModuleModel;
            })
            .toCoroutine()
            .then(WriteLock.apply((modifiableRootModel, continuation) -> {
                modifiableRootModel.commit();
                return null;
            }));
    }

    @RequiredReadAction
    public static ModifiableRootModel createModuleWithSingleContent(String dirName, VirtualFile dir, ModifiableModuleModel modifiableModuleModel) {
        Module module = modifiableModuleModel.newModule(dirName + " (Root)", dir.getPath());

        ModuleRootManager moduleRootManager = ModuleRootManager.getInstance(module);
        ModifiableRootModel modifiableModel = moduleRootManager.getModifiableModel();
        modifiableModel.addContentEntry(dir);

        return modifiableModel;
    }
}
