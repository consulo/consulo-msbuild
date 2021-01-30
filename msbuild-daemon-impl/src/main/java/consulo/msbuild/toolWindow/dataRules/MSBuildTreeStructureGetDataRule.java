package consulo.msbuild.toolWindow.dataRules;

import com.intellij.ide.impl.dataRules.GetDataRule;
import com.intellij.openapi.actionSystem.DataProvider;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.ui.content.Content;
import com.intellij.ui.tree.StructureTreeModel;
import consulo.msbuild.toolWindow.MSBuildToolWindowKeys;
import consulo.msbuild.toolWindow.MSBuildToolWindowPanel;
import consulo.util.dataholder.Key;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * @author VISTALL
 * @since 21/01/2021
 */
public class MSBuildTreeStructureGetDataRule implements GetDataRule<StructureTreeModel<?>>
{
	@Nonnull
	@Override
	public Key<StructureTreeModel<?>> getKey()
	{
		return MSBuildToolWindowKeys.TREE_STRUCTURE;
	}

	@Nullable
	@Override
	public StructureTreeModel<?> getData(@Nonnull DataProvider dataProvider)
	{
		ToolWindow window = dataProvider.getDataUnchecked(PlatformDataKeys.TOOL_WINDOW);
		if(window  != null && window.getId().equals("MSBuild"))
		{
			Content selectedContent = window.getContentManager().getSelectedContent();

			if(selectedContent != null && selectedContent.getComponent() instanceof MSBuildToolWindowPanel)
			{
				return ((MSBuildToolWindowPanel) selectedContent.getComponent()).getModel();
			}
		}
		return null;
	}
}
