package consulo.msbuild.toolWindow.dataRules;

import consulo.annotation.component.ExtensionImpl;
import consulo.dataContext.DataProvider;
import consulo.dataContext.GetDataRule;
import consulo.language.editor.PlatformDataKeys;
import consulo.msbuild.toolWindow.MSBuildToolWindowKeys;
import consulo.msbuild.toolWindow.MSBuildToolWindowPanel;
import consulo.ui.ex.awt.tree.StructureTreeModel;
import consulo.ui.ex.content.Content;
import consulo.ui.ex.toolWindow.ToolWindow;
import consulo.util.dataholder.Key;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

/**
 * @author VISTALL
 * @since 21/01/2021
 */
@ExtensionImpl
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
