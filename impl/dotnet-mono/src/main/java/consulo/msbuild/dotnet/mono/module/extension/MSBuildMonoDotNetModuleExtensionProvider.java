package consulo.msbuild.dotnet.mono.module.extension;

import consulo.annotation.component.ExtensionImpl;
import consulo.localize.LocalizeValue;
import consulo.module.content.layer.ModuleExtensionProvider;
import consulo.module.content.layer.ModuleRootLayer;
import consulo.module.extension.ModuleExtension;
import consulo.module.extension.MutableModuleExtension;
import consulo.ui.image.Image;
import consulo.mono.dotnet.icon.MonoDotNetIconGroup;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

/**
 * @author VISTALL
 * @since 2026-10-07
 */
@ExtensionImpl
public class MSBuildMonoDotNetModuleExtensionProvider implements ModuleExtensionProvider<MSBuildMonoDotNetModuleExtension>
{
	@Nonnull
	@Override
	public String getId()
	{
		return "dotnet-mono-by-msbuild";
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
		return LocalizeValue.localizeTODO("Mono (MSBuild)");
	}

	@Nonnull
	@Override
	public Image getIcon()
	{
		return MonoDotNetIconGroup.mono();
	}

	@Nonnull
	@Override
	public ModuleExtension<MSBuildMonoDotNetModuleExtension> createImmutableExtension(@Nonnull ModuleRootLayer moduleRootLayer)
	{
		return new MSBuildMonoDotNetModuleExtension(getId(), moduleRootLayer);
	}

	@Nonnull
	@Override
	public MutableModuleExtension<MSBuildMonoDotNetModuleExtension> createMutableExtension(@Nonnull ModuleRootLayer moduleRootLayer)
	{
		return new MSBuildMonoDotNetMutableModuleExtension(getId(), moduleRootLayer);
	}
}
