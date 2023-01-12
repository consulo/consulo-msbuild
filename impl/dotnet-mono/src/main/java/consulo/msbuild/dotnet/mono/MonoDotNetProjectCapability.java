package consulo.msbuild.dotnet.mono;

import consulo.annotation.component.ExtensionImpl;
import consulo.content.bundle.Sdk;
import consulo.content.bundle.SdkTable;
import consulo.dotnet.module.extension.DotNetMutableModuleExtension;
import consulo.mono.dotnet.sdk.MonoSdkType;
import consulo.msbuild.MSBuildProcessProvider;
import consulo.msbuild.dotnet.mono.module.extension.MSBuildMonoDotNetMutableModuleExtension;
import consulo.msbuild.dotnet.impl.BaseDotNetProjectCapability;
import consulo.util.lang.StringUtil;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Map;

/**
 * @author VISTALL
 * @since 21/01/2021
 */
@ExtensionImpl
public class MonoDotNetProjectCapability extends BaseDotNetProjectCapability
{
	@Override
	public boolean isApplicable(@Nonnull MSBuildProcessProvider provider)
	{
		return provider instanceof MonoMSBuildProcessProvider;
	}

	@Nonnull
	@Override
	protected Class<? extends DotNetMutableModuleExtension> getMutableExtensionClass()
	{
		return MSBuildMonoDotNetMutableModuleExtension.class;
	}

	@Override
	protected void postInitialize(DotNetMutableModuleExtension<?> extension, Map<String, String> properties, MSBuildProcessProvider buildProcessProvider, Sdk msBuildSdk)
	{
		super.postInitialize(extension, properties, buildProcessProvider, msBuildSdk);

		String targetFrameworkVersion = properties.get("TargetFrameworkVersion");
		if(targetFrameworkVersion != null)
		{
			targetFrameworkVersion = StringUtil.trimStart(targetFrameworkVersion, "v");

			List<Sdk> sdksOfType = SdkTable.getInstance().getSdksOfType(MonoSdkType.getInstance());

			for(Sdk sdk : sdksOfType)
			{
				if(targetFrameworkVersion.equals(sdk.getVersionString()))
				{
					extension.getInheritableSdk().set(null, sdk);
					break;
				}
			}
		}
	}
}
