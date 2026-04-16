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
	requires consulo.ide.api;
	requires consulo.language.editor.api;
	requires consulo.language.impl;
	requires consulo.compiler.api;
	requires consulo.file.editor.api;
	requires consulo.module.ui.api;
	requires consulo.project.ui.view.api;
	requires consulo.ui.ex.api;
	requires consulo.ui.ex.awt.api;
	requires consulo.build.ui.api;
	requires consulo.process.api;

	// TODO remove in future
	requires java.desktop;

	// opens to MSBuildStandardResourceProvider
	exports consulo.msbuild.impl to com.intellij.xml;
}