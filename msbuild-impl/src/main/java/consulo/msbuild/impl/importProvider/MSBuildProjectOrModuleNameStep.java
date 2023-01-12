package consulo.msbuild.impl.importProvider;

import consulo.application.Application;
import consulo.content.bundle.Sdk;
import consulo.disposer.Disposable;
import consulo.ide.newModule.ui.UnifiedProjectOrModuleNameStep;
import consulo.localize.LocalizeValue;
import consulo.module.ui.BundleBox;
import consulo.module.ui.BundleBoxBuilder;
import consulo.msbuild.MSBuildProcessProvider;
import consulo.msbuild.importProvider.MSBuildBaseImportContext;
import consulo.ui.annotation.RequiredUIAccess;
import consulo.ui.util.FormBuilder;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author VISTALL
 * @since 01/01/2021
 */
public class MSBuildProjectOrModuleNameStep<C extends MSBuildBaseImportContext> extends UnifiedProjectOrModuleNameStep<C>
{
	private final C myContext;
	private BundleBox myBundleBox;

	private Map<String, MSBuildProcessProvider> mySdksFromProviders = new HashMap<>();

	public MSBuildProjectOrModuleNameStep(C context)
	{
		super(context);
		myContext = context;
	}

	@RequiredUIAccess
	@Override
	protected void extend(@Nonnull FormBuilder builder, @Nonnull Disposable uiDisposable)
	{
		super.extend(builder, uiDisposable);

		BundleBoxBuilder boxBuilder = BundleBoxBuilder.create(uiDisposable);
		//boxBuilder.withNoneItem("<Auto Select>", PlatformIconGroup.actionsFind());
		boxBuilder.withSdkTypeFilter(sdkTypeId -> false);
		myBundleBox = boxBuilder.build();

		List<MSBuildProcessProvider> providers = Application.get().getExtensionList(MSBuildProcessProvider.class);

		for(MSBuildProcessProvider buildProcessProvider : providers)
		{
			buildProcessProvider.fillBundles(sdk -> {
				mySdksFromProviders.put(sdk.getName(), buildProcessProvider);
				
				myBundleBox.addBundleItem(sdk);
			});
		}

		Sdk targetSdk = null;
		for(MSBuildProcessProvider provider : providers)
		{
			targetSdk = provider.findBundleForImport(myContext);
			if(targetSdk != null)
			{
				myBundleBox.setSelectedBundle(targetSdk.getName());
				break;
			}
		}

		builder.addLabeled(LocalizeValue.localizeTODO("MSBuild:"), myBundleBox.getComponent());
	}

	@Override
	public void onStepLeave(@Nonnull C context)
	{
		String bundleName = myBundleBox.getSelectedBundleName();
		if(bundleName != null)
		{
			MSBuildProcessProvider msBuildProcessProvider = mySdksFromProviders.get(bundleName);

			context.setMSBuildBundleName(bundleName);

			context.setProvider(msBuildProcessProvider);
		}
	}
}
