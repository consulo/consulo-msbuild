package consulo.msbuild.dotnet.core.module.extension;

import com.intellij.openapi.projectRoots.Sdk;
import consulo.disposer.Disposable;
import consulo.dotnet.module.extension.DotNetMutableModuleExtension;
import consulo.module.extension.MutableModuleInheritableNamedPointer;
import consulo.msbuild.module.extension.MSBuildProjectMutableModuleExtension;
import consulo.roots.ModuleRootLayer;
import consulo.ui.Component;
import consulo.ui.annotation.RequiredUIAccess;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * @author VISTALL
 * @since 18/01/2021
 */
public class MSBuildDotNetCoreMutableModuleExtension extends MSBuildDotNetCoreModuleExtension implements DotNetMutableModuleExtension<MSBuildDotNetCoreModuleExtension>,
		MSBuildProjectMutableModuleExtension<MSBuildDotNetCoreModuleExtension>
{
	public MSBuildDotNetCoreMutableModuleExtension(@Nonnull String id, @Nonnull ModuleRootLayer moduleRootLayer)
	{
		super(id, moduleRootLayer);
	}

	@Nonnull
	@Override
	public MutableModuleInheritableNamedPointer<Sdk> getInheritableSdk()
	{
		return (MutableModuleInheritableNamedPointer<Sdk>) super.getInheritableSdk();
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
	public boolean isModified(@Nonnull MSBuildDotNetCoreModuleExtension originalExtension)
	{
		return isModifiedImpl(originalExtension);
	}
}
