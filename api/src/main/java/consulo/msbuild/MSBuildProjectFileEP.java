package consulo.msbuild;

import com.intellij.openapi.application.Application;
import com.intellij.openapi.extensions.AbstractExtensionPointBean;
import com.intellij.util.xmlb.annotations.Attribute;
import consulo.extensions.StrictExtensionPointName;

import javax.annotation.Nonnull;
import java.util.HashSet;
import java.util.Set;

/**
 * @author VISTALL
 * @since 16/01/2021
 */
public class MSBuildProjectFileEP extends AbstractExtensionPointBean
{
	public static final StrictExtensionPointName<Application, MSBuildProjectFileEP> EP_NAME = StrictExtensionPointName.forApplication("consulo.msbuild.projectFile");

	@Attribute("extension")
	public String extension;

	@Nonnull
	public static Set<String> listAll()
	{
		Set<String> extensions = new HashSet<>();
		for(MSBuildProjectFileEP ep : EP_NAME.getExtensionList(Application.get()))
		{
			extensions.add(ep.extension);
		}
		return extensions;
	}
}
