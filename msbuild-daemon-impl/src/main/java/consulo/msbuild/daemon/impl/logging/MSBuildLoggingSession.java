package consulo.msbuild.daemon.impl.logging;

import com.intellij.execution.filters.TextConsoleBuilder;
import com.intellij.execution.filters.TextConsoleBuilderFactory;
import com.intellij.execution.ui.ConsoleView;
import com.intellij.execution.ui.ConsoleViewContentType;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.ZipperUpdater;
import com.intellij.ui.content.Content;
import com.intellij.ui.content.ContentManager;
import com.intellij.ui.content.MessageView;
import com.intellij.util.Alarm;
import consulo.disposer.Disposable;
import consulo.msbuild.daemon.impl.message.model.LogMessage;
import consulo.ui.UIAccess;
import consulo.util.dataholder.Key;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * @author VISTALL
 * @since 20/01/2021
 */
public class MSBuildLoggingSession implements Disposable
{
	private static final Key<MSBuildLoggingSession> KEY = Key.create("MSBuildLoggingSession");

	private final int myId;
	private final Project myProject;

	private ZipperUpdater myZipperUpdater = new ZipperUpdater(1000, Alarm.ThreadToUse.SWING_THREAD, this);

	private Queue<LogMessage> myLogMessages = new ConcurrentLinkedQueue<>();

	public MSBuildLoggingSession(int id, Project project)
	{
		myId = id;
		myProject = project;
	}

	public int getId()
	{
		return myId;
	}

	public void acceptMessage(LogMessage logMessage)
	{
		myLogMessages.add(logMessage);

		myZipperUpdater.queue(this::flush);
	}

	public void flush()
	{
		MessageView messageView = MessageView.SERVICE.getInstance(myProject);
		messageView.runWhenInitialized(() -> printToConsole(messageView));
	}

	private void printToConsole(MessageView messageView)
	{
		List<LogMessage> messages = new ArrayList<>();
		LogMessage target = null;

		while((target = myLogMessages.poll()) != null)
		{
			messages.add(target);
		}

		if(messages.isEmpty())
		{
			return;
		}

		ConsoleView console = getConsole(messageView);
		for(LogMessage message : messages)
		{
			console.print(message.LogText, ConsoleViewContentType.NORMAL_OUTPUT);
		}
	}

	private ConsoleView getConsole(MessageView messageView)
	{
		ContentManager contentManager = messageView.getContentManager();
		Content[] contents = contentManager.getContents();
		for(Content content : contents)
		{
			MSBuildLoggingSession data = content.getUserData(KEY);
			if(data == this)
			{
				MSBuildLoggingPanel component = (MSBuildLoggingPanel) content.getComponent();
				return component.getConsoleView();
			}
		}

		TextConsoleBuilder builder = TextConsoleBuilderFactory.getInstance().createBuilder(myProject);

		ConsoleView console = builder.getConsole();

		Content content = contentManager.getFactory().createContent(new MSBuildLoggingPanel(console), "MSBuild", false);
		content.putUserData(KEY, this);
		contentManager.addContent(content);
		contentManager.setSelectedContent(content);

		UIAccess uiAccess = UIAccess.current();

		uiAccess.give(() -> {
			messageView.getToolWindow().activate(null);
		});
		return console;
	}

	@Override
	public void dispose()
	{

	}
}
