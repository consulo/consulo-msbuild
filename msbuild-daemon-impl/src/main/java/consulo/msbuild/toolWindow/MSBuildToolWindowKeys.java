package consulo.msbuild.toolWindow;

import consulo.ui.ex.awt.tree.StructureTreeModel;
import consulo.util.dataholder.Key;

/**
 * @author VISTALL
 * @since 20/01/2021
 */
public interface MSBuildToolWindowKeys
{
	Key<StructureTreeModel<?>> TREE_STRUCTURE = Key.create("msbuild.key.structure");
}
