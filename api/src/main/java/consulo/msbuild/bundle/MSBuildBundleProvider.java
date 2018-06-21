package consulo.msbuild.bundle;

import java.util.List;

import javax.annotation.Nonnull;

import com.intellij.openapi.projectRoots.Sdk;
import com.intellij.openapi.projectRoots.SdkModificator;
import com.intellij.openapi.vfs.LocalFileSystem;
import com.intellij.openapi.vfs.VirtualFile;
import consulo.bundle.PredefinedBundlesProvider;

/**
 * @author VISTALL
 * @since 2018-02-08
 */
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
