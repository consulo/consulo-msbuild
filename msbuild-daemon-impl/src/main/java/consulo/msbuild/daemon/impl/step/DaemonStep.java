package consulo.msbuild.daemon.impl.step;

import consulo.localize.LocalizeValue;
import consulo.msbuild.daemon.impl.MSBuildDaemonContext;
import consulo.msbuild.daemon.impl.MSBuildDaemonService;
import consulo.msbuild.daemon.impl.logging.MSBuildLoggingSession;
import consulo.msbuild.daemon.impl.message.DaemonConnection;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import java.io.IOException;

/**
 * @author VISTALL
 * @since 23/04/2023
 */
public interface DaemonStep
{
	@Nonnull
	LocalizeValue getStepText();

	@SuppressWarnings("unchecked")
	void execute(@Nonnull MSBuildDaemonContext context, @Nonnull DaemonConnection connection, @Nullable MSBuildLoggingSession loggingSession) throws IOException;

	default boolean wantLogging()
	{
		return false;
	}

	default void refill(@Nonnull MSBuildDaemonService service, @Nonnull DaemonStepQueue queue)
	{
	}
}
