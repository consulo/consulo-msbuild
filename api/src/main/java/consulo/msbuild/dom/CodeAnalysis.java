// Generated on Sat Jan 28 04:58:19 MSK 2017
// DTD/Schema  :    http://schemas.microsoft.com/developer/msbuild/2003

package consulo.msbuild.dom;

import java.util.List;

import javax.annotation.Nonnull;

import consulo.xml.util.xml.DomElement;
import consulo.xml.util.xml.GenericAttributeValue;

/**
 * http://schemas.microsoft.com/developer/msbuild/2003:CodeAnalysisElemType interface.
 *
 * @author VISTALL
 */
public interface CodeAnalysis extends DomElement, Task
{

	/**
	 * Returns the value of the AlternativeToolName child.
	 *
	 * @return the value of the AlternativeToolName child.
	 */
	@Nonnull
	GenericAttributeValue<String> getAlternativeToolName();


	/**
	 * Returns the value of the AnalysisTimeout child.
	 *
	 * @return the value of the AnalysisTimeout child.
	 */
	@Nonnull
	GenericAttributeValue<String> getAnalysisTimeout();


	/**
	 * Returns the value of the ApplyLogFileXsl child.
	 *
	 * @return the value of the ApplyLogFileXsl child.
	 */
	@Nonnull
	GenericAttributeValue<Boolean> getApplyLogFileXsl();


	/**
	 * Returns the value of the Assemblies child.
	 *
	 * @return the value of the Assemblies child.
	 */
	@Nonnull
	GenericAttributeValue<String> getAssemblies();


	/**
	 * Returns the value of the ConsoleXsl child.
	 *
	 * @return the value of the ConsoleXsl child.
	 */
	@Nonnull
	GenericAttributeValue<String> getConsoleXsl();


	/**
	 * Returns the value of the Culture child.
	 *
	 * @return the value of the Culture child.
	 */
	@Nonnull
	GenericAttributeValue<String> getCulture();


	/**
	 * Returns the value of the DependentAssemblyPaths child.
	 *
	 * @return the value of the DependentAssemblyPaths child.
	 */
	@Nonnull
	GenericAttributeValue<String> getDependentAssemblyPaths();


	/**
	 * Returns the value of the Dictionaries child.
	 *
	 * @return the value of the Dictionaries child.
	 */
	@Nonnull
	GenericAttributeValue<String> getDictionaries();


	/**
	 * Returns the value of the FilesWritten child.
	 *
	 * @return the value of the FilesWritten child.
	 */
	@Nonnull
	GenericAttributeValue<String> getFilesWritten();


	/**
	 * Returns the value of the ForceOutput child.
	 *
	 * @return the value of the ForceOutput child.
	 */
	@Nonnull
	GenericAttributeValue<Boolean> getForceOutput();


	/**
	 * Returns the value of the GenerateSuccessFile child.
	 *
	 * @return the value of the GenerateSuccessFile child.
	 */
	@Nonnull
	GenericAttributeValue<Boolean> getGenerateSuccessFile();


	/**
	 * Returns the value of the IgnoreInvalidTargets child.
	 *
	 * @return the value of the IgnoreInvalidTargets child.
	 */
	@Nonnull
	GenericAttributeValue<Boolean> getIgnoreInvalidTargets();


	/**
	 * Returns the value of the IgnoreGeneratedCode child.
	 *
	 * @return the value of the IgnoreGeneratedCode child.
	 */
	@Nonnull
	GenericAttributeValue<Boolean> getIgnoreGeneratedCode();


	/**
	 * Returns the value of the Imports child.
	 *
	 * @return the value of the Imports child.
	 */
	@Nonnull
	GenericAttributeValue<String> getImports();


	/**
	 * Returns the value of the LogFile child.
	 *
	 * @return the value of the LogFile child.
	 */
	@Nonnull
	GenericAttributeValue<String> getLogFile();


	/**
	 * Returns the value of the LogFileXsl child.
	 *
	 * @return the value of the LogFileXsl child.
	 */
	@Nonnull
	GenericAttributeValue<String> getLogFileXsl();


	/**
	 * Returns the value of the OutputToConsole child.
	 *
	 * @return the value of the OutputToConsole child.
	 */
	@Nonnull
	GenericAttributeValue<Boolean> getOutputToConsole();


	/**
	 * Returns the value of the OverrideRuleVisibilities child.
	 *
	 * @return the value of the OverrideRuleVisibilities child.
	 */
	@Nonnull
	GenericAttributeValue<Boolean> getOverrideRuleVisibilities();


	/**
	 * Returns the value of the PlatformPath child.
	 *
	 * @return the value of the PlatformPath child.
	 */
	@Nonnull
	GenericAttributeValue<String> getPlatformPath();


	/**
	 * Returns the value of the Project child.
	 *
	 * @return the value of the Project child.
	 */
	@Nonnull
	GenericAttributeValue<String> getProject();


	/**
	 * Returns the value of the Quiet child.
	 *
	 * @return the value of the Quiet child.
	 */
	@Nonnull
	GenericAttributeValue<Boolean> getQuiet();


	/**
	 * Returns the value of the References child.
	 *
	 * @return the value of the References child.
	 */
	@Nonnull
	GenericAttributeValue<String> getReferences();


	/**
	 * Returns the value of the RuleAssemblies child.
	 *
	 * @return the value of the RuleAssemblies child.
	 */
	@Nonnull
	GenericAttributeValue<String> getRuleAssemblies();


	/**
	 * Returns the value of the Rules child.
	 *
	 * @return the value of the Rules child.
	 */
	@Nonnull
	GenericAttributeValue<String> getRules();


	/**
	 * Returns the value of the SaveMessagesToReport child.
	 *
	 * @return the value of the SaveMessagesToReport child.
	 */
	@Nonnull
	GenericAttributeValue<String> getSaveMessagesToReport();


	/**
	 * Returns the value of the SearchGlobalAssemblyCache child.
	 *
	 * @return the value of the SearchGlobalAssemblyCache child.
	 */
	@Nonnull
	GenericAttributeValue<Boolean> getSearchGlobalAssemblyCache();


	/**
	 * Returns the value of the Summary child.
	 *
	 * @return the value of the Summary child.
	 */
	@Nonnull
	GenericAttributeValue<Boolean> getSummary();


	/**
	 * Returns the value of the SuccessFile child.
	 *
	 * @return the value of the SuccessFile child.
	 */
	@Nonnull
	GenericAttributeValue<Boolean> getSuccessFile();


	/**
	 * Returns the value of the Timeout child.
	 *
	 * @return the value of the Timeout child.
	 */
	@Nonnull
	GenericAttributeValue<String> getTimeout();


	/**
	 * Returns the value of the TreatWarningsAsErrors child.
	 *
	 * @return the value of the TreatWarningsAsErrors child.
	 */
	@Nonnull
	GenericAttributeValue<Boolean> getTreatWarningsAsErrors();


	/**
	 * Returns the value of the ToolPath child.
	 *
	 * @return the value of the ToolPath child.
	 */
	@Nonnull
	GenericAttributeValue<String> getToolPath();


	/**
	 * Returns the value of the UpdateProject child.
	 *
	 * @return the value of the UpdateProject child.
	 */
	@Nonnull
	GenericAttributeValue<Boolean> getUpdateProject();


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
