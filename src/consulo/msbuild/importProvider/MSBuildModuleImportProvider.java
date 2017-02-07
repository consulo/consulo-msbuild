package consulo.msbuild.importProvider;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.Icon;

import org.intellij.lang.annotations.Language;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import com.intellij.ide.util.newProjectWizard.ProjectNameStep;
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
import com.intellij.util.containers.ContainerUtil;
import consulo.annotations.RequiredReadAction;
import consulo.moduleImport.ModuleImportProvider;
import consulo.msbuild.MSBuildIcons;
import consulo.msbuild.MSBuildSolutionManager;
import consulo.msbuild.VisualStudioSolutionFileType;
import consulo.msbuild.importProvider.item.MSBuildImportProject;

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

		MSBuildSetupStepEP[] extensions = MSBuildSetupStepEP.EP.getExtensions();

		Set<Class> classes = new LinkedHashSet<>();
		List<ModuleWizardStep> wizardSteps = new ArrayList<>();
		for(MSBuildImportProject project : context.getMappedProjects())
		{
			classes.add(project.getClass());
		}

		for(Class aClass : classes)
		{
			MSBuildSetupStepEP stepEP = ContainerUtil.find(extensions, it -> it.getImportProjectClass() == aClass);
			if(stepEP != null)
			{
				wizardSteps.add(stepEP.createStep(context, wizardContext));
			}
		}

		wizardSteps.add(new ProjectNameStep(wizardContext));
		return wizardSteps.toArray(new ModuleWizardStep[wizardSteps.size()]);
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
		MSBuildSolutionManager solutionManager = MSBuildSolutionManager.getInstance(project);
		solutionManager.setEnabled(true);
		solutionManager.setUrl(solutionFile);
		for(Map.Entry<String, MSBuildSolutionManager.ProjectOptions> entry : context.getProjectOptions().entrySet())
		{
			solutionManager.putOptions(entry.getKey(), entry.getValue());
		}

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
