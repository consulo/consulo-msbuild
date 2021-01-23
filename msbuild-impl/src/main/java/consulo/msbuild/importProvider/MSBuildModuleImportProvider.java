package consulo.msbuild.importProvider;

import com.intellij.openapi.application.WriteAction;
import com.intellij.openapi.module.ModifiableModuleModel;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.project.DumbAwareRunnable;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.roots.ModifiableRootModel;
import com.intellij.openapi.startup.StartupManager;
import com.intellij.openapi.util.io.FileUtil;
import com.intellij.openapi.util.text.HtmlBuilder;
import com.intellij.openapi.vfs.LocalFileSystem;
import com.intellij.openapi.vfs.VfsUtil;
import com.intellij.openapi.vfs.VirtualFile;
import consulo.annotation.access.RequiredReadAction;
import consulo.moduleImport.ModuleImportProvider;
import consulo.msbuild.MSBuildIcons;
import consulo.msbuild.MSBuildProjectFileEP;
import consulo.msbuild.daemon.impl.MSBuildDaemonService;
import consulo.msbuild.module.extension.MSBuildSolutionMutableModuleExtension;
import consulo.ui.image.Image;
import consulo.ui.wizard.WizardStep;
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
public class MSBuildModuleImportProvider implements ModuleImportProvider<MSBuildModuleImportContext>
{
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
		for(String ext : MSBuildProjectFileEP.listAll())
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

	@Nullable
	@Override
	public Image getIcon()
	{
		return MSBuildIcons.Msbuild;
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
			return MSBuildProjectFileEP.listAll().contains(ext);
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

	private File findSingleSolutionFile(File directory)
	{
		Set<String> exts = MSBuildProjectFileEP.listAll();

		File firstSolution = null;
		if(directory.isDirectory())
		{
			for(File file : directory.listFiles())
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
