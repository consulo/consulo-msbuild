package consulo.msbuild.daemon.impl.step;

import consulo.msbuild.daemon.impl.MSBuildDaemonContext;
import consulo.msbuild.daemon.impl.message.model.RunProjectResponse;
import consulo.msbuild.solution.model.WProject;

import jakarta.annotation.Nonnull;

/**
 * @author VISTALL
 * @since 19/01/2021
 */
public class RunTargetProjectStep extends BaseRunProjectStep
{
	private final String myTarget;

	public RunTargetProjectStep(WProject wProject, String target, boolean continueOnError)
	{
		super(wProject, new String[0], new String[] {target}, continueOnError);
		myTarget = target;
	}

	@Nonnull
	@Override
	public String getProjectStepText()
	{
		return "Running '" + myTarget + "' for ''{0}'' Project";
	}

	@Override
	public void handleResponse(@Nonnull MSBuildDaemonContext context, @Nonnull RunProjectResponse runProjectResponse)
	{
	}

	@Override
	public boolean wantLogging()
	{
		return true;
	}
}
