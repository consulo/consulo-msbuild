package consulo.msbuild.daemon.impl.logging;

import consulo.application.progress.ProgressIndicator;
import consulo.build.ui.BuildDescriptor;
import consulo.build.ui.BuildViewManager;
import consulo.build.ui.DefaultBuildDescriptor;
import consulo.build.ui.event.MessageEvent;
import consulo.build.ui.progress.BuildProgress;
import consulo.build.ui.progress.BuildProgressDescriptor;
import consulo.disposer.Disposable;
import consulo.localize.LocalizeValue;
import consulo.msbuild.daemon.impl.message.model.LogMessage;
import consulo.project.Project;
import consulo.util.lang.StringUtil;
import org.jetbrains.annotations.Nls;

import jakarta.annotation.Nonnull;

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
			if(!isNewLinePosition && !StringUtil.startsWithChar(message, '\r'))
			{
				text = '\n' + text;
			}
			isNewLinePosition = StringUtil.endsWithLineBreak(message);
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
	private final LocalizeValue myLoggingGroup;

	private final BuildProgress<BuildProgressDescriptor> myBuildProgress;

	private final ConsolePrinter myConsolePrinter;

	public MSBuildLoggingSession(int id, Project project, @Nonnull LocalizeValue loggingGroup)
	{
		myId = id;
		myProject = project;
		myLoggingGroup = loggingGroup;
		myBuildProgress = BuildViewManager.getInstance(project).createBuildProgress();
		myConsolePrinter = new ConsolePrinter(myBuildProgress);
	}

	public int getId()
	{
		return myId;
	}

	public void acceptMessage(LogMessage logMessage)
	{
		acceptMessage(logMessage.LogText, MessageEvent.Kind.INFO);
	}

	public void acceptMessage(String text, MessageEvent.Kind kind)
	{
		myConsolePrinter.print(text, kind);
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
				return myLoggingGroup.get();
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
