/*
 * Copyright 2013-2017 consulo.io
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package consulo.msbuild.impl.projectView;

import consulo.annotation.access.RequiredReadAction;
import consulo.annotation.component.ExtensionImpl;
import consulo.application.AllIcons;
import consulo.application.dumb.DumbAware;
import consulo.codeEditor.Editor;
import consulo.dataContext.DataProvider;
import consulo.fileEditor.FileEditor;
import consulo.fileEditor.FileEditorManager;
import consulo.fileEditor.TextEditor;
import consulo.ide.ServiceManager;
import consulo.ide.impl.idea.ide.projectView.impl.*;
import consulo.language.editor.LangDataKeys;
import consulo.language.editor.PlatformDataKeys;
import consulo.language.psi.*;
import consulo.language.util.ModuleUtilCore;
import consulo.module.Module;
import consulo.msbuild.MSBuildIcons;
import consulo.msbuild.impl.projectView.select.SolutionSelectInTarget;
import consulo.msbuild.module.extension.MSBuildSolutionModuleExtension;
import consulo.msbuild.solution.model.WProject;
import consulo.project.Project;
import consulo.project.ui.view.SelectInContext;
import consulo.project.ui.view.SelectInTarget;
import consulo.project.ui.view.tree.AbstractTreeNode;
import consulo.project.ui.view.tree.ViewSettings;
import consulo.ui.annotation.RequiredUIAccess;
import consulo.ui.ex.action.AnAction;
import consulo.ui.ex.action.AnActionEvent;
import consulo.ui.ex.awt.AutoScrollFromSourceHandler;
import consulo.ui.ex.awt.tree.AbstractTreeBuilder;
import consulo.ui.ex.awt.tree.AbstractTreeUpdater;
import consulo.ui.ex.tree.NodeDescriptor;
import consulo.ui.image.Image;
import consulo.util.dataholder.Key;
import consulo.virtualFileSystem.VirtualFile;
import consulo.virtualFileSystem.util.VirtualFileUtil;
import jakarta.inject.Inject;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import java.awt.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * @author VISTALL
 * @since 28-Jan-17
 */
@ExtensionImpl
public class SolutionViewPane extends AbstractProjectViewPSIPane
{
	private class MyAutoScrollFromSourceHandler extends AutoScrollFromSourceHandler
	{
		public MyAutoScrollFromSourceHandler(@Nonnull Project project, @Nonnull JComponent view)
		{
			super(project, view, project);
		}

		@Override
		protected boolean isAutoScrollEnabled()
		{
			return false;
		}

		@Override
		protected void setAutoScrollEnabled(boolean b)
		{

		}

		@Override
		public void install()
		{

		}

		public void scrollFromSource()
		{
			final FileEditorManager fileEditorManager = FileEditorManager.getInstance(myProject);
			final Editor selectedTextEditor = fileEditorManager.getSelectedTextEditor();
			if(selectedTextEditor != null)
			{
				selectElementAtCaret(selectedTextEditor);
				return;
			}
			final FileEditor[] editors = fileEditorManager.getSelectedEditors();
			for(FileEditor fileEditor : editors)
			{
				if(fileEditor instanceof TextEditor)
				{
					Editor editor = ((TextEditor) fileEditor).getEditor();
					selectElementAtCaret(editor);
					return;
				}
			}
			final VirtualFile[] selectedFiles = fileEditorManager.getSelectedFiles();
			if(selectedFiles.length > 0)
			{
				final PsiFile file = PsiManager.getInstance(myProject).findFile(selectedFiles[0]);
				if(file != null)
				{
					scrollFromFile(file, null);
				}
			}
		}

		private void selectElementAtCaret(@Nonnull Editor editor)
		{
			final PsiFile file = PsiDocumentManager.getInstance(myProject).getPsiFile(editor.getDocument());
			if(file == null)
			{
				return;
			}

			scrollFromFile(file, editor);
		}

