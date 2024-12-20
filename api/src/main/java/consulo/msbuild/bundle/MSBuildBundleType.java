/*
 * Copyright 2013-2015 must-be.org
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package consulo.msbuild.bundle;

import consulo.annotation.component.ExtensionImpl;
import consulo.application.util.SystemInfo;
import consulo.msbuild.MSBuildVersion;
import consulo.msbuild.icon.MSBuildIconGroup;
import consulo.platform.Platform;
import consulo.process.ExecutionException;
import consulo.process.cmd.GeneralCommandLine;
import consulo.process.util.CapturingProcessUtil;
import consulo.process.util.ProcessOutput;
import consulo.ui.image.Image;
import consulo.util.collection.ContainerUtil;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author VISTALL
 * @since 08.06.2015
 */
@ExtensionImpl
public class MSBuildBundleType extends BaseMSBuildBundleType
{
	public static class MSBuildInfo
	{
		private File myPath;
		private String myBitness;
		private MSBuildVersion myVersion;
		@Nullable
		private String myEdition;

		public MSBuildInfo(File path, String bitness, MSBuildVersion version, @Nullable String edition)
		{
			myPath = path;
			myBitness = bitness;
			myVersion = version;
			myEdition = edition;
		}

		public File getPath()
		{
			return myPath;
		}

		public String buildName()
		{
			StringBuilder builder = new StringBuilder();
			builder.append("MSBuild ");
			builder.append(myVersion.getInternalVersion());
			if(myEdition != null)
			{
				builder.append(" [VS ");
				builder.append(myVersion.getYearVersion());
				builder.append(" ");
				builder.append(myEdition);
				builder.append(" ");
				builder.append(myBitness);
				builder.append("]");
			}
			return builder.toString();
		}
	}

	@Nonnull
	public static String getExecutable(String home)
	{
		return home + "/bin/MSBuild.exe";
	}

	@Nonnull
	public static MSBuildBundleType getInstance()
	{
		return EP_NAME.findExtensionOrFail(MSBuildBundleType.class);
	}

	public MSBuildBundleType()
	{
		super("MSBUILD_BUNDLE");
	}

	@Nonnull
	@Override
	public Collection<String> suggestHomePaths()
	{
		return findMSBuilds().stream().map(msBuildInfo -> msBuildInfo.getPath().getPath()).collect(Collectors.toList());
	}

	@Nonnull
	public List<MSBuildInfo> findMSBuilds()
	{
		if(!SystemInfo.isWindows)
		{
			return Collections.emptyList();
		}

		List<MSBuildInfo> list = new ArrayList<>();

		collectVisualStudioCompilerPaths(list, "ProgramFiles", SystemInfo.is64Bit ? "x64" : "x86");
		collectVisualStudioCompilerPaths(list, "ProgramFiles(x86)", "x86");

		return list;
	}

	private void collectVisualStudioCompilerPaths(List<MSBuildInfo> list, String env, String bitness)
	{
		String programFiles = Platform.current().os().getEnvironmentVariable(env);
		if(programFiles != null)
		{
			File msbuildDir = new File(programFiles, "MSBuild");

			if(msbuildDir.exists())
			{
				for(MSBuildVersion version : MSBuildVersion.values())
				{
					File compilerPath = new File(msbuildDir, version.getInternalVersion());
					if(compilerPath.exists())
					{
						list.add(new MSBuildInfo(compilerPath, bitness, version, null));
					}
				}
			}

			File vsDirectory = new File(programFiles, "Microsoft Visual Studio");
			if(vsDirectory.exists())
			{
				for(MSBuildVersion version : MSBuildVersion.values())
				{
					for(String visualStudioEdition : MSBuildVersion.ourVisualStudioEditions)
					{
						File vsTargetDirectory = new File(vsDirectory, version.getYearVersion() + "/" + visualStudioEdition);
						if(vsTargetDirectory.exists())
						{
							File msBuildDirectory = new File(vsTargetDirectory, "MSBuild/" + version.getInternalVersion());
							if(msBuildDirectory.exists())
							{
								list.add(new MSBuildInfo(msBuildDirectory, bitness, version, visualStudioEdition));
							}

							msBuildDirectory = new File(vsTargetDirectory, "MSBuild/Current");
							if(msBuildDirectory.exists())
							{
								list.add(new MSBuildInfo(msBuildDirectory, bitness, version, visualStudioEdition));
							}
						}
					}
				}
			}
		}
	}

	@Override
	public boolean isValidSdkHome(String path)
	{
		return new File(getExecutable(path)).exists();
	}

	@Nullable
	@Override
	public String getVersionString(String sdkHome)
	{
		try
		{
			ProcessOutput processOutput = CapturingProcessUtil.execAndGetOutput(new GeneralCommandLine(getExecutable(sdkHome), "/version").withWorkDirectory(sdkHome));
			return ContainerUtil.getLastItem(processOutput.getStdoutLines());
		}
		catch(ExecutionException e)
		{
			return null;
		}
	}

	@Nonnull
	@Override
	public String getPresentableName()
	{
		return "MSBuild";
	}

	@Nullable
	@Override
	public Image getIcon()
	{
		return MSBuildIconGroup.msbuild();
	}
}
