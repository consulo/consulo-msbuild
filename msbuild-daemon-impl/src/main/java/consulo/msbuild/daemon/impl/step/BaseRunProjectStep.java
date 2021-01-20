package consulo.msbuild.daemon.impl.step;

import consulo.msbuild.daemon.impl.MSBuildDaemonContext;
import consulo.msbuild.daemon.impl.message.model.LogMessage;
import consulo.msbuild.daemon.impl.message.model.MSBuildVerbosity;
import consulo.msbuild.daemon.impl.message.model.RunProjectRequest;
import consulo.msbuild.daemon.impl.message.model.RunProjectResponse;
import consulo.msbuild.solution.model.WProject;

import javax.annotation.Nonnull;

/**
 * @author VISTALL
 * @since 01/01/2021
 */
public abstract class BaseRunProjectStep extends PerProjectDaemonStep<RunProjectRequest, RunProjectResponse>
{
	private final String[] myItems;
	private final String[] myTargets;

	public BaseRunProjectStep(WProject wProject, String[] items, String[] targets)
	{
		super(wProject);
		myItems = items;
		myTargets = targets;
	}

	@Nonnull
	@Override
	public RunProjectRequest prepareRequest(@Nonnull MSBuildDaemonContext context)
	{
		RunProjectRequest r = new RunProjectRequest();
		r.ProjectId = context.getRegisteredProjectId(myWProject);
		r.Verbosity = wantLogging() ? MSBuildVerbosity.Normal : MSBuildVerbosity.Quiet;
		r.EvaluateItems = myItems;
		r.RunTargets = myTargets;
		r.Configurations = buildProjectConfigurationInfo(context);
		// Even though some targets may fail it may still be possible for the main resolve target to return
		// information so we set ContinueOnError. This matches VS on Windows behaviour.
		r.GlobalProperties.put("ContinueOnError", "ErrorAndContinue");
		r.GlobalProperties.put("Silent", "true");
		r.GlobalProperties.put("DesignTimeBuild", "true");

		return r;
	}

	public boolean wantLogging()
	{
		return false;
	}
}
