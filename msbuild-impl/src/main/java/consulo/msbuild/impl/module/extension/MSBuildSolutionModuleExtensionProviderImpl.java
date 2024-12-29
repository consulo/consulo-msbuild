package consulo.msbuild.impl.module.extension;

import consulo.localize.LocalizeValue;
import consulo.module.content.layer.ModuleExtensionProvider;
import consulo.module.content.layer.ModuleRootLayer;
import consulo.module.extension.ModuleExtension;
import consulo.module.extension.MutableModuleExtension;
import consulo.msbuild.icon.MSBuildIconGroup;
import consulo.msbuild.module.extension.MSBuildSolutionModuleExtensionImpl;
import consulo.msbuild.module.extension.MSBuildSolutionMutableModuleExtensionImpl;
import consulo.ui.image.Image;

import jakarta.annotation.Nonnull;

/**
 * @author VISTALL
 * @since 11/01/2023
 */
public class MSBuildSolutionModuleExtensionProviderImpl implements ModuleExtensionProvider<MSBuildSolutionModuleExtensionImpl>
{
	@Nonnull
	@Override
	public String getId()
	{
		return "msbuild-default";
	}

	@Nonnull
	@Override
	public LocalizeValue getName()
	{
		return LocalizeValue.localizeTODO("MSBuild Solution");
	}

	@Nonnull
	@Override
	public Image getIcon()
	{
		return MSBuildIconGroup.msbuild();
	}

	@Override
	public boolean isSystemOnly()
	{
		return true;
	}

	@Nonnull
	@Override
	public ModuleExtension<MSBuildSolutionModuleExtensionImpl> createImmutableExtension(@Nonnull ModuleRootLayer moduleRootLayer)
	{
		return new MSBuildSolutionModuleExtensionImpl(getId(), moduleRootLayer);
	}

	@Nonnull
	@Override
	public MutableModuleExtension<MSBuildSolutionModuleExtensionImpl> createMutableExtension(@Nonnull ModuleRootLayer moduleRootLayer)
	{
		return new MSBuildSolutionMutableModuleExtensionImpl(getId(), moduleRootLayer);
	}
}
