package consulo.msbuild.impl.importProvider;

import consulo.annotation.access.RequiredReadAction;
import consulo.annotation.component.ExtensionImpl;
import consulo.application.WriteAction;
import consulo.ide.impl.idea.openapi.vfs.VfsUtil;
import consulo.ide.moduleImport.ModuleImportProvider;
import consulo.module.ModifiableModuleModel;
import consulo.module.Module;
import consulo.module.content.ModuleRootManager;
import consulo.module.content.layer.ModifiableRootModel;
import consulo.msbuild.MSBuildIcons;
import consulo.msbuild.impl.VisualStudioSolutionFileType;
import consulo.msbuild.daemon.impl.MSBuildDaemonService;
import consulo.msbuild.importProvider.SolutionModuleImportContext;
import consulo.msbuild.module.extension.MSBuildSolutionMutableModuleExtension;
import consulo.project.Project;
import consulo.project.startup.StartupActivity;
import consulo.project.startup.StartupManager;
import consulo.ui.ex.wizard.WizardStep;
import consulo.ui.image.Image;
import consulo.virtualFileSystem.LocalFileSystem;
import consulo.virtualFileSystem.VirtualFile;
import consulo.virtualFileSystem.fileType.FileTypeRegistry;
import org.intellij.lang.annotations.Language;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.File;
import java.util.function.Consumer;

/**
 * @author VISTALL
 * @since 01-Feb-17
 */
@ExtensionImpl
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
			// special case. We don't provide importing Unity project via solution file
			if(new File(fileOrDirectory, "ProjectSettings/ProjectSettings.asset").exists())
			{
				return false;
			}

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
			File[] files = directory.listFiles();
			if(files != null)
			{
				for(File file : files)
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

		StartupManager.getInstance(project).registerPostStartupActivity((StartupActivity.DumbAware) (ui, porject) -> {
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
