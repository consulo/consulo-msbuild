package consulo.msbuild.daemon.impl.message.model;

/**
 * @author VISTALL
 * @since 13/12/2020
 */
public class ProjectConfigurationInfo implements DataObject
{
	public String ProjectFile;

	public String ProjectGuid;

	public String Configuration;

	public String Platform;

	public boolean Enabled = true;

	@Override
	public String toString()
	{
		return "ProjectConfigurationInfo{" +
				"ProjectFile='" + ProjectFile + '\'' +
				", ProjectGuid='" + ProjectGuid + '\'' +
				", Configuration='" + Configuration + '\'' +
				", Platform='" + Platform + '\'' +
				", Enabled=" + Enabled +
				'}';
	}
}
