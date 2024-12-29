// Generated on Sat Jan 28 04:58:19 MSK 2017
// DTD/Schema  :    http://schemas.microsoft.com/developer/msbuild/2003

package consulo.msbuild.dom;

import java.util.List;

import jakarta.annotation.Nonnull;

import consulo.xml.util.xml.DomElement;
import consulo.xml.util.xml.GenericAttributeValue;
import consulo.xml.util.xml.Required;

/**
 * http://schemas.microsoft.com/developer/msbuild/2003:SGenElemType interface.
 *
 * @author VISTALL
 */
public interface SGen extends DomElement, Task
{

	/**
	 * Returns the value of the BuildAssemblyName child.
	 *
	 * @return the value of the BuildAssemblyName child.
	 */
	@Nonnull
	@Required
	GenericAttributeValue<String> getBuildAssemblyName();


	/**
	 * Returns the value of the BuildAssemblyPath child.
	 *
	 * @return the value of the BuildAssemblyPath child.
	 */
	@Nonnull
	@Required
	GenericAttributeValue<String> getBuildAssemblyPath();


	/**
	 * Returns the value of the DelaySign child.
	 *
	 * @return the value of the DelaySign child.
	 */
	@Nonnull
	GenericAttributeValue<Boolean> getDelaySign();


	/**
	 * Returns the value of the EnvironmentVariables child.
	 *
	 * @return the value of the EnvironmentVariables child.
	 */
	@Nonnull
	GenericAttributeValue<String> getEnvironmentVariables();


	/**
	 * Returns the value of the KeyContainer child.
	 *
	 * @return the value of the KeyContainer child.
	 */
	@Nonnull
	GenericAttributeValue<String> getKeyContainer();


	/**
	 * Returns the value of the KeyFile child.
	 *
	 * @return the value of the KeyFile child.
	 */
	@Nonnull
	GenericAttributeValue<String> getKeyFile();


	/**
	 * Returns the value of the LogStandardErrorAsError child.
	 *
	 * @return the value of the LogStandardErrorAsError child.
	 */
	@Nonnull
	GenericAttributeValue<Boolean> getLogStandardErrorAsError();


	/**
	 * Returns the value of the References child.
	 *
	 * @return the value of the References child.
	 */
	@Nonnull
	GenericAttributeValue<String> getReferences();


	/**
	 * Returns the value of the SdkToolsPath child.
	 *
	 * @return the value of the SdkToolsPath child.
	 */
	@Nonnull
	GenericAttributeValue<String> getSdkToolsPath();


	/**
	 * Returns the value of the SerializationAssembly child.
	 *
	 * @return the value of the SerializationAssembly child.
	 */
	@Nonnull
	GenericAttributeValue<String> getSerializationAssembly();


	/**
	 * Returns the value of the ShouldGenerateSerializer child.
	 *
	 * @return the value of the ShouldGenerateSerializer child.
	 */
	@Nonnull
	@Required
	GenericAttributeValue<Boolean> getShouldGenerateSerializer();


	/**
	 * Returns the value of the StandardErrorImportance child.
	 *
	 * @return the value of the StandardErrorImportance child.
	 */
	@Nonnull
	GenericAttributeValue<String> getStandardErrorImportance();


	/**
	 * Returns the value of the StandardOutputImportance child.
	 *
	 * @return the value of the StandardOutputImportance child.
	 */
	@Nonnull
	GenericAttributeValue<String> getStandardOutputImportance();


	/**
	 * Returns the value of the Timeout child.
	 *
	 * @return the value of the Timeout child.
	 */
	@Nonnull
	GenericAttributeValue<String> getTimeout();


	/**
	 * Returns the value of the ToolExe child.
	 *
	 * @return the value of the ToolExe child.
	 */
	@Nonnull
	GenericAttributeValue<String> getToolExe();


	/**
	 * Returns the value of the ToolPath child.
	 *
	 * @return the value of the ToolPath child.
	 */
	@Nonnull
	GenericAttributeValue<String> getToolPath();


	/**
	 * Returns the value of the UseProxyTypes child.
	 *
	 * @return the value of the UseProxyTypes child.
	 */
	@Nonnull
	@Required
	GenericAttributeValue<Boolean> getUseProxyTypes();


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
