package consulo.msbuild.impl.dom.expression.editor;

import consulo.annotation.component.ExtensionImpl;
import consulo.language.editor.rawHighlight.HighlightVisitor;
import consulo.language.editor.rawHighlight.HighlightVisitorFactory;
import consulo.language.psi.PsiFile;
import consulo.msbuild.impl.dom.expression.lang.psi.MSBuildExpressionFile;

import jakarta.annotation.Nonnull;

/**
 * @author VISTALL
 * @since 26/03/2023
 */
@ExtensionImpl
public class MSBuildExpressionHighlightVisitorFactory implements HighlightVisitorFactory
{
	@Override
	public boolean suitableForFile(@Nonnull PsiFile psiFile)
	{
		return psiFile instanceof MSBuildExpressionFile;
	}

	@Nonnull
	@Override
	public HighlightVisitor createVisitor()
	{
		return new MSBuildExpressionHighlightVisitor();
	}
}
