package consulo.msbuild.dotnet.mono;

import com.intellij.execution.configurations.GeneralCommandLine;
import com.intellij.openapi.projectRoots.Sdk;
import com.intellij.openapi.projectRoots.SdkTable;
import com.intellij.openapi.util.io.FileUtil;
import consulo.container.plugin.PluginManager;
import consulo.msbuild.MSBuildProcessProvider;
import consulo.msbuild.bundle.MSBuildBundleType;
import consulo.msbuild.dotnet.mono.bundle.MonoMSBuildBundleType;
import consulo.platform.Platform;
import consulo.util.jdom.JDOMUtil;
import jakarta.inject.Inject;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.function.Consumer;

/**
 * @author VISTALL
 * @since 21/01/2021
 */
public class MonoMSBuildProcessProvider implements MSBuildProcessProvider
{
	private final SdkTable mySdkTable;

	@Inject
	public MonoMSBuildProcessProvider(SdkTable sdkTable)
	{
		mySdkTable = sdkTable;
	}

	@Nonnull
	@Override
	public String getId()
	{
		return "mono";
	}

	@Override
	public void fillBundles(@Nonnull Consumer<Sdk> consumer)
	{
		List<Sdk> bundles = mySdkTable.getSdksOfType(MonoMSBuildBundleType.getInstance());
		for(Sdk bundle : bundles)
		{
			consumer.accept(bundle);
		}
	}

	@Nullable
	@Override
	public Sdk findBundle(@Nullable String bundleName)
	{
		if(bundleName != null)
		{
			return mySdkTable.findSdk(bundleName);
		}
		else
		{
			//TODO [VISTALL] auto select
			for(Sdk sdk : mySdkTable.getSdksOfType(MSBuildBundleType.getInstance()))
			{
				return sdk;
			}
		}

		return null;
	}

	@Nonnull
	@Override
	public GeneralCommandLine buildCommandLine(@Nonnull Sdk sdk, @Nonnull File exeFile, int port) throws IOException
	{
		File monoBinRoot = new File(sdk.getHomePath(), "../../../../bin");

		String monoRootBinPath = monoBinRoot.getCanonicalPath();

		GeneralCommandLine commandLine = new GeneralCommandLine();
		commandLine.setExePath(new File(monoRootBinPath, Platform.current().os().isWindows() ? "mono.exe" : "mono").getAbsolutePath());
		commandLine.addParameter(exeFile.getCanonicalPath());
		commandLine.addParameter(String.valueOf(port));
		commandLine.addParameter("true");
		return commandLine;
	}

	@Override
	public void doAdditionalCopy(@Nonnull File targetFile, @Nonnull File msBuildRunnerDir, @Nonnull Sdk msBuildSdk) throws IOException
	{
		File msBuildBinDir = new File(msBuildSdk.getHomePath(), "Bin");

		File configFile = new File(msBuildBinDir, "MSBuild.dll.config");
		if(configFile.exists())
		{
			Element rootElement = null;
			try
			{
				rootElement = JDOMUtil.load(configFile);
			}
			catch(JDOMException e)
			{
				throw new IOException(e);
			}

			Element msbuildToolsets = rootElement.getChild("msbuildToolsets");

			Element toolset = msbuildToolsets.getChild("toolset");

			// This is required for MSBuild to properly load the searchPaths element (@radical knows why)
			SetMSBuildConfigProperty(toolset, "MSBuildBinPath", msBuildBinDir.getAbsolutePath(), false, true);

			// this must match MSBuildBinPath w/MSBuild15
			SetMSBuildConfigProperty(toolset, "MSBuildToolsPath", msBuildBinDir.getAbsolutePath(), false, true);

			File extensionsPath = FileUtil.getParentFile(FileUtil.getParentFile(msBuildBinDir));
			File vsInstallRoot = FileUtil.getParentFile(extensionsPath);

			SetMSBuildConfigProperty(toolset, "MSBuildExtensionsPath", extensionsPath.getAbsolutePath());
			SetMSBuildConfigProperty(toolset, "MSBuildExtensionsPath32", extensionsPath.getAbsolutePath());
			SetMSBuildConfigProperty(toolset, "MSBuildToolsPath", msBuildBinDir.getAbsolutePath());
			SetMSBuildConfigProperty(toolset, "MSBuildToolsPath32", msBuildBinDir.getAbsolutePath());
			SetMSBuildConfigProperty(toolset, "MSBuildToolsPath64", msBuildBinDir.getAbsolutePath());
			SetMSBuildConfigProperty(toolset, "VsInstallRoot", vsInstallRoot.getAbsolutePath());

			File sdksPath = new File(msBuildBinDir, "Sdks");
			SetMSBuildConfigProperty(toolset, "MSBuildSDKsPath", sdksPath.getAbsolutePath());

			File roslynTargetsPath = new File(msBuildBinDir, "Roslyn");
			SetMSBuildConfigProperty(toolset, "RoslynTargetsPath", roslynTargetsPath.getAbsolutePath());

			File targetConfigFile = new File(msBuildRunnerDir, targetFile.getName() + ".config");

			JDOMUtil.writeDocument(new Document(rootElement), targetConfigFile, "\n");
		}
	}

	private static void SetMSBuildConfigProperty(Element toolset, String name, String value)
	{
		SetMSBuildConfigProperty(toolset, name, value, true, false);
	}

	private static void SetMSBuildConfigProperty(Element toolset, String name, String value, boolean append, boolean insertBefore)
	{
		List<Element> properties = toolset.getChildren("property");

		for(Element property : properties)
		{
			String propName = property.getAttributeValue("name");

			if(name.equals(propName))
			{
				property.setAttribute("value", value);
				return;
			}
		}

		Element element = new Element("property").setAttribute("name", name).setAttribute("value", value);
		if(insertBefore)
		{
			toolset.addContent(0, element);
		}
		else
		{
			toolset.addContent(element);
		}
	}

	@Nonnull
	@Override
	public File getTargetFile()
	{
		File net4Dir = new File(PluginManager.getPluginPath(MonoMSBuildProcessProvider.class), "mono");

		return new File(net4Dir, "Consulo.MSBuildBuilder.exe");
	}

	@Override
	public String[] getAdditionalCopyExtensions()
	{
		return new String[]{".exe.mdb"};
	}

	@Override
	public int getVersion()
	{
		return 4;
	}

	@Nonnull
	@Override
	public String getSolutionModuleExtensionId()
	{
		return "msbuild-mono";
	}

	@Nonnull
	@Override
	public String getLocaleForProcess()
	{
		return "en";
	}

	@Override
	@Nonnull
	public String[] getDependencyTargets()
	{
		return new String[]{
				"ResolveAssemblyReferencesDesignTime",
				"ResolveProjectReferencesDesignTime",
				// nuget refs not supported "ResolvePackageDependenciesDesignTime"
		};
	}
}
