package consulo.msbuild.impl.dom.expression.lang.psi;

import consulo.language.ast.ASTNode;
import consulo.language.impl.psi.ASTWrapperPsiElement;

import javax.annotation.Nonnull;

/**
 * @author VISTALL
 * @since 2018-06-25
 */
public class MSBuildBinaryExpression extends ASTWrapperPsiElement
{
	public MSBuildBinaryExpression(@Nonnull ASTNode node)
	{
		super(node);
	}
}
