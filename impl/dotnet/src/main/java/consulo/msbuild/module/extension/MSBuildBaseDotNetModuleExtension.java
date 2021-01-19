package consulo.msbuild.module.extension;

import consulo.annotation.access.RequiredReadAction;
import consulo.dotnet.debugger.DotNetModuleExtensionWithDebug;
import consulo.dotnet.module.extension.BaseDotNetModuleExtension;
import consulo.dotnet.module.extension.DotNetModuleExtension;
import consulo.msbuild.compiler.MSBuildCompileContext;
import consulo.roots.ModuleRootLayer;
import consulo.util.lang.StringUtil;
import org.jdom.Element;

import javax.annotation.Nonnull;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * @author VISTALL
 * @since 19/01/2021
 */
public abstract class MSBuildBaseDotNetModuleExtension<T extends MSBuildBaseDotNetModuleExtension<T>> extends BaseDotNetModuleExtension<T> implements DotNetModuleExtension<T>,
		DotNetModuleExtensionWithDebug, MSBuildProjectModuleExtension<T>
{
	private Set<String> myTargets = new LinkedHashSet<>();

	public MSBuildBaseDotNetModuleExtension(@Nonnull String id, @Nonnull ModuleRootLayer moduleRootLayer)
	{
		super(id, moduleRootLayer);
	}

	public void setTargets(@Nonnull Set<String> targets)
	{
		myTargets = new LinkedHashSet<>(targets);
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
		return super.isModifiedImpl(ex) || !myTargets.equals(ex.getTargets());
	}

	@Override
	public void commit(@Nonnull T mutableModuleExtension)
	{
		super.commit(mutableModuleExtension);
		setTargets(mutableModuleExtension.getTargets());
	}

	@Override
	protected void getStateImpl(@Nonnull Element element)
	{
		super.getStateImpl(element);

		element.addContent(new Element("msbuild-targets").setText(String.join(",", myTargets)));
	}

	@RequiredReadAction
	@Override
	protected void loadStateImpl(@Nonnull Element element)
	{
		super.loadStateImpl(element);

		String targets = element.getChildText("msbuild-targets");
		if(!StringUtil.isEmptyOrSpaces(targets))
		{
			String[] values = targets.split(",");
			setTargets(Set.of(values));
		}
	}

	@Nonnull
	@Override
	public Set<String> getTargets()
	{
		return myTargets;
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
