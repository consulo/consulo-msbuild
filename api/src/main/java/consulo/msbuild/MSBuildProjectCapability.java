package consulo.msbuild;

import com.intellij.openapi.application.Application;
import consulo.extensions.StrictExtensionPointName;

import javax.annotation.Nonnull;

/**
 * @author VISTALL
 * @since 16/01/2021
 */
public interface MSBuildProjectCapability
{
	StrictExtensionPointName<Application, MSBuildProjectCapability> EP_NAME = StrictExtensionPointName.forApplication("consulo.msbuild.projectCapability");

	@Nonnull
	String getId();
}
