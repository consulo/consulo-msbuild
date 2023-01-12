/**
 * @author VISTALL
 * @since 11/01/2023
 */
open module consulo.msbuild.api
{
	requires transitive consulo.ide.api;
	requires transitive com.intellij.xml;
	requires commons.jexl3;

	exports consulo.msbuild;
	exports consulo.msbuild.icon;
	exports consulo.msbuild.bundle;
	exports consulo.msbuild.compiler;
	exports consulo.msbuild.dom;
	exports consulo.msbuild.dom.annotation;
	exports consulo.msbuild.dom.expression.evaluate;
	exports consulo.msbuild.dom.expression.evaluate.impl;
	exports consulo.msbuild.dom.expression.evaluate.variable;
	exports consulo.msbuild.dom.expression.evaluate.variable.impl;
	exports consulo.msbuild.dom.walk;
	exports consulo.msbuild.importProvider;
	exports consulo.msbuild.module.extension;
	exports consulo.msbuild.solution.model;
	exports consulo.msbuild.solution.reader;

	//opens consulo.msbuild to consulo.util.xml.serializer;
}