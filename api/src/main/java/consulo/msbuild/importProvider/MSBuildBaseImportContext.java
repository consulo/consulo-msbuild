package consulo.msbuild.importProvider;

import consulo.ide.moduleImport.ModuleImportContext;
import consulo.msbuild.MSBuildProcessProvider;
import consulo.project.Project;

import javax.annotation.Nullable;

/**
 * @author VISTALL
 * @since 23/01/2021
 */
public abstract class MSBuildBaseImportContext extends ModuleImportContext
{
	private String myMSBuildBundleName;
	private MSBuildProcessProvider myProvider;

	public MSBuildBaseImportContext(@Nullable Project project)
	{
		super(project);
	}

	public void setMSBuildBundleName(String MSBuildBundleName)
	{
		myMSBuildBundleName = MSBuildBundleName;
	}

	public String getMSBuildBundleName()
	{
		return myMSBuildBundleName;
	}

	public MSBuildProcessProvider getProvider()
	{
		return myProvider;
	}

	public void setProvider(MSBuildProcessProvider provider)
	{
		myProvider = provider;
	}
}
