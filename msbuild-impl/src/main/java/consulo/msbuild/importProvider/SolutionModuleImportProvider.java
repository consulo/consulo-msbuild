package consulo.msbuild.importProvider;

import com.intellij.openapi.application.WriteAction;
import com.intellij.openapi.fileTypes.FileTypeRegistry;
import com.intellij.openapi.module.ModifiableModuleModel;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.project.DumbAwareRunnable;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.roots.ModifiableRootModel;
import com.intellij.openapi.roots.ModuleRootManager;
import com.intellij.openapi.startup.StartupManager;
import com.intellij.openapi.vfs.LocalFileSystem;
import com.intellij.openapi.vfs.VfsUtil;
import com.intellij.openapi.vfs.VirtualFile;
import consulo.annotation.access.RequiredReadAction;
import consulo.moduleImport.ModuleImportProvider;
import consulo.msbuild.MSBuildIcons;
import consulo.msbuild.VisualStudioSolutionFileType;
import consulo.msbuild.daemon.impl.MSBuildDaemonService;
import consulo.msbuild.module.extension.MSBuildSolutionMutableModuleExtension;
import consulo.ui.image.Image;
import consulo.ui.wizard.WizardStep;
import org.intellij.lang.annotations.Language;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.File;
import java.util.function.Consumer;

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
		if(fileOrDirectory.isDirectory())
		{
			return findSingleSolutionFile(fileOrDirectory) != null;
		}
		else
		{
			return FileTypeRegistry.getInstance().getFileTypeByFileName(fileOrDirectory.getName()) == VisualStudioSolutionFileType.INSTANCE;
		}
	}

	@Override
	public String getPathToBeImported(@Nonnull VirtualFile file)
	{
		if(file.isDirectory())
		{
			File solutionFile = findSingleSolutionFile(VfsUtil.virtualToIoFile(file));
			if(solutionFile != null)
			{
				return solutionFile.getPath();
			}
		}
		return file.getPath();
	}

	public static File findSingleSolutionFile(File directory)
	{
		File firstSolution = null;
		if(directory.isDirectory())
		{
			for(File file : directory.listFiles())
			{
				if(file.isFile())
				{
					if(FileTypeRegistry.getInstance().getFileTypeByFileName(file.getName()) == VisualStudioSolutionFileType.INSTANCE)
					{
						// already found - return null
						if(firstSolution != null)
						{
							return null;
						}

						firstSolution = file;
					}
				}
			}

		}
		return firstSolution;
	}

	@Override
	public void buildSteps(@Nonnull Consumer<WizardStep<SolutionModuleImportContext>> consumer, @Nonnull SolutionModuleImportContext context)
	{
		consumer.accept(new MSBuildProjectOrModuleNameStep<>(context));
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

		VirtualFile parent = solutionFile.getParent();

		final ModifiableRootModel mainModuleModel = createModuleWithSingleContent(parent.getName(), parent, modifiableModuleModel);

		MSBuildSolutionMutableModuleExtension<?> solExtension = mainModuleModel.getExtensionWithoutCheck(context.getProvider().getSolutionModuleExtensionId());
		assert solExtension != null;
		solExtension.setEnabled(true);
		solExtension.setSolutionFileUrl(solutionFile.getUrl());
		solExtension.setSdkName(context.getMSBuildBundleName());
		solExtension.setProcessProviderId(context.getProvider().getId());

		WriteAction.run(mainModuleModel::commit);

		consumer.accept(mainModuleModel.getModule());

		StartupManager.getInstance(project).registerPostStartupActivity((DumbAwareRunnable) () -> {
			MSBuildDaemonService.getInstance(project).forceUpdate();

			// TODO [VISTALL] create run configurations after reimport
		});
	}

	@RequiredReadAction
	public static ModifiableRootModel createModuleWithSingleContent(String dirName, VirtualFile dir, ModifiableModuleModel modifiableModuleModel)
	{
		Module module = modifiableModuleModel.newModule(dirName + " (Root)", dir.getPath());

		ModuleRootManager moduleRootManager = ModuleRootManager.getInstance(module);
		ModifiableRootModel modifiableModel = moduleRootManager.getModifiableModel();
		modifiableModel.addContentEntry(dir);

		return modifiableModel;
	}
}
