package consulo.msbuild.daemon.impl.step;

import consulo.util.lang.function.ThrowableConsumer;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.function.Predicate;

/**
 * @author VISTALL
 * @since 24/04/2023
 */
public class DaemonStepQueue
{
	private Queue<DaemonStep> mySteps = new ArrayDeque<>();

	public void join(DaemonStep step)
	{
		mySteps.add(step);
	}

	public <E extends Exception> void eat(@Nonnull ThrowableConsumer<DaemonStep, E> consumer) throws E
	{
		while(!mySteps.isEmpty())
		{
			DaemonStep step = mySteps.remove();

			consumer.consume(step);
		}
	}

	@Nullable
	public DaemonStep find(Predicate<DaemonStep> predicate)
	{
		for(DaemonStep step : mySteps)
		{
			if(predicate.test(step))
			{
				return step;
			}
		}

		return null;
	}
}
