package consulo.msbuild.compiler;

import consulo.compiler.CompileContext;
import consulo.msbuild.module.extension.MSBuildProjectModuleExtension;
import consulo.util.io.FileUtil;
import consulo.virtualFileSystem.VirtualFile;

import javax.annotation.Nonnull;
import java.io.File;
import java.io.IOException;
import java.util.function.Consumer;

/**
 * @author VISTALL
 * @since 2018-06-24
 */
public class MSBuildCompileContext
{
	private final MSBuildProjectModuleExtension myExtension;
	private final VirtualFile mySolutionFile;
	private final String myProjectName;
	private final CompileContext myCompileContext;

	private File myTempErrorFile;
	private File myTempWarningFile;

	public MSBuildCompileContext(@Nonnull MSBuildProjectModuleExtension extension, @Nonnull VirtualFile solutionFile, @Nonnull String projectName, @Nonnull CompileContext compileContext)
	{
		myExtension = extension;
		mySolutionFile = solutionFile;
		myProjectName = projectName;
		myCompileContext = compileContext;

		try
		{
			myTempErrorFile = FileUtil.createTempFile("msbuild_error", "txt", true);
			myTempWarningFile = FileUtil.createTempFile("msbuild_warning", "txt", true);
		}
		catch(IOException e)
		{
			throw new IllegalArgumentException(e);
		}
	}

	@Nonnull
	public CompileContext getCompileContext()
	{
		return myCompileContext;
	}

	@Nonnull
	public MSBuildProjectModuleExtension getExtension()
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

	@Nonnull
	public File getTempErrorFile()
	{
		return myTempErrorFile;
	}

	@Nonnull
	public File getTempWarningFile()
	{
		return myTempWarningFile;
	}

	public void addDefaultArguments(Consumer<String> args)
	{
		args.accept("/flp1:logfile=" + myTempErrorFile.getPath() + ";errorsonly");
		args.accept("/flp2:logfile=" + myTempWarningFile.getPath() + ";warningsonly");
		args.accept("/nologo");

		args.accept(FileUtil.toSystemDependentName(mySolutionFile.getPath()));
		args.accept("/t:" + myProjectName.replace(".", "_"));
		args.accept("/p:BuildProjectReferences=false");
		//args.accept("/p:Configuration=\"Release\"");
		//args.accept("/p:Platform=\"x86\"");
	}
}
