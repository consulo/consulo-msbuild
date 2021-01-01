package consulo.msbuild;

import com.intellij.execution.configurations.GeneralCommandLine;
import com.intellij.openapi.application.Application;
import com.intellij.openapi.projectRoots.Sdk;
import consulo.extensions.StrictExtensionPointName;

import javax.annotation.Nonnull;
import java.util.function.Consumer;

/**
 * @author VISTALL
 * @since 01/01/2021
 */
public interface MSBuildProcessProvider
{
	StrictExtensionPointName<Application, MSBuildProcessProvider> EP_NAME = StrictExtensionPointName.forApplication("consulo.msbuild.processProvider");

	String getId();

	void fillBundles(Consumer<Sdk> consumer);

	@Nonnull
	GeneralCommandLine buildCommandLine(@Nonnull Sdk sdk, int port);
}
