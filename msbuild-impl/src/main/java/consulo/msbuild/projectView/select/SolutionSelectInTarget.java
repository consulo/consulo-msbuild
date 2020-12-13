package consulo.msbuild.projectView.select;

import com.intellij.ide.CompositeSelectInTarget;
import com.intellij.ide.SelectInContext;
import com.intellij.ide.SelectInTarget;
import com.intellij.ide.impl.SelectInTargetPsiWrapper;
import com.intellij.ide.projectView.ProjectView;
import com.intellij.ide.projectView.SelectableTreeStructureProvider;
import com.intellij.ide.projectView.TreeStructureProvider;
import com.intellij.openapi.extensions.Extensions;
import com.intellij.openapi.project.DumbService;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.ActionCallback;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.openapi.wm.ToolWindowId;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFileSystemItem;
import com.intellij.psi.util.PsiUtilCore;
import consulo.msbuild.projectView.SolutionViewPane;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * @author VISTALL
 * @since 2018-08-07
 */
public class SolutionSelectInTarget extends SelectInTargetPsiWrapper implements CompositeSelectInTarget
{
	public SolutionSelectInTarget(Project project)
	{
		super(project);
	}

	@Override
	public String toString()
	{
		return null;
	}

	@Override
	protected final void select(final Object selector, final VirtualFile virtualFile, final boolean requestFocus)
	{
		select(myProject, selector, getMinorViewId(), virtualFile, requestFocus);
	}

	@Nonnull
	public static ActionCallback select(@Nonnull Project project,
										final Object toSelect,
										@Nullable final String viewId,
										final VirtualFile virtualFile,
										final boolean requestFocus)
	{
		SolutionViewPane pane = (SolutionViewPane) ProjectView.getInstance(project).getProjectViewPaneById(SolutionViewPane.ID);
		if(pane == null)
		{
			return ActionCallback.DONE;
		}
		
		final ActionCallback result = new ActionCallback();

		pane.selectCB(toSelect, virtualFile, requestFocus).notify(result);
		return result;
	}

	@Override
	@Nonnull
	public Collection<SelectInTarget> getSubTargets(@Nonnull SelectInContext context)
	{
		return Arrays.asList(new SolutionSelectInTarget(myProject));
	}

	@Override
	protected boolean canSelect(PsiFileSystemItem file)
	{
		return true;
	}

	@Override
	public void select(PsiElement element, final boolean requestFocus)
	{
		PsiElement toSelect = null;
		for(TreeStructureProvider provider : getProvidersDumbAware())
		{
			if(provider instanceof SelectableTreeStructureProvider)
			{
				toSelect = ((SelectableTreeStructureProvider) provider).getTopLevelElement(element);
			}
			if(toSelect != null)
			{
				break;
			}
		}

		toSelect = findElementToSelect(element, toSelect);

		if(toSelect != null)
		{
			VirtualFile virtualFile = PsiUtilCore.getVirtualFile(toSelect);
			select(toSelect, virtualFile, requestFocus);
		}
	}

	private TreeStructureProvider[] getProvidersDumbAware()
	{
		TreeStructureProvider[] allProviders = Extensions.getExtensions(TreeStructureProvider.EP_NAME, myProject);
		List<TreeStructureProvider> dumbAware = DumbService.getInstance(myProject).filterByDumbAwareness(allProviders);
		return dumbAware.toArray(new TreeStructureProvider[dumbAware.size()]);
	}

	@Override
	public final String getToolWindowId()
	{
		return ToolWindowId.PROJECT_VIEW;
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

	@Override
	protected boolean canWorkWithCustomObjects()
	{
		return true;
	}
}