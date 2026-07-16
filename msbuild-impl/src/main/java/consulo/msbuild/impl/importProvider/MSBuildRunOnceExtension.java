package consulo.msbuild.impl.importProvider;

import consulo.annotation.component.ExtensionImpl;
import consulo.msbuild.daemon.impl.MSBuildDaemonService;
import consulo.project.Project;
import consulo.project.ProjectRunOnceExtension;
import jakarta.inject.Inject;

/**
 * @author VISTALL
 * @since 2026-07-16
 */
@ExtensionImpl
public class MSBuildRunOnceExtension implements ProjectRunOnceExtension<MSBuildRunOnceExtension.Data> {
    public record Data(String id) {

    }
    public static final String ID = "msbuild";

    private final Project myProject;

    @Inject
    public MSBuildRunOnceExtension(Project project) {
        myProject = project;
    }

    @Override
    public String getId() {
        return ID;
    }

    @Override
    public Class<Data> getInputClass() {
        return Data.class;
    }

    @Override
    public void run(Data data) {
        MSBuildDaemonService.getInstance(myProject).forceUpdate();

        // TODO [VISTALL] create run configurations after reimport
    }
}
