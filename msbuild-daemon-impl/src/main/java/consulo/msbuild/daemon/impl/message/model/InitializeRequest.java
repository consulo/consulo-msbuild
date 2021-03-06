package consulo.msbuild.daemon.impl.message.model;

import consulo.msbuild.daemon.impl.message.DaemonMessage;
import consulo.msbuild.daemon.impl.message.TypedMap;

/**
 * @author VISTALL
 * @since 13/12/2020
 */
public class InitializeRequest implements DaemonMessage<InitializeRequestResponse>
{
	public int IdeProcessId;

	public String CultureName;

	public String BinDir;

	public TypedMap<String, String> GlobalProperties = new TypedMap<>(String.class, String.class);
}
