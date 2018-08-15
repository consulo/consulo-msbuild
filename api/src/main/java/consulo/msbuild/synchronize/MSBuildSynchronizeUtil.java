package consulo.msbuild.synchronize;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.ModuleUtilCore;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.Pair;
import com.intellij.openapi.util.io.FileUtilRt;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.util.containers.ContainerUtil;
import consulo.msbuild.MSBuildSolutionManager;
import consulo.msbuild.dom.ItemGroup;
import consulo.msbuild.dom.SimpleItem;
import consulo.msbuild.solution.model.WProject;

/**
 * @author VISTALL
 * @since 2018-08-15
 */
public class MSBuildSynchronizeUtil
{
	@Nullable
	public static Pair<WProject, SimpleItem> searchMappingInSolution(@Nonnull VirtualFile file, @Nonnull Project project)
	{
		MSBuildSolutionManager solutionManager = MSBuildSolutionManager.getInstance(project);
		if(!solutionManager.isEnabled())
		{
			return null;
		}

		Module module = ModuleUtilCore.findModuleForFile(file, project);
		if(module == null)
		{
			return null;
		}

		Map.Entry<String, MSBuildSolutionManager.ProjectOptions> entry = solutionManager.getOptionsByModuleName(module.getName());
		if(entry == null)
		{
			return null;
		}

		WProject wProject = solutionManager.getSolution().findProjectByName(entry.getKey());
		if(wProject == null)
		{
			return null;
		}

		consulo.msbuild.dom.Project domProject = wProject.getDomProject();
		if(domProject == null)
		{
			return null;
		}

		List<ItemGroup> itemGroups = domProject.getItemGroups();
		for(ItemGroup itemGroup : itemGroups)
		{
			List<List<? extends SimpleItem>> list = new ArrayList<>();
			list.add(itemGroup.getCompiles());
			list.add(itemGroup.getResourceCompiles());
			list.add(itemGroup.getNones());
			list.add(itemGroup.getItems());
			list.add(itemGroup.getContents());

			Iterable<? extends SimpleItem> list1 = ContainerUtil.<SimpleItem>flattenIterables((List)list);
			SimpleItem item = searchItem(list1, file, wProject);
			if(item != null)
			{
				return Pair.create(wProject, item);
			}
		}

		return null;
	}

	private static SimpleItem searchItem(Iterable<? extends SimpleItem> items, VirtualFile virtualFile, WProject wProject)
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
				return item;
			}
		}

		return null;
	}
}
