package consulo.msbuild.daemon.impl.network;

import com.intellij.util.concurrency.AppExecutorUtil;
import consulo.logging.Logger;
import consulo.msbuild.daemon.impl.MSBuildDaemonService;
import consulo.msbuild.daemon.impl.message.BinaryMessage;
import consulo.msbuild.daemon.impl.message.DaemonConnection;
import consulo.msbuild.daemon.impl.message.DaemonMessage;
import consulo.msbuild.daemon.impl.message.model.LogMessage;
import consulo.util.concurrent.AsyncResult;
import consulo.util.lang.Pair;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Objects;
import java.util.concurrent.locks.LockSupport;

/**
 * @author VISTALL
 * @since 21/01/2021
 */
public class MSBuildSocketThread extends Thread
{
	private static final Logger LOG = Logger.getInstance(MSBuildSocketThread.class);

	private final Socket mySocket;
	private final MSBuildDaemonService myDaemonService;

	private final InputStream myInputStream;
	private final OutputStream myOutputStream;

	private DaemonConnection myDaemonConnection;

	private boolean myConnectAccepted;
	private Thread myParkThread;

	public MSBuildSocketThread(Socket socket, MSBuildDaemonService daemonService) throws IOException
	{
		super("MSBuildClientReader: " + socket);
		mySocket = socket;
		myDaemonService = daemonService;
		myInputStream = socket.getInputStream();
		myOutputStream = socket.getOutputStream();
	}

	public void setDaemonConnection(DaemonConnection daemonConnection)
	{
		myDaemonConnection = daemonConnection;
	}

	@Override
	public void run()
	{
		while(mySocket.isConnected())
		{
			try
			{
				int type = myInputStream.read();
				if(type == -1)
				{
					break;
				}

				int length = readInt(myInputStream);

				byte[] data = myInputStream.readNBytes(length);

				ByteBuffer buffer = ByteBuffer.wrap(data);

				buffer.order(ByteOrder.LITTLE_ENDIAN);

				process(buffer);
			}
			catch(IOException e)
			{
				LOG.warn(e);
				break;
			}
		}
	}

	public void waitForConnect()
	{
		if(!myConnectAccepted)
		{
			myParkThread = Thread.currentThread();

			LockSupport.park();
		}
	}

	private void process(ByteBuffer buffer)
	{
		BinaryMessage binaryMessage = new BinaryMessage(buffer);

		//System.out.println("receive " + binaryMessage.toString() + " left " + leftBytes);

		switch(binaryMessage.Name)
		{
			case "Connect":
				if(!myConnectAccepted)
				{
					myConnectAccepted = true;
					Thread parkThread = myParkThread;
					myParkThread = null;
					LockSupport.unpark(Objects.requireNonNull(parkThread));
				}
				break;
			case "Error":
			{
				Object message = binaryMessage.Arguments.get("Message");

				AppExecutorUtil.getAppExecutorService().execute(() ->
				{
					Pair<DaemonMessage, AsyncResult> handler = myDaemonConnection.getHandler(binaryMessage.Id);
					if(handler != null)
					{
						handler.getSecond().rejectWithThrowable(new IOException((String) message));
					}
				});
				break;
			}
			case "MonoDevelop.Projects.MSBuild.LogMessage":
			{
				LogMessage logMessage = binaryMessage.toModel(LogMessage.class);

				myDaemonService.acceptLogMessage(logMessage);
				break;
			}
			default:
			{
				DaemonConnection result = myDaemonConnection;

				AppExecutorUtil.getAppExecutorService().execute(() ->
				{
					Pair<DaemonMessage, consulo.util.concurrent.AsyncResult> handler = result.getHandler(binaryMessage.Id);
					if(handler != null)
					{
						Object object = binaryMessage.toModel(handler.getFirst());
						handler.getSecond().setDone(object);
					}
				});
				break;
			}
		}
	}

	private int readInt(InputStream stream) throws IOException
	{
		int p0 = stream.read();
		int p1 = stream.read();
		int p2 = stream.read();
		int p3 = stream.read();

		return ((p3 & 0xFF) << 24) |
				((p2 & 0xFF) << 16) |
				((p1 & 0xFF) << 8) |
				((p0 & 0xFF) << 0);
	}

	public void write(ByteBuffer byteBuffer) throws IOException
	{
		int position = byteBuffer.position();

		byteBuffer.position(0);

		byte[] data = new byte[position];

		byteBuffer.get(data);
		
		myOutputStream.write(data);
	}
}

