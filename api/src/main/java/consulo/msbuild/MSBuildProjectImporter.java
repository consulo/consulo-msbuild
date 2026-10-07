package consulo.msbuild;

import consulo.annotation.component.ComponentScope;
import consulo.annotation.component.ServiceAPI;
import consulo.project.Project;
import jakarta.annotation.Nonnull;

/**
 * @author VISTALL
 * @since 2026-10-07
 */
@ServiceAPI(ComponentScope.PROJECT)
public interface MSBuildProjectImporter {
    @Nonnull
    static MSBuildProjectImporter getInstance(@Nonnull Project project) {
        return project.getInstance(MSBuildProjectImporter.class);
    }

    void reimport();
}
