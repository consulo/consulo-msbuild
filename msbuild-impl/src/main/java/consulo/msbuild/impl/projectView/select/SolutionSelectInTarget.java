package consulo.msbuild.impl.projectView.select;

import consulo.component.extension.Extensions;
import consulo.ide.impl.idea.ide.CompositeSelectInTarget;
import consulo.ide.impl.idea.ide.impl.SelectInTargetPsiWrapper;
import consulo.language.psi.PsiElement;
import consulo.language.psi.PsiFileSystemItem;
import consulo.language.psi.PsiUtilCore;
import consulo.msbuild.impl.projectView.SolutionViewPane;
import consulo.project.DumbService;
import consulo.project.Project;
import consulo.project.ui.view.ProjectView;
import consulo.project.ui.view.SelectInContext;
import consulo.project.ui.view.SelectInTarget;
import consulo.project.ui.view.tree.SelectableTreeStructureProvider;
import consulo.project.ui.view.tree.TreeStructureProvider;
import consulo.project.ui.wm.ToolWindowId;
import consulo.util.concurrent.ActionCallback;
import consulo.virtualFileSystem.VirtualFile;

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