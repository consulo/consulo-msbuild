package consulo.msbuild.importProvider;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.LocalFileSystem;
import com.intellij.openapi.vfs.VirtualFile;
import consulo.msbuild.solution.reader.SlnFile;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.nio.charset.StandardCharsets;

/**
 * @author VISTALL
 * @since 01-Feb-17
 */
public class SolutionModuleImportContext extends MSBuildBaseImportContext
{
	private SlnFile mySlnFile = new SlnFile();

	public SolutionModuleImportContext(@Nullable Project project)
	{
		super(project);
	}

	@Override
	public void setFileToImport(String dirOrFile)
	{
		String solutionFilePath = getPath();
		VirtualFile solutionFile = LocalFileSystem.getInstance().findFileByPath(solutionFilePath);
		assert solutionFile != null;

		setName(solutionFile.getNameWithoutExtension());
		setPath(solutionFile.getParent().getPath());

		super.setFileToImport(solutionFile.getPath());

		try (LineNumberReader reader = new LineNumberReader(new InputStreamReader(new FileInputStream(solutionFilePath), StandardCharsets.UTF_8)))
		{
			mySlnFile.Read(reader);
		}
		catch(IOException ignored)
		{
		}
	}

	@Nonnull
	public SlnFile getSlnFile()
	{
		return mySlnFile;
	}
}
