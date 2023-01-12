package consulo.msbuild.importProvider;

import consulo.project.Project;
import consulo.virtualFileSystem.LocalFileSystem;
import consulo.virtualFileSystem.VirtualFile;

import javax.annotation.Nullable;

/**
 * @author VISTALL
 * @since 23/01/2021
 */
public class MSBuildModuleImportContext extends MSBuildBaseImportContext
{
	public MSBuildModuleImportContext(@Nullable Project project)
	{
		super(project);
	}

	@Override
	public void setFileToImport(String dirOrFile)
	{
		String projecFilePath = getPath();
		VirtualFile projectFile = LocalFileSystem.getInstance().findFileByPath(projecFilePath);
		assert projectFile != null;

		setName(projectFile.getNameWithoutExtension());
		setPath(projectFile.getParent().getPath());

		super.setFileToImport(projectFile.getPath());
	}
}
