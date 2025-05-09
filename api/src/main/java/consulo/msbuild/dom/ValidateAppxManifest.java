// Generated on Sat Jan 28 04:58:20 MSK 2017
// DTD/Schema  :    http://schemas.microsoft.com/developer/msbuild/2003

package consulo.msbuild.dom;

import java.util.List;

import jakarta.annotation.Nonnull;

import consulo.xml.util.xml.DomElement;
import consulo.xml.util.xml.GenericAttributeValue;
import consulo.xml.util.xml.Required;

/**
 * http://schemas.microsoft.com/developer/msbuild/2003:ValidateAppxManifestElemType interface.
 *
 * @author VISTALL
 */
public interface ValidateAppxManifest extends DomElement, Task
{

	/**
	 * Returns the value of the Input child.
	 *
	 * @return the value of the Input child.
	 */
	@Nonnull
	@Required
	GenericAttributeValue<String> getInput();


	/**
	 * Returns the value of the SourceAppxManifest child.
	 *
	 * @return the value of the SourceAppxManifest child.
	 */
	@Nonnull
	@Required
	GenericAttributeValue<String> getSourceAppxManifest();


	/**
	 * Returns the value of the AppxManifestSchema child.
	 *
	 * @return the value of the AppxManifestSchema child.
	 */
	@Nonnull
	@Required
	GenericAttributeValue<String> getAppxManifestSchema();


	/**
	 * Returns the value of the StoreAssociationFile child.
	 *
	 * @return the value of the StoreAssociationFile child.
	 */
	@Nonnull
	GenericAttributeValue<String> getStoreAssociationFile();


	/**
	 * Returns the value of the TargetPlatformIdentifier child.
	 *
	 * @return the value of the TargetPlatformIdentifier child.
	 */
	@Nonnull
	@Required
	GenericAttributeValue<String> getTargetPlatformIdentifier();


	/**
	 * Returns the value of the TargetPlatformVersion child.
	 *
	 * @return the value of the TargetPlatformVersion child.
	 */
	@Nonnull
	@Required
	GenericAttributeValue<String> getTargetPlatformVersion();


	/**
	 * Returns the value of the OSMinVersion child.
	 *
	 * @return the value of the OSMinVersion child.
	 */
	@Nonnull
	@Required
	GenericAttributeValue<String> getOSMinVersion();


	/**
	 * Returns the value of the OSMaxVersionTested child.
	 *
	 * @return the value of the OSMaxVersionTested child.
	 */
	@Nonnull
	@Required
	GenericAttributeValue<String> getOSMaxVersionTested();


	/**
	 * Returns the value of the PlatformVersionDescriptions child.
	 *
	 * @return the value of the PlatformVersionDescriptions child.
	 */
	@Nonnull
	@Required
	GenericAttributeValue<String> getPlatformVersionDescriptions();


	/**
	 * Returns the value of the ResolvedSDKReferences child.
	 *
	 * @return the value of the ResolvedSDKReferences child.
	 */
	@Nonnull
	@Required
	GenericAttributeValue<String> getResolvedSDKReferences();


	/**
	 * Returns the value of the StrictManifestValidationEnabled child.
	 *
	 * @return the value of the StrictManifestValidationEnabled child.
	 */
	@Nonnull
	GenericAttributeValue<Boolean> getStrictManifestValidationEnabled();


	/**
	 * Returns the value of the ValidateWinmds child.
	 *
	 * @return the value of the ValidateWinmds child.
	 */
	@Nonnull
	GenericAttributeValue<Boolean> getValidateWinmds();


	/**
	 * Returns the value of the NonFrameworkSdkReferences child.
	 *
	 * @return the value of the NonFrameworkSdkReferences child.
	 */
	@Nonnull
	GenericAttributeValue<String> getNonFrameworkSdkReferences();


	/**
	 * Returns the value of the WinmdFiles child.
	 *
	 * @return the value of the WinmdFiles child.
	 */
	@Nonnull
	GenericAttributeValue<String> getWinmdFiles();


	/**
	 * Returns the value of the SDKWinmdFiles child.
	 *
	 * @return the value of the SDKWinmdFiles child.
	 */
	@Nonnull
	GenericAttributeValue<String> getSDKWinmdFiles();


	/**
	 * Returns the value of the ManagedWinmdInprocImplementation child.
	 *
	 * @return the value of the ManagedWinmdInprocImplementation child.
	 */
	@Nonnull
	GenericAttributeValue<String> getManagedWinmdInprocImplementation();


	/**
	 * Returns the value of the ValidateManifest child.
	 *
	 * @return the value of the ValidateManifest child.
	 */
	@Nonnull
	GenericAttributeValue<Boolean> getValidateManifest();


	/**
	 * Returns the value of the Resources child.
	 *
	 * @return the value of the Resources child.
	 */
	@Nonnull
	GenericAttributeValue<String> getResources();


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
