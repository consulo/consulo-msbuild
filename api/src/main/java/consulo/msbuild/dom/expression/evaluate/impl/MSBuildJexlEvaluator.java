package consulo.msbuild.dom.expression.evaluate.impl;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

import org.apache.commons.jexl3.JexlBuilder;
import org.apache.commons.jexl3.JexlContext;
import org.apache.commons.jexl3.JexlEngine;
import org.apache.commons.jexl3.JexlException;
import org.apache.commons.jexl3.JexlExpression;
import org.apache.commons.jexl3.MapContext;
import consulo.msbuild.dom.expression.evaluate.MSBuildEvaluateContext;
import consulo.msbuild.dom.expression.evaluate.MSBuildEvaluatioException;
import consulo.msbuild.dom.expression.evaluate.MSBuildEvaluator;
import consulo.msbuild.dom.expression.evaluate.variable.MSBuildVariableProvider;

/**
 * @author VISTALL
 * @since 2018-07-30
 * <p>
 * <p>
 * Initial impl from https://github.com/consulo/msbuild-utils4j  by Matthias Bromisch
 */
public class MSBuildJexlEvaluator implements MSBuildEvaluator
{
	/**
	 * Class to implement methods for MSBuild Conditions.
	 * This class provides the functions <code>hasTrailingSlash</code> and
	 * <code>exists</code>
	 */
	public class MSBuildFunctions
	{
		public Boolean hasTrailingSlash(String text)
		{
			if(text.endsWith("\\"))
			{
				return true;
			}

			return text.endsWith("/");
		}

		public Boolean exists(String path)
		{
			File file = new File(path);
			return file.exists();
		}
	}

	/**
	 * Class to correct some mistakes of Microsoft Visual Studio.
	 * Microsoft Visual Studio provides some import projects which have false
	 * conditions. Some conditions haven't correct declared strings to be
	 * validated.
	 * <p>
	 * For example:
	 * <p>
	 * <code>$(EnableManagedIncrementalBuild)==''</code>
	 * The property <code>$(EnableManagedIncrementalBuild)</code> isn't enclosed
	 * in '''. This class tries to correct them.
	 */
	public static class ConditionFix
	{
		private final String condition;
		private InputStream stream;

		public ConditionFix(String condition)
		{
			this.condition = condition;
		}

		/**
		 * Returns a corrected condition.
		 * Fixes only incorrect string declarations.
		 *
		 * @return
		 * @throws consulo.msbuild.dom.expression.evaluate.MSBuildEvaluatioException
		 */
		public String getFixedCondition() throws MSBuildEvaluatioException
		{
			StringBuilder result = new StringBuilder();
			try
			{
				stream = new ByteArrayInputStream(condition.getBytes("UTF-8"));
				int ch;
				while((ch = stream.read()) > -1)
				{
					if(ch == '$')
					{
						result.append(parseProperty());
					}
					else if(ch == '\'')
					{
						result.append(parseString());
					}
					else
					{
						result.append((char) ch);
					}
				}
			}
			catch(IOException ex)
			{
				throw new MSBuildEvaluatioException(ex);
			}

			return result.toString();
		}

		/**
		 * Returns the property enclosed in '''.
		 *
		 * @return
		 * @throws MSBuildEvaluatioException
		 */
		private String parseProperty() throws MSBuildEvaluatioException
		{
			StringBuilder property = new StringBuilder("'$(");

			int ch;
			try
			{
				ch = stream.read();
				if(ch != '(')
				{
					throw new MSBuildEvaluatioException("'(' expected for property");
				}
				while(((ch = stream.read()) > -1) && ch != ')')
				{
					property.append((char) ch);
				}
			}
			catch(IOException ex)
			{
				throw new MSBuildEvaluatioException(ex);
			}

			property.append(")'");
			return property.toString();
		}

		/**
		 * Returns the complete String.
		 * This method is for completion, so we don't parse properties enclosed
		 * in '''.
		 *
		 * @return
		 * @throws MSBuildEvaluatioException
		 */
		private String parseString() throws MSBuildEvaluatioException
		{
			StringBuilder string = new StringBuilder("'");

			int ch;
			try
			{
				while(((ch = stream.read()) > -1) && ch != '\'')
				{
					string.append((char) ch);
				}
			}
			catch(IOException ex)
			{
				throw new MSBuildEvaluatioException(ex);
			}

			string.append('\'');
			return string.toString();
		}
	}

	/**
	 * Normalize the condition to work properly with JEXL.
	 *
	 * @param _condition
	 * @return normalized condition for JEXL.
	 */
	private static String normalizeCondition(String _condition)
	{
		String result = _condition;

		// Normalize methods of 'exists' and 'hasTrailingSlash'
		result = result.replaceAll("(?i)Exists\\(", "msbuild:exists(");
		result = result.replaceAll("(?i)HasTrailingSlash\\(", "msbuild:hasTrailingSlash(");

		// normalize 'and' and 'or'
		result = result.replaceAll("(?i)and", "and");
		result = result.replaceAll("(?i)or", "or");

		// normalize backslashes
		result = result.replace("\\", "/");

		return result;
	}

	private JexlEngine myEngine;

	public MSBuildJexlEvaluator()
	{
		Map<String, Object> functions = new HashMap<>();
		functions.put("msbuild", new MSBuildFunctions());

		myEngine = new JexlBuilder().strict(true).namespaces(functions).create();
	}

	@SuppressWarnings("unchecked")
	@Nullable
	@Override
	public <T> T evaluate(@Nonnull String text, @Nonnull MSBuildEvaluateContext context, @Nonnull Class<T> expectedValue) throws MSBuildEvaluatioException
	{
		/*
		first we have to fix the condition for possibles errors made by
        Microsoft Visual Studio.
        */
		ConditionFix fix = new ConditionFix(text);
		String fixedCondition = fix.getFixedCondition();

		// normalize the condition for JEXL
		fixedCondition = normalizeCondition(fixedCondition);

		Map<String, MSBuildVariableProvider> variables = context.getVariables();
		Map<String, String> properties = new HashMap<>();
		for(Map.Entry<String, MSBuildVariableProvider> entry : variables.entrySet())
		{
			String evaluate = context.evaluateUnsafe(entry.getValue().getClass());
			if(evaluate != null)
			{
				properties.put(entry.getKey(), evaluate);
			}
		}

		// replace all properties
		for(Map.Entry<String, String> entry : properties.entrySet())
		{
			String name = String.format("$(%s)", entry.getKey());
			fixedCondition = fixedCondition.replace(name, entry.getValue());
		}

		JexlExpression expression = myEngine.createExpression(fixedCondition);

		// evaluate
		JexlContext dummyContext = new MapContext();
		try
		{
			Object obj = expression.evaluate(dummyContext);
			if(obj != null && expectedValue.isInstance(obj))
			{
				return (T) obj;
			}
			return null;
		}
		catch(JexlException ex)
		{
			throw new MSBuildEvaluatioException(ex);
		}
	}
}
