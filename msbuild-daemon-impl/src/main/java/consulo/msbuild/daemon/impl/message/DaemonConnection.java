package consulo.msbuild.daemon.impl.message;

import com.intellij.util.concurrency.AppExecutorUtil;
import consulo.msbuild.daemon.impl.message.model.DataObject;
import consulo.msbuild.daemon.impl.message.model.LogMessage;
import consulo.msbuild.daemon.impl.message.model.RunProjectRequest;
import consulo.msbuild.daemon.impl.step.BaseRunProjectStep;
import consulo.msbuild.daemon.impl.step.DaemonStep;
import consulo.util.concurrent.AsyncResult;
import consulo.util.lang.Pair;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.nio.ByteOrder;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author VISTALL
 * @since 13/12/2020
 */
public class DaemonConnection
{
	private AtomicInteger myNextId = new AtomicInteger();

	private final Channel myChannel;

	private Map<Integer, Pair<DaemonMessage, AsyncResult>> myHandlers = new ConcurrentHashMap<>();

	private Map<Integer, BaseRunProjectStep> myLoggingSetup = new ConcurrentHashMap<>();

	private AtomicInteger myLoggerIds = new AtomicInteger();

	private Future<?> myPingFutures = CompletableFuture.completedFuture(null);

	public DaemonConnection(Channel channel)
	{
		myChannel = channel;

		// TODO timeout handler drop
		myPingFutures = AppExecutorUtil.getAppScheduledExecutorService().scheduleWithFixedDelay(() -> {

			///	sendWithResponse(Ping.INSTANCE);

		}, 5, 5, TimeUnit.SECONDS);
	}

	public void dispose()
	{
		myPingFutures.cancel(false);
	}

	public void prepareLogging(DaemonMessage request, DaemonStep step)
	{
		if(step instanceof BaseRunProjectStep)
		{
			RunProjectRequest runProjectRequest = (RunProjectRequest) request;

			int loggerId = myLoggerIds.incrementAndGet();

			runProjectRequest.LogWriterId = loggerId;

			myLoggingSetup.put(loggerId, (BaseRunProjectStep) step);
		}
	}

	@Nonnull
	public synchronized <R extends DataObject> AsyncResult<R> sendWithResponse(DaemonMessage<R> message)
	{
		AsyncResult<R> result = AsyncResult.undefined();

		BinaryMessage binaryMessage = new BinaryMessage(myNextId.incrementAndGet(), message);

		byte[] array = binaryMessage.toByteArray();

		ByteBuf buffer = Unpooled.buffer(array.length + 4 + 1).order(ByteOrder.LITTLE_ENDIAN);

		buffer.writeByte((byte) 1);

		buffer.writeIntLE(array.length);

		buffer.writeBytes(array);

		ChannelFuture channelFuture = myChannel.writeAndFlush(buffer).syncUninterruptibly();

		//System.out.println("send " + binaryMessage + ", is success " + channelFuture.isSuccess());

		myHandlers.put(binaryMessage.Id, Pair.createNonNull(message, result));

		return result;
	}

	@Nullable
	public Pair<DaemonMessage, AsyncResult> getHandler(int id)
	{
		return myHandlers.remove(id);
	}

	public void runLogging(LogMessage logMessage)
	{
		BaseRunProjectStep baseRunProjectStep = myLoggingSetup.get(logMessage.LoggerId);
		if(baseRunProjectStep != null)
		{
			baseRunProjectStep.acceptLogMessage(logMessage);
		}
	}
}
