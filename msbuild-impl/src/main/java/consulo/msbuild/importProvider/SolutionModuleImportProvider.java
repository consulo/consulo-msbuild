package consulo.msbuild.importProvider;

import java.io.File;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.intellij.lang.annotations.Language;
import com.intellij.openapi.application.WriteAction;
import com.intellij.openapi.fileTypes.FileTypeRegistry;
import com.intellij.openapi.module.ModifiableModuleModel;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.roots.ModifiableRootModel;
import com.intellij.openapi.roots.ModuleRootManager;
import com.intellij.openapi.vfs.LocalFileSystem;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.util.containers.ContainerUtil;
import consulo.annotation.access.RequiredReadAction;
import consulo.ide.newProject.ui.ProjectOrModuleNameStep;
import consulo.moduleImport.ModuleImportProvider;
import consulo.msbuild.MSBuildIcons;
import consulo.msbuild.MSBuildSolutionManager;
import consulo.msbuild.VisualStudioSolutionFileType;
import consulo.msbuild.importProvider.item.MSBuildImportProject;
import consulo.ui.image.Image;
import consulo.ui.wizard.WizardStep;

/**
 * @author VISTALL
 * @since 01-Feb-17
 */
public class SolutionModuleImportProvider implements ModuleImportProvider<SolutionModuleImportContext>
{
	@Nonnull
	@Override
	public SolutionModuleImportContext createContext(@Nullable Project project)
	{
		return new SolutionModuleImportContext(project);
	}

	@Nonnull
	@Override
	@Language("HTML")
	public String getFileSample()
	{
		return "<b>Visual Studio</b> solution file (*.sln)";
	}

	@Nonnull
	@Override
	public String getName()
	{
		return "Visual Studio";
	}

	@Nullable
	@Override
	public Image getIcon()
	{
		return MSBuildIcons.VisualStudio;
	}

	@Override
	public boolean canImport(@Nonnull File fileOrDirectory)
	{
		return !fileOrDirectory.isDirectory() && FileTypeRegistry.getInstance().getFileTypeByFileName(fileOrDirectory.getName()) == VisualStudioSolutionFileType.INSTANCE;
	}

	@Override
	public String getPathToBeImported(@Nonnull VirtualFile file)
	{
		return file.getPath();
	}

	@Override
	public void buildSteps(@Nonnull Consumer<WizardStep<SolutionModuleImportContext>> consumer, @Nonnull SolutionModuleImportContext context)
	{
		List<MSBuildSetupStepEP> extensions = MSBuildSetupStepEP.EP.getExtensionList();

		Set<Class> classes = new LinkedHashSet<>();
		for(MSBuildImportProject project : context.getMappedProjects())
		{
			classes.add(project.getClass());
		}

		for(Class aClass : classes)
		{
			MSBuildSetupStepEP stepEP = ContainerUtil.find(extensions, it -> it.getImportProjectClass() == aClass);
			if(stepEP != null)
			{
				consumer.accept(stepEP.createStep(context));
			}
		}

		consumer.accept(new ProjectOrModuleNameStep<>(context));
	}

	@RequiredReadAction
	@Override
	public void process(@Nonnull SolutionModuleImportContext context,
						@Nonnull Project project,
						@Nonnull ModifiableModuleModel modifiableModuleModel,
						@Nonnull Consumer<Module> consumer)
	{
		String fileToImport = context.getFileToImport();

		VirtualFile solutionFile = LocalFileSystem.getInstance().findFileByPath(fileToImport);
		assert solutionFile != null;
		MSBuildSolutionManager solutionManager = MSBuildSolutionManager.getInstance(project);
		solutionManager.setEnabled(true);
		solutionManager.setUrl(solutionFile);
		for(Map.Entry<String, MSBuildSolutionManager.ProjectOptions> entry : context.getProjectOptions().entrySet())
		{
			solutionManager.putOptions(entry.getKey(), entry.getValue());
		}

		VirtualFile parent = solutionFile.getParent();

		final ModifiableRootModel mainModuleModel = createModuleWithSingleContent(parent.getName() + " (Solution)", parent, modifiableModuleModel);
		consumer.accept(mainModuleModel.getModule());
		WriteAction.run(mainModuleModel::commit);
	}

	@RequiredReadAction
	private ModifiableRootModel createModuleWithSingleContent(String name, VirtualFile dir, ModifiableModuleModel modifiableModuleModel)
	{
		Module module = modifiableModuleModel.newModule(name, dir.getPath());

		ModuleRootManager moduleRootManager = ModuleRootManager.getInstance(module);
		ModifiableRootModel modifiableModel = moduleRootManager.getModifiableModel();
		modifiableModel.addContentEntry(dir);

		return modifiableModel;
	}
}
