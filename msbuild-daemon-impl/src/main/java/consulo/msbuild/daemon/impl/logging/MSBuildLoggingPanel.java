package consulo.msbuild.daemon.impl.logging;

import com.intellij.execution.ui.ConsoleView;
import com.intellij.ui.components.panels.Wrapper;

/**
 * @author VISTALL
 * @since 20/01/2021
 */
public class MSBuildLoggingPanel extends Wrapper
{
	private final ConsoleView myConsoleView;

	public MSBuildLoggingPanel(ConsoleView consoleView)
	{
		myConsoleView = consoleView;

		setContent(myConsoleView.getComponent());
	}

	public ConsoleView getConsoleView()
	{
		return myConsoleView;
	}
}
