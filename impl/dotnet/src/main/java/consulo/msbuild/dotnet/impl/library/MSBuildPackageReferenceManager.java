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
import consulo.dotnet.packageManager.DotNetPackageManager;
import consulo.dotnet.packageManager.DotNetPackageReference;
import consulo.language.editor.WriteCommandAction;
import consulo.language.psi.PsiDocumentManager;
import consulo.language.psi.PsiFile;
import consulo.language.psi.PsiManager;
import consulo.language.util.ModuleUtilCore;
import consulo.module.Module;
import consulo.msbuild.MSBuildImportUtil;
import consulo.msbuild.MSBuildProjectImporter;
import consulo.msbuild.MSBuildWorkspaceData;
import consulo.msbuild.dom.ItemGroup;
import consulo.msbuild.dom.PackageReference;
import consulo.msbuild.dom.Project;
import consulo.msbuild.dotnet.impl.module.extension.MSBuildBaseDotNetModuleExtension;
import consulo.ui.annotation.RequiredUIAccess;
import consulo.virtualFileSystem.VirtualFile;
import consulo.xml.dom.DomFileElement;
import consulo.xml.dom.DomManager;
import consulo.xml.language.psi.XmlFile;
import org.jspecify.annotations.Nullable;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * The packages of an SDK-style project: the {@code PackageReference} items of its project file. A change is saved and reimported,
 * the import restores the packages.
 */
@ExtensionImpl
public class MSBuildPackageReferenceManager implements DotNetPackageManager {
    @RequiredReadAction
    @Override
    public boolean isApplicable(Module module) {
        return findSdkProjectInfo(module) != null;
    }

    @RequiredReadAction
    @Override
    public List<DotNetPackageReference> getPackages(Module module) {
        Project root = findProjectElement(module);
        if (root == null) {
            return List.of();
        }
        List<DotNetPackageReference> packages = new ArrayList<>();
        for (ItemGroup group : root.getItemGroups()) {
            for (PackageReference reference : group.getPackageReferences()) {
                String id = reference.getInclude().getStringValue();
                if (id != null && !id.isBlank()) {
                    packages.add(new DotNetPackageReference(id.trim(), reference.getVersion().getStringValue()));
                }
            }
        }
        return packages;
    }

    @RequiredUIAccess
    @Override
    public CompletableFuture<?> install(Module module, String id, String version) {
        try {
            WriteCommandAction.runWriteCommandAction(module.getProject(), () -> addReference(module, id, version));
            return CompletableFuture.completedFuture(null);
        }
        catch (RuntimeException e) {
            return CompletableFuture.failedFuture(e);
        }
    }

    @RequiredUIAccess
    @Override
    public CompletableFuture<?> uninstall(Module module, String id) {
        try {
            WriteCommandAction.runWriteCommandAction(module.getProject(), () -> removeReference(module, id));
            return CompletableFuture.completedFuture(null);
        }
        catch (RuntimeException e) {
            return CompletableFuture.failedFuture(e);
        }
    }

    /**
     * References the version of the package - changes the version of an existing reference - and reimports after the command.
     */
    @RequiredWriteAction
    public static void addReference(Module module, String id, String version) {
        Project root = findProjectElement(module);
        if (root == null) {
            throw new IllegalStateException("No project file of module " + module.getName());
        }
        ItemGroup target = null;
        for (ItemGroup group : root.getItemGroups()) {
            for (PackageReference reference : group.getPackageReferences()) {
                if (id.equalsIgnoreCase(reference.getInclude().getStringValue())) {
                    if (!version.equals(reference.getVersion().getStringValue())) {
                        reference.getVersion().setStringValue(version);
                    }
                    reimport(module);
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
        reimport(module);
    }

    @RequiredWriteAction
    private static void removeReference(Module module, String id) {
        Project root = findProjectElement(module);
        if (root == null) {
            throw new IllegalStateException("No project file of module " + module.getName());
        }
        for (ItemGroup group : root.getItemGroups()) {
            for (PackageReference reference : group.getPackageReferences()) {
                if (id.equalsIgnoreCase(reference.getInclude().getStringValue())) {
                    reference.undefine();
                    // the group of the packages only goes with its last one
                    if (group.getPackageReferences().isEmpty() && group.getXmlTag() != null && group.getXmlTag().getSubTags().length == 0) {
                        group.undefine();
                    }
                    reimport(module);
                    return;
                }
            }
        }
    }

    private static void reimport(Module module) {
        consulo.project.Project project = module.getProject();
        VirtualFile projectFile = findProjectFile(module);
        // the import reads the project file from the disk - after the write command that changed it, and after its save
        Application.get().invokeLater(() -> {
            PsiDocumentManager.getInstance(project).commitAllDocuments();
            if (projectFile != null) {
                FileDocumentManager documentManager = FileDocumentManager.getInstance();
                Document document = documentManager.getCachedDocument(projectFile);
                if (document != null) {
                    documentManager.saveDocument(document);
                }
            }
            MSBuildProjectImporter.getInstance(project).reimport();
        }, project.getDisposed());
    }

    @RequiredReadAction
    private static @Nullable Project findProjectElement(Module module) {
        VirtualFile virtualFile = findProjectFile(module);
        PsiFile psiFile = virtualFile == null ? null : PsiManager.getInstance(module.getProject()).findFile(virtualFile);
        if (!(psiFile instanceof XmlFile xmlFile)) {
            return null;
        }
        DomFileElement<Project> fileElement = DomManager.getDomManager(module.getProject()).getFileElement(xmlFile, Project.class);
        return fileElement == null ? null : fileElement.getRootElement();
    }

    private static @Nullable VirtualFile findProjectFile(Module module) {
        MSBuildWorkspaceData.ProjectInfo info = findSdkProjectInfo(module);
        String projectFile = info == null ? null : info.properties.get("MSBuildProjectFullPath");
        return projectFile == null ? null : MSBuildImportUtil.findFile(module.getProject(), projectFile);
    }

    /**
     * The info of the SDK-style project of the module: an old-style project references the assemblies of a .NET Framework, not packages.
     */
    static MSBuildWorkspaceData.@Nullable ProjectInfo findSdkProjectInfo(Module module) {
        if (ModuleUtilCore.getExtension(module, MSBuildBaseDotNetModuleExtension.class) == null) {
            return null;
        }
        for (MSBuildWorkspaceData.ProjectInfo info : MSBuildWorkspaceData.getInstance(module.getProject()).getProjectInfos()) {
            if (module.getName().equals(info.moduleName)) {
                return "true".equalsIgnoreCase(info.properties.get("UsingMicrosoftNETSdk")) ? info : null;
            }
        }
        return null;
    }
}
