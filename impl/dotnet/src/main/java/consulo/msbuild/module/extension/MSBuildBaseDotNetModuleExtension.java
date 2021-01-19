package consulo.msbuild.module.extension;

import consulo.annotation.access.RequiredReadAction;
import consulo.dotnet.debugger.DotNetModuleExtensionWithDebug;
import consulo.dotnet.module.extension.BaseDotNetModuleExtension;
import consulo.dotnet.module.extension.DotNetModuleExtension;
import consulo.msbuild.compiler.MSBuildCompileContext;
import consulo.roots.ModuleRootLayer;
import org.jdom.Element;

import javax.annotation.Nonnull;

/**
 * @author VISTALL
 * @since 19/01/2021
 */
public abstract class MSBuildBaseDotNetModuleExtension<T extends MSBuildBaseDotNetModuleExtension<T>> extends BaseDotNetModuleExtension<T> implements DotNetModuleExtension<T>,
		DotNetModuleExtensionWithDebug, MSBuildProjectModuleExtension<T>
{
	public MSBuildBaseDotNetModuleExtension(@Nonnull String id, @Nonnull ModuleRootLayer moduleRootLayer)
	{
		super(id, moduleRootLayer);
	}

	public void setPlatform(String platform)
	{

	}

	public void setConfiguration(String configuration)
	{

	}

	@Override
	public boolean isModifiedImpl(T ex)
	{
		return super.isModifiedImpl(ex);
	}

	@Override
	public void commit(@Nonnull T mutableModuleExtension)
	{
		super.commit(mutableModuleExtension);
	}

	@Override
	protected void getStateImpl(@Nonnull Element element)
	{
		super.getStateImpl(element);
	}

	@RequiredReadAction
	@Override
	protected void loadStateImpl(@Nonnull Element element)
	{
		super.loadStateImpl(element);
	}

	@Override
	public boolean isSupportCompilation()
	{
		return false;
	}

	@Override
	public void build(MSBuildCompileContext context)
	{

	}

	@Override
	public String getConfiguration()
	{
		return null;
	}

	@Override
	public String getPlatform()
	{
		return null;
	}
}
