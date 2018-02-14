package consulo.msbuild.compiler;

import org.jetbrains.annotations.NotNull;
import com.intellij.openapi.compiler.CompileContext;
import com.intellij.openapi.compiler.CompileScope;
import com.intellij.openapi.compiler.TranslatingCompiler;
import com.intellij.openapi.fileTypes.FileType;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.util.Chunk;

/**
 * @author VISTALL
 * @since 2018-02-06
 */
public class MSBuildCompiler implements TranslatingCompiler
{
	@Override
	public boolean isCompilableFile(VirtualFile virtualFile, CompileContext compileContext)
	{
		return true;
	}

	@Override
	public void compile(CompileContext compileContext, Chunk<Module> chunk, VirtualFile[] virtualFiles, OutputSink outputSink)
	{
		Module module = chunk.getNodes().iterator().next();

		System.out.println("test");
	}

	@NotNull
	@Override
	public FileType[] getInputFileTypes()
	{
		return FileType.EMPTY_ARRAY;
	}

	@NotNull
	@Override
	public FileType[] getOutputFileTypes()
	{
		return FileType.EMPTY_ARRAY;
	}

	@NotNull
	@Override
	public String getDescription()
	{
		return "MSBuild Compiler";
	}

	@Override
	public boolean validateConfiguration(CompileScope compileScope)
	{
		return true;
	}
}
