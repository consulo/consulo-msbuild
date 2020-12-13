package consulo.msbuild.daemon.impl.message.model;

import consulo.msbuild.daemon.impl.message.BinaryMessage;
import consulo.msbuild.daemon.impl.message.DaemonMessage;

/**
 * @author VISTALL
 * @since 13/12/2020
 */
public class Ping implements DaemonMessage<BinaryMessage>
{
	public static final Ping INSTANCE = new Ping();

	@Override
	public String getName()
	{
		return "Ping";
	}

	@Override
	public String getTarget()
	{
		return "Process";
	}
}
