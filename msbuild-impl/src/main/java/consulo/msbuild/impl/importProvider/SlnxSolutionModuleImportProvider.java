package consulo.msbuild.impl.importProvider;

import consulo.annotation.component.ExtensionImpl;
import jakarta.annotation.Nonnull;

/**
 * XML solution format. Preferred - shown first if directory contains both formats
 *
 * @author VISTALL
 * @since 2026-10-24
 */
@ExtensionImpl(id = "slnx", order = "before sln")
public class SlnxSolutionModuleImportProvider extends SolutionModuleImportProvider {
    @Nonnull
    @Override
    protected String getSolutionExtension() {
        return "slnx";
    }
}
