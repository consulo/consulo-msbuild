// Generated on Sat Jan 28 04:58:19 MSK 2017
// DTD/Schema  :    http://schemas.microsoft.com/developer/msbuild/2003

package consulo.msbuild.dom;

import javax.annotation.Nonnull;

import consulo.xml.util.xml.DomElement;
import consulo.xml.util.xml.Required;

/**
 * http://schemas.microsoft.com/developer/msbuild/2003:OtherwiseType interface.
 * <pre>
 * <h3>Type http://schemas.microsoft.com/developer/msbuild/2003:OtherwiseType documentation</h3>
 * <!-- _locID_text="OtherwiseType" _locComment="" -->Groups PropertyGroup and/or ItemGroup elements that are used if no Conditions on sibling When elements evaluate to true
 * </pre>
 *
 * @author VISTALL
 */
public interface Otherwise extends DomElement
{

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
