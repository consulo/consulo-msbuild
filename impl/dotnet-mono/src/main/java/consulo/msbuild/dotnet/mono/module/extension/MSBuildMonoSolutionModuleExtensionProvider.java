package consulo.msbuild.dotnet.mono.module.extension;

import consulo.annotation.component.ExtensionImpl;
import consulo.localize.LocalizeValue;
import consulo.module.content.layer.ModuleExtensionProvider;
import consulo.module.content.layer.ModuleRootLayer;
import consulo.module.extension.ModuleExtension;
import consulo.module.extension.MutableModuleExtension;
import consulo.ui.image.Image;
import consulo.mono.dotnet.icon.MonoDotNetIconGroup;
import consulo.msbuild.module.extension.MSBuildSolutionModuleExtensionImpl;
import consulo.msbuild.module.extension.MSBuildSolutionMutableModuleExtensionImpl;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

/**
 * @author VISTALL
 * @since 2026-10-07
 */
@ExtensionImpl
public class MSBuildMonoSolutionModuleExtensionProvider implements ModuleExtensionProvider<MSBuildSolutionModuleExtensionImpl>
{
	@Nonnull
	@Override
	public String getId()
	{
		return "msbuild-mono";
	}

	@Nullable
	@Override
	public String getParentId()
	{
		return null;
	}

	@Override
	public boolean isSystemOnly()
	{
		return true;
	}

	@Nonnull
	@Override
	public LocalizeValue getName()
	{
		return LocalizeValue.localizeTODO("MSBuild Solution (Mono)");
	}

	@Nonnull
	@Override
	public Image getIcon()
	{
		return MonoDotNetIconGroup.mono();
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
