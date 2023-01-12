package consulo.msbuild.csharp;

import consulo.annotation.component.ExtensionImpl;
import consulo.msbuild.MSBuildProjectFile;

import javax.annotation.Nonnull;

/**
 * @author VISTALL
 * @since 10/01/2023
 */
@ExtensionImpl
public class CSProjProjectFile implements MSBuildProjectFile
{
	@Nonnull
	@Override
	public String getExtension()
	{
		return "csproj";
	}
}
