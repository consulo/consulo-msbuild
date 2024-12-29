// Generated on Sat Jan 28 04:58:19 MSK 2017
// DTD/Schema  :    http://schemas.microsoft.com/developer/msbuild/2003

package consulo.msbuild.dom;

import jakarta.annotation.Nonnull;

import consulo.xml.util.xml.DomElement;
import consulo.xml.util.xml.GenericAttributeValue;
import consulo.xml.util.xml.Required;

/**
 * http://schemas.microsoft.com/developer/msbuild/2003:CodeAnalysisDependentAssemblyPathsElemType interface.
 *
 * @author VISTALL
 */
public interface CodeAnalysisDependentAssemblyPaths extends DomElement
{

	/**
	 * Returns the value of the simple content.
	 *
	 * @return the value of the simple content.
	 */
	@Nonnull
	@Required
	String getValue();

	/**
	 * Sets the value of the simple content.
	 *
	 * @param value the new value to set
	 */
	void setValue(@Nonnull String value);


	/**
	 * Returns the value of the Include child.
	 * <pre>
	 * <h3>Attribute null:Include documentation</h3>
	 * <!-- _locID_text="CodeAnalysisDependentAssemblyPaths_Include" _locComment="" -->A fully qualified path to a directory containing reference assemblies to be used by Code Analysis.
	 * </pre>
	 *
	 * @return the value of the Include child.
	 */
	@Nonnull
	@Required
	GenericAttributeValue<String> getInclude();


	/**
	 * Returns the value of the Condition child.
	 * <pre>
	 * <h3>Attribute null:Condition documentation</h3>
	 * <!-- _locID_text="SimpleItemType_Condition" _locComment="" -->Optional expression evaluated to determine whether the items should be evaluated
	 * </pre>
	 *
	 * @return the value of the Condition child.
	 */
	@Nonnull
	GenericAttributeValue<String> getCondition();


}
