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
import consulo.application.Application;
import consulo.document.Document;
import consulo.document.FileDocumentManager;
import consulo.dotnet.assembly.summary.DotNetAddableLibrary;
import consulo.dotnet.assembly.summary.DotNetAddableLibraryProvider;
import consulo.language.psi.PsiDocumentManager;
import consulo.language.psi.PsiFile;
import consulo.language.psi.PsiManager;
import consulo.language.util.ModuleUtilCore;
import consulo.module.Module;
import consulo.module.content.ModuleRootManager;
import consulo.msbuild.MSBuildProjectImporter;
import consulo.msbuild.MSBuildWorkspaceData;
import consulo.msbuild.dom.ItemGroup;
import consulo.msbuild.dom.PackageReference;
import consulo.msbuild.dom.Project;
import consulo.msbuild.dotnet.impl.module.extension.MSBuildBaseDotNetModuleExtension;
import consulo.virtualFileSystem.LocalFileSystem;
import consulo.virtualFileSystem.VirtualFile;
import consulo.xml.dom.DomFileElement;
import consulo.xml.dom.DomManager;
import consulo.xml.language.psi.XmlFile;
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

    private record PackageLibrary(String id, String version, Path file, Path projectFile) implements DotNetAddableLibrary {
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
            consulo.project.Project project = module.getProject();
            VirtualFile virtualFile = LocalFileSystem.getInstance().refreshAndFindFileByNioFile(projectFile);
            PsiFile psiFile = virtualFile == null ? null : PsiManager.getInstance(project).findFile(virtualFile);
            if (!(psiFile instanceof XmlFile xmlFile)) {
                return;
            }
            DomFileElement<Project> fileElement = DomManager.getDomManager(project).getFileElement(xmlFile, Project.class);
            if (fileElement == null) {
                return;
            }
            Project root = fileElement.getRootElement();
            ItemGroup target = null;
            for (ItemGroup group : root.getItemGroups()) {
                for (PackageReference reference : group.getPackageReferences()) {
                    if (id.equalsIgnoreCase(reference.getInclude().getStringValue())) {
                        // already there - only the restore is missing
                        reimport(project, virtualFile);
                        return;
                    }
                }
                if (target == null && !group.getPackageReferences().isEmpty()) {
                    target = group;
                }
            }
            if (target == null) {
                target = root.addItemGroup();
            }
            PackageReference reference = target.addPackageReference();
            reference.getInclude().setStringValue(id);
            reference.getVersion().setStringValue(version);
            reimport(project, virtualFile);
        }

        private static void reimport(consulo.project.Project project, VirtualFile projectFile) {
            // the import reads the project file from the disk - after the write command that changed it, and after its save
            Application.get().invokeLater(() -> {
                PsiDocumentManager.getInstance(project).commitAllDocuments();
                FileDocumentManager documentManager = FileDocumentManager.getInstance();
                Document document = documentManager.getCachedDocument(projectFile);
                if (document != null) {
                    documentManager.saveDocument(document);
                }
                MSBuildProjectImporter.getInstance(project).reimport();
            }, project.getDisposed());
        }
    }

    @RequiredReadAction
    @Override
    public List<DotNetAddableLibrary> getAddableLibraries(Module module) {
        if (ModuleUtilCore.getExtension(module, MSBuildBaseDotNetModuleExtension.class) == null) {
            return List.of();
        }
        MSBuildWorkspaceData.ProjectInfo info = findInfo(module);
        if (info == null || !"true".equalsIgnoreCase(info.properties.get("UsingMicrosoftNETSdk"))) {
            // an old-style project references the assemblies of a .NET Framework, not packages - not supported yet
            return List.of();
        }
        String projectFile = info.properties.get("MSBuildProjectFullPath");
        String targetFramework = targetFramework(info);
        Path packageRoot = packageRoot(info);
        if (projectFile == null || targetFramework == null || packageRoot == null || !Files.isDirectory(packageRoot)) {
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
                addPackage(packageDirectory, targetFramework, Path.of(projectFile), libraries);
            }
        }
        catch (IOException e) {
            return List.of();
        }
        return libraries;
    }

    private static void addPackage(Path packageDirectory, String targetFramework, Path projectFile, List<DotNetAddableLibrary> libraries) throws IOException {
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
                    libraries.add(new PackageLibrary(id, version, file, projectFile));
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

    private static MSBuildWorkspaceData.@Nullable ProjectInfo findInfo(Module module) {
        for (MSBuildWorkspaceData.ProjectInfo info : MSBuildWorkspaceData.getInstance(module.getProject()).getProjectInfos()) {
            if (module.getName().equals(info.moduleName)) {
                return info;
            }
        }
        return null;
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
