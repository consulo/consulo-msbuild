package consulo.msbuild;

import com.intellij.execution.configurations.GeneralCommandLine;
import com.intellij.openapi.projectRoots.Sdk;
import com.intellij.openapi.projectRoots.SdkTable;
import com.intellij.openapi.util.io.FileUtil;
import consulo.container.plugin.PluginManager;
import consulo.msbuild.bundle.MSBuildBundleType;
import consulo.msbuild.daemon.impl.MSBuildDaemonService;
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
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

/**
 * @author VISTALL
 * @since 01/01/2021
 */
public class DefaultMSBuildProcessProvider implements MSBuildProcessProvider
{
	private final SdkTable mySdkTable;

	@Inject
	public DefaultMSBuildProcessProvider(SdkTable sdkTable)
	{
		mySdkTable = sdkTable;
	}

	@Override
	public String getId()
	{
		return DEFAULT_ID;
	}

	@Override
	public void fillBundles(@Nonnull Consumer<Sdk> consumer)
	{
		List<Sdk> bundles = mySdkTable.getSdksOfType(MSBuildBundleType.getInstance());
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
	public GeneralCommandLine buildCommandLine(@Nonnull Sdk sdk, @Nonnull File targetFile, int port)
	{
		GeneralCommandLine c = new GeneralCommandLine();
		c.setExePath(targetFile.getPath());
		c.addParameter(String.valueOf(port));
		c.addParameter("true"); // debug

		//c.withEnvironment("MSBUILD_EXE_NAME", "MSBuildTaskHost.exe");
		//c.withEnvironment("MSBUILDTASKHOST_EXE_NAME", "MSBuildTaskHost.exe");
		c.withEnvironment("MSBUILD_EXE_PATH", targetFile.getAbsolutePath());

		c.withEnvironment("MSBUILDTASKHOSTLOCATION", sdk.getHomePath());
		c.withEnvironment("MSBUILDTASKHOSTLOCATION64", sdk.getHomePath());
		return c;
	}

	@Nonnull
	@Override
	public File getTargetFile()
	{
		File net4Dir = new File(PluginManager.getPluginPath(MSBuildDaemonService.class), "net4");

		return new File(net4Dir, "Consulo.MSBuildBuilder.exe");
	}

	@Override
	public void doAdditionalCopy(@Nonnull File targetFile, @Nonnull File msBuildRunnerDir, @Nonnull Sdk msBuildSdk) throws IOException
	{
		File msBuildBinDir = new File(msBuildSdk.getHomePath(), "Bin");

		File configFile = new File(msBuildBinDir, "MSBuild.exe.config");
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

			if(Platform.current().os().isWindows())
			{
				File extensionsPath = FileUtil.getParentFile(FileUtil.getParentFile(msBuildBinDir));
				File vsInstallRoot = FileUtil.getParentFile(extensionsPath);
				File devEnvDir = new File(vsInstallRoot, "Common7/IDE");

				SetMSBuildConfigProperty(toolset, "MSBuildExtensionsPath", extensionsPath.getAbsolutePath());
				SetMSBuildConfigProperty(toolset, "MSBuildExtensionsPath32", extensionsPath.getAbsolutePath());
				SetMSBuildConfigProperty(toolset, "MSBuildToolsPath", msBuildBinDir.getAbsolutePath());
				SetMSBuildConfigProperty(toolset, "MSBuildToolsPath32", msBuildBinDir.getAbsolutePath());
				SetMSBuildConfigProperty(toolset, "MSBuildToolsPath64", msBuildBinDir.getAbsolutePath());
				SetMSBuildConfigProperty(toolset, "VsInstallRoot", vsInstallRoot.getAbsolutePath());
				SetMSBuildConfigProperty(toolset, "DevEnvDir", devEnvDir + "\\");
				SetMSBuildConfigProperty(toolset, "NuGetRestoreTargets", new File(devEnvDir, "CommonExtensions\\Microsoft\\NuGet\\NuGet.targets").getAbsolutePath());

				File sdksPath = new File(msBuildBinDir, "Sdks");
				SetMSBuildConfigProperty(toolset, "MSBuildSDKsPath", sdksPath.getAbsolutePath());

				File roslynTargetsPath = new File(msBuildBinDir, "Roslyn");
				SetMSBuildConfigProperty(toolset, "RoslynTargetsPath", roslynTargetsPath.getAbsolutePath());

				File vcTargetsPath = new File(devEnvDir, "VC/VCTargets");
				SetMSBuildConfigProperty(toolset, "VCTargetsPath", vcTargetsPath.getAbsolutePath());

			}
			//			else
			//			{
			//				var path = MSBuildProjectService.GetProjectImportSearchPaths(runtime, false).FirstOrDefault(p = > p.Property == "MSBuildSDKsPath");
			//				if(path != null)
			//					SetMSBuildConfigProperty(toolset, path.Property, path.Path);
			//			}
			//
			//			var projectImportSearchPaths = toolset.Element("projectImportSearchPaths");
			//			if(projectImportSearchPaths != null)
			//			{
			//				var os = Platform.IsMac ? "osx" : Platform.IsWindows ? "windows" : "unix";
			//				XElement searchPaths = projectImportSearchPaths.Elements("searchPaths").FirstOrDefault(sp = > sp.Attribute("os") ?.Value == os);
			//				if(searchPaths == null)
			//				{
			//					searchPaths = new XElement("searchPaths");
			//					searchPaths.SetAttributeValue("os", os);
			//					projectImportSearchPaths.Add(searchPaths);
			//				}
			//				foreach(var path in MSBuildProjectService.GetProjectImportSearchPaths(runtime, false))
			//				SetMSBuildConfigProperty(searchPaths, path.Property, path.Path, append:true, insertBefore:false);
			//			}

			File targetConfigFile = new File(msBuildRunnerDir, targetFile.getName() + ".config");

			JDOMUtil.writeDocument(new Document(rootElement), targetConfigFile, "\n");
		}
	}

	@Override
	public void fillGlobalProperties(@Nonnull Sdk msBuildSdk, @Nonnull Map<String, String> properties)
	{
		// When running the dev15 MSBuild from commandline or inside MSBuild, it sets "VSToolsPath" correctly. when running from MD, it falls back to a bad default. override it.
		if(Platform.current().os().isWindows())
		{
			File binDir = getBinDir(msBuildSdk);

			String toolsVersion = "16.0";
			properties.put("VSToolsPath", Paths.get(binDir.getAbsolutePath(), "..", "..", "Microsoft", "VisualStudio", "v" + toolsVersion).toFile().getAbsolutePath());
		}
	}

	@Override
	public int getVersion()
	{
		return 2;
	}

	@Nonnull
	@Override
	public String getSolutionModuleExtensionId()
	{
		return "msbuild-default";
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
}
