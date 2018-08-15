package consulo.msbuild.synchronize;

import java.util.List;
import java.util.Map;

import javax.annotation.Nonnull;

import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.ModuleUtilCore;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.Disposer;
import com.intellij.openapi.util.io.FileUtilRt;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.openapi.vfs.VirtualFileEvent;
import com.intellij.openapi.vfs.VirtualFileListener;
import com.intellij.psi.xml.XmlElement;
import consulo.msbuild.MSBuildSolutionManager;
import consulo.msbuild.dom.ItemGroup;
import consulo.msbuild.dom.SimpleItem;
import consulo.msbuild.solution.model.WProject;

/**
 * @author VISTALL
 * @since 2018-08-15
 */
public class MSBuildSynchronizeFileListener implements VirtualFileListener
{
	private final MSBuildSynchronizeQueue myQueue;

	private final Project myProject;
	private final MSBuildSolutionManager mySolutionManager;

	public MSBuildSynchronizeFileListener(Project project, MSBuildSolutionManager solutionManager)
	{
		myProject = project;
		myQueue = new MSBuildSynchronizeQueue(project);
		Disposer.register(myProject, myQueue::stop);
		mySolutionManager = solutionManager;
	}

	@Override
	public void beforeFileDeletion(@Nonnull VirtualFileEvent event)
	{
		VirtualFile file = event.getFile();

		Module module = ModuleUtilCore.findModuleForFile(file, myProject);
		if(module == null)
		{
			return;
		}

		Map.Entry<String, MSBuildSolutionManager.ProjectOptions> entry = mySolutionManager.getOptionsByModuleName(module.getName());
		if(entry == null)
		{
			return;
		}

		WProject wProject = mySolutionManager.getSolution().findProjectByName(entry.getKey());
		if(wProject == null)
		{
			return;
		}

		consulo.msbuild.dom.Project domProject = wProject.getDomProject();
		if(domProject == null)
		{
			return;
		}

		List<ItemGroup> itemGroups = domProject.getItemGroups();
		for(ItemGroup itemGroup : itemGroups)
		{
			searchItemAndRemove(itemGroup.getCompiles(), file, wProject);
			searchItemAndRemove(itemGroup.getResourceCompiles(), file, wProject);
			searchItemAndRemove(itemGroup.getNones(), file, wProject);
			searchItemAndRemove(itemGroup.getItems(), file, wProject);
			searchItemAndRemove(itemGroup.getContents(), file, wProject);
		}
	}

	private void searchItemAndRemove(List<? extends SimpleItem> items, VirtualFile virtualFile, WProject wProject)
	{
		VirtualFile projectFile = wProject.getVirtualFile();
		assert projectFile != null;
		VirtualFile baseDir = projectFile.getParent();

		for(SimpleItem item : items)
		{
			String value = item.getInclude().getStringValue();
			if(value == null)
			{
				continue;
			}

			VirtualFile file = baseDir.findFileByRelativePath(FileUtilRt.toSystemIndependentName(value));

			if(virtualFile.equals(file))
			{
				myQueue.add(projectFile, () ->
				{
					XmlElement xmlElement = item.getXmlElement();
					if(xmlElement != null)
					{
						xmlElement.delete();
					}
				});
			}
		}
	}
}
