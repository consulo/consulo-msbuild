package consulo.msbuild.dom.expression.evaluate;

import consulo.msbuild.dom.expression.evaluate.impl.MSBuildJexlExpressionEvaluator;
import consulo.msbuild.evaluate.MSBuildEvaluateContext;

import javax.annotation.Nonnull;

/**
 * @author VISTALL
 * @since 2018-07-30
 */
public interface MSBuildExpressionEvaluator
{
	static MSBuildExpressionEvaluator create()
	{
		// FIXME [VISTALL] we need replace it via new evaluator parsed by expression language
		return new MSBuildJexlExpressionEvaluator();
	}

	String evaluatePath(@Nonnull String text, @Nonnull MSBuildEvaluateContext context) throws MSBuildEvaluatioException;

	<T> T evaluate(@Nonnull String text, @Nonnull MSBuildEvaluateContext context, @Nonnull Class<T> expectedValue) throws MSBuildEvaluatioException;
}
