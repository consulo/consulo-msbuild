package consulo.msbuild.editor;

import com.intellij.ide.util.ChooseElementsDialog;
import com.intellij.openapi.fileEditor.FileEditor;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.Pair;
import com.intellij.openapi.util.io.FileUtil;
import com.intellij.openapi.vfs.VfsUtil;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiManager;
import com.intellij.psi.xml.XmlFile;
import com.intellij.ui.EditorNotificationPanel;
import com.intellij.ui.EditorNotifications;
import com.intellij.util.xml.DomFileElement;
import com.intellij.util.xml.DomManager;
import consulo.annotation.access.RequiredReadAction;
import consulo.editor.notifications.EditorNotificationProvider;
import consulo.msbuild.MSBuildIcons;
import consulo.msbuild.MSBuildProjectType;
import consulo.msbuild.MSBuildSolutionManager;
import consulo.msbuild.dom.ItemGroup;
import consulo.msbuild.dom.SimpleItem;
import consulo.msbuild.solution.model.WProject;
import consulo.msbuild.synchronize.MSBuildFileReferenceType;
import consulo.msbuild.synchronize.MSBuildSynchronizeQueue;
import consulo.msbuild.synchronize.MSBuildSynchronizeUtil;
import consulo.ui.image.Image;
import consulo.vfs.ArchiveFileSystem;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import jakarta.inject.Inject;
import javax.swing.*;
import java.util.List;

/**
 * @author VISTALL
 * @since 2018-08-15
 */
public class MSBuildOutOfProjectEditorNotificationProvider implements EditorNotificationProvider<EditorNotificationPanel>
{
	private final Project myProject;

	@jakarta.inject.Inject
	public MSBuildOutOfProjectEditorNotificationProvider(Project project)
	{
		myProject = project;
	}

	@RequiredReadAction
	@Nullable
	@Override
	public EditorNotificationPanel createNotificationPanel(@Nonnull VirtualFile sourceFile, @Nonnull FileEditor fileEditor)
	{
		if(sourceFile.getFileSystem() instanceof ArchiveFileSystem)
		{
			return null;
		}

		Pair<WProject, SimpleItem> pair = MSBuildSynchronizeUtil.searchMappingInSolution(sourceFile, myProject);
		if(pair != null)
		{
			return null;
		}

		MSBuildSolutionManager solutionManager = MSBuildSolutionManager.getInstance(myProject);
		if(!solutionManager.isEnabled())
		{
			return null;
		}

		if(sourceFile.equals(solutionManager.getSolutionFile()))
		{
			return null;
		}

		for(WProject wProject : solutionManager.getSolution().getProjects())
		{
			if(sourceFile.equals(wProject.getVirtualFile()))
			{
				return null;
			}
		}

		EditorNotificationPanel panel = new EditorNotificationPanel();
		panel.text("Unregistered file in solution");

		panel.createActionLabel("Register to project...", () ->
		{
			ChooseElementsDialog<WProject> dialog = new ChooseElementsDialog<WProject>(myProject, solutionManager.getSolution().getProjects(), "Choose Project", "Please select project for map")
			{
				@Override
				protected String getItemText(WProject wProject)
				{
					String path = "??";
					VirtualFile projectFile = wProject.getVirtualFile();
					if(projectFile != null)
					{
						path = FileUtil.toSystemDependentName(projectFile.getPath());
					}
					return wProject.getName() + " (" + path + ")";
				}

				@Nullable
				@Override
				protected Image getItemIcon(WProject wProject)
				{
					return MSBuildIcons.Msbuild;
				}
			};

			List<WProject> wProjects = dialog.showAndGetResult();
			if(wProjects.size() != 1)
			{
				return;
			}

			WProject wProject = wProjects.get(0);

			VirtualFile projectFile = wProject.getVirtualFile();
			if(projectFile == null)
			{
				return;
			}

			MSBuildSynchronizeQueue queue = MSBuildSynchronizeQueue.getInstance(myProject);

			queue.add(projectFile, () ->
			{
				PsiManager psiManager = PsiManager.getInstance(myProject);

				PsiFile file = psiManager.findFile(projectFile);
				if(file == null)
				{
					return;
				}

				DomManager domManager = DomManager.getDomManager(myProject);
				DomFileElement<consulo.msbuild.dom.Project> fileElement = domManager.getFileElement((XmlFile) file, consulo.msbuild.dom.Project.class);
				if(fileElement == null)
				{
					return;
				}

				consulo.msbuild.dom.Project rootElement = fileElement.getRootElement();

				MSBuildProjectType projectType = MSBuildProjectType.getProjectType(wProject.getTypeGUID());
				if(projectType == null)
				{
					return;
				}

				MSBuildFileReferenceType referenceType = projectType.getFileReferenceType(sourceFile);

				ItemGroup selectedItemGroup = null;
				List<ItemGroup> itemGroups = rootElement.getItemGroups();
				for(ItemGroup itemGroup : itemGroups)
				{
					List<? extends SimpleItem> simpleItems = referenceType.selectItems(itemGroup);
					if(!simpleItems.isEmpty())
					{
						selectedItemGroup = itemGroup;
					}
				}

				if(selectedItemGroup == null)
				{
					selectedItemGroup = rootElement.addItemGroup();
				}

				SimpleItem simpleItem = referenceType.addItem(selectedItemGroup);

				VirtualFile parent = projectFile.getParent();
				assert parent != null;

				String relativePath = VfsUtil.getRelativePath(sourceFile, parent, '\\');

				simpleItem.getInclude().setStringValue(relativePath);

				SwingUtilities.invokeLater(() -> EditorNotifications.getInstance(myProject).updateNotifications(sourceFile));
			});
		});
		return panel;
	}
}
