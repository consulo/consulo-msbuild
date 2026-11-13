package consulo.msbuild.impl.importProvider;

import consulo.annotation.access.RequiredReadAction;
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
import consulo.util.io.FileUtil;
import consulo.util.concurrent.coroutine.Coroutine;
import consulo.virtualFileSystem.LocalFileSystem;
import consulo.virtualFileSystem.VirtualFile;
import consulo.virtualFileSystem.fileType.FileTypeRegistry;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Stream;

/**
 * Imports one solution format. Each format has own provider, so if directory contains both formats - user will choose import target
 *
 * @author VISTALL
 * @since 01-Feb-17
 */
public abstract class SolutionModuleImportProvider implements ModuleImportProvider<SolutionModuleImportContext> {
    /**
     * @return solution file extension without dot
     */
    @Nonnull
    protected abstract String getSolutionExtension();

    @Nonnull
    @Override
    public SolutionModuleImportContext createContext(@Nullable Project project) {
        return new SolutionModuleImportContext(project);
    }

    @Nonnull
    @Override
    public LocalizeValue getName() {
        return LocalizeValue.localizeTODO("Visual Studio Solution (." + getSolutionExtension() + ")");
    }

    @Nonnull
    @Override
    public Image getIcon() {
        return MSBuildIconGroup.visualstudio();
    }

    @Override
    public boolean canImport(@Nonnull Path path) {
        if (Files.isDirectory(path)) {
            // special case. We don't provide importing Unity project via solution file
            if (Files.exists(path.resolve("ProjectSettings/ProjectSettings.asset"))) {
                return false;
            }

            return findSingleSolutionFile(path) != null;
        }
        else {
            return isOwnSolutionFile(path);
        }
    }

    @Override
    public String getPathToBeImported(@Nonnull Path file) {
        if (Files.isDirectory(file)) {
            Path solutionFile = findSingleSolutionFile(file);
            if (solutionFile != null) {
                return solutionFile.toString();
            }
        }
        return file.toString();
    }

    /**
     * @return solution file of this provider format, null if there no files, or more than one
     */
    @Nullable
    private Path findSingleSolutionFile(Path directory) {
        Path firstSolution = null;
        for (Path file : listFiles(directory)) {
            if (isOwnSolutionFile(file)) {
                // already found - return null
                if (firstSolution != null) {
                    return null;
                }

                firstSolution = file;
            }
        }
        return firstSolution;
    }

    private boolean isOwnSolutionFile(Path file) {
        String name = file.getFileName().toString();
        return FileTypeRegistry.getInstance().getFileTypeByFileName(name) == VisualStudioSolutionFileType.INSTANCE
            && getSolutionExtension().equalsIgnoreCase(FileUtil.getExtension(name));
    }

    public static boolean hasSolutionFile(Path directory) {
        for (Path file : listFiles(directory)) {
            if (FileTypeRegistry.getInstance().getFileTypeByFileName(file.getFileName().toString()) == VisualStudioSolutionFileType.INSTANCE) {
                return true;
            }
        }
        return false;
    }

    /**
     * The files of a directory of any machine.
     */
    static List<Path> listFiles(Path directory) {
        try (Stream<Path> children = Files.list(directory)) {
            return children.filter(Files::isRegularFile).toList();
        }
        catch (IOException e) {
            return List.of();
        }
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
