// Generated on Sat Jan 28 04:58:18 MSK 2017
// DTD/Schema  :    http://schemas.microsoft.com/developer/msbuild/2003

package consulo.msbuild.dom;

import java.util.List;

import javax.annotation.Nonnull;

import consulo.xml.util.xml.DomElement;
import consulo.xml.util.xml.GenericAttributeValue;
import consulo.xml.util.xml.Required;

/**
 * http://schemas.microsoft.com/developer/msbuild/2003:AssignProjectConfigurationElemType interface.
 *
 * @author VISTALL
 */
public interface AssignProjectConfiguration extends DomElement, Task
{

	/**
	 * Returns the value of the AssignedProjects child.
	 *
	 * @return the value of the AssignedProjects child.
	 */
	@Nonnull
	GenericAttributeValue<String> getAssignedProjects();


	/**
	 * Returns the value of the CurrentProjectConfiguration child.
	 *
	 * @return the value of the CurrentProjectConfiguration child.
	 */
	@Nonnull
	GenericAttributeValue<String> getCurrentProjectConfiguration();


	/**
	 * Returns the value of the CurrentProjectPlatform child.
	 *
	 * @return the value of the CurrentProjectPlatform child.
	 */
	@Nonnull
	GenericAttributeValue<String> getCurrentProjectPlatform();


	/**
	 * Returns the value of the DefaultToVcxPlatformMapping child.
	 *
	 * @return the value of the DefaultToVcxPlatformMapping child.
	 */
	@Nonnull
	GenericAttributeValue<String> getDefaultToVcxPlatformMapping();


	/**
	 * Returns the value of the ProjectReferences child.
	 *
	 * @return the value of the ProjectReferences child.
	 */
	@Nonnull
	@Required
	GenericAttributeValue<String> getProjectReferences();


	/**
	 * Returns the value of the ResolveConfigurationPlatformUsingMappings child.
	 *
	 * @return the value of the ResolveConfigurationPlatformUsingMappings child.
	 */
	@Nonnull
	GenericAttributeValue<Boolean> getResolveConfigurationPlatformUsingMappings();


	/**
	 * Returns the value of the SolutionConfigurationContents child.
	 *
	 * @return the value of the SolutionConfigurationContents child.
	 */
	@Nonnull
	GenericAttributeValue<String> getSolutionConfigurationContents();


	/**
	 * Returns the value of the UnassignedProjects child.
	 *
	 * @return the value of the UnassignedProjects child.
	 */
	@Nonnull
	GenericAttributeValue<String> getUnassignedProjects();


	/**
	 * Returns the value of the VcxToDefaultPlatformMapping child.
	 *
	 * @return the value of the VcxToDefaultPlatformMapping child.
	 */
	@Nonnull
	GenericAttributeValue<String> getVcxToDefaultPlatformMapping();


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
