package consulo.msbuild.toolWindow.dataRules;

import consulo.annotation.component.ExtensionImpl;
import consulo.dataContext.DataSink;
import consulo.dataContext.DataSnapshot;
import consulo.dataContext.UiDataRule;
import consulo.language.editor.PlatformDataKeys;
import consulo.msbuild.toolWindow.MSBuildToolWindowKeys;
import consulo.msbuild.toolWindow.MSBuildToolWindowPanel;
import consulo.ui.ex.content.Content;
import consulo.ui.ex.toolWindow.ToolWindow;

/**
 * @author VISTALL
 * @since 21/01/2021
 */
@ExtensionImpl
public class MSBuildTreeStructureGetDataRule implements UiDataRule {
    @Override
    public void uiDataSnapshot(DataSink sink, DataSnapshot snapshot) {
        sink.lazyValue(MSBuildToolWindowKeys.TREE_STRUCTURE, dataSnapshot -> {
            ToolWindow window = dataSnapshot.get(PlatformDataKeys.TOOL_WINDOW);
            if (window != null && window.getId().equals("MSBuild")) {
                Content selectedContent = window.getContentManager().getSelectedContent();

                if (selectedContent != null && selectedContent.getComponent() instanceof MSBuildToolWindowPanel) {
                    return ((MSBuildToolWindowPanel) selectedContent.getComponent()).getModel();
                }
            }

            return null;
        });
    }
}
