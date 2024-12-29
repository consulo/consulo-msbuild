// Generated on Sat Jan 28 04:58:19 MSK 2017
// DTD/Schema  :    http://schemas.microsoft.com/developer/msbuild/2003

package consulo.msbuild.dom;

import java.util.List;

import jakarta.annotation.Nonnull;

import consulo.xml.util.xml.DomElement;
import consulo.xml.util.xml.GenericAttributeValue;
import consulo.xml.util.xml.NameStrategy;
import consulo.xml.util.xml.NameStrategyForAttributes;
import consulo.xml.util.xml.SubTagList;

/**
 * http://schemas.microsoft.com/developer/msbuild/2003:ItemGroupType interface.
 * <pre>
 * <h3>Type http://schemas.microsoft.com/developer/msbuild/2003:ItemGroupType documentation</h3>
 * <!-- _locID_text="ItemGroupType" _locComment="" -->Groups item list definitions
 * </pre>
 *
 * @author VISTALL
 */
@NameStrategy(MSBuildNameStrategy.class)
@NameStrategyForAttributes(MSBuildNameStrategy.class)
public interface ItemGroup extends DomElement
{

	/**
	 * Returns the value of the Condition child.
	 * <pre>
	 * <h3>Attribute null:Condition documentation</h3>
	 * <!-- _locID_text="ItemGroupType_Condition" _locComment="" -->Optional expression evaluated to determine whether the ItemGroup should be used
	 * </pre>
	 *
	 * @return the value of the Condition child.
	 */
	@Nonnull
	GenericAttributeValue<String> getCondition();


	/**
	 * Returns the value of the Label child.
	 * <pre>
	 * <h3>Attribute null:Label documentation</h3>
	 * <!-- _locID_text="ItemGroupType_Label" _locComment="" -->Optional expression. Used to identify or order system and user elements
	 * </pre>
	 *
	 * @return the value of the Label child.
	 */
	@Nonnull
	GenericAttributeValue<String> getLabel();


	/**
	 * Returns the list of Item children.
	 *
	 * @return the list of Item children.
	 */
	@Nonnull
	List<SimpleItem> getItems();

	/**
	 * Adds new child to the list of Item children.
	 *
	 * @return created child
	 */
	SimpleItem addItem();


	/**
	 * Returns the list of Link children.
	 *
	 * @return the list of Link children.
	 */
	@Nonnull
	List<LinkItem> getLinks();

	/**
	 * Adds new child to the list of Link children.
	 *
	 * @return created child
	 */
	LinkItem addLink();


	/**
	 * Returns the list of ResourceCompile children.
	 *
	 * @return the list of ResourceCompile children.
	 */
	@Nonnull
	List<ResourceCompile> getResourceCompiles();

	/**
	 * Adds new child to the list of ResourceCompile children.
	 *
	 * @return created child
	 */
	ResourceCompile addResourceCompile();


	/**
	 * Returns the list of ResourceCompile children.
	 *
	 * @return the list of ResourceCompile children.
	 */
	@Nonnull
	List<EmbeddedResource> getEmbeddedResources();

	/**
	 * Adds new child to the list of ResourceCompile children.
	 *
	 * @return created child
	 */
	EmbeddedResource addEmbeddedResource();

	/**
	 * Returns the list of ResourceCompile children.
	 *
	 * @return the list of ResourceCompile children.
	 */
	@Nonnull
	List<Compile> getCompiles();

	/**
	 * Adds new child to the list of ResourceCompile children.
	 *
	 * @return created child
	 */
	Compile addCompile();

	@Nonnull
	List<Content> getContents();

	Content addContent();

	@Nonnull
	List<None> getNones();

	None addNone();

	@Nonnull
	List<ProjectReference> getProjectReferences();

	@Nonnull
	List<PackageReference> getPackageReferences();

	/**
	 * Returns the list of PreBuildEvent children.
	 *
	 * @return the list of PreBuildEvent children.
	 */
	@Nonnull
	List<PreBuildEventItem> getPreBuildEvents();

	/**
	 * Adds new child to the list of PreBuildEvent children.
	 *
	 * @return created child
	 */
	PreBuildEventItem addPreBuildEvent();


	/**
	 * Returns the list of PostBuildEvent children.
	 *
	 * @return the list of PostBuildEvent children.
	 */
	@Nonnull
	List<PostBuildEventItem> getPostBuildEvents();

	/**
	 * Adds new child to the list of PostBuildEvent children.
	 *
	 * @return created child
	 */
	PostBuildEventItem addPostBuildEvent();


	/**
	 * @return references
	 */
	@SubTagList("Reference")
	List<Reference> getReferences();
}
