package consulo.msbuild.synchronize;

import javax.annotation.Nonnull;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.Pair;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.openapi.vfs.VirtualFileEvent;
import com.intellij.openapi.vfs.VirtualFileListener;
import com.intellij.psi.xml.XmlElement;
import consulo.msbuild.dom.SimpleItem;
import consulo.msbuild.solution.model.WProject;

/**
 * @author VISTALL
 * @since 2018-08-15
 */
public class MSBuildSynchronizeFileListener implements VirtualFileListener
{
	private final Project myProject;

	public MSBuildSynchronizeFileListener(Project project)
	{
		myProject = project;
	}

	@Override
	public void beforeFileDeletion(@Nonnull VirtualFileEvent event)
	{
		VirtualFile file = event.getFile();

		Pair<WProject, SimpleItem> pair = MSBuildSynchronizeUtil.searchMappingInSolution(file, myProject);
		if(pair == null)
		{
			return;
		}

		SimpleItem item = pair.getSecond();

		MSBuildSynchronizeQueue queue = MSBuildSynchronizeQueue.getInstance(myProject);

		queue.add(pair.getFirst().getVirtualFile(), () ->
		{
			XmlElement xmlElement = item.getXmlElement();
			if(xmlElement != null)
			{
				xmlElement.delete();
			}
		});
	}
}
