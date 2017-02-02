package consulo.msbuild.importProvider;

import java.util.ArrayList;
import java.util.List;

import javax.swing.Icon;

import org.intellij.lang.annotations.Language;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import com.intellij.ide.util.projectWizard.ModuleWizardStep;
import com.intellij.ide.util.projectWizard.WizardContext;
import com.intellij.openapi.application.WriteAction;
import com.intellij.openapi.module.ModifiableModuleModel;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.ModuleManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.roots.ModifiableRootModel;
import com.intellij.openapi.roots.ModuleRootManager;
import com.intellij.openapi.roots.ui.configuration.ModulesProvider;
import com.intellij.openapi.vfs.LocalFileSystem;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.packaging.artifacts.ModifiableArtifactModel;
import consulo.annotations.RequiredReadAction;
import consulo.moduleImport.ModuleImportProvider;
import consulo.msbuild.MSBuildIcons;
import consulo.msbuild.MSBuildSolutionManager;
import consulo.msbuild.VisualStudioSolutionFileType;
import consulo.msbuild.importProvider.ui.MSBuildSetupTargetStep;

/**
 * @author VISTALL
 * @since 01-Feb-17
 */
public class MSBuildModuleImportProvider implements ModuleImportProvider<MSBuildModuleImportContext>
{
	@NotNull
	@Override
	public MSBuildModuleImportContext createContext()
	{
		return new MSBuildModuleImportContext();
	}

	@NotNull
	@Override
	@Language("HTML")
	public String getFileSample()
	{
		return "<b>Visual Studio</b> solution file (*.sln)";
	}

	@NotNull
	@Override
	public String getName()
	{
		return "Visual Studio";
	}

	@Nullable
	@Override
	public Icon getIcon()
	{
		return MSBuildIcons.VisualStudio;
	}

	@Override
	public boolean canImport(@NotNull VirtualFile fileOrDirectory)
	{
		return fileOrDirectory.getFileType() == VisualStudioSolutionFileType.INSTANCE;
	}

	@Override
	public String getPathToBeImported(@NotNull VirtualFile file)
	{
		return file.getPath();
	}

	@Override
	@NotNull
	public ModuleWizardStep[] createSteps(@NotNull WizardContext wizardContext, @NotNull MSBuildModuleImportContext context)
	{
		VirtualFile fileByPath = LocalFileSystem.getInstance().findFileByPath(wizardContext.getProjectFileDirectory());
		assert fileByPath != null;
		wizardContext.setProjectName(fileByPath.getNameWithoutExtension());
		wizardContext.setProjectFileDirectory(fileByPath.getParent().getPath());

		return new ModuleWizardStep[]{new MSBuildSetupTargetStep(context, wizardContext)};
	}

	@NotNull
	@Override
	@RequiredReadAction
	public List<Module> commit(@NotNull MSBuildModuleImportContext context,
			@NotNull Project project,
			@Nullable ModifiableModuleModel old,
			@NotNull ModulesProvider modulesProvider,
			@Nullable ModifiableArtifactModel artifactModel)
	{
		String fileToImport = context.getFileToImport();

		VirtualFile solutionFile = LocalFileSystem.getInstance().findFileByPath(fileToImport);
		assert solutionFile != null;
		MSBuildSolutionManager.getInstance(project).setUrl(solutionFile);
		VirtualFile parent = solutionFile.getParent();

		List<Module> modules = new ArrayList<>();

		final ModifiableModuleModel modifiableModuleModel = old == null ? ModuleManager.getInstance(project).getModifiableModel() : old;

		final ModifiableRootModel mainModuleModel = createModuleWithSingleContent(parent.getName() + " (Solution)", parent, modifiableModuleModel);
		modules.add(mainModuleModel.getModule());
		WriteAction.run(mainModuleModel::commit);

		if(modifiableModuleModel != old)
		{
			WriteAction.run(modifiableModuleModel::commit);
		}
		return modules;
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