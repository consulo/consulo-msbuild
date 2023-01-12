package consulo.msbuild.daemon.impl.logging;

import consulo.execution.ui.console.ConsoleView;
import consulo.ui.ex.awt.Wrapper;

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
