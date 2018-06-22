package consulo.msbuild.dotnet.microsoft;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;

import com.intellij.openapi.projectRoots.Sdk;
import com.intellij.openapi.projectRoots.SdkTable;
import com.intellij.openapi.projectRoots.SdkType;
import consulo.microsoft.dotnet.sdk.MicrosoftDotNetSdkType;
import consulo.msbuild.bundle.MSBuildBundleType;
import consulo.msbuild.importProvider.item.MSBuildDotNetImportTarget;
import consulo.msbuild.module.extension.resolve.AutomaticBundleInfo;
import consulo.msbuild.module.extension.resolve.DefaultBundleInfo;
import consulo.msbuild.module.extension.resolve.MSBuildBundleInfo;

/**
 * @author VISTALL
 * @since 2018-06-22
 */
public class MicrosoftDotNetImportTarget extends MSBuildDotNetImportTarget
{
	public MicrosoftDotNetImportTarget()
	{
		super(".NET", "microsoft-dotnet");
	}

	@Nonnull
	@Override
	public List<MSBuildBundleInfo> getBundleInfoList()
	{
		List<MSBuildBundleInfo> list = new ArrayList<>();
		list.add(AutomaticBundleInfo.INSTANCE);
		for(Sdk sdk : SdkTable.getInstance().getSdksOfType(MSBuildBundleType.getInstance()))
		{
			list.add(new DefaultBundleInfo(sdk));
		}
		return list;
	}

	@Nonnull
	@Override
	public SdkType getSdkType()
	{
		return MicrosoftDotNetSdkType.getInstance();
	}
}
