package consulo.msbuild;

import com.intellij.execution.configurations.GeneralCommandLine;
import com.intellij.openapi.application.Application;
import com.intellij.openapi.projectRoots.Sdk;
import consulo.extensions.StrictExtensionPointName;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.File;
import java.io.IOException;
import java.util.Locale;
import java.util.Map;
import java.util.function.Consumer;

/**
 * @author VISTALL
 * @since 01/01/2021
 */
public interface MSBuildProcessProvider
{
	StrictExtensionPointName<Application, MSBuildProcessProvider> EP_NAME = StrictExtensionPointName.forApplication("consulo.msbuild.processProvider");

	String DEFAULT_ID = "standalone";

	@Nonnull
	String getId();

	void fillBundles(@Nonnull Consumer<Sdk> consumer);

	@Nullable
	Sdk findBundle(@Nullable String bundleName);

	@Nonnull
	GeneralCommandLine buildCommandLine(@Nonnull Sdk sdk, @Nonnull File exeFile, int port) throws IOException;

	@Nonnull
	File getTargetFile();

	default void doAdditionalCopy(@Nonnull File targetFile, @Nonnull File msBuildRunnerDir, @Nonnull Sdk msBuildSdk) throws IOException
	{
	}

	default String[] getAdditionalCopyExtensions()
	{
		return new String[]{".pdb"};
	}

	default void fillGlobalProperties(@Nonnull Sdk msBuildSdk, @Nonnull Map<String, String> properties)
	{
	}

	default File getBinDir(@Nonnull Sdk sdk)
	{
		return new File(sdk.getHomePath(), "Bin");
	}

	int getVersion();

	@Nonnull
	String getSolutionModuleExtensionId();

	@Nonnull
	default String getLocaleForProcess()
	{
		return Locale.getDefault().toString();
	}

	@Nonnull
	default String[] getDependencyTargets()
	{
		return new String[]{
				"ResolveAssemblyReferencesDesignTime",
				"ResolveProjectReferencesDesignTime",
				"ResolvePackageDependenciesDesignTime"
		};
	}
}
