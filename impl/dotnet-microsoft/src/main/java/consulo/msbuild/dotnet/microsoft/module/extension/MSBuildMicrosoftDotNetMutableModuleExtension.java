package consulo.msbuild.dotnet.microsoft.module.extension;

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
 * @since 19/01/2021
 */
public class MSBuildMicrosoftDotNetMutableModuleExtension extends MSBuildMicrosoftDotNetModuleExtension implements DotNetMutableModuleExtension<MSBuildMicrosoftDotNetModuleExtension>,
		MSBuildProjectMutableModuleExtension<MSBuildMicrosoftDotNetModuleExtension>
{
	public MSBuildMicrosoftDotNetMutableModuleExtension(@Nonnull String id, @Nonnull ModuleRootLayer moduleRootLayer)
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
	public boolean isModified(@Nonnull MSBuildMicrosoftDotNetModuleExtension originalExtension)
	{
		return isModifiedImpl(originalExtension);
	}
}
