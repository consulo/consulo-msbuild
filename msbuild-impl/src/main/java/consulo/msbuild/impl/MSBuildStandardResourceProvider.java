package consulo.msbuild.impl;

import consulo.annotation.component.ExtensionImpl;
import consulo.xml.standardResource.ResourceRegistrar;
import consulo.xml.standardResource.StandardResourceProvider;

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
		registrar.addStdResource("http://schemas.microsoft.com/developer/msbuild/2003", "/consulo/msbuild/impl/Microsoft.Build.xsd", MSBuildStandardResourceProvider.class);
	}
}
