// Generated on Sat Jan 28 04:58:19 MSK 2017
// DTD/Schema  :    http://schemas.microsoft.com/developer/msbuild/2003

package consulo.msbuild.dom;

import java.util.List;

import jakarta.annotation.Nonnull;

import consulo.xml.util.xml.DomElement;
import consulo.xml.util.xml.GenericAttributeValue;

/**
 * http://schemas.microsoft.com/developer/msbuild/2003:GenerateDeploymentManifestElemType interface.
 *
 * @author VISTALL
 */
public interface GenerateDeploymentManifest extends DomElement, Task
{

	/**
	 * Returns the value of the AssemblyName child.
	 *
	 * @return the value of the AssemblyName child.
	 */
	@Nonnull
	GenericAttributeValue<String> getAssemblyName();


	/**
	 * Returns the value of the AssemblyVersion child.
	 *
	 * @return the value of the AssemblyVersion child.
	 */
	@Nonnull
	GenericAttributeValue<String> getAssemblyVersion();


	/**
	 * Returns the value of the CreateDesktopShortcut child.
	 *
	 * @return the value of the CreateDesktopShortcut child.
	 */
	@Nonnull
	GenericAttributeValue<Boolean> getCreateDesktopShortcut();


	/**
	 * Returns the value of the DeploymentUrl child.
	 *
	 * @return the value of the DeploymentUrl child.
	 */
	@Nonnull
	GenericAttributeValue<String> getDeploymentUrl();


	/**
	 * Returns the value of the Description child.
	 *
	 * @return the value of the Description child.
	 */
	@Nonnull
	GenericAttributeValue<String> getDescription();


	/**
	 * Returns the value of the DisallowUrlActivation child.
	 *
	 * @return the value of the DisallowUrlActivation child.
	 */
	@Nonnull
	GenericAttributeValue<Boolean> getDisallowUrlActivation();


	/**
	 * Returns the value of the EntryPoint child.
	 *
	 * @return the value of the EntryPoint child.
	 */
	@Nonnull
	GenericAttributeValue<String> getEntryPoint();


	/**
	 * Returns the value of the ErrorReportUrl child.
	 *
	 * @return the value of the ErrorReportUrl child.
	 */
	@Nonnull
	GenericAttributeValue<String> getErrorReportUrl();


	/**
	 * Returns the value of the InputManifest child.
	 *
	 * @return the value of the InputManifest child.
	 */
	@Nonnull
	GenericAttributeValue<String> getInputManifest();


	/**
	 * Returns the value of the Install child.
	 *
	 * @return the value of the Install child.
	 */
	@Nonnull
	GenericAttributeValue<Boolean> getInstall();


	/**
	 * Returns the value of the MapFileExtensions child.
	 *
	 * @return the value of the MapFileExtensions child.
	 */
	@Nonnull
	GenericAttributeValue<Boolean> getMapFileExtensions();


	/**
	 * Returns the value of the MaxTargetPath child.
	 *
	 * @return the value of the MaxTargetPath child.
	 */
	@Nonnull
	GenericAttributeValue<String> getMaxTargetPath();


	/**
	 * Returns the value of the MinimumRequiredVersion child.
	 *
	 * @return the value of the MinimumRequiredVersion child.
	 */
	@Nonnull
	GenericAttributeValue<String> getMinimumRequiredVersion();


	/**
	 * Returns the value of the OutputManifest child.
	 *
	 * @return the value of the OutputManifest child.
	 */
	@Nonnull
	GenericAttributeValue<String> getOutputManifest();


	/**
	 * Returns the value of the Platform child.
	 *
	 * @return the value of the Platform child.
	 */
	@Nonnull
	GenericAttributeValue<String> getPlatform();


	/**
	 * Returns the value of the Product child.
	 *
	 * @return the value of the Product child.
	 */
	@Nonnull
	GenericAttributeValue<String> getProduct();


	/**
	 * Returns the value of the Publisher child.
	 *
	 * @return the value of the Publisher child.
	 */
	@Nonnull
	GenericAttributeValue<String> getPublisher();


	/**
	 * Returns the value of the SuiteName child.
	 *
	 * @return the value of the SuiteName child.
	 */
	@Nonnull
	GenericAttributeValue<String> getSuiteName();


	/**
	 * Returns the value of the SupportUrl child.
	 *
	 * @return the value of the SupportUrl child.
	 */
	@Nonnull
	GenericAttributeValue<String> getSupportUrl();


	/**
	 * Returns the value of the TargetCulture child.
	 *
	 * @return the value of the TargetCulture child.
	 */
	@Nonnull
	GenericAttributeValue<String> getTargetCulture();


	/**
	 * Returns the value of the TargetFrameworkMoniker child.
	 *
	 * @return the value of the TargetFrameworkMoniker child.
	 */
	@Nonnull
	GenericAttributeValue<String> getTargetFrameworkMoniker();


	/**
	 * Returns the value of the TargetFrameworkVersion child.
	 *
	 * @return the value of the TargetFrameworkVersion child.
	 */
	@Nonnull
	GenericAttributeValue<String> getTargetFrameworkVersion();


	/**
	 * Returns the value of the TrustUrlParameters child.
	 *
	 * @return the value of the TrustUrlParameters child.
	 */
	@Nonnull
	GenericAttributeValue<Boolean> getTrustUrlParameters();


	/**
	 * Returns the value of the UpdateEnabled child.
	 *
	 * @return the value of the UpdateEnabled child.
	 */
	@Nonnull
	GenericAttributeValue<Boolean> getUpdateEnabled();


	/**
	 * Returns the value of the UpdateInterval child.
	 *
	 * @return the value of the UpdateInterval child.
	 */
	@Nonnull
	GenericAttributeValue<String> getUpdateInterval();


	/**
	 * Returns the value of the UpdateMode child.
	 *
	 * @return the value of the UpdateMode child.
	 */
	@Nonnull
	GenericAttributeValue<String> getUpdateMode();


	/**
	 * Returns the value of the UpdateUnit child.
	 *
	 * @return the value of the UpdateUnit child.
	 */
	@Nonnull
	GenericAttributeValue<String> getUpdateUnit();


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
