// Generated on Sat Jan 28 04:58:20 MSK 2017
// DTD/Schema  :    http://schemas.microsoft.com/developer/msbuild/2003

package consulo.msbuild.dom;

import javax.annotation.Nonnull;

import consulo.xml.util.xml.DomElement;
import consulo.xml.util.xml.GenericAttributeValue;
import consulo.xml.util.xml.Required;

/**
 * http://schemas.microsoft.com/developer/msbuild/2003:UsingTaskBodyType interface.
 * <pre>
 * <h3>Type http://schemas.microsoft.com/developer/msbuild/2003:UsingTaskBodyType documentation</h3>
 * <!-- _locID_text="UsingTaskBodyType" _locComment="" -->Contains the inline task implementation. Content is opaque to MSBuild.
 * </pre>
 *
 * @author VISTALL
 */
public interface UsingTaskBody extends DomElement
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
	 * Returns the value of the Evaluate child.
	 * <pre>
	 * <h3>Attribute null:Evaluate documentation</h3>
	 * <!-- _locID_text="UsingTaskBodyType_Evaluate" _locComment="" -->Whether the body should have properties expanded before use. Defaults to false.
	 * </pre>
	 *
	 * @return the value of the Evaluate child.
	 */
	@Nonnull
	GenericAttributeValue<Boolean> getEvaluate();


}
