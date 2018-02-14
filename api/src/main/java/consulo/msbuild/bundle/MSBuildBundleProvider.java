package consulo.msbuild.bundle;

import java.util.List;

import org.jetbrains.annotations.NotNull;
import com.intellij.openapi.projectRoots.impl.SdkImpl;
import com.intellij.openapi.vfs.LocalFileSystem;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.util.Consumer;
import consulo.bundle.PredefinedBundlesProvider;

/**
 * @author VISTALL
 * @since 2018-02-08
 */
public class MSBuildBundleProvider extends PredefinedBundlesProvider
{
	@Override
	public void createBundles(@NotNull Consumer<SdkImpl> consumer)
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

				SdkImpl sdk = createSdkWithName(type, buildInfo.buildName());
				sdk.setHomePath(path);
				sdk.setVersionString(type.getVersionString(sdk));

				consumer.consume(sdk);
			}
		}
	}
}
