/**
 * @author VISTALL
 * @since 11/01/2023
 */
module consulo.msbuild.impl.dotnet.mono
{
	requires consulo.msbuild.impl.dotnet;
	requires consulo.msbuild.lang.impl.csharp;
	requires consulo.dotnet.psi.impl;
	requires consulo.dotnet.mono;
	requires consulo.dotnet.debugger.impl;
	requires consulo.dotnet.mono.debugger.impl;
	requires consulo.internal.dotnet.asm;

	// TODO remove in future
	requires java.desktop;
}