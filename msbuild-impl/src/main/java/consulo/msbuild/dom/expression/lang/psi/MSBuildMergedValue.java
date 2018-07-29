package consulo.msbuild.dom.expression.lang.psi;

import javax.annotation.Nonnull;

import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;

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
