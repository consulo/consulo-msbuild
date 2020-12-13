package consulo.msbuild.daemon.impl.message.model;

import consulo.msbuild.daemon.impl.message.DaemonMessage;

/**
 * @author VISTALL
 * @since 13/12/2020
 */
public class LoadProjectRequest implements DaemonMessage<LoadProjectResponse>
{
	public String ProjectFile;
}
