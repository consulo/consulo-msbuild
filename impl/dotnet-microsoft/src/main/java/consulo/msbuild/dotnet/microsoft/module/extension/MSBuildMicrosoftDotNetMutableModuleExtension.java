package consulo.msbuild.dotnet.microsoft.module.extension;

import consulo.content.bundle.Sdk;
import consulo.disposer.Disposable;
import consulo.dotnet.module.extension.DotNetConfigurationPanel;
import consulo.dotnet.module.extension.DotNetMutableModuleExtension;
import consulo.dotnet.psi.impl.module.extension.DotNetQualifiedElementQualifierProducer;
import consulo.module.content.layer.ModuleRootLayer;
import consulo.module.extension.MutableModuleInheritableNamedPointer;
import consulo.msbuild.module.extension.MSBuildProjectMutableModuleExtension;
import consulo.ui.Component;
import consulo.ui.annotation.RequiredUIAccess;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

/**
 * @author VISTALL
 * @since 19/01/2021
 */
public class MSBuildMicrosoftDotNetMutableModuleExtension extends MSBuildMicrosoftDotNetModuleExtension implements DotNetMutableModuleExtension<MSBuildMicrosoftDotNetModuleExtension>,
    MSBuildProjectMutableModuleExtension<MSBuildMicrosoftDotNetModuleExtension> {
    public MSBuildMicrosoftDotNetMutableModuleExtension(@Nonnull String id, @Nonnull ModuleRootLayer moduleRootLayer) {
        super(id, moduleRootLayer);
    }

    @Nonnull
    @Override
    public MutableModuleInheritableNamedPointer<Sdk> getInheritableSdk() {
        return (MutableModuleInheritableNamedPointer<Sdk>) super.getInheritableSdk();
    }

    @RequiredUIAccess
    @Nullable
    @Override
    public Component createConfigurationComponent(@Nonnull Disposable uiDisposable, @Nonnull Runnable updateOnCheck) {
        return DotNetConfigurationPanel.create(this, DotNetQualifiedElementQualifierProducer.INSTANCE, getVariables(), uiDisposable,
            updateOnCheck);
    }

    @Override
    public void setEnabled(boolean val) {
        myIsEnabled = val;
    }

    @Override
    public boolean isModified(@Nonnull MSBuildMicrosoftDotNetModuleExtension originalExtension) {
        return isModifiedImpl(originalExtension);
    }
}
