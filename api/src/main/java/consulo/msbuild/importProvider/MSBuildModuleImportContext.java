package consulo.msbuild.importProvider;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Nonnull;
import consulo.moduleImport.ModuleImportContext;
import consulo.msbuild.MSBuildProjectType;
import consulo.msbuild.MSBuildSolutionManager;
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
	private Map<String, MSBuildSolutionManager.ProjectOptions> myProjectOptions = new HashMap<>();

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

			myItems.add(projectType.createImportItem(project, this));
		}

		return super.setFileToImport(fileToImport);
	}

	@Nonnull
	public MSBuildSolutionManager.ProjectOptions getOptions(@Nonnull String name)
	{
		return myProjectOptions.computeIfAbsent(name, s -> new MSBuildSolutionManager.ProjectOptions());
	}

	public Map<String, MSBuildSolutionManager.ProjectOptions> getProjectOptions()
	{
		return myProjectOptions;
	}

	public List<MSBuildImportProject> getMappedProjects()
	{
		return myItems;
	}

	@Nonnull
	public SlnFile getSlnFile()
	{
		return mySlnFile;
	}
}
