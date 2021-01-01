package consulo.msbuild.daemon.impl.message.model;

import consulo.msbuild.daemon.impl.message.DaemonMessage;

/**
 * @author VISTALL
 * @since 01/01/2021
 */
public class GetProjectItemsRequest implements DaemonMessage<GetProjectItemsResponse>
{
	public int TaskId;

	public int ProjectId;

	public ProjectConfigurationInfo[] Configurations;
}
