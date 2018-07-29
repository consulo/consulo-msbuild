package consulo.msbuild.compiler;

import java.util.function.Consumer;

import javax.annotation.Nonnull;

import com.intellij.openapi.util.io.FileUtil;
import com.intellij.openapi.vfs.VirtualFile;
import consulo.msbuild.module.extension.MSBuildRootExtension;

/**
 * @author VISTALL
 * @since 2018-06-24
 */
public class MSBuildCompileContext
{
	private MSBuildRootExtension myExtension;
	private VirtualFile mySolutionFile;
	private String myProjectName;

	public MSBuildCompileContext(@Nonnull MSBuildRootExtension extension, @Nonnull VirtualFile solutionFile, @Nonnull String projectName)
	{
		myExtension = extension;
		mySolutionFile = solutionFile;
		myProjectName = projectName;
	}

	@Nonnull
	public MSBuildRootExtension getExtension()
	{
		return myExtension;
	}

	@Nonnull
	public VirtualFile getSolutionFile()
	{
		return mySolutionFile;
	}

	@Nonnull
	public String getProjectName()
	{
		return myProjectName;
	}

	public void addDefaultArguments(Consumer<String> args)
	{
		args.accept("/consoleloggerparameters:ErrorsOnly;WarningsOnly;NoSummary;DisableMPLogging;ForceNoAlign");
		args.accept("/nologo");

		args.accept(FileUtil.toSystemDependentName(mySolutionFile.getPath()));
		args.accept("/t:" + myProjectName.replace(".", "_"));
		args.accept("/p:BuildProjectReferences=false");
		//args.accept("/p:Configuration=\"Release\"");
		//args.accept("/p:Platform=\"x86\"");
	}
}
