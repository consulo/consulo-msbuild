// Generated on Sat Jan 28 04:58:18 MSK 2017
// DTD/Schema  :    http://schemas.microsoft.com/developer/msbuild/2003

package consulo.msbuild.dom;

import javax.annotation.Nonnull;

import consulo.xml.util.xml.DomElement;
import consulo.xml.util.xml.GenericAttributeValue;
import consulo.xml.util.xml.GenericDomValue;

/**
 * http://schemas.microsoft.com/developer/msbuild/2003:AppxManifestMetadataElemType interface.
 *
 * @author VISTALL
 */
public interface AppxManifestMetadata extends DomElement, SimpleItem
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
	 * Returns the value of the Value child.
	 * <pre>
	 * <h3>Element http://schemas.microsoft.com/developer/msbuild/2003:Value documentation</h3>
	 * <!-- _locID_text="AppxManifestMetadata_Value" _locComment="" -->Literal value of app manifest metadata to insert into manifest.
	 * </pre>
	 *
	 * @return the value of the Value child.
	 */
	@Nonnull
	GenericDomValue<String> getValue();


	/**
	 * Returns the value of the Version child.
	 * <pre>
	 * <h3>Element http://schemas.microsoft.com/developer/msbuild/2003:Version documentation</h3>
	 * <!-- _locID_text="AppxManifestMetadata_Version" _locComment="" -->Version to be inserted as a value of app manifest metadata to insert into manifest.
	 * </pre>
	 *
	 * @return the value of the Version child.
	 */
	@Nonnull
	GenericDomValue<String> getVersion();


	/**
	 * Returns the value of the Name child.
	 * <pre>
	 * <h3>Element http://schemas.microsoft.com/developer/msbuild/2003:Name documentation</h3>
	 * <!-- _locID_text="AppxManifestMetadata_Name" _locComment="" -->Name of app manifest metadata to insert into manifest.
	 * </pre>
	 *
	 * @return the value of the Name child.
	 */
	@Nonnull
	GenericDomValue<String> getName();


}
