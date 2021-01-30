package consulo.msbuild.daemon.impl.message.model;

import consulo.msbuild.daemon.impl.message.DaemonMessage;

/**
 * @author VISTALL
 * @since 16/01/2021
 */
public class GetPropertiesRequest implements DaemonMessage<GetPropertiesResponse>
{
	public int TaskId;

	public int ProjectId;

	public ProjectConfigurationInfo[] Configurations;
}
