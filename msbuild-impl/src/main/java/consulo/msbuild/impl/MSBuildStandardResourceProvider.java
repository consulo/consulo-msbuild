package consulo.msbuild.impl;

import consulo.annotation.component.ExtensionImpl;
import consulo.xml.javaee.ResourceRegistrar;
import consulo.xml.javaee.StandardResourceProvider;

/**
 * @author VISTALL
 * @since 11/01/2023
 */
@ExtensionImpl
public class MSBuildStandardResourceProvider implements StandardResourceProvider
{
	@Override
	public void registerResources(ResourceRegistrar registrar)
	{
		registrar.addStdResource("http://schemas.microsoft.com/developer/msbuild/2003", "/standardSchemes/Microsoft.Build.xsd");
	}
}
