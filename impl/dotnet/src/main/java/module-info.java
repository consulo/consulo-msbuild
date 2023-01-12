/**
 * @author VISTALL
 * @since 11/01/2023
 */
module consulo.msbuild.impl.dotnet
{
	requires transitive consulo.msbuild.api;
	requires transitive consulo.dotnet.api;
	requires consulo.dotnet.debugger.impl;

	exports consulo.msbuild.dotnet.impl;
	exports consulo.msbuild.dotnet.impl.module.extension;
}