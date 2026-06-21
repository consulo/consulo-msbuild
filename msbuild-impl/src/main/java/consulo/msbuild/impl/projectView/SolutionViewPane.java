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
import consulo.dataContext.DataSink;
import consulo.dataContext.UiDataProvider;
import consulo.fileEditor.FileEditor;
import consulo.fileEditor.FileEditorManager;
import consulo.fileEditor.TextEditor;
import consulo.ide.impl.idea.ide.projectView.impl.AbstractProjectViewPSIPane;
import consulo.ide.impl.idea.ide.projectView.impl.ProjectAbstractTreeStructureBase;
import consulo.ide.impl.idea.ide.projectView.impl.ProjectTreeStructure;
import consulo.ide.impl.idea.ide.projectView.impl.ProjectViewTree;
import consulo.language.editor.LangDataKeys;
import consulo.language.editor.PlatformDataKeys;
import consulo.language.psi.*;
import consulo.language.util.ModuleUtilCore;
import consulo.localize.LocalizeValue;
import consulo.module.Module;
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
import consulo.virtualFileSystem.VirtualFile;
import consulo.virtualFileSystem.util.VirtualFileUtil;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import jakarta.inject.Inject;

import javax.swing.*;
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
public class SolutionViewPane extends AbstractProjectViewPSIPane {
    private class MyAutoScrollFromSourceHandler extends AutoScrollFromSourceHandler {
        public MyAutoScrollFromSourceHandler(@Nonnull Project project, @Nonnull JComponent view) {
            super(project, view, project);
        }

        @Override
        protected boolean isAutoScrollEnabled() {
            return false;
        }

        @Override
        protected void setAutoScrollEnabled(boolean b) {

        }

        @Override
        public void install() {

        }

        public void scrollFromSource() {
            final FileEditorManager fileEditorManager = FileEditorManager.getInstance(myProject);
            final Editor selectedTextEditor = fileEditorManager.getSelectedTextEditor();
            if (selectedTextEditor != null) {
                selectElementAtCaret(selectedTextEditor);
                return;
            }
            final FileEditor[] editors = fileEditorManager.getSelectedEditors();
            for (FileEditor fileEditor : editors) {
                if (fileEditor instanceof TextEditor) {
                    Editor editor = ((TextEditor) fileEditor).getEditor();
                    selectElementAtCaret(editor);
                    return;
                }
            }
            final VirtualFile[] selectedFiles = fileEditorManager.getSelectedFiles();
            if (selectedFiles.length > 0) {
                final PsiFile file = PsiManager.getInstance(myProject).findFile(selectedFiles[0]);
                if (file != null) {
                    scrollFromFile(file, null);
                }
            }
        }

        private void selectElementAtCaret(@Nonnull Editor editor) {
            final PsiFile file = PsiDocumentManager.getInstance(myProject).getPsiFile(editor.getDocument());
            if (file == null) {
                return;
            }

            scrollFromFile(file, editor);
        }

        private void scrollFromFile(@Nonnull PsiFile file, @Nullable Editor editor) {
            SmartPsiElementPointer<PsiFile> pointer = SmartPointerManager.getInstance(myProject).createSmartPsiElementPointer(file);
            PsiDocumentManager.getInstance(myProject).performLaterWhenAllCommitted(() ->
            {
                SelectInTarget target = SolutionViewPane.this.createSelectInTarget();
                if (target == null) {
                    return;
                }

                PsiFile restoredPsi = pointer.getElement();
                if (restoredPsi == null) {
                    return;
                }

                final SelectInContext selectInContext = new SelectInContext() {
                    @Nonnull
                    @Override
                    public Project getProject() {
                        return restoredPsi.getProject();
                    }

                    @Nonnull
                    @Override
                    public VirtualFile getVirtualFile() {
                        return restoredPsi.getVirtualFile();
                    }

                    @Nullable
                    @Override
                    public Object getSelectorInFile() {
                        return null;
                    }
                };

                if (target.canSelect(selectInContext)) {
                    target.selectIn(selectInContext, false);
                }
            });
        }
    }

    private final class MyPanel extends JPanel implements UiDataProvider {
        MyPanel() {
            super(new BorderLayout());
        }

