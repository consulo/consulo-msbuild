package consulo.msbuild.impl.importProvider;

import consulo.annotation.component.ExtensionImpl;
import consulo.application.Application;
import consulo.application.concurrent.coroutine.ReadLock;
import consulo.application.concurrent.coroutine.WriteLock;
import consulo.application.dumb.DumbAwareRunnable;
import consulo.localize.LocalizeValue;
import consulo.module.ModifiableModuleModel;
import consulo.module.Module;
import consulo.module.content.layer.ModifiableRootModel;
import consulo.module.creation.importing.ModuleImportProvider;
import consulo.msbuild.MSBuildProjectFile;
import consulo.msbuild.daemon.impl.MSBuildDaemonService;
import consulo.msbuild.icon.MSBuildIconGroup;
import consulo.msbuild.importProvider.MSBuildModuleImportContext;
import consulo.msbuild.module.extension.MSBuildSolutionMutableModuleExtension;
import consulo.project.Project;
import consulo.project.ProjectRunOneService;
import consulo.project.startup.StartupManager;
import consulo.ui.ex.wizard.WizardStep;
import consulo.ui.image.Image;
import consulo.util.concurrent.coroutine.Coroutine;
import consulo.util.io.FileUtil;
import consulo.virtualFileSystem.LocalFileSystem;
import consulo.virtualFileSystem.VirtualFile;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import jakarta.inject.Inject;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Set;
import java.util.function.Consumer;

/**
 * @author VISTALL
 * @since 23/01/2021
 */
@ExtensionImpl
public class MSBuildModuleImportProvider implements ModuleImportProvider<MSBuildModuleImportContext> {
    private final Application myApplication;

    @Inject
    public MSBuildModuleImportProvider(Application application) {
        myApplication = application;
    }

    @Nonnull
    @Override
    public MSBuildModuleImportContext createContext(@Nullable Project project) {
        return new MSBuildModuleImportContext(project);
    }

    @Nonnull
    @Override
    public LocalizeValue getName() {
        return LocalizeValue.localizeTODO("MSBuild");
    }

    @Nonnull
    @Override
    public Image getIcon() {
        return MSBuildIconGroup.msbuild();
    }

    @Override
    public boolean canImport(@Nonnull Path path) {
        // if target directory contains Solution - do not allow import as MSBuild
        if (Files.isDirectory(path)) {
            if (SolutionModuleImportProvider.hasSolutionFile(path)) {
                return false;
            }

            return findSingleSolutionFile(path) != null;
        }
        else {
            String ext = FileUtil.getExtension(path.getFileName().toString());
            return MSBuildProjectFile.listAll(myApplication).contains(ext);
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

    @Nullable
    private Path findSingleSolutionFile(Path directory) {
        Set<String> exts = MSBuildProjectFile.listAll(myApplication);

        Path firstSolution = null;
        for (Path file : SolutionModuleImportProvider.listFiles(directory)) {
            String ext = FileUtil.getExtension(file.getFileName().toString());
            if (exts.contains(ext)) {
                // already found - return null
                if (firstSolution != null) {
                    return null;
                }

                firstSolution = file;
            }
        }
        return firstSolution;
    }

    @Override
    public void buildSteps(@Nonnull Consumer<WizardStep<MSBuildModuleImportContext>> consumer, @Nonnull MSBuildModuleImportContext context) {
        consumer.accept(new MSBuildProjectOrModuleNameStep<>(context));
    }

    @Override
    public Coroutine<Object, Object> process(@Nonnull MSBuildModuleImportContext context,
                                             @Nonnull Project project,
                                             @Nonnull ModifiableModuleModel modifiableModuleModel,
                                             @Nonnull Consumer<Module> consumer) {
        return ReadLock.apply((i, continuation) -> {
                String fileToImport = context.getFileToImport();

                VirtualFile projectFile = LocalFileSystem.getInstance().findFileByPath(fileToImport);
                assert projectFile != null;

                VirtualFile parent = projectFile.getParent();

                final ModifiableRootModel mainModuleModel = SolutionModuleImportProvider.createModuleWithSingleContent(parent.getName(), parent, modifiableModuleModel);

                MSBuildSolutionMutableModuleExtension<?> solExtension = mainModuleModel.getExtensionWithoutCheck(context.getProvider().getSolutionModuleExtensionId());
                assert solExtension != null;
                solExtension.setEnabled(true);
                solExtension.setProjectFileUrl(projectFile.getUrl());
                solExtension.setSdkName(context.getMSBuildBundleName());
                solExtension.setProcessProviderId(context.getProvider().getId());

                consumer.accept(mainModuleModel.getModule());

                project.getInstance(ProjectRunOneService.class).register(MSBuildRunOnceExtension.ID, new MSBuildRunOnceExtension.Data(""));

                return modifiableModuleModel;
            })
            .toCoroutine()
            .then(WriteLock.apply((model, continuation) -> {
                model.commit();
                return null;
            }));
    }
}
