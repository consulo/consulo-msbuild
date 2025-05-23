package consulo.msbuild.daemon.impl.step;

import consulo.msbuild.daemon.impl.MSBuildDaemonContext;
import consulo.msbuild.daemon.impl.message.model.MSBuildVerbosity;
import consulo.msbuild.daemon.impl.message.model.RunProjectRequest;
import consulo.msbuild.daemon.impl.message.model.RunProjectResponse;
import consulo.msbuild.solution.model.WProject;

import jakarta.annotation.Nonnull;

/**
 * @author VISTALL
 * @since 01/01/2021
 */
public abstract class BaseRunProjectStep extends PerProjectRemoteDaemonStep<RunProjectRequest, RunProjectResponse>
{
	private final String[] myItems;
	private final String[] myTargets;
	private final boolean myContinueOnError;

	public BaseRunProjectStep(WProject wProject, String[] items, String[] targets)
	{
		this(wProject, items, targets, true);
	}

	public BaseRunProjectStep(WProject wProject, String[] items, String[] targets, boolean continueOnError)
	{
		super(wProject);
		myItems = items;
		myTargets = targets;
		myContinueOnError = continueOnError;
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
		r.GlobalProperties.put("ContinueOnError", myContinueOnError ? "ErrorAndContinue" : "ErrorAndStop");
		r.GlobalProperties.put("Silent", "true");
		r.GlobalProperties.put("DesignTimeBuild", "true");

		r.GlobalProperties.put("GenerateResourceMSBuildArchitecture", "CurrentArchitecture");
		r.GlobalProperties.put("GenerateResourceMSBuildRuntime", "CurrentRuntime");

		return r;
	}
}
