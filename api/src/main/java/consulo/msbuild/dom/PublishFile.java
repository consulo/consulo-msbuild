// Generated on Sat Jan 28 04:58:19 MSK 2017
// DTD/Schema  :    http://schemas.microsoft.com/developer/msbuild/2003

package consulo.msbuild.dom;

import jakarta.annotation.Nonnull;

import consulo.xml.util.xml.DomElement;
import consulo.xml.util.xml.GenericAttributeValue;
import consulo.xml.util.xml.GenericDomValue;
import consulo.xml.util.xml.Required;

/**
 * http://schemas.microsoft.com/developer/msbuild/2003:PublishFileElemType interface.
 *
 * @author VISTALL
 */
public interface PublishFile extends DomElement, SimpleItem
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
	 * Returns the value of the Visible child.
	 * <pre>
	 * <h3>Element http://schemas.microsoft.com/developer/msbuild/2003:Visible documentation</h3>
	 * <!-- _locID_text="PublishFile_InProject" _locComment="" -->Display in user interface (optional, boolean)
	 * </pre>
	 *
	 * @return the value of the Visible child.
	 */
	@Nonnull
	@Required
	GenericDomValue<String> getVisible();


	/**
	 * Returns the value of the Group child.
	 *
	 * @return the value of the Group child.
	 */
	@Nonnull
	@Required
	GenericDomValue<String> getGroup();


	/**
	 * Returns the value of the IncludeHash child.
	 * <pre>
	 * <h3>Element http://schemas.microsoft.com/developer/msbuild/2003:IncludeHash documentation</h3>
	 * <!-- _locID_text="PublishFile_IncludeHash" _locComment="" -->(boolean)
	 * </pre>
	 *
	 * @return the value of the IncludeHash child.
	 */
	@Nonnull
	@Required
	GenericDomValue<String> getIncludeHash();


	/**
	 * Returns the value of the IsAssembly child.
	 * <pre>
	 * <h3>Element http://schemas.microsoft.com/developer/msbuild/2003:IsAssembly documentation</h3>
	 * <!-- _locID_text="PublishFile_IsAssembly" _locComment="" -->(boolean)
	 * </pre>
	 *
	 * @return the value of the IsAssembly child.
	 */
	@Nonnull
	@Required
	GenericDomValue<String> getIsAssembly();


	/**
	 * Returns the value of the PublishState child.
	 * <pre>
	 * <h3>Element http://schemas.microsoft.com/developer/msbuild/2003:PublishState documentation</h3>
	 * <!-- _locID_text="PublishFile_PublishState" _locComment="" -->Default, Included, Excluded, DataFile, ManifestEntryPoint, or Prerequisite
	 * </pre>
	 *
	 * @return the value of the PublishState child.
	 */
	@Nonnull
	@Required
	GenericDomValue<String> getPublishState();


}
