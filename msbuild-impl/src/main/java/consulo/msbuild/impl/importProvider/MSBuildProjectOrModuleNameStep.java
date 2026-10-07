package consulo.msbuild.impl.importProvider;

import consulo.application.Application;
import consulo.disposer.Disposable;
import consulo.localize.LocalizeValue;
import consulo.module.creation.ui.UnifiedProjectOrModuleNameStep;
import consulo.msbuild.importProvider.MSBuildBaseImportContext;
import consulo.msbuild.importProvider.MSBuildBundleChooser;
import consulo.ui.annotation.RequiredUIAccess;
import consulo.ui.util.FormBuilder;
import jakarta.annotation.Nonnull;


/**
 * @author VISTALL
 * @since 01/01/2021
 */
public class MSBuildProjectOrModuleNameStep<C extends MSBuildBaseImportContext> extends UnifiedProjectOrModuleNameStep<C> {
    private final C myContext;
    private MSBuildBundleChooser myBundleChooser;

    public MSBuildProjectOrModuleNameStep(C context) {
        super(context);
        myContext = context;
    }

    @RequiredUIAccess
    @Override
    protected void extend(@Nonnull FormBuilder builder, @Nonnull Disposable uiDisposable) {
        super.extend(builder, uiDisposable);

        myBundleChooser = new MSBuildBundleChooser(Application.get(), uiDisposable, myContext);

        builder.addLabeled(LocalizeValue.localizeTODO("MSBuild:"), myBundleChooser.getComponent());
    }

    @Override
    public void onStepLeave(@Nonnull C context) {
        String bundleName = myBundleChooser.getSelectedBundleName();
        if (bundleName != null) {
            context.setMSBuildBundleName(bundleName);
            context.setProvider(myBundleChooser.getSelectedProvider());
        }
    }
}
