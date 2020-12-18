package consulo.msbuild.daemon.impl.message.model;

import consulo.msbuild.daemon.impl.message.DaemonMessage;

/**
 * @author VISTALL
 * @since 13/12/2020
 */
public class GetTargetsRequest implements DaemonMessage<GetTargetsResponse>
{
	public int TaskId;

	public int ProjectId;

	public ProjectConfigurationInfo[] Configurations;
}
