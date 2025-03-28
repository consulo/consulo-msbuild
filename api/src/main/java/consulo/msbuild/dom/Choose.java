// Generated on Sat Jan 28 04:58:19 MSK 2017
// DTD/Schema  :    http://schemas.microsoft.com/developer/msbuild/2003

package consulo.msbuild.dom;

import java.util.List;

import jakarta.annotation.Nonnull;

import consulo.xml.util.xml.DomElement;
import consulo.xml.util.xml.GenericAttributeValue;
import consulo.xml.util.xml.Required;

/**
 * http://schemas.microsoft.com/developer/msbuild/2003:ChooseType interface.
 * <pre>
 * <h3>Type http://schemas.microsoft.com/developer/msbuild/2003:ChooseType documentation</h3>
 * <!-- _locID_text="ChooseType" _locComment="" -->Groups When and Otherwise elements
 * </pre>
 *
 * @author VISTALL
 */
public interface Choose extends DomElement
{

	/**
	 * Returns the value of the Label child.
	 * <pre>
	 * <h3>Attribute null:Label documentation</h3>
	 * <!-- _locID_text="ChooseType_Label" _locComment="" -->Optional expression. Used to identify or order system and user elements
	 * </pre>
	 *
	 * @return the value of the Label child.
	 */
	@Nonnull
	GenericAttributeValue<String> getLabel();


	/**
	 * Returns the list of When children.
	 *
	 * @return the list of When children.
	 */
	@Nonnull
	@Required
	List<When> getWhens();

	/**
	 * Adds new child to the list of When children.
	 *
	 * @return created child
	 */
	When addWhen();


	/**
	 * Returns the value of the Otherwise child.
	 *
	 * @return the value of the Otherwise child.
	 */
	@Nonnull
	Otherwise getOtherwise();


}
