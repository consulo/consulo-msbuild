package consulo.msbuild.dotnet.core;

import com.intellij.execution.configurations.GeneralCommandLine;
import com.intellij.openapi.projectRoots.Sdk;
import com.intellij.openapi.projectRoots.SdkTable;
import com.intellij.openapi.util.io.FileUtil;
import consulo.container.plugin.PluginManager;
import consulo.dotnet.core.bundle.DotNetCoreBundleType;
import consulo.msbuild.MSBuildProcessProvider;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

/**
 * @author VISTALL
 * @since 10/01/2021
 */
public class DotNetCoreMSBuildProcessProvider implements MSBuildProcessProvider
{
	private static final String ourRuntimeJsonSuffix = ".runtimeconfig.json";
	private static final String ourDepsJsonSuffix = ".deps.json";

	@Override
	public File getBinDir(@Nonnull Sdk sdk)
	{
		return new File(sdk.getHomePath());
	}

	@Override
	public String getId()
	{
		return "dotnet-core";
	}

	@Override
	public void fillBundles(@Nonnull Consumer<Sdk> consumer)
	{
		List<Sdk> sdksOfType = SdkTable.getInstance().getSdksOfType(DotNetCoreBundleType.getInstance());
		for(Sdk sdk : sdksOfType)
		{
			consumer.accept(sdk);
		}
	}

	@Nullable
	@Override
	public Sdk findBundle(@Nullable String bundleName)
	{
		return SdkTable.getInstance().findSdk(bundleName);
	}

	@Nonnull
	@Override
	public GeneralCommandLine buildCommandLine(@Nonnull Sdk msBuildSdk, @Nonnull File targetFile, int port)
	{
		File executable = DotNetCoreBundleType.getExecutablePath(msBuildSdk.getHomePath());

		GeneralCommandLine c = new GeneralCommandLine();
		c.setExePath(executable.getPath());
		c.setWorkDirectory(msBuildSdk.getHomePath());
		c.addParameter(targetFile.getAbsolutePath());
		c.addParameter(String.valueOf(port));
		c.addParameter("true"); // debug

		// set exe path to original msbuild dir
		c.withEnvironment("MSBUILD_EXE_PATH", new File(msBuildSdk.getHomePath(), "MSBuild.dll").getAbsolutePath());
		//c.withEnvironment("MSBuildSDKsPath", new File(msBuildSdk.getHomePath(), "Sdks").getAbsolutePath());
		return c;
	}

	@Nonnull
	@Override
	public File getTargetFile()
	{
		File net4Dir = new File(PluginManager.getPluginPath(DotNetCoreMSBuildProcessProvider.class), "netcoreapp3.1");

		return new File(net4Dir, "Consulo.MSBuildBuilder.dll");
	}

	@Override
	public void fillGlobalProperties(@Nonnull Sdk msBuildSdk, @Nonnull Map<String, String> properties)
	{
		properties.put("MSBuildExtensionsPath", msBuildSdk.getHomePath());
		properties.put("MSBuildToolsPath32", msBuildSdk.getHomePath());
		properties.put("MSBuildToolsPath64", msBuildSdk.getHomePath());
		properties.put("NuGetRestoreTargets", msBuildSdk.getHomePath() + "/NuGet.targets");
	}

	@Override
	public void doAdditionalCopy(@Nonnull File targetFile, @Nonnull File msBuildRunnerDir, @Nonnull Sdk msBuildSdk) throws IOException
	{
		String nameWithoutExtension = FileUtil.getNameWithoutExtension(targetFile);

		File msBuildRuntimeJson = new File(msBuildSdk.getHomePath(), "MSBuild" + ourRuntimeJsonSuffix);
		if(msBuildRuntimeJson.exists())
		{
			FileUtil.copy(msBuildRuntimeJson, new File(msBuildRunnerDir, nameWithoutExtension + ourRuntimeJsonSuffix));
		}

//		String depsFileName = "MSBuild" + ourDepsJsonSuffix;
//		File msBuildDepsJson = new File(msBuildSdk.getHomePath(), depsFileName);
//		if(msBuildDepsJson.exists())
//		{
//			String jsonText = FileUtil.loadFile(msBuildDepsJson);
//
//			String targetFileName = nameWithoutExtension + ourDepsJsonSuffix;
//
//			jsonText = jsonText.replace(depsFileName, targetFileName);
//
//			Gson gson = new GsonBuilder().setPrettyPrinting().create();
//			Map map = gson.fromJson(jsonText, Map.class);
//
//			Map<String, Map> libraries = (Map) map.get("libraries");
//
//			Map<String, Object> libraryInfoProject = new LinkedHashMap<>();
//			libraryInfoProject.put("type", "project");
//			libraryInfoProject.put("serviceable", false);
//			libraryInfoProject.put("sha512", "");
//
//			Map<String, Map> newLibraries = new LinkedHashMap<>();
//			map.put("libraries", newLibraries);
//
//			for(String libraryName : libraries.keySet())
//			{
//				newLibraries.put(libraryName, libraryInfoProject);
//			}
//
//			String newJson = gson.toJson(map);
//			FileUtil.writeToFile(new File(msBuildRunnerDir, targetFileName), newJson);
//		}
	}

	@Override
	public int getVersion()
	{
		return 4;
	}
}