		private void scrollFromFile(@Nonnull PsiFile file, @Nullable Editor editor)
		{
			SmartPsiElementPointer<PsiFile> pointer = SmartPointerManager.getInstance(myProject).createSmartPsiElementPointer(file);
			PsiDocumentManager.getInstance(myProject).performLaterWhenAllCommitted(() ->
			{
				SelectInTarget target = SolutionViewPane.this.createSelectInTarget();
				if(target == null)
				{
					return;
				}

				PsiFile restoredPsi = pointer.getElement();
				if(restoredPsi == null)
				{
					return;
				}

				final SelectInContext selectInContext = new SelectInContext()
				{
					@Nonnull
					@Override
					public Project getProject()
					{
						return restoredPsi.getProject();
					}

					@Nonnull
					@Override
					public VirtualFile getVirtualFile()
					{
						return restoredPsi.getVirtualFile();
					}

					@Nullable
					@Override
					public Object getSelectorInFile()
					{
						return null;
					}
				};

				if(target.canSelect(selectInContext))
				{
					target.selectIn(selectInContext, false);
				}
			});
		}
	}

	private final class MyPanel extends JPanel implements DataProvider
	{
		MyPanel()
		{
			super(new BorderLayout());
		}

		@Nullable
		private Object getSelectedNodeElement()
		{
			final AbstractProjectViewPane currentProjectViewPane = SolutionViewPane.this;
			if(currentProjectViewPane == null)
			{ // can happen if not initialized yet
				return null;
			}
			DefaultMutableTreeNode node = currentProjectViewPane.getSelectedNode();
			if(node == null)
			{
				return null;
			}
			Object userObject = node.getUserObject();
			if(userObject instanceof AbstractTreeNode)
			{
				return ((AbstractTreeNode) userObject).getValue();
			}
			if(!(userObject instanceof NodeDescriptor))
			{
				return null;
			}
			return ((NodeDescriptor) userObject).getElement();
		}

