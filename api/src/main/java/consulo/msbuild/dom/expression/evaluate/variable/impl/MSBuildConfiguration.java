package consulo.msbuild.dom.expression.evaluate.variable.impl;

import consulo.annotation.access.RequiredReadAction;
import consulo.annotation.component.ExtensionImpl;
import consulo.msbuild.dom.expression.evaluate.MSBuildEvaluateContext;
import consulo.msbuild.dom.expression.evaluate.MSBuildEvaluatioException;
import consulo.msbuild.dom.expression.evaluate.variable.MSBuildVariableProvider;
import consulo.msbuild.module.extension.MSBuildProjectModuleExtension;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

/**
 * @author VISTALL
 * @since 2018-07-30
 */
@ExtensionImpl
public class MSBuildConfiguration extends MSBuildVariableProvider
{
	public MSBuildConfiguration()
	{
		super("Configuration");
	}

	@RequiredReadAction
	@Nullable
	@Override
	public String evaluateUnsafe(@Nonnull MSBuildEvaluateContext context) throws MSBuildEvaluatioException
	{
		MSBuildProjectModuleExtension<?> moduleExtension = context.getModuleExtension();
		return moduleExtension == null ? null : moduleExtension.getConfiguration();
	}
}
