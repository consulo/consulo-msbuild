/**
 * @author VISTALL
 * @since 11/01/2023
 */
module consulo.msbuild.daemon.impl
{
	requires consulo.msbuild.api;
	requires consulo.build.ui.api;
	requires consulo.ui.ex.api;
	requires consulo.execution.api;
	requires consulo.process.api;

	// TODO remove in future
	requires java.desktop;

	exports consulo.msbuild.daemon.impl;
	exports consulo.msbuild.daemon.impl.logging;
	exports consulo.msbuild.daemon.impl.message;
	exports consulo.msbuild.daemon.impl.message.model;
	exports consulo.msbuild.daemon.impl.network;
	exports consulo.msbuild.daemon.impl.step;
	exports consulo.msbuild.toolWindow;
	exports consulo.msbuild.toolWindow.actions;
	exports consulo.msbuild.toolWindow.dataRules;
	exports consulo.msbuild.toolWindow.nodes;
}