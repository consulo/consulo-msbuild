// Generated on Sat Jan 28 04:58:19 MSK 2017
// DTD/Schema  :    http://schemas.microsoft.com/developer/msbuild/2003

package consulo.msbuild.dom;

import javax.annotation.Nonnull;

import consulo.xml.util.xml.Convert;
import consulo.xml.util.xml.DomElement;
import consulo.xml.util.xml.GenericAttributeValue;
import consulo.xml.util.xml.GenericDomValue;
import consulo.xml.util.xml.NameStrategy;
import consulo.xml.util.xml.NameStrategyForAttributes;
import consulo.xml.util.xml.Required;
import consulo.xml.util.xml.converters.PathReferenceConverter;

/**
 * http://schemas.microsoft.com/developer/msbuild/2003:CompileElemType interface.
 *
 * @author VISTALL
 */
@NameStrategy(MSBuildNameStrategy.class)
@NameStrategyForAttributes(MSBuildNameStrategy.class)
public interface Compile extends DomElement, SimpleItem
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
	 * <!-- _locID_text="Compile_Include" _locComment="" -->Semi-colon separated list of source files (wildcards are allowed)
	 * </pre>
	 *
	 * @return the value of the Include child.
	 */
	@Nonnull
	@Convert(PathReferenceConverter.class)
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

	GenericDomValue<String> getDesignTimeSharedInput();
}
