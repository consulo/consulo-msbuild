package consulo.msbuild.impl.importProvider;

import consulo.annotation.component.ExtensionImpl;
import jakarta.annotation.Nonnull;

/**
 * Classic text solution format
 *
 * @author VISTALL
 * @since 2026-10-24
 */
@ExtensionImpl(id = "sln")
public class SlnSolutionModuleImportProvider extends SolutionModuleImportProvider {
    @Nonnull
    @Override
    protected String getSolutionExtension() {
        return "sln";
    }
}
