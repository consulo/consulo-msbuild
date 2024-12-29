package consulo.msbuild.impl.editor;

import consulo.annotation.access.RequiredReadAction;
import consulo.annotation.component.ExtensionImpl;
import consulo.fileEditor.EditorNotificationBuilder;
import consulo.fileEditor.EditorNotificationProvider;
import consulo.fileEditor.FileEditor;
import consulo.project.Project;
import consulo.virtualFileSystem.VirtualFile;
import jakarta.inject.Inject;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import java.util.function.Supplier;

/**
 * @author VISTALL
 * @since 2018-08-15
 */
@ExtensionImpl
public class MSBuildOutOfProjectEditorNotificationProvider implements EditorNotificationProvider
{
	private final Project myProject;

	@Inject
	public MSBuildOutOfProjectEditorNotificationProvider(Project project)
	{
		myProject = project;
	}

	@Nonnull
	@Override
	public String getId()
	{
		return "msbuild-out-of-project";
	}

	@RequiredReadAction
	@Nullable
	@Override
	public EditorNotificationBuilder buildNotification(@Nonnull VirtualFile virtualFile, @Nonnull FileEditor fileEditor, @Nonnull Supplier<EditorNotificationBuilder> supplier)
	{
//		if(sourceFile.getFileSystem() instanceof ArchiveFileSystem)
//		{
//			return null;
//		}
//
//		Pair<WProject, SimpleItem> pair = MSBuildSynchronizeUtil.searchMappingInSolution(sourceFile, myProject);
//		if(pair != null)
//		{
//			return null;
//		}
//
//		MSBuildSolutionManager solutionManager = MSBuildSolutionManager.getInstance(myProject);
//		if(!solutionManager.isEnabled())
//		{
//			return null;
//		}
//
//		if(sourceFile.equals(solutionManager.getSolutionFile()))
//		{
//			return null;
//		}
//
//		for(WProject wProject : solutionManager.getSolution().getProjects())
//		{
//			if(sourceFile.equals(wProject.getVirtualFile()))
//			{
//				return null;
//			}
//		}
//
//		EditorNotificationPanel panel = new EditorNotificationPanel();
//		panel.text("Unregistered file in solution");
//
//		panel.createActionLabel("Register to project...", () ->
//		{
//			ChooseElementsDialog<WProject> dialog = new ChooseElementsDialog<WProject>(myProject, solutionManager.getSolution().getProjects(), "Choose Project", "Please select project for map")
//			{
//				@Override
//				protected String getItemText(WProject wProject)
//				{
//					String path = "??";
//					VirtualFile projectFile = wProject.getVirtualFile();
//					if(projectFile != null)
//					{
//						path = FileUtil.toSystemDependentName(projectFile.getPath());
//					}
//					return wProject.getName() + " (" + path + ")";
//				}
//
//				@Nullable
//				@Override
//				protected Image getItemIcon(WProject wProject)
//				{
//					return MSBuildIcons.Msbuild;
//				}
//			};
//
//			List<WProject> wProjects = dialog.showAndGetResult();
//			if(wProjects.size() != 1)
//			{
//				return;
//			}
//
//			WProject wProject = wProjects.get(0);
//
//			VirtualFile projectFile = wProject.getVirtualFile();
//			if(projectFile == null)
//			{
//				return;
//			}
//
//			MSBuildSynchronizeQueue queue = MSBuildSynchronizeQueue.getInstance(myProject);
//
//			queue.add(projectFile, () ->
//			{
//				PsiManager psiManager = PsiManager.getInstance(myProject);
//
//				PsiFile file = psiManager.findFile(projectFile);
//				if(file == null)
//				{
//					return;
//				}
//
//				DomManager domManager = DomManager.getDomManager(myProject);
//				DomFileElement<consulo.msbuild.dom.Project> fileElement = domManager.getFileElement((XmlFile) file, consulo.msbuild.dom.Project.class);
//				if(fileElement == null)
//				{
//					return;
//				}
//
//				consulo.msbuild.dom.Project rootElement = fileElement.getRootElement();
//
//				MSBuildProjectType projectType = MSBuildProjectType.getProjectType(wProject.getTypeGUID());
//				if(projectType == null)
//				{
//					return;
//				}
//
//				MSBuildFileReferenceType referenceType = projectType.getFileReferenceType(sourceFile);
//
//				ItemGroup selectedItemGroup = null;
//				List<ItemGroup> itemGroups = rootElement.getItemGroups();
//				for(ItemGroup itemGroup : itemGroups)
//				{
//					List<? extends SimpleItem> simpleItems = referenceType.selectItems(itemGroup);
//					if(!simpleItems.isEmpty())
//					{
//						selectedItemGroup = itemGroup;
//					}
//				}
//
//				if(selectedItemGroup == null)
//				{
//					selectedItemGroup = rootElement.addItemGroup();
//				}
//
//				SimpleItem simpleItem = referenceType.addItem(selectedItemGroup);
//
//				VirtualFile parent = projectFile.getParent();
//				assert parent != null;
//
//				String relativePath = VfsUtil.getRelativePath(sourceFile, parent, '\\');
//
//				simpleItem.getInclude().setStringValue(relativePath);
//
//				SwingUtilities.invokeLater(() -> EditorNotifications.getInstance(myProject).updateNotifications(sourceFile));
//			});
//		});
//		return panel;
		return null;
	}
}
