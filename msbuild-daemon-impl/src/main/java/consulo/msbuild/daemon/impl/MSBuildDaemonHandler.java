package consulo.msbuild.daemon.impl;

import com.intellij.openapi.util.AsyncResult;
import com.intellij.util.concurrency.AppExecutorUtil;
import consulo.logging.Logger;
import consulo.msbuild.daemon.impl.message.BinaryMessage;
import consulo.msbuild.daemon.impl.message.DaemonConnection;
import consulo.msbuild.daemon.impl.message.DaemonMessage;
import consulo.util.lang.Pair;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import javax.annotation.Nonnull;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/**
 * @author VISTALL
 * @since 12/12/2020
 */
public class MSBuildDaemonHandler extends ChannelInboundHandlerAdapter
{
	private static final Logger LOG = Logger.getInstance(MSBuildDaemonHandler.class);

	private final MSBuildDaemonService myDaemonService;

	private AsyncResult<DaemonConnection> myChannelAsync = AsyncResult.undefined();

	public MSBuildDaemonHandler(MSBuildDaemonService daemonService)
	{
		myDaemonService = daemonService;
	}

	@Override
	@SuppressWarnings("unchecked")
	public void channelRead(ChannelHandlerContext ctx, Object msg)
	{
		ByteBuf byteBuf = (ByteBuf) msg;

		byte type = byteBuf.readByte();

		int arraySize = byteBuf.readIntLE();

		byte[] data = new byte[arraySize];

		byteBuf.readBytes(data);

		ByteBuffer buffer = ByteBuffer.wrap(data).order(ByteOrder.LITTLE_ENDIAN);

		BinaryMessage binaryMessage = new BinaryMessage(buffer);

		System.out.println("receive " + binaryMessage.toString() + " left " + byteBuf.readableBytes());

		switch(binaryMessage.Name)
		{
			case "Connect":
				myChannelAsync.setDone(new DaemonConnection(ctx.channel()));
				break;
			default:
				DaemonConnection result = myChannelAsync.getResult();

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


	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception
	{
		LOG.error(cause);
	}

	@Nonnull
	public AsyncResult<DaemonConnection> connectAsync()
	{
		return myChannelAsync;
	}
}
