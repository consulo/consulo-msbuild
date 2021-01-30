package consulo.msbuild.module.extension;

import consulo.disposer.Disposable;
import consulo.roots.ModuleRootLayer;
import consulo.ui.Component;
import consulo.ui.annotation.RequiredUIAccess;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Objects;

/**
 * @author VISTALL
 * @since 18/01/2021
 */
public class MSBuildSolutionMutableModuleExtensionImpl extends MSBuildSolutionModuleExtensionImpl implements MSBuildSolutionMutableModuleExtension<MSBuildSolutionModuleExtensionImpl>
{
	public MSBuildSolutionMutableModuleExtensionImpl(@Nonnull String id, @Nonnull ModuleRootLayer moduleRootLayer)
	{
		super(id, moduleRootLayer);
	}

	@Override
	public void setSolutionFileUrl(@Nullable String url)
	{
		mySolutionUrl = url;
	}

	@Override
	public void setProjectFileUrl(@Nullable String url)
	{
		myProjectUrl = url;
	}

	@Override
	public void setProjectUUID(@Nullable String uuid)
	{
		myProjectUUID = uuid;
	}

	@Override
	public void setProcessProviderId(@Nonnull String id)
	{
		myProviderId = id;
	}

	@Override
	public void setSdkName(@Nonnull String name)
	{
		mySdkName = name;
	}

	@RequiredUIAccess
	@Nullable
	@Override
	public Component createConfigurationComponent(@Nonnull Disposable uiDisposable, @Nonnull Runnable updateOnCheck)
	{
		return null;
	}

	@Override
	public void setEnabled(boolean val)
	{
		myIsEnabled = val;
	}

	@Override
	public boolean isModified(@Nonnull MSBuildSolutionModuleExtensionImpl originalExtension)
	{
		return myIsEnabled != originalExtension.isEnabled() ||
				!Objects.equals(mySdkName, originalExtension.mySdkName) ||
				!Objects.equals(mySolutionUrl, originalExtension.mySolutionUrl) ||
				!Objects.equals(myProjectUrl, originalExtension.myProjectUrl) ||
				!Objects.equals(myProjectUUID, originalExtension.myProjectUUID) ||
				!Objects.equals(myProviderId, originalExtension.myProviderId);
	}
}
