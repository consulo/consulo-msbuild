// Generated on Sat Jan 28 04:58:19 MSK 2017
// DTD/Schema  :    http://schemas.microsoft.com/developer/msbuild/2003

package consulo.msbuild.dom;

import java.util.List;

import jakarta.annotation.Nonnull;

import consulo.xml.util.xml.DomElement;
import consulo.xml.util.xml.GenericAttributeValue;
import consulo.xml.util.xml.Required;

/**
 * http://schemas.microsoft.com/developer/msbuild/2003:GenerateAppxPackageRecipeElemType interface.
 *
 * @author VISTALL
 */
public interface GenerateAppxPackageRecipe extends DomElement, Task
{

	/**
	 * Returns the value of the AppxManifestXml child.
	 *
	 * @return the value of the AppxManifestXml child.
	 */
	@Nonnull
	@Required
	GenericAttributeValue<String> getAppxManifestXml();


	/**
	 * Returns the value of the SourceAppxManifest child.
	 *
	 * @return the value of the SourceAppxManifest child.
	 */
	@Nonnull
	@Required
	GenericAttributeValue<String> getSourceAppxManifest();


	/**
	 * Returns the value of the SolutionConfiguration child.
	 *
	 * @return the value of the SolutionConfiguration child.
	 */
	@Nonnull
	@Required
	GenericAttributeValue<String> getSolutionConfiguration();


	/**
	 * Returns the value of the PayloadFiles child.
	 *
	 * @return the value of the PayloadFiles child.
	 */
	@Nonnull
	@Required
	GenericAttributeValue<String> getPayloadFiles();


	/**
	 * Returns the value of the FrameworkSdkPackages child.
	 *
	 * @return the value of the FrameworkSdkPackages child.
	 */
	@Nonnull
	@Required
	GenericAttributeValue<String> getFrameworkSdkPackages();


	/**
	 * Returns the value of the RecipeFile child.
	 *
	 * @return the value of the RecipeFile child.
	 */
	@Nonnull
	@Required
	GenericAttributeValue<String> getRecipeFile();


	/**
	 * Returns the value of the SystemBinaries child.
	 *
	 * @return the value of the SystemBinaries child.
	 */
	@Nonnull
	@Required
	GenericAttributeValue<String> getSystemBinaries();


	/**
	 * Returns the value of the ReservedFileNames child.
	 *
	 * @return the value of the ReservedFileNames child.
	 */
	@Nonnull
	@Required
	GenericAttributeValue<String> getReservedFileNames();


	/**
	 * Returns the value of the QueryNamespacePrefix child.
	 *
	 * @return the value of the QueryNamespacePrefix child.
	 */
	@Nonnull
	@Required
	GenericAttributeValue<String> getQueryNamespacePrefix();


	/**
	 * Returns the value of the QueryNamespace81Prefix child.
	 *
	 * @return the value of the QueryNamespace81Prefix child.
	 */
	@Nonnull
	@Required
	GenericAttributeValue<String> getQueryNamespace81Prefix();


	/**
	 * Returns the value of the ManifestFileNameQueries child.
	 *
	 * @return the value of the ManifestFileNameQueries child.
	 */
	@Nonnull
	@Required
	GenericAttributeValue<String> getManifestFileNameQueries();


	/**
	 * Returns the value of the ManifestImageFileNameQueries child.
	 *
	 * @return the value of the ManifestImageFileNameQueries child.
	 */
	@Nonnull
	@Required
	GenericAttributeValue<String> getManifestImageFileNameQueries();


	/**
	 * Returns the value of the PackageArchitecture child.
	 *
	 * @return the value of the PackageArchitecture child.
	 */
	@Nonnull
	@Required
	GenericAttributeValue<String> getPackageArchitecture();


	/**
	 * Returns the value of the ProjectDir child.
	 *
	 * @return the value of the ProjectDir child.
	 */
	@Nonnull
	@Required
	GenericAttributeValue<String> getProjectDir();


	/**
	 * Returns the value of the TargetPlatformIdentifier child.
	 *
	 * @return the value of the TargetPlatformIdentifier child.
	 */
	@Nonnull
	@Required
	GenericAttributeValue<String> getTargetPlatformIdentifier();


	/**
	 * Returns the value of the IndexedPayloadFiles child.
	 *
	 * @return the value of the IndexedPayloadFiles child.
	 */
	@Nonnull
	GenericAttributeValue<String> getIndexedPayloadFiles();


	/**
	 * Returns the value of the MakePriExtensionPath child.
	 *
	 * @return the value of the MakePriExtensionPath child.
	 */
	@Nonnull
	GenericAttributeValue<String> getMakePriExtensionPath();


	/**
	 * Returns the value of the Condition child.
	 * <pre>
	 * <h3>Attribute null:Condition documentation</h3>
	 * <!-- _locID_text="TaskType_Condition" _locComment="" -->Optional expression evaluated to determine whether the task should be executed
	 * </pre>
	 *
	 * @return the value of the Condition child.
	 */
	@Nonnull
	GenericAttributeValue<String> getCondition();


	/**
	 * Returns the value of the ContinueOnError child.
	 * <pre>
	 * <h3>Attribute null:ContinueOnError documentation</h3>
	 * <!-- _locID_text="TaskType_ContinueOnError" _locComment="" -->Optional boolean indicating whether a recoverable task error should be ignored. Default false
	 * </pre>
	 *
	 * @return the value of the ContinueOnError child.
	 */
	@Nonnull
	GenericAttributeValue<Boolean> getContinueOnError();


	/**
	 * Returns the value of the Architecture child.
	 * <pre>
	 * <h3>Attribute null:Architecture documentation</h3>
	 * <!-- _locID_text="TaskType_Architecture" _locComment="" -->Defines the bitness of the task if it must be run specifically in a 32bit or 64bit process. If not specified, it will run with the
	 * bitness of the build process.  If there are multiple tasks defined in UsingTask with the same name but with different Architecture attribute values, the value of the Architecture attribute
	 * specified here will be used to match and select the correct task
	 * </pre>
	 *
	 * @return the value of the Architecture child.
	 */
	@Nonnull
	GenericAttributeValue<String> getArchitecture();


	/**
	 * Returns the value of the Runtime child.
	 * <pre>
	 * <h3>Attribute null:Runtime documentation</h3>
	 * <!-- _locID_text="TaskType_Runtime" _locComment="" -->Defines the .NET runtime of the task. This must be specified if the task must run on a specific version of the .NET runtime. If not
	 * specified, the task will run on the runtime being used by the build process. If there are multiple tasks defined in UsingTask with the same name but with different Runtime attribute values,
	 * the value of the Runtime attribute specified here will be used to match and select the correct task
	 * </pre>
	 *
	 * @return the value of the Runtime child.
	 */
	@Nonnull
	GenericAttributeValue<String> getRuntime();


	/**
	 * Returns the list of Output children.
	 * <pre>
	 * <h3>Element http://schemas.microsoft.com/developer/msbuild/2003:Output documentation</h3>
	 * <!-- _locID_text="TaskType_Output" _locComment="" -->Optional element specifying a specific task output to be gathered
	 * </pre>
	 *
	 * @return the list of Output children.
	 */
	@Nonnull
	List<Output> getOutputs();

	/**
	 * Adds new child to the list of Output children.
	 *
	 * @return created child
	 */
	Output addOutput();


}
