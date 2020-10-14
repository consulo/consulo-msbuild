package consulo.msbuild.dom;

import com.intellij.util.xml.DomElement;
import com.intellij.util.xml.GenericAttributeValue;
import consulo.msbuild.dom.annotation.ExpressionFragment;

import javax.annotation.Nonnull;

/**
 * @author VISTALL
 * @since 2020-06-24
 */
public interface ElementWithCondition extends DomElement
{
	@Nonnull
	@ExpressionFragment
	GenericAttributeValue<String> getCondition();
}
