package consulo.msbuild.compiler;

import consulo.logging.Logger;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * @author VISTALL
 * @since 2018-09-17
 */
public class MSBuildReportLine
{
	private static final Logger LOGGER = Logger.getInstance(MSBuildReportLine.class);

	@Nullable
	public static MSBuildReportLine parse(@Nonnull String line)
	{
		try
		{
			String projectFile = null;
			String filePath = null;
			String message = null;
			String id = null;
			int lineNumber = -1;
			int columnNumber = -1;

			int startProjectPath = line.lastIndexOf('[');
			if(startProjectPath != -1)
			{
				projectFile = line.substring(startProjectPath + 1, line.length());
				line = line.substring(0, startProjectPath);

				// cut ]
				projectFile = projectFile.substring(0, projectFile.length() - 1);
			}

			int colonIndex = line.indexOf(':');
			if(colonIndex != -1)
			{
				filePath = line.substring(0, colonIndex).trim();
				line = line.substring(colonIndex + 1, line.length()).trim();
			}

			colonIndex = line.indexOf(':');
			if(colonIndex != -1)
			{
				id = line.substring(0, colonIndex).trim();
				line = line.substring(colonIndex + 1, line.length()).trim();

				String[] values = id.split(" ");
				id = values[1].trim();
			}

			message = line;

			if(projectFile == null || filePath == null)
			{
				return null;
			}

			int parIndex = filePath.lastIndexOf('(');
			if(parIndex != -1)
			{
				String lineAndNumber = filePath.substring(parIndex + 1, filePath.length() - 1);
				String[] split = lineAndNumber.split(",");
				lineNumber = Integer.parseInt(split[0]);
				columnNumber = Integer.parseInt(split[1]);

				filePath = filePath.substring(0, parIndex);
			}
			return new MSBuildReportLine(filePath, lineNumber, columnNumber, id, message, projectFile);
		}
		catch(Exception e)
		{
			LOGGER.warn("Line: " + line, e);
		}
		return null;
	}

	private final String myFilePath;
	private final int myLine;
	private final int myColumn;
	private final String myId;
	private final String myMessage;
	private final String myProjectPath;

	public MSBuildReportLine(String filePath, int line, int column, String id, String message, String projectPath)
	{
		myFilePath = filePath;
		myLine = line;
		myColumn = column;
		myId = id;
		myMessage = message;
		myProjectPath = projectPath;
	}

	public String getId()
	{
		return myId;
	}

	public String getFilePath()
	{
		return myFilePath;
	}

	public int getLine()
	{
		return myLine;
	}

	public int getColumn()
	{
		return myColumn;
	}

	public String getMessage()
	{
		return myMessage;
	}

	public String getProjectPath()
	{
		return myProjectPath;
	}
}
