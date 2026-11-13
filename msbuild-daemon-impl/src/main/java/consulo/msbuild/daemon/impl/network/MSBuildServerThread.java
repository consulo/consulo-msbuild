package consulo.msbuild.daemon.impl.network;

import consulo.logging.Logger;
import consulo.msbuild.daemon.impl.MSBuildDaemonService;
import consulo.platform.PlatformServerSocket;
import consulo.platform.PlatformSocket;
import consulo.util.io.StreamUtil;

import java.io.IOException;
import java.util.function.Consumer;

/**
 * @author VISTALL
 * @since 21/01/2021
 */
public class MSBuildServerThread extends Thread
{
	private static final Logger LOG = Logger.getInstance(MSBuildServerThread.class);

	private final MSBuildDaemonService myDaemonService;
	private final Consumer<MSBuildSocketThread> myConnectionAcceptor;

	private boolean myShutdown;

	private final PlatformServerSocket myServerSocket;

	/**
	 * @param serverSocket a server socket of the machine of the daemon
	 */
	public MSBuildServerThread(MSBuildDaemonService daemonService, PlatformServerSocket serverSocket, Consumer<MSBuildSocketThread> connectionAcceptor)
	{
		super("MSBuildServerThread: " + serverSocket.getPort());
		myDaemonService = daemonService;
		myServerSocket = serverSocket;
		myConnectionAcceptor = connectionAcceptor;

		setDaemon(true);

		start();
	}

	public void shutdown()
	{
		myShutdown = true;
		StreamUtil.closeStream(myServerSocket);
	}

	@Override
	public void run()
	{
		while(!myShutdown)
		{
			try
			{
				PlatformSocket socket = myServerSocket.accept();

				MSBuildSocketThread socketThread = new MSBuildSocketThread(socket, myDaemonService);
				myConnectionAcceptor.accept(socketThread);
				socketThread.start();
			}
			catch(IOException e)
			{
				if(!myShutdown)
				{
					LOG.warn(e);
				}
			}
		}
	}
}
