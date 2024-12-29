package consulo.msbuild.dotnet.microsoft.module.extension;

import consulo.content.bundle.Sdk;
import consulo.disposer.Disposable;
import consulo.dotnet.module.extension.DotNetConfigurationPanel;
import consulo.dotnet.module.extension.DotNetMutableModuleExtension;
import consulo.dotnet.psi.impl.module.extension.DotNetQualifiedElementQualifierProducer;
import consulo.localize.LocalizeValue;
import consulo.module.content.layer.ModuleRootLayer;
import consulo.module.extension.MutableModuleInheritableNamedPointer;
import consulo.module.extension.swing.SwingMutableModuleExtension;
import consulo.msbuild.module.extension.MSBuildProjectMutableModuleExtension;
import consulo.ui.Component;
import consulo.ui.Label;
import consulo.ui.annotation.RequiredUIAccess;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import javax.swing.*;

/**
 * @author VISTALL
 * @since 19/01/2021
 */
public class MSBuildMicrosoftDotNetMutableModuleExtension extends MSBuildMicrosoftDotNetModuleExtension implements DotNetMutableModuleExtension<MSBuildMicrosoftDotNetModuleExtension>,
		MSBuildProjectMutableModuleExtension<MSBuildMicrosoftDotNetModuleExtension>, SwingMutableModuleExtension
{
	public MSBuildMicrosoftDotNetMutableModuleExtension(@Nonnull String id, @Nonnull ModuleRootLayer moduleRootLayer)
	{
		super(id, moduleRootLayer);
	}

	@Nonnull
	@Override
	public MutableModuleInheritableNamedPointer<Sdk> getInheritableSdk()
	{
		return (MutableModuleInheritableNamedPointer<Sdk>) super.getInheritableSdk();
	}

	@RequiredUIAccess
	@Nullable
	@Override
	public Component createConfigurationComponent(@Nonnull Disposable uiDisposable, @Nonnull Runnable updateOnCheck)
	{
		return Label.create(LocalizeValue.localizeTODO("Unsupported UI"));
	}

	@RequiredUIAccess
	@Nullable
	@Override
	public JComponent createConfigurablePanel(@Nonnull Disposable disposable, @Nonnull Runnable runnable)
	{
		return new DotNetConfigurationPanel(this, DotNetQualifiedElementQualifierProducer.INSTANCE, getVariables(), runnable);
	}

	@Override
	public void setEnabled(boolean val)
	{
		myIsEnabled = val;
	}

	@Override
	public boolean isModified(@Nonnull MSBuildMicrosoftDotNetModuleExtension originalExtension)
	{
		return isModifiedImpl(originalExtension);
	}
}
