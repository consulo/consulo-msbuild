// Generated on Sat Jan 28 04:58:19 MSK 2017
// DTD/Schema  :    http://schemas.microsoft.com/developer/msbuild/2003

package consulo.msbuild.dom;

import consulo.msbuild.dom.annotation.ExpressionFragment;
import consulo.xml.util.xml.*;

import jakarta.annotation.Nonnull;
import java.util.List;

/**
 * http://schemas.microsoft.com/developer/msbuild/2003:PropertyGroupType interface.
 * <pre>
 * <h3>Type http://schemas.microsoft.com/developer/msbuild/2003:PropertyGroupType documentation</h3>
 * <!-- _locID_text="PropertyGroupType" _locComment="" -->Groups property definitions
 * </pre>
 *
 * @author VISTALL
 */
@NameStrategy(MSBuildNameStrategy.class)
@NameStrategyForAttributes(MSBuildNameStrategy.class)
public interface PropertyGroup extends DomElement
{

	/**
	 * Returns the value of the Condition child.
	 * <pre>
	 * <h3>Attribute null:Condition documentation</h3>
	 * <!-- _locID_text="PropertyGroupType_Condition" _locComment="" -->Optional expression evaluated to determine whether the PropertyGroup should be used
	 * </pre>
	 *
	 * @return the value of the Condition child.
	 */
	@Nonnull
	@ExpressionFragment
	GenericAttributeValue<String> getCondition();


	/**
	 * Returns the value of the Label child.
	 * <pre>
	 * <h3>Attribute null:Label documentation</h3>
	 * <!-- _locID_text="PropertyGroupType_Label" _locComment="" -->Optional expression. Used to identify or order system and user elements
	 * </pre>
	 *
	 * @return the value of the Label child.
	 */
	@Nonnull
	GenericAttributeValue<String> getLabel();


	@CustomChildren
	List<Property> getProperties();
}
