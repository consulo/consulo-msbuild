package consulo.msbuild.toolWindow;

import consulo.dataContext.DataSink;
import consulo.disposer.Disposable;
import consulo.msbuild.MSBuildProjectListener;
import consulo.msbuild.daemon.impl.MSBuildDaemonService;
import consulo.msbuild.toolWindow.nodes.TargetNodeDescriptor;
import consulo.project.Project;
import consulo.ui.ex.awt.ScrollPaneFactory;
import consulo.ui.ex.awt.SimpleToolWindowPanel;
import consulo.ui.ex.awt.event.DoubleClickListener;
import consulo.ui.ex.awt.tree.AsyncTreeModel;
import consulo.ui.ex.awt.tree.StructureTreeModel;
import consulo.ui.ex.awt.tree.Tree;
import consulo.ui.ex.awt.tree.TreeUtil;
import consulo.util.dataholder.Key;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

import javax.swing.tree.TreePath;
import java.awt.event.MouseEvent;

/**
 * @author VISTALL
 * @since 01/01/2021
 */
public class MSBuildToolWindowPanel extends SimpleToolWindowPanel implements Disposable {
    private final Tree myTree;
    private final StructureTreeModel<MSBuildTreeStructure> myModel;

    public MSBuildToolWindowPanel(@Nonnull Project project) {
        super(true, true);

        myModel = new StructureTreeModel<>(new MSBuildTreeStructure(project), this);
        myTree = new Tree(new AsyncTreeModel(myModel, this));
        new DoubleClickListener() {
            @Override
            protected boolean onDoubleClick(MouseEvent mouseEvent) {
                TreePath selectionPath = myTree.getSelectionPath();
                if (selectionPath == null) {
                    return false;
                }
                Object userObject = TreeUtil.getLastUserObject(selectionPath);
                if (userObject instanceof TargetNodeDescriptor) {
                    MSBuildDaemonService.getInstance(project).runTarget(((TargetNodeDescriptor) userObject).getValue());
                    return true;
                }
                return false;
            }
        }.installOn(myTree);

        setContent(ScrollPaneFactory.createScrollPane(myTree, true));

        project.getMessageBus().connect().subscribe(MSBuildProjectListener.class, () -> myModel.invalidate());
    }

    @Override
    public void uiDataSnapshot(DataSink sink) {
        super.uiDataSnapshot(sink);
        sink.set(MSBuildToolWindowKeys.TREE_STRUCTURE, myModel);
    }

    public StructureTreeModel<MSBuildTreeStructure> getModel() {
        return myModel;
    }

    @Override
    public void dispose() {

    }
}
