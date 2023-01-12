// Generated on Sat Jan 28 04:58:20 MSK 2017
// DTD/Schema  :    http://schemas.microsoft.com/developer/msbuild/2003

package consulo.msbuild.dom;

import javax.annotation.Nonnull;

import consulo.xml.util.xml.DomElement;
import consulo.xml.util.xml.GenericAttributeValue;
import consulo.xml.util.xml.Required;

/**
 * http://schemas.microsoft.com/developer/msbuild/2003:WhenType interface.
 * <pre>
 * <h3>Type http://schemas.microsoft.com/developer/msbuild/2003:WhenType documentation</h3>
 * <!-- _locID_text="WhenType" _locComment="" -->Groups PropertyGroup and/or ItemGroup elements
 * </pre>
 *
 * @author VISTALL
 */
public interface When extends DomElement
{

	/**
	 * Returns the value of the Condition child.
	 * <pre>
	 * <h3>Attribute null:Condition documentation</h3>
	 * <!-- _locID_text="WhenType_Condition" _locComment="" -->Optional expression evaluated to determine whether the child PropertyGroups and/or ItemGroups should be used
	 * </pre>
	 *
	 * @return the value of the Condition child.
	 */
	@Nonnull
	@Required
	GenericAttributeValue<String> getCondition();


	/**
	 * Returns the value of the PropertyGroup child.
	 *
	 * @return the value of the PropertyGroup child.
	 */
	@Nonnull
	@Required
	PropertyGroup getPropertyGroup();


	/**
	 * Returns the value of the ItemGroup child.
	 *
	 * @return the value of the ItemGroup child.
	 */
	@Nonnull
	@Required
	ItemGroup getItemGroup();


	/**
	 * Returns the value of the Choose child.
	 *
	 * @return the value of the Choose child.
	 */
	@Nonnull
	@Required
	Choose getChoose();


}
