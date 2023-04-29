package consulo.msbuild.impl.importProvider;

import consulo.annotation.access.RequiredReadAction;
import consulo.annotation.component.ExtensionImpl;
import consulo.application.Application;
import consulo.application.WriteAction;
import consulo.application.dumb.DumbAwareRunnable;
import consulo.application.util.HtmlBuilder;
import consulo.ide.moduleImport.ModuleImportProvider;
import consulo.module.ModifiableModuleModel;
import consulo.module.Module;
import consulo.module.content.layer.ModifiableRootModel;
import consulo.msbuild.MSBuildProjectFile;
import consulo.msbuild.daemon.impl.MSBuildDaemonService;
import consulo.msbuild.icon.MSBuildIconGroup;
import consulo.msbuild.importProvider.MSBuildModuleImportContext;
import consulo.msbuild.module.extension.MSBuildSolutionMutableModuleExtension;
import consulo.project.Project;
import consulo.project.startup.StartupManager;
import consulo.ui.ex.wizard.WizardStep;
import consulo.ui.image.Image;
import consulo.util.io.FileUtil;
import consulo.virtualFileSystem.LocalFileSystem;
import consulo.virtualFileSystem.VirtualFile;
import consulo.virtualFileSystem.util.VirtualFileUtil;
import jakarta.inject.Inject;
import org.intellij.lang.annotations.Language;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.File;
import java.util.Set;
import java.util.function.Consumer;

/**
 * @author VISTALL
 * @since 23/01/2021
 */
@ExtensionImpl
public class MSBuildModuleImportProvider implements ModuleImportProvider<MSBuildModuleImportContext>
{
	private final Application myApplication;

	@Inject
	public MSBuildModuleImportProvider(Application application)
	{
		myApplication = application;
	}

	@Nonnull
	@Override
	public MSBuildModuleImportContext createContext(@Nullable Project project)
	{
		return new MSBuildModuleImportContext(project);
	}

	@Nonnull
	@Override
	@Language("HTML")
	public String getFileSample()
	{
		HtmlBuilder builder = new HtmlBuilder();
		builder.wrapWith("b").addText("MSBuild");
		builder.append("project files (");
		for(String ext : MSBuildProjectFile.listAll(myApplication))
		{
			builder.append("*." + ext);
		}
		builder.append(")");
		return builder.toString();
	}

	@Nonnull
	@Override
	public String getName()
	{
		return "MSBuild";
	}

	@Nonnull
	@Override
	public Image getIcon()
	{
		return MSBuildIconGroup.msbuild();
	}

	@Override
	public boolean canImport(@Nonnull File fileOrDirectory)
	{
		// if target directory contains Solution - do not allow import as MSBuild
		if(fileOrDirectory.isDirectory())
		{
			if(SolutionModuleImportProvider.findSingleSolutionFile(fileOrDirectory) != null)
			{
				return false;
			}
		}

		if(fileOrDirectory.isDirectory())
		{
			return findSingleSolutionFile(fileOrDirectory) != null;
		}
		else
		{
			String ext = FileUtil.getExtension(fileOrDirectory.getName());
			return MSBuildProjectFile.listAll(myApplication).contains(ext);
		}
	}

	@Override
	public String getPathToBeImported(@Nonnull VirtualFile file)
	{
		if(file.isDirectory())
		{
			File solutionFile = findSingleSolutionFile(VirtualFileUtil.virtualToIoFile(file));
			if(solutionFile != null)
			{
				return solutionFile.getPath();
			}
		}
		return file.getPath();
	}

	private File findSingleSolutionFile(File directory)
	{
		Set<String> exts = MSBuildProjectFile.listAll(myApplication);

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
						String ext = FileUtil.getExtension(file.getName());
						if(exts.contains(ext))
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
	public void buildSteps(@Nonnull Consumer<WizardStep<MSBuildModuleImportContext>> consumer, @Nonnull MSBuildModuleImportContext context)
	{
		consumer.accept(new MSBuildProjectOrModuleNameStep<>(context));
	}

	@RequiredReadAction
	@Override
	public void process(@Nonnull MSBuildModuleImportContext context,
						@Nonnull Project project,
						@Nonnull ModifiableModuleModel modifiableModuleModel,
						@Nonnull Consumer<Module> consumer)
	{
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

		WriteAction.run(mainModuleModel::commit);

		consumer.accept(mainModuleModel.getModule());

		StartupManager.getInstance(project).registerPostStartupActivity((DumbAwareRunnable) () -> {
			MSBuildDaemonService.getInstance(project).forceUpdate();

			// TODO [VISTALL] create run configurations after reimport
		});
	}
}
