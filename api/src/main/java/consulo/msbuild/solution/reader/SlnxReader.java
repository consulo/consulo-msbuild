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
package consulo.msbuild.solution.reader;

import consulo.msbuild.MSBuildGUID;
import consulo.msbuild.solution.model.WSolution;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import consulo.util.jdom.JDOMUtil;
import org.jdom.Element;
import org.jdom.JDOMException;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;

/**
 * XML solution format ({@code .slnx}): {@code <Solution><Folder Name="/src/"><Project Path="src/A/A.csproj"/>...}.
 * Projects are read into the model of {@code .sln} files: the type GUID comes from the project file extension, a
 * missing id is derived from the path.
 *
 * @author VISTALL
 * @since 2026-10-25
 */
public final class SlnxReader {
    private static final String CSHARP = "{FAE04EC0-301F-11D3-BF4B-00C04F79EFBC}";
    private static final String FSHARP = "{F2A71F9B-5D33-465A-A702-920D77279786}";
    private static final String VISUAL_BASIC = "{F184B08F-C81C-45F6-A57F-5ABD9991F28F}";
    private static final String UNKNOWN = "{9A19103F-16F7-4668-BE54-9A1E7A4F7556}";

    private SlnxReader() {
    }

    public static void read(@Nonnull InputStream stream, @Nonnull SlnFile file) throws IOException {
        Element root;
        try {
            root = JDOMUtil.load(stream);
        }
        catch (JDOMException e) {
            throw new IOException(e);
        }
        if (root == null || !"Solution".equals(root.getName())) {
            throw new IOException("Not a solution file");
        }
        Map<String, String> explicitFolderIds = new HashMap<>();
        collectFolderIds(root, explicitFolderIds);
        readProjects(root, file, null, new Folders(explicitFolderIds));
    }

    /**
     * Folder ids are collected before reading - nested folder can be listed before its parent
     */
    private static void collectFolderIds(Element element, Map<String, String> ids) {
        for (Element child : element.getChildren("Folder")) {
            String id = child.getAttributeValue("Id", "");
            if (!id.isEmpty()) {
                ids.put(normalizeFolderPath(child.getAttributeValue("Name", "")), braced(id));
            }
            collectFolderIds(child, ids);
        }
    }

    /**
     * @param explicitIds folder id from {@code Id} attribute by normalized full path
     * @param ids         id of created folder by normalized full path
     */
    private record Folders(Map<String, String> explicitIds, Map<String, String> ids) {
        Folders(Map<String, String> explicitIds) {
            this(explicitIds, new HashMap<>());
        }
    }

    /**
     * Folders are added as solution folder projects, nesting goes to {@code NestedProjects} section - like in {@code .sln} files.
     * Folder elements are usually flat siblings with full path names ({@code /src/}, {@code /src/libs/}), so parent folder
     * is taken from the name, not from xml parent.
     */
    private static void readProjects(Element element, SlnFile file, @Nullable String parentId, Folders folders) {
        for (Element child : element.getChildren()) {
            if ("Folder".equals(child.getName())) {
                String folderPath = normalizeFolderPath(child.getAttributeValue("Name", ""));
                if (folderPath.equals("/")) {
                    readProjects(child, file, parentId, folders);
                    continue;
                }
                String folderId = folder(file, folderPath, folders);
                readProjects(child, file, folderId, folders);
            }
            else if ("Project".equals(child.getName())) {
                String path = child.getAttributeValue("Path", "");
                if (path.isEmpty()) {
                    continue;
                }
                SlnProject project = new SlnProject();
                project.setParentFile(file);
                project.FilePath = path;
                project.Name = nameOf(path);
                String type = child.getAttributeValue("Type", "");
                project.TypeGuid = type.isEmpty() ? typeGuidOf(path) : braced(type);
                String id = child.getAttributeValue("Id", "");
                project.Id = id.isEmpty() ? derivedId(path.replace('\\', '/')) : braced(id);
                file.getProjects().add(project);
                nest(file, project.Id, parentId);
            }
        }
    }

    /**
     * Returns id of folder with full path, creates it and missing parent folders
     */
    private static String folder(SlnFile file, String folderPath, Folders folders) {
        String known = folders.ids().get(folderPath);
        if (known != null) {
            return known;
        }

        // "/src/libs/" -> "/src/"
        int parentEnd = folderPath.lastIndexOf('/', folderPath.length() - 2);
        String parentPath = folderPath.substring(0, parentEnd + 1);
        String parentId = parentPath.equals("/") ? null : folder(file, parentPath, folders);

        String name = folderPath.substring(parentEnd + 1, folderPath.length() - 1);
        SlnProject folder = new SlnProject();
        folder.setParentFile(file);
        folder.FilePath = name;
        folder.Name = name;
        folder.TypeGuid = MSBuildGUID.SolutionFolder;
        String id = folders.explicitIds().get(folderPath);
        folder.Id = id != null ? id : derivedId("folder:" + folderPath);
        file.getProjects().add(folder);
        folders.ids().put(folderPath, folder.Id);
        nest(file, folder.Id, parentId);
        return folder.Id;
    }

    private static String derivedId(String key) {
        return braced(UUID.nameUUIDFromBytes(key.getBytes(StandardCharsets.UTF_8)).toString());
    }

    private static void nest(SlnFile file, String childId, @Nullable String parentId) {
        if (parentId == null) {
            return;
        }
        SlnSection section = file.getSections().GetOrCreateSection(WSolution.SECTION_NESTED_PROJECTS, SlnSectionType.PreProcess);
        section.setParentFile(file);
        section.getProperties().getValues().put(childId, parentId);
    }

    /**
     * {@code src\\libs} -> {@code /src/libs/}
     */
    private static String normalizeFolderPath(String path) {
        StringBuilder builder = new StringBuilder("/");
        for (String segment : path.replace('\\', '/').split("/")) {
            if (!segment.isEmpty()) {
                builder.append(segment).append('/');
            }
        }
        return builder.toString();
    }

    private static String nameOf(String path) {
        String normalized = path.replace('\\', '/');
        String name = normalized.substring(normalized.lastIndexOf('/') + 1);
        int dot = name.lastIndexOf('.');
        return dot < 0 ? name : name.substring(0, dot);
    }

    private static String typeGuidOf(String path) {
        String lower = path.toLowerCase(Locale.ROOT);
        if (lower.endsWith(".csproj")) {
            return CSHARP;
        }
        if (lower.endsWith(".fsproj")) {
            return FSHARP;
        }
        if (lower.endsWith(".vbproj")) {
            return VISUAL_BASIC;
        }
        return UNKNOWN;
    }

    private static String braced(String guid) {
        String upper = guid.toUpperCase(Locale.ROOT);
        return upper.startsWith("{") ? upper : "{" + upper + "}";
    }
}
