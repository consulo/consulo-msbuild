package consulo.msbuild.compiler;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.intellij.openapi.compiler.CompileContext;
import com.intellij.openapi.compiler.CompilerMessageCategory;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.util.text.StringUtil;

/**
 * @author VISTALL
 * @since 2018-09-17
 * <p>
 * stub from jenkins
 */
public class MSBuildConsoleParser
{
	private static final Logger LOGGER = Logger.getInstance(MSBuildConsoleParser.class);

	private int myErrorNumbers;

	private List<String> myLines = new ArrayList<>();

	public void parse(String line)
	{
		LOGGER.info(line);
		myLines.add(line);

		Pattern patternErrors = Pattern.compile(".*\\d+\\sError\\(s\\).*");

		Matcher mErrors = patternErrors.matcher(line);

		if(mErrors.matches())
		{ // Match the number of errors
			String[] part = line.split(" ");
			myErrorNumbers = findNumber(part);
		}
	}

	private static int findNumber(String[] array)
	{
		for(String s : array)
		{
			int i = StringUtil.parseInt(s, 0);
			if(i != 0)
			{
				return i;
			}
		}
		return 0;
	}

	public void report(CompileContext compileContext)
	{
		for(String line : myLines)
		{
			compileContext.addMessage(CompilerMessageCategory.INFORMATION, line, null, -1, -1);
		}

		if(myErrorNumbers > 0)
		{
			compileContext.addMessage(CompilerMessageCategory.ERROR, "Failed to compile", null, -1, -1);
		}
	}
}
