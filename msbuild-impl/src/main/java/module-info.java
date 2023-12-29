/**
 * @author VISTALL
 * @since 11/01/2023
 */
module consulo.msbuild
{
	requires consulo.msbuild.api;
	requires consulo.msbuild.daemon.impl;

	// TODO remove in future
	requires consulo.ide.impl;

	// TODO remove in future
	requires java.desktop;

	// opens to MSBuildStandardResourceProvider
	exports consulo.msbuild.impl to com.intellij.xml;
}