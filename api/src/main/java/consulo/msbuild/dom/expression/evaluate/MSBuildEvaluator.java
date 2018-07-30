package consulo.msbuild.dom.expression.evaluate;

import javax.annotation.Nonnull;

import consulo.msbuild.dom.expression.evaluate.impl.MSBuildJexlEvaluator;

/**
 * @author VISTALL
 * @since 2018-07-30
 */
public interface MSBuildEvaluator
{
	static MSBuildEvaluator create()
	{
		// FIXME [VISTALL] we need replace it via new evaluator parsed by expression language
		return new MSBuildJexlEvaluator();
	}

	<T> T evaluate(@Nonnull String text, @Nonnull MSBuildEvaluateContext context, @Nonnull Class<T> expectedValue) throws MSBuildEvaluatioException;
}
