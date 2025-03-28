// Generated on Sat Jan 28 04:58:20 MSK 2017
// DTD/Schema  :    http://schemas.microsoft.com/developer/msbuild/2003

package consulo.msbuild.dom;

import java.util.List;

import jakarta.annotation.Nonnull;

import consulo.xml.util.xml.DomElement;
import consulo.xml.util.xml.GenericAttributeValue;
import consulo.xml.util.xml.Required;

/**
 * http://schemas.microsoft.com/developer/msbuild/2003:VCBuildElemType interface.
 *
 * @author VISTALL
 */
public interface VCBuild extends DomElement, Task
{

	/**
	 * Returns the value of the AdditionalLibPaths child.
	 *
	 * @return the value of the AdditionalLibPaths child.
	 */
	@Nonnull
	GenericAttributeValue<String> getAdditionalLibPaths();


	/**
	 * Returns the value of the AdditionalLinkLibraryPaths child.
	 *
	 * @return the value of the AdditionalLinkLibraryPaths child.
	 */
	@Nonnull
	GenericAttributeValue<String> getAdditionalLinkLibraryPaths();


	/**
	 * Returns the value of the AdditionalOptions child.
	 *
	 * @return the value of the AdditionalOptions child.
	 */
	@Nonnull
	GenericAttributeValue<String> getAdditionalOptions();


	/**
	 * Returns the value of the Clean child.
	 *
	 * @return the value of the Clean child.
	 */
	@Nonnull
	GenericAttributeValue<Boolean> getClean();


	/**
	 * Returns the value of the Configuration child.
	 *
	 * @return the value of the Configuration child.
	 */
	@Nonnull
	GenericAttributeValue<String> getConfiguration();


	/**
	 * Returns the value of the Override child.
	 *
	 * @return the value of the Override child.
	 */
	@Nonnull
	GenericAttributeValue<String> getOverride();


	/**
	 * Returns the value of the Platform child.
	 *
	 * @return the value of the Platform child.
	 */
	@Nonnull
	GenericAttributeValue<String> getPlatform();


	/**
	 * Returns the value of the Projects child.
	 *
	 * @return the value of the Projects child.
	 */
	@Nonnull
	@Required
	GenericAttributeValue<String> getProjects();


	/**
	 * Returns the value of the Rebuild child.
	 *
	 * @return the value of the Rebuild child.
	 */
	@Nonnull
	GenericAttributeValue<Boolean> getRebuild();


	/**
	 * Returns the value of the SolutionFile child.
	 *
	 * @return the value of the SolutionFile child.
	 */
	@Nonnull
	GenericAttributeValue<String> getSolutionFile();


	/**
	 * Returns the value of the Timeout child.
	 *
	 * @return the value of the Timeout child.
	 */
	@Nonnull
	GenericAttributeValue<String> getTimeout();


	/**
	 * Returns the value of the ToolPath child.
	 *
	 * @return the value of the ToolPath child.
	 */
	@Nonnull
	GenericAttributeValue<String> getToolPath();


	/**
	 * Returns the value of the UseEnvironment child.
	 *
	 * @return the value of the UseEnvironment child.
	 */
	@Nonnull
	GenericAttributeValue<Boolean> getUseEnvironment();


	/**
	 * Returns the value of the UserEnvironment child.
	 *
	 * @return the value of the UserEnvironment child.
	 */
	@Nonnull
	GenericAttributeValue<Boolean> getUserEnvironment();


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
