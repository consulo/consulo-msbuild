package consulo.msbuild.compiler;

import javax.annotation.Nonnull;

import com.intellij.compiler.impl.FileIndexCompileScope;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.roots.FileIndex;

/**
 * @author VISTALL
 * @since 2018-02-06
 */
public class MSBuildCompileScope extends FileIndexCompileScope
{
	@Override
	protected FileIndex[] getFileIndices()
	{
		return new FileIndex[0];
	}

	@Override
	public boolean belongs(String url)
	{
		return false;
	}

	@Nonnull
	@Override
	public Module[] getAffectedModules()
	{
		return new Module[0];
	}
}
