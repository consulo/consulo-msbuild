// Generated on Sat Jan 28 04:58:19 MSK 2017
// DTD/Schema  :    http://schemas.microsoft.com/developer/msbuild/2003

package consulo.msbuild.dom;

import javax.annotation.Nonnull;

import com.intellij.util.xml.NameStrategy;
import com.intellij.util.xml.NameStrategyForAttributes;
import com.intellij.util.xml.Required;

/**
 * http://schemas.microsoft.com/developer/msbuild/2003:ContentElemType interface.
 *
 * @author VISTALL
 */
@NameStrategy(MSBuildNameStrategy.class)
@NameStrategyForAttributes(MSBuildNameStrategy.class)
public interface Content extends SimpleItem
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
}
