package consulo.msbuild.daemon.impl.message.model;

import consulo.msbuild.daemon.impl.message.DaemonMessage;
import consulo.msbuild.daemon.impl.message.TypedMap;

import java.util.Map;

/**
 * @author VISTALL
 * @since 13/12/2020
 */
public class RunProjectRequest implements DaemonMessage<RunProjectResponse>
{
	public int ProjectId;

	public String Content;

	public ProjectConfigurationInfo[] Configurations;

	public int LogWriterId;

	public MSBuildEvent EnabledLogEvents;

	public MSBuildVerbosity Verbosity;

	public String[] RunTargets;

	public String[] EvaluateItems;

	public String[] EvaluateProperties;

	public Map<String, String> GlobalProperties = new TypedMap<>(String.class, String.class);

	public int TaskId;

	public String BinLogFilePath;
}
