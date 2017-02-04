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
import consulo.msbuild.MSBuildProjectType;
import consulo.msbuild.importProvider.item.MSBuildImportProject;
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
			// it will always return null for MSBuildGUID.SolutionFolder
			MSBuildProjectType projectType = MSBuildProjectType.getProjectType(project.TypeGuid);
			if(projectType == null)
			{
				continue;
			}

			myItems.add(projectType.createImportItem(project));
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
