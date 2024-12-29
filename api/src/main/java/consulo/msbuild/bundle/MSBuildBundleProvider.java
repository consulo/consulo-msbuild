package consulo.msbuild.bundle;

import consulo.annotation.component.ExtensionImpl;
import consulo.content.bundle.PredefinedBundlesProvider;
import consulo.content.bundle.Sdk;
import consulo.content.bundle.SdkModificator;
import consulo.virtualFileSystem.LocalFileSystem;
import consulo.virtualFileSystem.VirtualFile;

import jakarta.annotation.Nonnull;
import java.util.List;

/**
 * @author VISTALL
 * @since 2018-02-08
 */
@ExtensionImpl
public class MSBuildBundleProvider extends PredefinedBundlesProvider
{
	@Override
	public void createBundles(@Nonnull Context context)
	{
		MSBuildBundleType type = MSBuildBundleType.getInstance();
		List<MSBuildBundleType.MSBuildInfo> msBuilds = type.findMSBuilds();

		for(MSBuildBundleType.MSBuildInfo buildInfo : msBuilds)
		{
			String path = buildInfo.getPath().getPath();

			if(type.isValidSdkHome(path))
			{
				VirtualFile dirPath = LocalFileSystem.getInstance().findFileByPath(path);
				if(dirPath == null)
				{
					continue;
				}

				Sdk sdk = context.createSdkWithName(type, buildInfo.buildName());

				SdkModificator modificator = sdk.getSdkModificator();
				modificator.setHomePath(path);
				modificator.setVersionString(type.getVersionString(path));
				modificator.commitChanges();
			}
		}
	}
}
