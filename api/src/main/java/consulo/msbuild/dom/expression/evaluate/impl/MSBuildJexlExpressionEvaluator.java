package consulo.msbuild.dom.expression.evaluate.impl;

import consulo.msbuild.evaluate.MSBuildEvaluateContext;
import consulo.msbuild.dom.expression.evaluate.MSBuildEvaluatioException;
import consulo.msbuild.dom.expression.evaluate.MSBuildExpressionEvaluator;
import org.apache.commons.jexl3.*;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * @author VISTALL
 * @since 2018-07-30
 * <p>
 * <p>
 * Initial impl from https://github.com/consulo/msbuild-utils4j  by Matthias Bromisch
 */
public class MSBuildJexlExpressionEvaluator implements MSBuildExpressionEvaluator
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
		protected String parseProperty() throws MSBuildEvaluatioException
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

	public MSBuildJexlExpressionEvaluator()
	{
		Map<String, Object> functions = new HashMap<>();
		functions.put("msbuild", new MSBuildFunctions());

		myEngine = new JexlBuilder().strict(true).namespaces(functions).create();
	}

	@Override
	public String evaluatePath(@Nonnull String text, @Nonnull MSBuildEvaluateContext context) throws MSBuildEvaluatioException
	{
		ConditionFix fix = new ConditionFix(text)
		{
			@Override
			protected String parseProperty() throws MSBuildEvaluatioException
			{
				String property = super.parseProperty();
				String path = evaluate(property, context, String.class);
				return path;
			}
		};
		return fix.getFixedCondition();
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

		Map<String, String> properties = context.getVariableValues();

		// replace all properties
		for(Map.Entry<String, String> entry : properties.entrySet())
		{
			String name = String.format("$(%s)", entry.getKey());
			fixedCondition = fixedCondition.replace(name, entry.getValue());
		}

		JexlExpression expression = null;
		try
		{
			expression = myEngine.createExpression(fixedCondition);
		}
		catch(JexlException.Parsing e)
		{
			throw new MSBuildEvaluatioException("Wrong parsing: " + fixedCondition, e);
		}

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
