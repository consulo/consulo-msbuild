package consulo.msbuild.impl.compiler;

import consulo.annotation.component.ExtensionImpl;
import consulo.compiler.scope.CompileModuleScopeFactory;
import consulo.compiler.scope.FileIndexCompileScope;
import consulo.compiler.scope.ModuleRootCompileScope;
import consulo.language.util.ModuleUtilCore;
import consulo.module.Module;
import consulo.msbuild.module.extension.MSBuildProjectModuleExtension;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * @author VISTALL
 * @since 2018-02-06
 */
@ExtensionImpl
public class MSBuildCompileModuleScopeFactory implements CompileModuleScopeFactory
{
	@Nullable
	@Override
	public FileIndexCompileScope createScope(@Nonnull Module module, boolean includeDependentModules)
	{
		MSBuildProjectModuleExtension extension = ModuleUtilCore.getExtension(module, MSBuildProjectModuleExtension.class);
		if(extension != null)
		{
			return new ModuleRootCompileScope(module, includeDependentModules);
		}
		return null;
	}
}
