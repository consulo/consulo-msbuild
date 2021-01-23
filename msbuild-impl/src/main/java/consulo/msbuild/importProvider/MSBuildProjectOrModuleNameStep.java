package consulo.msbuild.importProvider;

import com.intellij.openapi.application.Application;
import consulo.bundle.ui.BundleBox;
import consulo.bundle.ui.BundleBoxBuilder;
import consulo.disposer.Disposable;
import consulo.ide.newProject.ui.UnifiedProjectOrModuleNameStep;
import consulo.localize.LocalizeValue;
import consulo.msbuild.MSBuildProcessProvider;
import consulo.platform.base.icon.PlatformIconGroup;
import consulo.ui.annotation.RequiredUIAccess;
import consulo.ui.util.FormBuilder;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.Map;

/**
 * @author VISTALL
 * @since 01/01/2021
 */
public class MSBuildProjectOrModuleNameStep<C extends MSBuildBaseImportContext> extends UnifiedProjectOrModuleNameStep<C>
{
	private Disposable myUiDisposable;
	private BundleBox myBundleBox;

	private Map<String, MSBuildProcessProvider> mySdksFromProviders = new HashMap<>();

	public MSBuildProjectOrModuleNameStep(C context)
	{
		super(context);
	}

	@RequiredUIAccess
	@Override
	protected void extend(@Nonnull FormBuilder builder)
	{
		super.extend(builder);

		myUiDisposable = Disposable.newDisposable();

		BundleBoxBuilder boxBuilder = BundleBoxBuilder.create(myUiDisposable);
		boxBuilder.withNoneItem("<Auto Select>", PlatformIconGroup.actionsFind());
		boxBuilder.withSdkTypeFilter(sdkTypeId -> false);
		myBundleBox = boxBuilder.build();

		for(MSBuildProcessProvider buildProcessProvider : MSBuildProcessProvider.EP_NAME.getExtensionList(Application.get()))
		{
			buildProcessProvider.fillBundles(sdk -> {
				mySdksFromProviders.put(sdk.getName(), buildProcessProvider);
				
				myBundleBox.addBundleItem(sdk);
			});
		}
		builder.addLabeled(LocalizeValue.localizeTODO("MSBuild:"), myBundleBox.getComponent());
	}

	@Override
	public void disposeUIResources()
	{
		mySdksFromProviders.clear();
		
		if(myUiDisposable != null)
		{
			myUiDisposable.disposeWithTree();
			myUiDisposable = null;
		}

		myBundleBox = null;
	}


	@Override
	public void onStepLeave(@Nonnull C solutionModuleImportContext)
	{
		String bundleName = myBundleBox.getSelectedBundleName();
		if(bundleName != null)
		{
			MSBuildProcessProvider msBuildProcessProvider = mySdksFromProviders.get(bundleName);

			solutionModuleImportContext.setMSBuildBundleName(bundleName);

			solutionModuleImportContext.setProvider(msBuildProcessProvider);
		}
		else
		{
			// TODO [VISTALL] implement autoselect
			throw new IllegalArgumentException();
		}
	}
}
