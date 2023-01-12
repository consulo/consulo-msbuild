package consulo.msbuild.dotnet.mono.bundle;

import consulo.annotation.component.ExtensionImpl;
import consulo.content.bundle.SdkType;
import consulo.dotnet.module.extension.AssemblyInfoCacheService;
import consulo.internal.dotnet.asm.mbel.AssemblyInfo;
import consulo.mono.dotnet.sdk.MonoSdkType;
import consulo.ui.image.Image;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * @author VISTALL
 * @since 21/01/2021
 */
@ExtensionImpl
public class MonoMSBuildBundleType extends SdkType
{
	@Nonnull
	public static MonoMSBuildBundleType getInstance()
	{
		return EP_NAME.findExtensionOrFail(MonoMSBuildBundleType.class);
	}

	public static File getMSBuildDll(String path)
	{
		return new File(path, "bin/MSBuild.dll");
	}

	public MonoMSBuildBundleType()
	{
		super("MONO_MSBUILD_BUNDLE");
	}

	@Nonnull
	@Override
	public Collection<String> suggestHomePaths()
	{
		Collection<String> paths = MonoSdkType.getInstance().suggestHomePaths();

		Set<String> msBuildPath = new LinkedHashSet<>();
		for(String monoPath : paths)
		{
			File mono = new File(monoPath, "../msbuild/");

			try
			{
				String canonicalPath = mono.getCanonicalPath();

				msBuildPath.add(canonicalPath);
			}
			catch(IOException ignored)
			{
			}
		}

		Set<String> msBuildPaths = new LinkedHashSet<>();
		for(String p : msBuildPath)
		{
			// dir msbuild inside mono, it's holder
			File msRoot = new File(p);
			File[] files = msRoot.listFiles();
			if(files != null)
			{
				for (File file : files)
				{
					msBuildPaths.add(file.getAbsolutePath());
				}
			}
		}
		return msBuildPaths;
	}

	@Override
	public boolean canCreatePredefinedSdks()
	{
		return true;
	}

	@Override
	public boolean isValidSdkHome(String path)
	{
		return getMSBuildDll(path).exists();
	}

	@Nullable
	@Override
	public String getVersionString(String sdkHome)
	{
		AssemblyInfo assemblyInfo = AssemblyInfoCacheService.getInstance().getAssemblyInfo(getMSBuildDll(sdkHome));
		if(assemblyInfo == null)
		{
			return null;
		}
		return assemblyInfo.getMajorVersion() + "." + assemblyInfo.getMinorVersion();
	}

	@Override
	public String suggestSdkName(String currentSdkName, String sdkHome)
	{
		return getPresentableName() + " " + getVersionString(sdkHome);
	}

	@Nonnull
	@Override
	public String getPresentableName()
	{
		return "Mono MSBuild";
	}

	@Nullable
	@Override
	public Image getGroupIcon()
	{
		return MonoSdkType.getInstance().getGroupIcon();
	}

	@Nullable
	@Override
	public Image getIcon()
	{
		return MonoSdkType.getInstance().getIcon();
	}
}
