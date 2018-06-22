package consulo.msbuild.module.extension.resolve;

import javax.annotation.Nonnull;

import com.intellij.openapi.projectRoots.Sdk;
import consulo.bundle.SdkUtil;
import consulo.msbuild.importProvider.item.MSBuildImportTarget;
import consulo.msbuild.module.extension.MSBuildRootExtension;
import consulo.ui.image.Image;

/**
 * @author VISTALL
 * @since 2018-06-22
 */
public class DefaultBundleInfo implements MSBuildBundleInfo
{
	private Sdk mySdk;

	public DefaultBundleInfo(@Nonnull Sdk sdk)
	{
		mySdk = sdk;
	}

	@Nonnull
	public Sdk getSdk()
	{
		return mySdk;
	}

	@Nonnull
	@Override
	public String getId()
	{
		return mySdk.getName();
	}

	@Nonnull
	@Override
	public String getName()
	{
		return mySdk.getName();
	}

	@Nonnull
	@Override
	public Image getIcon()
	{
		return SdkUtil.getIcon(mySdk);
	}

	@Nonnull
	@Override
	public Object resolveSdk(@Nonnull MSBuildImportTarget target, @Nonnull MSBuildRootExtension<?> rootExtension)
	{
		return mySdk;
	}
}
