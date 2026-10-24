package consulo.msbuild.importProvider;

import consulo.logging.Logger;
import consulo.msbuild.solution.reader.SlnFile;
import consulo.msbuild.solution.reader.SlnxReader;
import consulo.project.Project;
import consulo.virtualFileSystem.LocalFileSystem;
import consulo.virtualFileSystem.VirtualFile;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.nio.charset.StandardCharsets;

/**
 * @author VISTALL
 * @since 01-Feb-17
 */
public class SolutionModuleImportContext extends MSBuildBaseImportContext {
    private static final Logger LOG = Logger.getInstance(SolutionModuleImportContext.class);

    private SlnFile mySlnFile = new SlnFile();

    public SolutionModuleImportContext(@Nullable Project project) {
        super(project);
    }

    @Override
    public void setFileToImport(String dirOrFile) {
        String solutionFilePath = getPath();
        VirtualFile solutionFile = LocalFileSystem.getInstance().findFileByPath(solutionFilePath);
        assert solutionFile != null;

        setName(solutionFile.getNameWithoutExtension());
        setPath(solutionFile.getParent().getPath());

        super.setFileToImport(solutionFile.getPath());

        if ("slnx".equalsIgnoreCase(solutionFile.getExtension())) {
            try (InputStream stream = solutionFile.getInputStream()) {
                SlnxReader.read(stream, mySlnFile);
            }
            catch (IOException e) {
                LOG.warn("Failed to read solution file: " + solutionFile.getPath(), e);
            }
            return;
        }

        try (LineNumberReader reader = new LineNumberReader(new InputStreamReader(solutionFile.getInputStream(), StandardCharsets.UTF_8))) {
            mySlnFile.Read(reader);
        }
        catch (IOException ignored) {
        }
    }

    @Nonnull
    public SlnFile getSlnFile() {
        return mySlnFile;
    }
}
