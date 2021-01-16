package consulo.msbuild;

import com.intellij.openapi.application.Application;
import com.intellij.openapi.extensions.AbstractExtensionPointBean;
import com.intellij.util.xmlb.annotations.Attribute;
import consulo.extensions.StrictExtensionPointName;

/**
 * @author VISTALL
 * @since 16/01/2021
 */
public class MSBuildProjectFileEP extends AbstractExtensionPointBean
{
	public static final StrictExtensionPointName<Application, MSBuildProjectFileEP> EP_NAME = StrictExtensionPointName.forApplication("consulo.msbuild.projectFile");

	@Attribute("extension")
	public String extension;
}
