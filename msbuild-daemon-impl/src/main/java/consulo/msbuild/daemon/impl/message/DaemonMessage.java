package consulo.msbuild.daemon.impl.message;

import consulo.msbuild.daemon.impl.message.model.DataObject;

/**
 * @author VISTALL
 * @since 13/12/2020
 */
public interface DaemonMessage<RESPONSE extends DataObject> extends DataObject
{
	default String getName()
	{
		return "MonoDevelop.Projects.MSBuild." + getClass().getSimpleName();
	}

	default String getTarget()
	{
		return "";
	}
}
