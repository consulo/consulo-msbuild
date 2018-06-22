package consulo.msbuild.module.extension.resolve;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.intellij.icons.AllIcons;
import consulo.msbuild.importProvider.item.MSBuildImportTarget;
import consulo.msbuild.module.extension.MSBuildRootExtension;
import consulo.ui.image.Image;

/**
 * @author VISTALL
 * @since 2018-06-22
 */
public class AutomaticBundleInfo implements MSBuildBundleInfo
{
	public static final AutomaticBundleInfo INSTANCE = new AutomaticBundleInfo();

	@Nonnull
	@Override
	public String getId()
	{
		return "$$$auto$$$";
	}

	@Nonnull
	@Override
	public String getName()
	{
		return "Auto";
	}

	@Nonnull
	@Override
	public Image getIcon()
	{
		return AllIcons.Actions.FindPlain;
	}

	@Nullable
	@Override
	public Object resolveSdk(@Nonnull MSBuildImportTarget target, @Nonnull MSBuildRootExtension<?> rootExtension)
	{
		return rootExtension.resolveAutoSdk();
	}
}
