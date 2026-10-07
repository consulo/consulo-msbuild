package consulo.msbuild.dotnet.mono.module.extension;

import consulo.annotation.component.ExtensionImpl;
import consulo.localize.LocalizeValue;
import consulo.module.content.layer.ModuleExtensionProvider;
import consulo.module.content.layer.ModuleRootLayer;
import consulo.module.extension.ModuleExtension;
import consulo.module.extension.MutableModuleExtension;
import consulo.ui.image.Image;
import consulo.mono.dotnet.icon.MonoDotNetIconGroup;
import consulo.msbuild.csharp.module.extension.MSBuildCSharpModuleExtension;
import consulo.msbuild.csharp.module.extension.MSBuildCSharpMutableModuleExtension;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

/**
 * @author VISTALL
 * @since 2026-10-07
 */
@ExtensionImpl
public class MSBuildMonoCSharpModuleExtensionProvider implements ModuleExtensionProvider<MSBuildCSharpModuleExtension>
{
	@Nonnull
	@Override
	public String getId()
	{
		return "csharp-mono-by-msbuild";
	}

	@Nullable
	@Override
	public String getParentId()
	{
		return "dotnet-mono-by-msbuild";
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
		return LocalizeValue.localizeTODO("C#");
	}

	@Nonnull
	@Override
	public Image getIcon()
	{
		return MonoDotNetIconGroup.mono();
	}

	@Nonnull
	@Override
	public ModuleExtension<MSBuildCSharpModuleExtension> createImmutableExtension(@Nonnull ModuleRootLayer moduleRootLayer)
	{
		return new MSBuildCSharpModuleExtension(getId(), moduleRootLayer);
	}

	@Nonnull
	@Override
	public MutableModuleExtension<MSBuildCSharpModuleExtension> createMutableExtension(@Nonnull ModuleRootLayer moduleRootLayer)
	{
		return new MSBuildCSharpMutableModuleExtension(getId(), moduleRootLayer);
	}
}
