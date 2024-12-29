package consulo.msbuild.dotnet.microsoft;

import consulo.annotation.component.ExtensionImpl;
import consulo.content.bundle.Sdk;
import consulo.content.bundle.SdkTable;
import consulo.dotnet.module.extension.DotNetMutableModuleExtension;
import consulo.microsoft.dotnet.sdk.MicrosoftDotNetSdkType;
import consulo.msbuild.MSBuildProcessProvider;
import consulo.msbuild.dotnet.microsoft.module.extension.MSBuildMicrosoftDotNetMutableModuleExtension;
import consulo.msbuild.dotnet.impl.BaseDotNetProjectCapability;
import consulo.util.lang.StringUtil;

import jakarta.annotation.Nonnull;
import java.util.List;
import java.util.Map;

/**
 * @author VISTALL
 * @since 19/01/2021
 */
@ExtensionImpl
public class MicrosoftDotNetProjectCapability extends BaseDotNetProjectCapability
{
	@Override
	public boolean isApplicable(@Nonnull MSBuildProcessProvider provider)
	{
		return MSBuildProcessProvider.DEFAULT_ID.equals(provider.getId());
	}

	@Nonnull
	@Override
	protected Class<? extends DotNetMutableModuleExtension> getMutableExtensionClass()
	{
		return MSBuildMicrosoftDotNetMutableModuleExtension.class;
	}

	@Override
	protected void postInitialize(DotNetMutableModuleExtension<?> extension, Map<String, String> properties, MSBuildProcessProvider buildProcessProvider, Sdk msBuildSdk)
	{
		super.postInitialize(extension, properties, buildProcessProvider, msBuildSdk);

		String targetFrameworkVersion = properties.get("TargetFrameworkVersion");
		if(targetFrameworkVersion != null)
		{
			targetFrameworkVersion = StringUtil.trimStart(targetFrameworkVersion, "v");
			
			List<Sdk> sdksOfType = SdkTable.getInstance().getSdksOfType(MicrosoftDotNetSdkType.getInstance());

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
