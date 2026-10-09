package consulo.msbuild;

import consulo.content.base.ExcludedContentFolderTypeProvider;
import consulo.logging.Logger;
import consulo.module.content.layer.ContentEntry;
import consulo.module.content.layer.ModifiableRootModel;
import consulo.util.io.FileUtil;
import consulo.virtualFileSystem.fileType.FileType;
import consulo.virtualFileSystem.fileType.FileTypeRegistry;
import consulo.virtualFileSystem.util.VirtualFileUtil;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

/**
 * @author VISTALL
 * @since 2026-10-10
 */
public final class MSBuildImportUtil {
    private static final Logger LOG = Logger.getInstance(MSBuildImportUtil.class);

    private static final int MAX_EXPAND_DEPTH = 8;

    private MSBuildImportUtil() {
    }

    @Nonnull
    public static List<String> splitList(@Nullable String value) {
        if (value == null) {
            return List.of();
        }
        List<String> result = new ArrayList<>();
        for (String part : value.split(";")) {
            String trimmed = part.trim();
            if (!trimmed.isEmpty()) {
                result.add(trimmed);
            }
        }
        return result;
    }

    @Nullable
    public static Path resolvePath(@Nonnull Path baseDir, @Nullable String value) {
        if (value == null) {
            return null;
        }
        String path = value.trim().replace('\\', '/');
        if (path.isEmpty()) {
            return null;
        }
        try {
            Path candidate = Path.of(path);
            return (candidate.isAbsolute() ? candidate : baseDir.resolve(candidate)).normalize();
        }
        catch (InvalidPathException e) {
            return null;
        }
    }

    @Nonnull
    public static String expandProperties(@Nonnull String value, @Nonnull Map<String, String> properties) {
        return expandProperties(value, properties, 0);
    }

    @Nonnull
    public static Set<String> collectContentUrls(@Nonnull ModifiableRootModel rootModel) {
        Set<String> contentUrls = new HashSet<>();
        for (ContentEntry entry : rootModel.getContentEntries()) {
            contentUrls.add(entry.getUrl());
        }
        return contentUrls;
    }

    public static void addFiles(@Nonnull ModifiableRootModel rootModel,
                                @Nonnull Path dir,
                                @Nonnull Set<FileType> fileTypes,
                                @Nonnull Set<String> contentUrls) {
        if (!Files.isDirectory(dir)) {
            return;
        }
        List<Path> files = new ArrayList<>();
        try (Stream<Path> children = Files.list(dir)) {
            children.filter(Files::isRegularFile).sorted().forEach(files::add);
        }
        catch (IOException e) {
            LOG.warn("Cannot list directory " + dir, e);
            return;
        }
        FileTypeRegistry fileTypeRegistry = FileTypeRegistry.getInstance();
        for (Path file : files) {
            if (!fileTypes.contains(fileTypeRegistry.getFileTypeByFileName(file.getFileName().toString()))) {
                continue;
            }
            String url = pathToUrl(file);
            if (contentUrls.add(url)) {
                rootModel.addSingleContentEntry(url);
            }
        }
    }

    public static void excludeFolder(@Nonnull ModifiableRootModel rootModel,
                                     @Nonnull Collection<Path> baseDirs,
                                     @Nullable Path dir,
                                     @Nonnull Set<String> contentUrls) {
        if (dir == null || !isStrictlyInside(dir, baseDirs)) {
            return;
        }
        String url = pathToUrl(dir);
        if (contentUrls.add(url)) {
            rootModel.addContentEntry(url).addFolder(url, ExcludedContentFolderTypeProvider.getInstance());
        }
    }

    @Nonnull
    public static String pathToUrl(@Nonnull Path path) {
        return VirtualFileUtil.pathToUrl(FileUtil.toSystemIndependentName(path.toString()));
    }

    private static boolean isStrictlyInside(Path dir, Collection<Path> baseDirs) {
        for (Path baseDir : baseDirs) {
            if (!dir.equals(baseDir) && dir.startsWith(baseDir)) {
                return true;
            }
        }
        return false;
    }

    private static String expandProperties(String value, Map<String, String> properties, int depth) {
        int start = value.indexOf("$(");
        if (start < 0 || depth >= MAX_EXPAND_DEPTH) {
            return value;
        }
        StringBuilder builder = new StringBuilder();
        int index = 0;
        while (start >= 0) {
            int end = value.indexOf(')', start + 2);
            if (end < 0) {
                break;
            }
            String name = value.substring(start + 2, end);
            builder.append(value, index, start);
            if (isPropertyName(name)) {
                String propertyValue = properties.get(name);
                builder.append(propertyValue == null ? "" : expandProperties(propertyValue, properties, depth + 1));
            }
            else {
                builder.append(value, start, end + 1);
            }
            index = end + 1;
            start = value.indexOf("$(", index);
        }
        builder.append(value, index, value.length());
        return builder.toString();
    }

    private static boolean isPropertyName(String name) {
        if (name.isEmpty() || !(Character.isLetter(name.charAt(0)) || name.charAt(0) == '_')) {
            return false;
        }
        for (int i = 1; i < name.length(); i++) {
            char c = name.charAt(i);
            if (!(Character.isLetterOrDigit(c) || c == '_' || c == '-')) {
                return false;
            }
        }
        return true;
    }
}
