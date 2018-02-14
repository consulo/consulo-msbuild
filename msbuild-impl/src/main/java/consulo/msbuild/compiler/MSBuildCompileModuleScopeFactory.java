package consulo.msbuild.compiler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.intellij.compiler.impl.FileIndexCompileScope;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.ModuleUtilCore;
import consulo.compiler.impl.CompileModuleScopeFactory;
import consulo.compiler.impl.ModuleRootCompileScope;
import consulo.msbuild.module.extension.MSBuildRootExtension;

/**
 * @author VISTALL
 * @since 2018-02-06
 */
public class MSBuildCompileModuleScopeFactory implements CompileModuleScopeFactory
{
	@Nullable
	@Override
	public FileIndexCompileScope createScope(@Nonnull Module module, boolean includeDependentModules)
	{
		MSBuildRootExtension extension = ModuleUtilCore.getExtension(module, MSBuildRootExtension.class);
		if(extension != null)
		{
			return new ModuleRootCompileScope(module, includeDependentModules);
		}
		return null;
	}
}
