// Generated on Sat Jan 28 04:58:19 MSK 2017
// DTD/Schema  :    http://schemas.microsoft.com/developer/msbuild/2003

package consulo.msbuild.dom;

import java.util.List;

import javax.annotation.Nonnull;

import com.intellij.util.xml.DomElement;
import com.intellij.util.xml.GenericAttributeValue;

/**
 * http://schemas.microsoft.com/developer/msbuild/2003:ResolveManifestFilesElemType interface.
 *
 * @author VISTALL
 */
public interface ResolveManifestFiles extends DomElement, Task
{

	/**
	 * Returns the value of the DeploymentManifestEntryPoint child.
	 *
	 * @return the value of the DeploymentManifestEntryPoint child.
	 */
	@Nonnull
	GenericAttributeValue<String> getDeploymentManifestEntryPoint();


	/**
	 * Returns the value of the EntryPoint child.
	 *
	 * @return the value of the EntryPoint child.
	 */
	@Nonnull
	GenericAttributeValue<String> getEntryPoint();


	/**
	 * Returns the value of the ExtraFiles child.
	 *
	 * @return the value of the ExtraFiles child.
	 */
	@Nonnull
	GenericAttributeValue<String> getExtraFiles();


	/**
	 * Returns the value of the Files child.
	 *
	 * @return the value of the Files child.
	 */
	@Nonnull
	GenericAttributeValue<String> getFiles();


	/**
	 * Returns the value of the ManagedAssemblies child.
	 *
	 * @return the value of the ManagedAssemblies child.
	 */
	@Nonnull
	GenericAttributeValue<String> getManagedAssemblies();


	/**
	 * Returns the value of the NativeAssemblies child.
	 *
	 * @return the value of the NativeAssemblies child.
	 */
	@Nonnull
	GenericAttributeValue<String> getNativeAssemblies();


	/**
	 * Returns the value of the OutputAssemblies child.
	 *
	 * @return the value of the OutputAssemblies child.
	 */
	@Nonnull
	GenericAttributeValue<String> getOutputAssemblies();


	/**
	 * Returns the value of the OutputDeploymentManifestEntryPoint child.
	 *
	 * @return the value of the OutputDeploymentManifestEntryPoint child.
	 */
	@Nonnull
	GenericAttributeValue<String> getOutputDeploymentManifestEntryPoint();


	/**
	 * Returns the value of the OutputEntryPoint child.
	 *
	 * @return the value of the OutputEntryPoint child.
	 */
	@Nonnull
	GenericAttributeValue<String> getOutputEntryPoint();


	/**
	 * Returns the value of the OutputFiles child.
	 *
	 * @return the value of the OutputFiles child.
	 */
	@Nonnull
	GenericAttributeValue<String> getOutputFiles();


	/**
	 * Returns the value of the PublishFiles child.
	 *
	 * @return the value of the PublishFiles child.
	 */
	@Nonnull
	GenericAttributeValue<String> getPublishFiles();


	/**
	 * Returns the value of the SatelliteAssemblies child.
	 *
	 * @return the value of the SatelliteAssemblies child.
	 */
	@Nonnull
	GenericAttributeValue<String> getSatelliteAssemblies();


	/**
	 * Returns the value of the SigningManifests child.
	 *
	 * @return the value of the SigningManifests child.
	 */
	@Nonnull
	GenericAttributeValue<Boolean> getSigningManifests();


	/**
	 * Returns the value of the TargetCulture child.
	 *
	 * @return the value of the TargetCulture child.
	 */
	@Nonnull
	GenericAttributeValue<String> getTargetCulture();


	/**
	 * Returns the value of the TargetFrameworkVersion child.
	 *
	 * @return the value of the TargetFrameworkVersion child.
	 */
	@Nonnull
	GenericAttributeValue<String> getTargetFrameworkVersion();


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
