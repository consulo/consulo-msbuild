package consulo.msbuild;

import com.intellij.execution.configurations.GeneralCommandLine;
import com.intellij.openapi.projectRoots.Sdk;

import javax.annotation.Nonnull;
import java.util.function.Consumer;

/**
 * @author VISTALL
 * @since 01/01/2021
 */
public class DefaultMSBuildProcessProvider implements MSBuildProcessProvider
{
	@Override
	public String getId()
	{
		return "default";
	}

	@Override
	public void fillBundles(Consumer<Sdk> consumer)
	{

	}

	@Nonnull
	@Override
	public GeneralCommandLine buildCommandLine(@Nonnull Sdk sdk, int port)
	{
		return null;
	}

}
