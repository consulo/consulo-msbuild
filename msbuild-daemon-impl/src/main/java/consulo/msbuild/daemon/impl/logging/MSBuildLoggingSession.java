package consulo.msbuild.daemon.impl.logging;

import com.intellij.build.BuildDescriptor;
import com.intellij.build.BuildViewManager;
import com.intellij.build.DefaultBuildDescriptor;
import com.intellij.build.events.MessageEvent;
import com.intellij.build.progress.BuildProgress;
import com.intellij.build.progress.BuildProgressDescriptor;
import com.intellij.openapi.progress.ProgressIndicator;
import com.intellij.openapi.project.Project;
import consulo.disposer.Disposable;
import consulo.msbuild.daemon.impl.message.model.LogMessage;
import consulo.util.lang.StringUtil;
import org.jetbrains.annotations.Nls;

import javax.annotation.Nonnull;

/**
 * @author VISTALL
 * @since 20/01/2021
 */
public class MSBuildLoggingSession implements Disposable
{
	private static final String ANSI_RESET = "\u001B[0m";
	private static final String ANSI_RED = "\u001B[31m";
	private static final String ANSI_YELLOW = "\u001B[33m";
	private static final String ANSI_BOLD = "\u001b[1m";

	private static class ConsolePrinter
	{
		@Nonnull
		private final BuildProgress<BuildProgressDescriptor> progress;
		private volatile boolean isNewLinePosition = true;

		private ConsolePrinter(@Nonnull BuildProgress<BuildProgressDescriptor> progress)
		{
			this.progress = progress;
		}

		private void print(@Nonnull String message, @Nonnull MessageEvent.Kind kind)
		{
			String text = wrapWithAnsiColor(kind, message);
			if(!isNewLinePosition && !com.intellij.openapi.util.text.StringUtil.startsWithChar(message, '\r'))
			{
				text = '\n' + text;
			}
			isNewLinePosition = com.intellij.openapi.util.text.StringUtil.endsWithLineBreak(message);
			progress.output(text, kind != MessageEvent.Kind.ERROR);
		}

		@Nls
		private static String wrapWithAnsiColor(MessageEvent.Kind kind, @Nls String message)
		{
			if(kind == MessageEvent.Kind.SIMPLE)
			{
				return message;
			}
			String color;
			if(kind == MessageEvent.Kind.ERROR)
			{
				color = ANSI_RED;
			}
			else if(kind == MessageEvent.Kind.WARNING)
			{
				color = ANSI_YELLOW;
			}
			else
			{
				color = ANSI_BOLD;
			}
			final String ansiReset = ANSI_RESET;
			return color + message + ansiReset;
		}
	}

	private final int myId;
	private final Project myProject;
	private final String myLoggingGroup;

	private final BuildProgress<BuildProgressDescriptor> myBuildProgress;

	private final ConsolePrinter myConsolePrinter;

	public MSBuildLoggingSession(int id, Project project, String loggingGroup)
	{
		myId = id;
		myProject = project;
		myLoggingGroup = loggingGroup;
		myBuildProgress = BuildViewManager.createBuildProgress(project);
		myConsolePrinter = new ConsolePrinter(myBuildProgress);
	}

	public int getId()
	{
		return myId;
	}

	public void acceptMessage(LogMessage logMessage)
	{
		myConsolePrinter.print(logMessage.LogText, MessageEvent.Kind.INFO);
	}

	public void start(ProgressIndicator indicator)
	{
		DefaultBuildDescriptor buildDescriptor = new DefaultBuildDescriptor(myId, "MSBuild", StringUtil.notNullize(myProject.getBasePath()), System.currentTimeMillis());
		myBuildProgress.start(new BuildProgressDescriptor()
		{
			@Nonnull
			@Override
			public String getTitle()
			{
				return myLoggingGroup;
			}

			@Override
			@Nonnull
			public BuildDescriptor getBuildDescriptor()
			{
				return buildDescriptor;
			}
		});
	}

	public void stop()
	{
		myBuildProgress.finish(System.currentTimeMillis());
	}

	@Override
	public void dispose()
	{

	}
}