        @Override
        public void uiDataSnapshot(DataSink sink) {
            // delegate to the pane first so its data wins (matches the old getData delegation order)
            sink.uiDataSnapshot(SolutionViewPane.this);

            sink.lazy(PlatformDataKeys.VIRTUAL_FILE_ARRAY, this::getSelectedVirtualFiles);
            sink.lazy(LangDataKeys.MODULE, () -> {
                VirtualFile[] virtualFiles = getSelectedVirtualFiles();
                if (virtualFiles == null || virtualFiles.length <= 1) {
                    return null;
                }
                final Set<Module> modules = new HashSet<>();
                for (VirtualFile virtualFile : virtualFiles) {
                    modules.add(ModuleUtilCore.findModuleForFile(virtualFile, myProject));
                }
                return modules.size() == 1 ? modules.iterator().next() : null;
            });
        }

        @Nullable
        @RequiredReadAction
        private VirtualFile[] getSelectedVirtualFiles() {
            PsiElement[] psiElements = SolutionViewPane.this.getSelectedPSIElements();
            if (psiElements.length == 0) {
                return null;
            }
            Set<VirtualFile> files = new LinkedHashSet<>();
            for (PsiElement element : psiElements) {
                final VirtualFile virtualFile = PsiUtilCore.getVirtualFile(element);
                if (virtualFile != null) {
                    files.add(virtualFile);
                }
            }
            return files.size() > 0 ? VirtualFileUtil.toVirtualFileArray(files) : null;
        }
    }

    private class ScrollFromSourceAction extends AnAction implements DumbAware {
        private ScrollFromSourceAction() {
            super("Scroll from Source", "Select the file open in the active editor", AllIcons.General.Locate);
        }

        @RequiredUIAccess
        @Override
        public void actionPerformed(@Nonnull AnActionEvent e) {
            myAutoScrollFromSourceHandler.scrollFromSource();
        }
    }

    private final MyAutoScrollFromSourceHandler myAutoScrollFromSourceHandler;

    @Nonnull
    public static SolutionViewPane getInstance(@Nonnull Project project) {
        return project.getInstance(SolutionViewPane.class);
    }

    public static final String ID = "SolutionViewPane";

    private JComponent myComponent;

    @Inject
    public SolutionViewPane(Project project) {
        super(project);

        myComponent = buildComponent();
        myAutoScrollFromSourceHandler = new MyAutoScrollFromSourceHandler(myProject, myComponent);
    }

    @Override
    public boolean isInitiallyVisible() {
        return MSBuildSolutionModuleExtension.getSolutionModuleExtension(myProject) != null;
    }

    @Nonnull
    @Override
    public ProjectAbstractTreeStructureBase createStructure() {
        return new ProjectTreeStructure(myProject, ID) {
            @Override
            @RequiredReadAction
            protected AbstractTreeNode createRoot(Project project, ViewSettings settings) {
                MSBuildSolutionModuleExtension<?> extension = MSBuildSolutionModuleExtension.getSolutionModuleExtension(myProject);
                if (extension == null) {
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
    protected ProjectViewTree createTree(@Nonnull DefaultTreeModel treeModel) {
        return new ProjectViewTree(myProject, treeModel) {
        };
    }

    private JComponent buildComponent() {
        MyPanel panel = new MyPanel();
        JComponent component = createComponent();
        component.setBorder(null);
        panel.add(BorderLayout.CENTER, component);
        return panel;
    }

    @Nonnull
    public JComponent getComponent() {
        return myComponent;
    }

    @Nonnull
    @Override
    public JComponent createComponent() {
        JComponent component = super.createComponent();
        myTree.setRootVisible(true);
        return component;
    }

    @Nonnull
    @Override
    protected AbstractTreeUpdater createTreeUpdater(@Nonnull AbstractTreeBuilder treeBuilder) {
        return new AbstractTreeUpdater(treeBuilder);
    }

    @Nonnull
    @Override
    public LocalizeValue getTitle() {
        return LocalizeValue.localizeTODO("Solution Explorer");
    }

    @Nonnull
    @Override
    public String getId() {
        return ID;
    }

    @Override
    public int getWeight() {
        return 2;
    }

    @Nonnull
    @Override
    public SelectInTarget createSelectInTarget() {
        return new SolutionSelectInTarget(myProject);
    }
}
