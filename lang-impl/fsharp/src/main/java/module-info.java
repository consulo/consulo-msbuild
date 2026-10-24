/**
 * MSBuild import of F# projects ({@code .fsproj}).
 *
 * @author VISTALL
 * @since 2026-10-25
 */
module consulo.msbuild.lang.impl.fsharp {
    requires transitive consulo.msbuild.impl.dotnet;
    requires transitive consulo.fsharp.api;

    exports consulo.msbuild.fsharp;
    exports consulo.msbuild.fsharp.module.extension;
}
