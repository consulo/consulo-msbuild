package consulo.msbuild.daemon.impl.network;

import consulo.logging.Logger;
import consulo.msbuild.daemon.impl.MSBuildDaemonService;
import consulo.util.io.StreamUtil;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
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

	private ServerSocket myServerSocket;

	public MSBuildServerThread(MSBuildDaemonService daemonService, int port, Consumer<MSBuildSocketThread> connectionAcceptor)
	{
		super("MSBuildServerThread: " + port);
		myDaemonService = daemonService;
		myConnectionAcceptor = connectionAcceptor;

		setDaemon(true);

		try
		{
			myServerSocket = new ServerSocket(port, 0, InetAddress.getLoopbackAddress());

			start();
		}
		catch(IOException e)
		{
			throw new IllegalArgumentException(e);
		}
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
				Socket socket = myServerSocket.accept();

				MSBuildSocketThread socketThread = new MSBuildSocketThread(socket, myDaemonService);
				myConnectionAcceptor.accept(socketThread);
				socketThread.start();
			}
			catch(IOException e)
			{
				LOG.warn(e);
			}
		}
	}
}
