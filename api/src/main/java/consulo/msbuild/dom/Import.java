// Generated on Sat Jan 28 04:58:19 MSK 2017
// DTD/Schema  :    http://schemas.microsoft.com/developer/msbuild/2003

package consulo.msbuild.dom;

import jakarta.annotation.Nonnull;
import consulo.xml.util.xml.DomElement;
import consulo.xml.util.xml.GenericAttributeValue;
import consulo.xml.util.xml.NameStrategy;
import consulo.xml.util.xml.NameStrategyForAttributes;
import consulo.xml.util.xml.Required;
import consulo.msbuild.dom.annotation.ExpressionFragment;

/**
 * http://schemas.microsoft.com/developer/msbuild/2003:ImportType interface.
 * <pre>
 * <h3>Type http://schemas.microsoft.com/developer/msbuild/2003:ImportType documentation</h3>
 * <!-- _locID_text="ImportType" _locComment="" -->Declares that the contents of another project file should be inserted at this location
 * </pre>
 *
 * @author VISTALL
 */
@NameStrategy(MSBuildNameStrategy.class)
@NameStrategyForAttributes(MSBuildNameStrategy.class)
public interface Import extends DomElement
{

	/**
	 * Returns the value of the Condition child.
	 * <pre>
	 * <h3>Attribute null:Condition documentation</h3>
	 * <!-- _locID_text="ImportType_Condition" _locComment="" -->Optional expression evaluated to determine whether the import should occur
	 * </pre>
	 *
	 * @return the value of the Condition child.
	 */
	@Nonnull
	@ExpressionFragment
	GenericAttributeValue<String> getCondition();


	/**
	 * Returns the value of the Project child.
	 * <pre>
	 * <h3>Attribute null:Project documentation</h3>
	 * <!-- _locID_text="ImportType_Project" _locComment="" -->Project file to import
	 * </pre>
	 *
	 * @return the value of the Project child.
	 */
	@Nonnull
	@Required
	@ExpressionFragment(path = true)
	GenericAttributeValue<String> getProject();


	/**
	 * Returns the value of the Label child.
	 * <pre>
	 * <h3>Attribute null:Label documentation</h3>
	 * <!-- _locID_text="ImportType_Label" _locComment="" -->Optional expression. Used to identify or order system and user elements
	 * </pre>
	 *
	 * @return the value of the Label child.
	 */
	@Nonnull
	GenericAttributeValue<String> getLabel();
}
