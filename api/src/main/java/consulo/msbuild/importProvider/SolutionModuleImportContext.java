package consulo.msbuild.importProvider;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.LocalFileSystem;
import com.intellij.openapi.vfs.VirtualFile;
import consulo.moduleImport.ModuleImportContext;
import consulo.msbuild.MSBuildProjectType;
import consulo.msbuild.MSBuildSolutionManager;
import consulo.msbuild.importProvider.item.MSBuildImportProject;
import consulo.msbuild.solution.reader.SlnFile;
import consulo.msbuild.solution.reader.SlnProject;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author VISTALL
 * @since 01-Feb-17
 */
public class SolutionModuleImportContext extends ModuleImportContext
{
	private SlnFile mySlnFile = new SlnFile();
	private List<MSBuildImportProject> myItems = new ArrayList<>();
	private Map<String, MSBuildSolutionManager.ProjectOptions> myProjectOptions = new HashMap<>();
	private String myMSBuildBundleName;

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
	}

	@Nonnull
	public MSBuildSolutionManager.ProjectOptions getOptions(@Nonnull String name)
	{
		return myProjectOptions.computeIfAbsent(name, s -> new MSBuildSolutionManager.ProjectOptions());
	}

	@Deprecated
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

	public void setMSBuildBundleName(String MSBuildBundleName)
	{
		myMSBuildBundleName = MSBuildBundleName;
	}

	public String getMSBuildBundleName()
	{
		return myMSBuildBundleName;
	}
}
