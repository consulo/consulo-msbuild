package consulo.msbuild.importProvider;

import consulo.awt.TargetAWT;
import consulo.bundle.ui.BundleBox;
import consulo.bundle.ui.BundleBoxBuilder;
import consulo.disposer.Disposable;
import consulo.disposer.Disposer;
import consulo.ide.newProject.ui.ProjectOrModuleNameStep;
import consulo.localize.LocalizeValue;
import consulo.msbuild.bundle.MSBuildBundleType;
import consulo.platform.base.icon.PlatformIconGroup;
import consulo.ui.Component;
import consulo.ui.annotation.RequiredUIAccess;
import consulo.ui.util.LabeledBuilder;

import javax.annotation.Nonnull;
import java.awt.*;

/**
 * @author VISTALL
 * @since 01/01/2021
 */
public class MSBuildProjectOrModuleNameStep extends ProjectOrModuleNameStep<SolutionModuleImportContext>
{
	private Disposable myUiDisposable;
	private final BundleBox myBundleBox;

	public MSBuildProjectOrModuleNameStep(SolutionModuleImportContext context)
	{
		super(context);

		myUiDisposable = Disposable.newDisposable();

		BundleBoxBuilder builder = BundleBoxBuilder.create(myUiDisposable);
		builder.withSdkTypeFilterByType(MSBuildBundleType.getInstance());
		builder.withNoneItem("<Auto Select>", PlatformIconGroup.actionsFind());
		myBundleBox = builder.build();

		Component labeled = LabeledBuilder.filled(LocalizeValue.localizeTODO("MSBuild:"), myBundleBox);

		myAdditionalContentPanel.add(TargetAWT.to(labeled), BorderLayout.CENTER);
	}

	@Override
	public void disposeUIResources()
	{
		if(myUiDisposable != null)
		{
			Disposer.dispose(myUiDisposable);
			myUiDisposable = null;
		}
	}

	@Override
	@RequiredUIAccess
	public void onStepEnter(@Nonnull SolutionModuleImportContext solutionModuleImportContext)
	{
		myBundleBox.setSelectedBundle(solutionModuleImportContext.getMSBuildBundleName());
	}

	@Override
	public void onStepLeave(@Nonnull SolutionModuleImportContext solutionModuleImportContext)
	{
		solutionModuleImportContext.setMSBuildBundleName(myBundleBox.getSelectedBundleName());
	}
}
