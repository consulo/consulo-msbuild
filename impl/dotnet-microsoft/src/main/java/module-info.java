/**
 * @author VISTALL
 * @since 11/01/2023
 */
module consulo.msbuild.impl.dotnet.microsoft
{
	requires consulo.msbuild.impl.dotnet;
	requires consulo.msbuild.lang.impl.csharp;
	requires consulo.dotnet.psi.impl;
	requires consulo.dotnet.microsoft;
	requires consulo.dotnet.debugger.impl;
	requires consulo.dotnet.microsoft.debugger.impl;

	// TODO remove in future
	requires java.desktop;
}