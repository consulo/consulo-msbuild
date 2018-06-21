package consulo.msbuild.importProvider.item;

import javax.annotation.Nonnull;

import com.intellij.openapi.projectRoots.SdkType;

/**
 * @author VISTALL
 * @since 2018-06-22
 */
public class UnknownBuildDotNetImportTarget extends MSBuildDotNetImportTarget
{
	public static final UnknownBuildDotNetImportTarget INSTANCE = new UnknownBuildDotNetImportTarget();

	public UnknownBuildDotNetImportTarget()
	{
		super("??", "");
	}

	@Nonnull
	@Override
	public SdkType getSdkType()
	{
		throw new UnsupportedOperationException();
	}
}
