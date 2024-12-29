package consulo.msbuild.impl.dom.expression.lang;

import consulo.annotation.component.ExtensionImpl;
import consulo.language.psi.PsiElement;
import consulo.xml.psi.xml.XmlText;

import jakarta.annotation.Nonnull;

/**
 * @author VISTALL
 * @since 11/01/2023
 */
@ExtensionImpl
public class MSBuildExpressionXmlTextInjector extends MSBuildBaseExpressionXmlInjector
{
	@Nonnull
	@Override
	public Class<? extends PsiElement> getElementClass()
	{
		return XmlText.class;
	}
}