		@Override
		public Object getData(@Nonnull Key<?> dataId)
		{
			final AbstractProjectViewPane currentProjectViewPane = SolutionViewPane.this;
			if(currentProjectViewPane != null)
			{
				final Object paneSpecificData = currentProjectViewPane.getData(dataId);
				if(paneSpecificData != null)
				{
					return paneSpecificData;
				}
			}

			if(LangDataKeys.PSI_ELEMENT == dataId)
			{
				if(currentProjectViewPane == null)
				{
					return null;
				}
				final PsiElement[] elements = currentProjectViewPane.getSelectedPSIElements();
				return elements.length == 1 ? elements[0] : null;
			}
			if(LangDataKeys.PSI_ELEMENT_ARRAY == dataId)
			{
				if(currentProjectViewPane == null)
				{
					return null;
				}
				PsiElement[] elements = currentProjectViewPane.getSelectedPSIElements();
				return elements.length == 0 ? null : elements;
			}
			if(PlatformDataKeys.VIRTUAL_FILE_ARRAY == dataId)
			{
				PsiElement[] psiElements = getDataUnchecked(LangDataKeys.PSI_ELEMENT_ARRAY);
				if(psiElements == null)
				{
					return null;
				}
				Set<VirtualFile> files = new LinkedHashSet<>();
				for(PsiElement element : psiElements)
				{
					final VirtualFile virtualFile = PsiUtilCore.getVirtualFile(element);
					if(virtualFile != null)
					{
						files.add(virtualFile);
					}
				}
				return files.size() > 0 ? VirtualFileUtil.toVirtualFileArray(files) : null;
			}
			if(LangDataKeys.MODULE == dataId)
			{
				VirtualFile[] virtualFiles = getDataUnchecked(PlatformDataKeys.VIRTUAL_FILE_ARRAY);
				if(virtualFiles == null || virtualFiles.length <= 1)
				{
					return null;
				}
				final Set<Module> modules = new HashSet<>();
				for(VirtualFile virtualFile : virtualFiles)
				{
					modules.add(ModuleUtilCore.findModuleForFile(virtualFile, myProject));
				}
				return modules.size() == 1 ? modules.iterator().next() : null;
			}
			if(LangDataKeys.TARGET_PSI_ELEMENT == dataId)
			{
				return null;
			}
			/*if(PlatformDataKeys.CUT_PROVIDER == dataId)
			{
				return myCopyPasteDelegator.getCutProvider();
			}
			if(PlatformDataKeys.COPY_PROVIDER == dataId)
			{
				return myCopyPasteDelegator.getCopyProvider();
			}
			if(PlatformDataKeys.PASTE_PROVIDER == dataId)
			{
				return myCopyPasteDelegator.getPasteProvider();
			}
			if(LangDataKeys.IDE_VIEW == dataId)
			{
				return myIdeView;
			}
			if(PlatformDataKeys.DELETE_ELEMENT_PROVIDER == dataId)
			{
				final Module[] modules = getSelectedModules();
				if(modules != null)
				{
					return myDeleteModuleProvider;
				}
				final LibraryOrderEntry orderEntry = getSelectedLibrary();
				if(orderEntry != null)
				{
					return new DeleteProvider()
					{
						@Override
						public void deleteElement(@NotNull DataContext dataContext)
						{
							detachLibrary(orderEntry, myProject);
						}

						@Override
						public boolean canDeleteElement(@NotNull DataContext dataContext)
						{
							return true;
						}
					};
				}
				return myDeletePSIElementProvider;
			}
			if(PlatformDataKeys.HELP_ID == dataId)
			{
				return HelpID.PROJECT_VIEWS;
			}
			if(ProjectViewImpl.DATA_KEY == dataId)
			{
				return ProjectViewImpl.this;
			}
			if(PlatformDataKeys.PROJECT_CONTEXT == dataId)
			{
				Object selected = getSelectedNodeElement();
				return selected instanceof Project ? selected : null;
			}
			if(LangDataKeys.MODULE_CONTEXT == dataId)
			{
				Object selected = getSelectedNodeElement();
				if(selected instanceof Module)
				{
					return !((Module) selected).isDisposed() ? selected : null;
				}
				else if(selected instanceof PsiDirectory)
				{
					return moduleByContentRoot(((PsiDirectory) selected).getVirtualFile());
				}
				else if(selected instanceof VirtualFile)
				{
					return moduleByContentRoot((VirtualFile) selected);
				}
				else
				{
					return null;
				}
			}

			if(LangDataKeys.MODULE_CONTEXT_ARRAY == dataId)
			{
				return getSelectedModules();
			}
			if(ModuleGroup.ARRAY_DATA_KEY == dataId)
			{
				final List<ModuleGroup> selectedElements = getSelectedElements(ModuleGroup.class);
				return selectedElements.isEmpty() ? null : selectedElements.toArray(new ModuleGroup[selectedElements.size()]);
			}
			if(LibraryGroupElement.ARRAY_DATA_KEY == dataId)
			{
				final List<LibraryGroupElement> selectedElements = getSelectedElements(LibraryGroupElement.class);
				return selectedElements.isEmpty() ? null : selectedElements.toArray(new LibraryGroupElement[selectedElements.size()]);
			}
			if(NamedLibraryElement.ARRAY_DATA_KEY == dataId)
			{
				final List<NamedLibraryElement> selectedElements = getSelectedElements(NamedLibraryElement.class);
				return selectedElements.isEmpty() ? null : selectedElements.toArray(new NamedLibraryElement[selectedElements.size()]);
			}

			if(QuickActionProvider.KEY == dataId)
			{
				return ProjectViewImpl.this;
			}  */

			return null;
		}

