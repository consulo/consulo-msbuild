/**
 * @author VISTALL
 * @since 11/01/2023
 */
module consulo.msbuild.lang.impl.csharp
{
	requires transitive consulo.msbuild.impl.dotnet;
	requires transitive consulo.csharp.api;

	requires com.intellij.xml;
	requires consulo.csharp.base.impl;

	exports consulo.msbuild.csharp;
	exports consulo.msbuild.csharp.module.extension;
}