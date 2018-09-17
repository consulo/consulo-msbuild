package consulo.msbuild.compiler;

import java.io.File;
import java.io.IOException;
import java.util.List;

import com.intellij.openapi.compiler.CompileContext;
import com.intellij.openapi.compiler.CompilerMessageCategory;
import com.intellij.openapi.util.io.FileUtil;
import com.intellij.openapi.vfs.LocalFileSystem;
import com.intellij.openapi.vfs.VirtualFile;

/**
 * @author VISTALL
 * @since 2018-09-17
 */
public class MSBuildReporter
{
	public static void report(MSBuildCompileContext context)
	{
		File tempErrorFile = context.getTempErrorFile();
		File tempWarningFile = context.getTempWarningFile();

		try
		{
			List<String> errors = FileUtil.loadLines(tempErrorFile);
			FileUtil.asyncDelete(tempErrorFile);

			for(String error : errors)
			{
				report(CompilerMessageCategory.ERROR, error, context.getCompileContext());
			}

			List<String> warnings = FileUtil.loadLines(tempWarningFile);
			FileUtil.asyncDelete(tempWarningFile);

			for(String warning : warnings)
			{
				report(CompilerMessageCategory.WARNING, warning, context.getCompileContext());
			}
		}
		catch(IOException e)
		{
			throw new IllegalArgumentException(e);
		}
	}

	private static void report(CompilerMessageCategory category, String line, CompileContext compileContext)
	{
		MSBuildReportLine reportLine = MSBuildReportLine.parse(line);

		if(reportLine == null)
		{
			return;
		}

		VirtualFile projectFile = LocalFileSystem.getInstance().findFileByPath(reportLine.getProjectPath());
		if(projectFile == null)
		{
			return;
		}

		VirtualFile parent = projectFile.getParent();

		VirtualFile target = null;
		if(FileUtil.isAbsolute(reportLine.getFilePath()))
		{
			target = LocalFileSystem.getInstance().findFileByPath(reportLine.getFilePath());
		}
		else
		{
			target = parent.findFileByRelativePath(FileUtil.toSystemIndependentName(reportLine.getFilePath()));
		}

		if(target == null)
		{
			return;
		}

		compileContext.addMessage(category, reportLine.getId() + ": " + reportLine.getMessage(), target.getUrl(), reportLine.getLine(), reportLine.getColumn());
	}
}
