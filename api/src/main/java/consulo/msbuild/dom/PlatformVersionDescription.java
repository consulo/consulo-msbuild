// Generated on Sat Jan 28 04:58:19 MSK 2017
// DTD/Schema  :    http://schemas.microsoft.com/developer/msbuild/2003

package consulo.msbuild.dom;

import jakarta.annotation.Nonnull;

import consulo.xml.util.xml.DomElement;
import consulo.xml.util.xml.GenericAttributeValue;
import consulo.xml.util.xml.GenericDomValue;
import consulo.xml.util.xml.Required;

/**
 * http://schemas.microsoft.com/developer/msbuild/2003:PlatformVersionDescriptionElemType interface.
 *
 * @author VISTALL
 */
public interface PlatformVersionDescription extends DomElement, SimpleItem
{

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


	/**
	 * Returns the value of the Include child.
	 * <pre>
	 * <h3>Attribute null:Include documentation</h3>
	 * <!-- _locID_text="SimpleItemType_Include" _locComment="" -->Semi-colon separated list of files (wildcards are allowed) or other item names to include in this item list
	 * </pre>
	 *
	 * @return the value of the Include child.
	 */
	@Nonnull
	GenericAttributeValue<String> getInclude();


	/**
	 * Returns the value of the Exclude child.
	 * <pre>
	 * <h3>Attribute null:Exclude documentation</h3>
	 * <!-- _locID_text="SimpleItemType_Exclude" _locComment="" -->Semi-colon separated list of files (wildcards are allowed) or other item names to exclude from the Include list
	 * </pre>
	 *
	 * @return the value of the Exclude child.
	 */
	@Nonnull
	GenericAttributeValue<String> getExclude();


	/**
	 * Returns the value of the Remove child.
	 * <pre>
	 * <h3>Attribute null:Remove documentation</h3>
	 * <!-- _locID_text="SimpleItemType_Remove" _locComment="" -->Semi-colon separated list of files (wildcards are allowed) or other item names to remove from the existing list contents
	 * </pre>
	 *
	 * @return the value of the Remove child.
	 */
	@Nonnull
	GenericAttributeValue<String> getRemove();


	/**
	 * Returns the value of the Update child.
	 * <pre>
	 * <h3>Attribute null:Update documentation</h3>
	 * <!-- _locID_text="SimpleItemType_Remove" _locComment="" -->Semi-colon separated list of files (wildcards are allowed) or other item names to be updated with the metadata from contained in
	 * this xml element
	 * </pre>
	 *
	 * @return the value of the Update child.
	 */
	@Nonnull
	GenericAttributeValue<String> getUpdate();


	/**
	 * Returns the value of the Label child.
	 * <pre>
	 * <h3>Attribute null:Label documentation</h3>
	 * <!-- _locID_text="ImportGroupType_Label" _locComment="" -->Optional expression. Used to identify or order system and user elements
	 * </pre>
	 *
	 * @return the value of the Label child.
	 */
	@Nonnull
	GenericAttributeValue<String> getLabel();


	/**
	 * Returns the value of the TargetPlatformIdentifier child.
	 * <pre>
	 * <h3>Element http://schemas.microsoft.com/developer/msbuild/2003:TargetPlatformIdentifier documentation</h3>
	 * <!-- _locID_text="PlatformVersionDescription_TargetPlatformIdentifier" _locComment="" -->Target platform identifier.
	 * </pre>
	 *
	 * @return the value of the TargetPlatformIdentifier child.
	 */
	@Nonnull
	@Required
	GenericDomValue<String> getTargetPlatformIdentifier();


	/**
	 * Returns the value of the TargetPlatformVersion child.
	 * <pre>
	 * <h3>Element http://schemas.microsoft.com/developer/msbuild/2003:TargetPlatformVersion documentation</h3>
	 * <!-- _locID_text="PlatformVersionDescription_TargetPlatformVersion" _locComment="" -->Target platform version.
	 * </pre>
	 *
	 * @return the value of the TargetPlatformVersion child.
	 */
	@Nonnull
	@Required
	GenericDomValue<String> getTargetPlatformVersion();


	/**
	 * Returns the value of the OSVersion child.
	 * <pre>
	 * <h3>Element http://schemas.microsoft.com/developer/msbuild/2003:OSVersion documentation</h3>
	 * <!-- _locID_text="PlatformVersionDescription_OSVersion" _locComment="" -->Internal OS version.
	 * </pre>
	 *
	 * @return the value of the OSVersion child.
	 */
	@Nonnull
	@Required
	GenericDomValue<String> getOSVersion();


}
