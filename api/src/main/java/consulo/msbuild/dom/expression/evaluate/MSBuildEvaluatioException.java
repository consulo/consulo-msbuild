package consulo.msbuild.dom.expression.evaluate;

/**
 * @author VISTALL
 * @since 2018-07-30
 */
public class MSBuildEvaluatioException extends RuntimeException
{
	public MSBuildEvaluatioException()
	{
	}

	public MSBuildEvaluatioException(String message)
	{
		super(message);
	}

	public MSBuildEvaluatioException(String message, Throwable cause)
	{
		super(message, cause);
	}

	public MSBuildEvaluatioException(Throwable cause)
	{
		super(cause);
	}

	public MSBuildEvaluatioException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace)
	{
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
