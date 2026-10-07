package consulo.msbuild.dotnet.microsoft.module.extension;

import consulo.annotation.component.ExtensionImpl;
import consulo.localize.LocalizeValue;
import consulo.module.content.layer.ModuleExtensionProvider;
import consulo.module.content.layer.ModuleRootLayer;
import consulo.module.extension.ModuleExtension;
import consulo.module.extension.MutableModuleExtension;
import consulo.ui.image.Image;
import consulo.dotnet.microsoft.icon.MicrosoftDotNetIconGroup;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

/**
 * @author VISTALL
 * @since 2026-10-07
 */
@ExtensionImpl
public class MSBuildMicrosoftDotNetModuleExtensionProvider implements ModuleExtensionProvider<MSBuildMicrosoftDotNetModuleExtension>
{
	@Nonnull
	@Override
	public String getId()
	{
		return "dotnet-by-msbuild";
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
		return LocalizeValue.localizeTODO(".NET Framework (MSBuild)");
	}

	@Nonnull
	@Override
	public Image getIcon()
	{
		return MicrosoftDotNetIconGroup.dotnet();
	}

	@Nonnull
	@Override
	public ModuleExtension<MSBuildMicrosoftDotNetModuleExtension> createImmutableExtension(@Nonnull ModuleRootLayer moduleRootLayer)
	{
		return new MSBuildMicrosoftDotNetModuleExtension(getId(), moduleRootLayer);
	}

	@Nonnull
	@Override
	public MutableModuleExtension<MSBuildMicrosoftDotNetModuleExtension> createMutableExtension(@Nonnull ModuleRootLayer moduleRootLayer)
	{
		return new MSBuildMicrosoftDotNetMutableModuleExtension(getId(), moduleRootLayer);
	}
}
