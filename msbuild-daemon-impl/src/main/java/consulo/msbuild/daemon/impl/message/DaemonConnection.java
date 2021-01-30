package consulo.msbuild.daemon.impl.message;

import consulo.msbuild.daemon.impl.logging.MSBuildLoggingSession;
import consulo.msbuild.daemon.impl.message.model.DataObject;
import consulo.msbuild.daemon.impl.message.model.RunProjectRequest;
import consulo.msbuild.daemon.impl.network.MSBuildSocketThread;
import consulo.msbuild.daemon.impl.step.BaseRunProjectStep;
import consulo.msbuild.daemon.impl.step.DaemonStep;
import consulo.util.concurrent.AsyncResult;
import consulo.util.lang.Pair;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author VISTALL
 * @since 13/12/2020
 */
public class DaemonConnection
{
	private final MSBuildSocketThread mySocketThread;

	private AtomicInteger myNextId = new AtomicInteger();

	private Map<Integer, Pair<DaemonMessage, AsyncResult>> myHandlers = new ConcurrentHashMap<>();

	//private Future<?> myPingFutures = CompletableFuture.completedFuture(null);

	public DaemonConnection(MSBuildSocketThread socketThread)
	{
		mySocketThread = socketThread;

		// TODO timeout handler drop
//		myPingFutures = AppExecutorUtil.getAppScheduledExecutorService().scheduleWithFixedDelay(() -> {
//
//			///	sendWithResponse(Ping.INSTANCE);
//
//		}, 5, 5, TimeUnit.SECONDS);
	}

	public void dispose()
	{
		//myPingFutures.cancel(false);
	}

	public void prepareLogging(DaemonMessage request, DaemonStep step, MSBuildLoggingSession loggingSession)
	{
		if(step instanceof BaseRunProjectStep && ((BaseRunProjectStep) step).wantLogging())
		{
			RunProjectRequest runProjectRequest = (RunProjectRequest) request;

			Objects.requireNonNull(loggingSession, "logging session must initialized first");

			runProjectRequest.LogWriterId = loggingSession.getId();
		}
	}

	@Nonnull
	public <R extends DataObject> AsyncResult<R> sendWithResponse(DaemonMessage<R> message) throws IOException
	{
		AsyncResult<R> result = AsyncResult.undefined();

		BinaryMessage binaryMessage = new BinaryMessage(myNextId.incrementAndGet(), message);

		byte[] array = binaryMessage.toByteArray();

		ByteBuffer byteBuffer = ByteBuffer.allocateDirect(array.length + 4 + 1);

		byteBuffer.order(ByteOrder.LITTLE_ENDIAN);

		byteBuffer.put((byte) 1);

		byteBuffer.putInt(array.length);

		byteBuffer.put(array);

		mySocketThread.write(byteBuffer);

		//System.out.println("send " + binaryMessage + ", is success " + channelFuture.isSuccess());

		myHandlers.put(binaryMessage.Id, Pair.createNonNull(message, result));

		return result;
	}

	@Nullable
	public Pair<DaemonMessage, AsyncResult> getHandler(int id)
	{
		return myHandlers.remove(id);
	}
}
