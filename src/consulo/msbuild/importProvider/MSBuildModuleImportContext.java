package consulo.msbuild.importProvider;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import org.jetbrains.annotations.NotNull;
import consulo.moduleImport.ModuleImportContext;
import consulo.msbuild.MSBuildGUID;
import consulo.msbuild.solution.reader.SlnFile;
import consulo.msbuild.solution.reader.SlnProject;

/**
 * @author VISTALL
 * @since 01-Feb-17
 */
public class MSBuildModuleImportContext extends ModuleImportContext
{
	private SlnFile mySlnFile = new SlnFile();
	private List<MSBuildImportProject> myItems = new ArrayList<>();

	@Override
	public ModuleImportContext setFileToImport(String fileToImport)
	{
		try (LineNumberReader reader = new LineNumberReader(new InputStreamReader(new FileInputStream(fileToImport), StandardCharsets.UTF_8)))
		{
			mySlnFile.Read(reader);
		}
		catch(IOException ignored)
		{
		}

		for(SlnProject project : mySlnFile.getProjects())
		{
			if(!MSBuildGUID.SolutionFolder.equals(project.TypeGuid))
			{
				myItems.add(new MSBuildImportProject(project, MSBuildImportTarget._NET));
			}
		}
		return super.setFileToImport(fileToImport);
	}

	public List<MSBuildImportProject> getMappedProjects()
	{
		return myItems;
	}

	@NotNull
	public SlnFile getSlnFile()
	{
		return mySlnFile;
	}
}
