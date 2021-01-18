package consulo.msbuild.module.extension;

import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.openapi.vfs.VirtualFileManager;
import consulo.annotation.access.RequiredReadAction;
import consulo.module.extension.impl.ModuleExtensionImpl;
import consulo.msbuild.solution.model.WProject;
import consulo.msbuild.solution.model.WSolution;
import consulo.roots.ModuleRootLayer;
import org.jdom.Element;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Collection;
import java.util.List;

/**
 * @author VISTALL
 * @since 18/01/2021
 */
public class MSBuildSolutionModuleExtensionImpl extends ModuleExtensionImpl<MSBuildSolutionModuleExtensionImpl> implements MSBuildSolutionModuleExtension<MSBuildSolutionModuleExtensionImpl>
{
	protected String mySdkName;
	protected String mySolutionUrl;
	protected String myProviderId;

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
	public VirtualFile getSolutionFile()
	{
		if(mySolutionUrl == null)
		{
			return null;
		}
		return VirtualFileManager.getInstance().findFileByUrl(mySolutionUrl);
	}

	@Nonnull
	@Override
	public Collection<WProject> getProjects()
	{
		VirtualFile solutionFile = getSolutionFile();
		if(solutionFile != null)
		{
			return WSolution.build(getProject(), solutionFile).getProjects();
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
		element.setAttribute("provider-id", myProviderId);
	}

	@RequiredReadAction
	@Override
	public void commit(@Nonnull MSBuildSolutionModuleExtensionImpl mutableModuleExtension)
	{
		super.commit(mutableModuleExtension);
		mySdkName = mutableModuleExtension.mySdkName;
		mySolutionUrl = mutableModuleExtension.mySolutionUrl;
		myProviderId = mutableModuleExtension.myProviderId;
	}
}
