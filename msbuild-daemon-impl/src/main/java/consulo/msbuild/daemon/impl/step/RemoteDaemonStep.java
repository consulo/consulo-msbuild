package consulo.msbuild.daemon.impl.step;

import consulo.msbuild.daemon.impl.MSBuildDaemonContext;
import consulo.msbuild.daemon.impl.logging.MSBuildLoggingSession;
import consulo.msbuild.daemon.impl.message.DaemonConnection;
import consulo.msbuild.daemon.impl.message.DaemonMessage;
import consulo.msbuild.daemon.impl.message.model.DataObject;
import consulo.util.concurrent.AsyncResult;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import java.io.IOException;

/**
 * @author VISTALL
 * @since 01/01/2021
 */
public abstract class RemoteDaemonStep<Request extends DaemonMessage<Response>, Response extends DataObject> implements DaemonStep
{
	@Nonnull
	public abstract Request prepareRequest(@Nonnull MSBuildDaemonContext context);

	public abstract void handleResponse(@Nonnull MSBuildDaemonContext context, @Nonnull Response response);

	@SuppressWarnings("unchecked")
	@Override
	public void execute(@Nonnull MSBuildDaemonContext context, @Nonnull DaemonConnection connection, @Nullable MSBuildLoggingSession loggingSession) throws IOException
	{
		DaemonMessage request = prepareRequest(context);

		connection.prepareLogging(request, this, loggingSession);

		AsyncResult<DataObject> result = connection.sendWithResponse(request);
		DataObject object = (DataObject) result.getResultSync();

		if(object != null)
		{
			handleResponse(context, (Response) object);
		}
		else
		{
			// todo error
			throw new IOException("failed");
		}
	}
}
