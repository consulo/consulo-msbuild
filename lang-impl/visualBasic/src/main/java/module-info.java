/**
 * MSBuild import of Visual Basic projects ({@code .vbproj}).
 *
 * @author VISTALL
 * @since 2026-10-25
 */
module consulo.msbuild.lang.impl.visualBasic {
    requires transitive consulo.msbuild.impl.dotnet;
    requires transitive consulo.visualBasic.dotnet.api;

    exports consulo.msbuild.visualBasic;
    exports consulo.msbuild.visualBasic.module.extension;
}
