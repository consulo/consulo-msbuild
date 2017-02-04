// Generated on Sat Jan 28 04:58:19 MSK 2017
// DTD/Schema  :    http://schemas.microsoft.com/developer/msbuild/2003

package consulo.msbuild.dom;

import java.util.List;

import org.jetbrains.annotations.NotNull;
import com.intellij.util.xml.DomElement;
import com.intellij.util.xml.GenericAttributeValue;
import com.intellij.util.xml.Required;

/**
 * http://schemas.microsoft.com/developer/msbuild/2003:SignAppxPackageElemType interface.
 *
 * @author VISTALL
 */
public interface SignAppxPackage extends DomElement, Task
{

	/**
	 * Returns the value of the AppxPackageToSign child.
	 *
	 * @return the value of the AppxPackageToSign child.
	 */
	@NotNull
	@Required
	GenericAttributeValue<String> getAppxPackageToSign();


	/**
	 * Returns the value of the CertificateThumbprint child.
	 *
	 * @return the value of the CertificateThumbprint child.
	 */
	@NotNull
	GenericAttributeValue<String> getCertificateThumbprint();


	/**
	 * Returns the value of the CertificateFile child.
	 *
	 * @return the value of the CertificateFile child.
	 */
	@NotNull
	GenericAttributeValue<String> getCertificateFile();


	/**
	 * Returns the value of the HashAlgorithmId child.
	 *
	 * @return the value of the HashAlgorithmId child.
	 */
	@NotNull
	@Required
	GenericAttributeValue<String> getHashAlgorithmId();


	/**
	 * Returns the value of the TargetPlatformIdentifier child.
	 *
	 * @return the value of the TargetPlatformIdentifier child.
	 */
	@NotNull
	@Required
	GenericAttributeValue<String> getTargetPlatformIdentifier();


	/**
	 * Returns the value of the TargetPlatformVersion child.
	 *
	 * @return the value of the TargetPlatformVersion child.
	 */
	@NotNull
	@Required
	GenericAttributeValue<String> getTargetPlatformVersion();


	/**
	 * Returns the value of the TargetPlatformSdkRootOverride child.
	 *
	 * @return the value of the TargetPlatformSdkRootOverride child.
	 */
	@NotNull
	GenericAttributeValue<String> getTargetPlatformSdkRootOverride();


	/**
	 * Returns the value of the SignAppxPackageExeFullPath child.
	 *
	 * @return the value of the SignAppxPackageExeFullPath child.
	 */
	@NotNull
	GenericAttributeValue<String> getSignAppxPackageExeFullPath();


	/**
	 * Returns the value of the MSBuildArchitecture child.
	 *
	 * @return the value of the MSBuildArchitecture child.
	 */
	@NotNull
	GenericAttributeValue<String> getMSBuildArchitecture();


	/**
	 * Returns the value of the EnableSigningChecks child.
	 *
	 * @return the value of the EnableSigningChecks child.
	 */
	@NotNull
	GenericAttributeValue<Boolean> getEnableSigningChecks();


	/**
	 * Returns the value of the ExportCertificate child.
	 *
	 * @return the value of the ExportCertificate child.
	 */
	@NotNull
	GenericAttributeValue<Boolean> getExportCertificate();


	/**
	 * Returns the value of the ResolvedThumbprint child.
	 *
	 * @return the value of the ResolvedThumbprint child.
	 */
	@NotNull
	GenericAttributeValue<String> getResolvedThumbprint();


	/**
	 * Returns the value of the AppxPackagePublicKeyFile child.
	 *
	 * @return the value of the AppxPackagePublicKeyFile child.
	 */
	@NotNull
	GenericAttributeValue<String> getAppxPackagePublicKeyFile();


	/**
	 * Returns the value of the Condition child.
	 * <pre>
	 * <h3>Attribute null:Condition documentation</h3>
	 * <!-- _locID_text="TaskType_Condition" _locComment="" -->Optional expression evaluated to determine whether the task should be executed
	 * </pre>
	 *
	 * @return the value of the Condition child.
	 */
	@NotNull
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
	@NotNull
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
	@NotNull
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
	@NotNull
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
	@NotNull
	List<Output> getOutputs();

	/**
	 * Adds new child to the list of Output children.
	 *
	 * @return created child
	 */
	Output addOutput();


}