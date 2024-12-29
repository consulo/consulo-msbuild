package consulo.msbuild.impl.dom.expression.lang.psi;

import consulo.language.ast.ASTNode;
import consulo.language.impl.psi.ASTWrapperPsiElement;

import jakarta.annotation.Nonnull;

/**
 * @author VISTALL
 * @since 2018-06-25
 */
public class MSBuildMergedValue extends ASTWrapperPsiElement
{
	public MSBuildMergedValue(@Nonnull ASTNode node)
	{
		super(node);
	}
}
