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

package consulo.msbuild.projectView;

import java.awt.BorderLayout;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.swing.Icon;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import com.intellij.ide.SelectInManager;
import com.intellij.ide.SelectInTarget;
import com.intellij.ide.impl.ProjectViewSelectInTarget;
import com.intellij.ide.projectView.ViewSettings;
import com.intellij.ide.projectView.impl.AbstractProjectViewPSIPane;
import com.intellij.ide.projectView.impl.AbstractProjectViewPane;
import com.intellij.ide.projectView.impl.ProjectAbstractTreeStructureBase;
import com.intellij.ide.projectView.impl.ProjectTreeStructure;
import com.intellij.ide.projectView.impl.ProjectViewTree;
import com.intellij.ide.util.treeView.AbstractTreeBuilder;
import com.intellij.ide.util.treeView.AbstractTreeNode;
import com.intellij.ide.util.treeView.AbstractTreeUpdater;
import com.intellij.ide.util.treeView.NodeDescriptor;
import com.intellij.openapi.actionSystem.DataProvider;
import com.intellij.openapi.actionSystem.LangDataKeys;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.ModuleUtil;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.Key;
import com.intellij.openapi.vfs.VfsUtil;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiElement;
import com.intellij.psi.util.PsiUtilCore;
import consulo.annotations.RequiredReadAction;
import consulo.awt.TargetAWT;
import consulo.msbuild.MSBuildIcons;
import consulo.msbuild.MSBuildSolutionManager;

/**
 * @author VISTALL
 * @since 28-Jan-17
 */
public class SolutionViewPane extends AbstractProjectViewPSIPane
{
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
				return files.size() > 0 ? VfsUtil.toVirtualFileArray(files) : null;
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
					modules.add(ModuleUtil.findModuleForFile(virtualFile, myProject));
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

	@Nonnull
	public static SolutionViewPane getInstance(@Nonnull Project project)
	{
		return ServiceManager.getService(project, SolutionViewPane.class);
	}

	public static final String ID = "SolutionViewPane";

	public SolutionViewPane(Project project)
	{
		super(project);
	}

	@Override
	public boolean isInitiallyVisible()
	{
		return MSBuildSolutionManager.getInstance(myProject).isEnabled();
	}

	@Override
	public ProjectAbstractTreeStructureBase createStructure()
	{
		return new ProjectTreeStructure(myProject, ID)
		{
			@Override
			@RequiredReadAction
			protected AbstractTreeNode createRoot(Project project, ViewSettings settings)
			{
				MSBuildSolutionManager solutionManager = MSBuildSolutionManager.getInstance(myProject);

				VirtualFile solutionFile = solutionManager.getSolutionFile();

				if(solutionFile == null)
				{
					return new SolutionViewErrorRootNode(project, settings);
				}

				return new SolutionViewRootNode(project, solutionFile, settings);
			}
		};
	}

	@Override
	protected ProjectViewTree createTree(DefaultTreeModel treeModel)
	{
		return new ProjectViewTree(myProject, treeModel) {};
	}

	public JComponent getComponent()
	{
		MyPanel panel = new MyPanel();
		JComponent component = createComponent();
		component.setBorder(null);
		panel.add(BorderLayout.CENTER, component);
		return panel;
	}

	@Override
	public JComponent createComponent()
	{
		JComponent component = super.createComponent();
		myTree.setRootVisible(true);
		return component;
	}

	@Override
	protected AbstractTreeUpdater createTreeUpdater(AbstractTreeBuilder treeBuilder)
	{
		return new AbstractTreeUpdater(treeBuilder);
	}

	@Override
	public String getTitle()
	{
		return "Solution Explorer";
	}

	@Override
	public Icon getIcon()
	{
		return TargetAWT.to(MSBuildIcons.Msbuild);
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

	@Override
	public SelectInTarget createSelectInTarget()
	{
		return new ProjectViewSelectInTarget(myProject)
		{
			@Override
			public String toString()
			{
				return SelectInManager.PROJECT;
			}

			@Nullable
			@Override
			public String getMinorViewId()
			{
				return null;
			}

			@Override
			public float getWeight()
			{
				return 0;
			}
		};
	}
}
