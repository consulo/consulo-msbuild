package consulo.msbuild.compiler;

import com.intellij.openapi.compiler.CompileContext;
import com.intellij.openapi.compiler.CompileScope;
import com.intellij.openapi.compiler.TranslatingCompiler;
import com.intellij.openapi.fileTypes.FileType;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.util.Chunk;
import consulo.msbuild.daemon.impl.MSBuildDaemonService;
import consulo.msbuild.daemon.impl.step.DaemonStep;
import consulo.msbuild.daemon.impl.step.InitializeProjectStep;
import consulo.msbuild.daemon.impl.step.RunTargetProjectStep;
import consulo.msbuild.module.extension.MSBuildSolutionModuleExtension;
import consulo.msbuild.solution.model.WProject;

import javax.annotation.Nonnull;
import java.util.*;

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
		Set<Module> nodes = chunk.getNodes();

		MSBuildSolutionModuleExtension<?> solutionExtension = MSBuildSolutionModuleExtension.getSolutionModuleExtension(compileContext.getProject());

		if(solutionExtension == null)
		{
			return;
		}

		Map<WProject, Module> map = new LinkedHashMap<>();
		for(Module module : nodes)
		{
			for(WProject wProject : solutionExtension.getProjects())
			{
				if(module.getName().equals(wProject.getName()))
				{
					map.put(wProject, module);
				}
			}
		}

		List<DaemonStep> steps = new ArrayList<>();

		for(WProject wProject : map.keySet())
		{
			steps.add(new InitializeProjectStep(wProject));
		}

		for(WProject project : map.keySet())
		{
			steps.add(new RunTargetProjectStep(project, "Build"));
		}

		MSBuildDaemonService daemonService = MSBuildDaemonService.getInstance(compileContext.getProject());

		daemonService.runSteps(steps, false).getResultSync();
	}

	@Nonnull
	@Override
	public FileType[] getInputFileTypes()
	{
		return FileType.EMPTY_ARRAY;
	}

	@Nonnull
	@Override
	public FileType[] getOutputFileTypes()
	{
		return FileType.EMPTY_ARRAY;
	}

	@Nonnull
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
