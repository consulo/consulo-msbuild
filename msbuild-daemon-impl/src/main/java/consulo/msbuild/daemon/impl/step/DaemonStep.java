package consulo.msbuild.daemon.impl.step;

import consulo.msbuild.daemon.impl.MSBuildDaemonContext;
import consulo.msbuild.daemon.impl.message.DaemonMessage;
import consulo.msbuild.daemon.impl.message.model.DataObject;

import javax.annotation.Nonnull;

/**
 * @author VISTALL
 * @since 01/01/2021
 */
public abstract class DaemonStep<Request extends DaemonMessage<Response>, Response extends DataObject>
{
	@Nonnull
	public abstract Request prepareRequest(@Nonnull MSBuildDaemonContext context);

	public abstract void handleResponse(@Nonnull MSBuildDaemonContext context, @Nonnull Response response);

	public abstract String getStepText();
}
