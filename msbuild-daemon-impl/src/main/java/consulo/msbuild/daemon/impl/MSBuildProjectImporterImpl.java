package consulo.msbuild.daemon.impl;

import consulo.annotation.component.ServiceImpl;
import consulo.msbuild.MSBuildProjectImporter;
import consulo.project.Project;
import jakarta.inject.Inject;
import jakarta.inject.Provider;
import jakarta.inject.Singleton;

/**
 * @author VISTALL
 * @since 2026-10-07
 */
@ServiceImpl
@Singleton
public class MSBuildProjectImporterImpl implements MSBuildProjectImporter {
    private final Provider<MSBuildDaemonService> myMSBuildDaemonServiceProvider;

    @Inject
    public MSBuildProjectImporterImpl(Provider<MSBuildDaemonService> msBuildDaemonServiceProvider) {
        myMSBuildDaemonServiceProvider = msBuildDaemonServiceProvider;
    }

    @Override
    public void reimport() {
        myMSBuildDaemonServiceProvider.get().forceUpdate();
    }
}
