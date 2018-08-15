package consulo.msbuild.synchronize;

import java.util.Collection;
import java.util.Collections;
import java.util.Deque;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.util.concurrency.AppExecutorUtil;
import com.intellij.util.containers.MultiMap;

/**
 * @author VISTALL
 * @since 2018-08-15
 */
public class MSBuildSynchronizeQueue implements Runnable
{
	public static class Change
	{
		private VirtualFile myVirtualFile;
		private Runnable myRunnable;

		public Change(VirtualFile virtualFile, Runnable runnable)
		{
			myVirtualFile = virtualFile;
			myRunnable = runnable;
		}
	}

	private final Project myProject;

	private Future<?> myQueueTask = CompletableFuture.completedFuture(null);

	private Deque<Change> myChanges = new ConcurrentLinkedDeque<>();

	public MSBuildSynchronizeQueue(Project project)
	{
		myProject = project;
		myQueueTask = AppExecutorUtil.getAppScheduledExecutorService().scheduleWithFixedDelay(this, 1, 1, TimeUnit.SECONDS);
	}

	public void add(VirtualFile virtualFile, Runnable runnable)
	{
		myChanges.add(new Change(virtualFile, runnable));
	}

	public void stop()
	{
		myQueueTask.cancel(false);
	}

	@Override
	public void run()
	{
		Collection<Map.Entry<VirtualFile, Collection<Runnable>>> entries = pullData();
		if(entries == null)
		{
			return;
		}

		for(Map.Entry<VirtualFile, Collection<Runnable>> entry : entries)
		{
			Collection<Runnable> value = entry.getValue();

			WriteCommandAction.runWriteCommandAction(myProject, () ->
			{
				for(Runnable runnable : value)
				{
					runnable.run();
				}
			});
		}
	}

	private Collection<Map.Entry<VirtualFile, Collection<Runnable>>> pullData()
	{
		MultiMap<VirtualFile, Runnable> mergedTasks = new MultiMap<>();

		Change temp;
		while((temp = myChanges.pollLast()) != null)
		{
			mergedTasks.putValue(temp.myVirtualFile, temp.myRunnable);
		}

		if(mergedTasks.isEmpty())
		{
			return Collections.emptyList();
		}

		return mergedTasks.entrySet();
	}
}
