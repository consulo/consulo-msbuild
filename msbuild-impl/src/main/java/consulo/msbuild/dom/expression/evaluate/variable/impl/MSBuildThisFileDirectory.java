package consulo.msbuild.dom.expression.evaluate.variable.impl;

import com.intellij.openapi.vfs.VirtualFile;
import consulo.annotation.access.RequiredReadAction;
import consulo.msbuild.dom.expression.evaluate.MSBuildEvaluatioException;
import consulo.msbuild.dom.expression.evaluate.variable.MSBuildVariableProvider;
import consulo.msbuild.evaluate.MSBuildEvaluateContext;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * @author VISTALL
 * @since 2020-06-24
 */
public class MSBuildThisFileDirectory extends MSBuildVariableProvider
{
	@RequiredReadAction
	@Nullable
	@Override
	public String evaluateUnsafe(@Nonnull MSBuildEvaluateContext context) throws MSBuildEvaluatioException
	{
		VirtualFile currentFile = context.getCurrentFile();
		return currentFile.getParent().getPath() + "/";
	}
}
