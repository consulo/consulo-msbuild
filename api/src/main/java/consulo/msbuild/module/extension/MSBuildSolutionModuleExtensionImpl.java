package consulo.msbuild.module.extension;

import consulo.annotation.access.RequiredReadAction;
import consulo.module.content.layer.ModuleRootLayer;
import consulo.module.content.layer.extension.ModuleExtensionBase;
import consulo.msbuild.solution.model.WProject;
import consulo.msbuild.solution.model.WSolution;
import consulo.virtualFileSystem.VirtualFile;
import consulo.virtualFileSystem.VirtualFileManager;
import org.jdom.Element;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import java.util.Collection;
import java.util.List;

/**
 * @author VISTALL
 * @since 18/01/2021
 */
public class MSBuildSolutionModuleExtensionImpl extends ModuleExtensionBase<MSBuildSolutionModuleExtensionImpl> implements MSBuildSolutionModuleExtension<MSBuildSolutionModuleExtensionImpl>
{
	protected String mySdkName;
	protected String myProviderId;

	protected String mySolutionUrl;
	protected String myProjectUrl;
	protected String myProjectUUID;

	public MSBuildSolutionModuleExtensionImpl(@Nonnull String id, @Nonnull ModuleRootLayer moduleRootLayer)
	{
		super(id, moduleRootLayer);
	}

	@Nullable
	@Override
	public String getSdkName()
	{
		return mySdkName;
	}

	@Nullable
	@Override
	public String getSolutionFileUrl()
	{
		return mySolutionUrl;
	}

	@Nullable
	@Override
	public String getProjectUrl()
	{
		return myProjectUrl;
	}

	@Nullable
	@Override
	public String getProjectUUID()
	{
		return myProjectUUID;
	}

	@Nullable
	@Override
	public VirtualFile getSolutionFile()
	{
		if(mySolutionUrl == null)
		{
			return null;
		}
		return VirtualFileManager.getInstance().findFileByUrl(mySolutionUrl);
	}

	@Nullable
	@Override
	public VirtualFile getProjectFile()
	{
		if(myProjectUrl == null)
		{
			return null;
		}
		return VirtualFileManager.getInstance().findFileByUrl(myProjectUrl);
	}

	@Nonnull
	@Override
	public Collection<WProject> getProjects()
	{
		if(myProjectUrl != null)
		{
			VirtualFile projectFile = getProjectFile();
			if(projectFile != null)
			{
				return List.of(new WProject(getModule().getProject(), projectFile, myProjectUUID));
			}
		}
		else
		{
			VirtualFile solutionFile = getSolutionFile();
			if(solutionFile != null)
			{
				return WSolution.build(getProject(), solutionFile).getProjects();
			}
		}
		return List.of();
	}

	@Nonnull
	@Override
	public String getProcessProviderId()
	{
		return myProviderId;
	}

	@RequiredReadAction
	@Override
	protected void loadStateImpl(@Nonnull Element element)
	{
		super.loadStateImpl(element);

		mySdkName = element.getAttributeValue("sdk");
		mySolutionUrl = element.getAttributeValue("solution-url");
		myProjectUrl = element.getAttributeValue("project-url");
		myProjectUUID = element.getAttributeValue("project-uuid");
		myProviderId = element.getAttributeValue("provider-id");
	}

	@Override
	protected void getStateImpl(@Nonnull Element element)
	{
		super.getStateImpl(element);

		element.setAttribute("sdk", mySdkName);
		if(mySolutionUrl != null)
		{
			element.setAttribute("solution-url", mySolutionUrl);
		}
		if(myProjectUrl != null)
		{
			element.setAttribute("project-url", myProjectUrl);
		}
		if(myProjectUUID != null)
		{
			element.setAttribute("project-uuid", myProjectUUID);
		}
		element.setAttribute("provider-id", myProviderId);
	}

	@RequiredReadAction
	@Override
	public void commit(@Nonnull MSBuildSolutionModuleExtensionImpl mutableModuleExtension)
	{
		super.commit(mutableModuleExtension);
		mySdkName = mutableModuleExtension.mySdkName;
		mySolutionUrl = mutableModuleExtension.mySolutionUrl;
		myProjectUrl = mutableModuleExtension.myProjectUrl;
		myProviderId = mutableModuleExtension.myProviderId;
		myProjectUUID = mutableModuleExtension.myProjectUUID;
	}
}
