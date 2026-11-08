/*
 * Copyright 2013-2026 consulo.io
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package consulo.msbuild.dotnet.impl.library;

import consulo.annotation.access.RequiredReadAction;
import consulo.annotation.access.RequiredWriteAction;
import consulo.annotation.component.ExtensionImpl;
import consulo.dotnet.assembly.summary.DotNetAddableLibrary;
import consulo.dotnet.assembly.summary.DotNetAddableLibraryProvider;
import consulo.module.Module;
import consulo.module.content.ModuleRootManager;
import consulo.msbuild.MSBuildWorkspaceData;
import org.jspecify.annotations.Nullable;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

/**
 * The project model of an SDK-style MSBuild project (.NET, .NET Core): the libraries it may add are the NuGet packages
 * restored on this machine ({@code NuGetPackageRoot}, the global packages folder) that the project does not use, at their
 * newest version and in the {@code lib} folder of the target framework of the project. Adding one writes a
 * {@code <PackageReference>} into the project file and reimports the solution, so that MSBuild restores it.
 *
 * @author VISTALL
 */
@ExtensionImpl
public class MSBuildPackageAddableLibraryProvider implements DotNetAddableLibraryProvider {
    private static final Pattern NUSPEC_ID = Pattern.compile("<id>\\s*([^<\\s]+)\\s*</id>");

    private record PackageLibrary(String id, String version, Path file) implements DotNetAddableLibrary {
        @Override
        public String getName() {
            return id + " " + version;
        }

        @Override
        public Path getFile() {
            return file;
        }

        @RequiredWriteAction
        @Override
        public void addTo(Module module) {
            MSBuildPackageReferenceManager.addReference(module, id, version);
        }
    }

    @RequiredReadAction
    @Override
    public List<DotNetAddableLibrary> getAddableLibraries(Module module) {
        MSBuildWorkspaceData.ProjectInfo info = MSBuildPackageReferenceManager.findSdkProjectInfo(module);
        if (info == null) {
            return List.of();
        }
        String targetFramework = targetFramework(info);
        Path packageRoot = packageRoot(info);
        if (targetFramework == null || packageRoot == null || !Files.isDirectory(packageRoot)) {
            return List.of();
        }

        Set<String> used = usedPackages(module, packageRoot);
        List<DotNetAddableLibrary> libraries = new ArrayList<>();
        try (Stream<Path> packages = Files.list(packageRoot)) {
            for (Path packageDirectory : (Iterable<Path>) packages::iterator) {
                String directoryName = packageDirectory.getFileName().toString();
                if (used.contains(directoryName.toLowerCase(Locale.ROOT)) || !Files.isDirectory(packageDirectory)) {
                    continue;
                }
                addPackage(packageDirectory, targetFramework, libraries);
            }
        }
        catch (IOException e) {
            return List.of();
        }
        return libraries;
    }

    private static void addPackage(Path packageDirectory, String targetFramework, List<DotNetAddableLibrary> libraries) throws IOException {
        String version = null;
        try (Stream<Path> versions = Files.list(packageDirectory)) {
            for (Path versionDirectory : (Iterable<Path>) versions::iterator) {
                String name = versionDirectory.getFileName().toString();
                if (Files.isDirectory(versionDirectory.resolve("lib")) && (version == null || NuGetFrameworks.compareVersions(name, version) > 0)) {
                    version = name;
                }
            }
        }
        if (version == null) {
            return;
        }
        Path versionDirectory = packageDirectory.resolve(version);
        Path lib = versionDirectory.resolve("lib");
        List<String> frameworks = new ArrayList<>();
        try (Stream<Path> folders = Files.list(lib)) {
            for (Path folder : (Iterable<Path>) folders::iterator) {
                if (Files.isDirectory(folder)) {
                    frameworks.add(folder.getFileName().toString());
                }
            }
        }
        String framework = NuGetFrameworks.select(targetFramework, frameworks);
        if (framework == null) {
            return;
        }
        String id = packageId(versionDirectory, packageDirectory.getFileName().toString());
        try (Stream<Path> files = Files.list(lib.resolve(framework))) {
            for (Path file : (Iterable<Path>) files::iterator) {
                if (file.getFileName().toString().toLowerCase(Locale.ROOT).endsWith(".dll")) {
                    libraries.add(new PackageLibrary(id, version, file));
                }
            }
        }
    }

    /**
     * @return the id of the package as written ({@code Newtonsoft.Json}): the folders of the global packages folder are lower case
     */
    private static String packageId(Path versionDirectory, String folderName) {
        Path nuspec = versionDirectory.resolve(folderName + ".nuspec");
        try {
            if (Files.isRegularFile(nuspec)) {
                String text = Files.readString(nuspec);
                Matcher matcher = NUSPEC_ID.matcher(text);
                if (matcher.find()) {
                    return matcher.group(1);
                }
            }
        }
        catch (IOException | RuntimeException ignored) {
        }
        return folderName;
    }

    /**
     * @return the lower case ids of the packages the module uses already - directly or not: their assemblies are its libraries
     */
    @RequiredReadAction
    private static Set<String> usedPackages(Module module, Path packageRoot) {
        Set<String> used = new HashSet<>();
        String root = packageRoot.toAbsolutePath().normalize().toString().replace('\\', '/');
        for (String url : ModuleRootManager.getInstance(module).orderEntries().librariesOnly().classes().getUrls()) {
            String path = url.replace('\\', '/');
            int index = path.indexOf(root);
            if (index < 0) {
                continue;
            }
            String rest = path.substring(index + root.length());
            if (rest.startsWith("/")) {
                rest = rest.substring(1);
            }
            int slash = rest.indexOf('/');
            if (slash > 0) {
                used.add(rest.substring(0, slash).toLowerCase(Locale.ROOT));
            }
        }
        return used;
    }

    @Nullable
    private static String targetFramework(MSBuildWorkspaceData.ProjectInfo info) {
        String framework = info.properties.get("TargetFramework");
        if (framework != null && !framework.isBlank()) {
            return framework.trim();
        }
        String frameworks = info.properties.get("TargetFrameworks");
        if (frameworks != null && !frameworks.isBlank()) {
            return frameworks.split(";")[0].trim();
        }
        return null;
    }

    @Nullable
    private static Path packageRoot(MSBuildWorkspaceData.ProjectInfo info) {
        String root = info.properties.get("NuGetPackageRoot");
        if (root != null && !root.isBlank()) {
            return Path.of(root.trim());
        }
        String environment = System.getenv("NUGET_PACKAGES");
        if (environment != null && !environment.isBlank()) {
            return Path.of(environment);
        }
        return Path.of(System.getProperty("user.home"), ".nuget", "packages");
    }
}