		/*
		@Nullable
		private Module[] getSelectedModules()
		{
			final AbstractProjectViewPane viewPane = getCurrentProjectViewPane();
			if(viewPane == null)
			{
				return null;
			}
			final Object[] elements = viewPane.getSelectedElements();
			ArrayList<Module> result = new ArrayList<>();
			for(Object element : elements)
			{
				if(element instanceof Module)
				{
					final Module module = (Module) element;
					if(!module.isDisposed())
					{
						result.add(module);
					}
				}
				else if(element instanceof ModuleGroup)
				{
					Collection<Module> modules = ((ModuleGroup) element).modulesInGroup(myProject, true);
					result.addAll(modules);
				}
				else if(element instanceof PsiDirectory)
				{
					Module module = moduleByContentRoot(((PsiDirectory) element).getVirtualFile());
					if(module != null)
					{
						result.add(module);
					}
				}
				else if(element instanceof VirtualFile)
				{
					Module module = moduleByContentRoot((VirtualFile) element);
					if(module != null)
					{
						result.add(module);
					}
				}
			}

			if(result.isEmpty())
			{
				return null;
			}
			else
			{
				return result.toArray(new Module[result.size()]);
			}
		} */
	}

	private class ScrollFromSourceAction extends AnAction implements DumbAware
	{
		private ScrollFromSourceAction()
		{
			super("Scroll from Source", "Select the file open in the active editor", AllIcons.General.Locate);
		}

		@RequiredUIAccess
		@Override
		public void actionPerformed(@Nonnull AnActionEvent e)
		{
			myAutoScrollFromSourceHandler.scrollFromSource();
		}
	}

	private final MyAutoScrollFromSourceHandler myAutoScrollFromSourceHandler;

	@Nonnull
	public static SolutionViewPane getInstance(@Nonnull Project project)
	{
		return ServiceManager.getService(project, SolutionViewPane.class);
	}

	public static final String ID = "SolutionViewPane";

	private JComponent myComponent;

	@Inject
	public SolutionViewPane(Project project)
	{
		super(project);

		myComponent = buildComponent();
		myAutoScrollFromSourceHandler = new MyAutoScrollFromSourceHandler(myProject, myComponent);
	}

	@Override
	public boolean isInitiallyVisible()
	{
		return MSBuildSolutionModuleExtension.getSolutionModuleExtension(myProject) != null;
	}

	@Nonnull
	@Override
	public ProjectAbstractTreeStructureBase createStructure()
	{
		return new ProjectTreeStructure(myProject, ID)
		{
			@Override
			@RequiredReadAction
			protected AbstractTreeNode createRoot(Project project, ViewSettings settings)
			{
				MSBuildSolutionModuleExtension<?> extension = MSBuildSolutionModuleExtension.getSolutionModuleExtension(myProject);
				if(extension == null)
				{
					return new SolutionViewErrorRootNode(project, settings);
				}

				VirtualFile solutionFile = extension.getSolutionFile();

				Collection<WProject> projects = extension.getProjects();

				return new SolutionViewRootNode(project, solutionFile, projects, settings);
			}
		};
	}

	@Nonnull
	@Override
	protected ProjectViewTree createTree(@Nonnull DefaultTreeModel treeModel)
	{
		return new ProjectViewTree(myProject, treeModel)
		{
		};
	}

	private JComponent buildComponent()
	{
		MyPanel panel = new MyPanel();
		JComponent component = createComponent();
		component.setBorder(null);
		panel.add(BorderLayout.CENTER, component);
		return panel;
	}

	@Nonnull
	public JComponent getComponent()
	{
		return myComponent;
	}

	@Nonnull
	@Override
	public JComponent createComponent()
	{
		JComponent component = super.createComponent();
		myTree.setRootVisible(true);
		return component;
	}

	@Nonnull
	@Override
	protected AbstractTreeUpdater createTreeUpdater(@Nonnull AbstractTreeBuilder treeBuilder)
	{
		return new AbstractTreeUpdater(treeBuilder);
	}

	@Nonnull
	@Override
	public String getTitle()
	{
		return "Solution Explorer";
	}

	@Nonnull
	@Override
	public String getId()
	{
		return ID;
	}

	@Override
	public int getWeight()
	{
		return 2;
	}

	@Nonnull
	@Override
	public SelectInTarget createSelectInTarget()
	{
		return new SolutionSelectInTarget(myProject);
	}
}
