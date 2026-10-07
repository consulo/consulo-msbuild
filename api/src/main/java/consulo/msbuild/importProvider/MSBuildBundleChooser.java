package consulo.msbuild.importProvider;

import consulo.application.Application;
import consulo.content.bundle.Sdk;
import consulo.disposer.Disposable;
import consulo.module.ui.BundleBox;
import consulo.module.ui.BundleBoxBuilder;
import consulo.msbuild.MSBuildProcessProvider;
import consulo.ui.Component;
import consulo.ui.annotation.RequiredUIAccess;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author VISTALL
 * @since 2026-10-07
 */
public final class MSBuildBundleChooser {
    private final BundleBox myBundleBox;
    private final Map<String, MSBuildProcessProvider> myProviders = new LinkedHashMap<>();

    @RequiredUIAccess
    public MSBuildBundleChooser(@Nonnull Application application, @Nonnull Disposable uiDisposable, @Nullable MSBuildBaseImportContext context) {
        BundleBoxBuilder boxBuilder = BundleBoxBuilder.create(uiDisposable);
        boxBuilder.withSdkTypeFilter(sdkTypeId -> false);
        myBundleBox = boxBuilder.build();

        List<MSBuildProcessProvider> providers = application.getExtensionList(MSBuildProcessProvider.class);
        for (MSBuildProcessProvider provider : providers) {
            provider.fillBundles(sdk -> {
                myProviders.put(sdk.getName(), provider);
                myBundleBox.addBundleItem(sdk);
            });
        }

        String selected = null;
        if (context != null) {
            for (MSBuildProcessProvider provider : providers) {
                Sdk targetSdk = provider.findBundleForImport(context);
                if (targetSdk != null) {
                    selected = targetSdk.getName();
                    break;
                }
            }
        }
        if (selected == null && !myProviders.isEmpty()) {
            selected = myProviders.keySet().iterator().next();
        }
        if (selected != null) {
            myBundleBox.setSelectedBundle(selected);
        }
    }

    @Nonnull
    public Component getComponent() {
        return myBundleBox.getComponent();
    }

    @Nullable
    public String getSelectedBundleName() {
        return myBundleBox.getSelectedBundleName();
    }

    @Nullable
    public MSBuildProcessProvider getSelectedProvider() {
        String bundleName = getSelectedBundleName();
        return bundleName != null ? myProviders.get(bundleName) : null;
    }
}
