// Generated on Sat Jan 28 04:58:19 MSK 2017
// DTD/Schema  :    http://schemas.microsoft.com/developer/msbuild/2003

package consulo.msbuild.dom;

import java.util.List;

import javax.annotation.Nonnull;

import consulo.xml.util.xml.DomElement;
import consulo.xml.util.xml.GenericAttributeValue;
import consulo.xml.util.xml.Required;

/**
 * http://schemas.microsoft.com/developer/msbuild/2003:GenerateProjectPriFileElemType interface.
 *
 * @author VISTALL
 */
public interface GenerateProjectPriFile extends DomElement, ToolTask
{

	/**
	 * Returns the value of the MakePriExeFullPath child.
	 *
	 * @return the value of the MakePriExeFullPath child.
	 */
	@Nonnull
	@Required
	GenericAttributeValue<String> getMakePriExeFullPath();


	/**
	 * Returns the value of the PriConfigXmlPath child.
	 *
	 * @return the value of the PriConfigXmlPath child.
	 */
	@Nonnull
	@Required
	GenericAttributeValue<String> getPriConfigXmlPath();


	/**
	 * Returns the value of the IndexFilesForQualifiersCollection child.
	 *
	 * @return the value of the IndexFilesForQualifiersCollection child.
	 */
	@Nonnull
	GenericAttributeValue<String> getIndexFilesForQualifiersCollection();


	/**
	 * Returns the value of the ProjectPriIndexName child.
	 *
	 * @return the value of the ProjectPriIndexName child.
	 */
	@Nonnull
	@Required
	GenericAttributeValue<String> getProjectPriIndexName();


	/**
	 * Returns the value of the MappingFileFormat child.
	 *
	 * @return the value of the MappingFileFormat child.
	 */
	@Nonnull
	GenericAttributeValue<String> getMappingFileFormat();


	/**
	 * Returns the value of the InsertReverseMap child.
	 *
	 * @return the value of the InsertReverseMap child.
	 */
	@Nonnull
	GenericAttributeValue<String> getInsertReverseMap();


	/**
	 * Returns the value of the ProjectDirectory child.
	 *
	 * @return the value of the ProjectDirectory child.
	 */
	@Nonnull
	@Required
	GenericAttributeValue<String> getProjectDirectory();


	/**
	 * Returns the value of the OutputFileName child.
	 *
	 * @return the value of the OutputFileName child.
	 */
	@Nonnull
	@Required
	GenericAttributeValue<String> getOutputFileName();


	/**
	 * Returns the value of the MakePriExtensionPath child.
	 *
	 * @return the value of the MakePriExtensionPath child.
	 */
	@Nonnull
	GenericAttributeValue<String> getMakePriExtensionPath();


	/**
	 * Returns the value of the QualifiersPath child.
	 *
	 * @return the value of the QualifiersPath child.
	 */
	@Nonnull
	GenericAttributeValue<String> getQualifiersPath();


	/**
	 * Returns the value of the GeneratedFilesListPath child.
	 *
	 * @return the value of the GeneratedFilesListPath child.
	 */
	@Nonnull
	GenericAttributeValue<String> getGeneratedFilesListPath();


	/**
	 * Returns the value of the AdditionalMakepriExeParameters child.
	 *
	 * @return the value of the AdditionalMakepriExeParameters child.
	 */
	@Nonnull
	GenericAttributeValue<String> getAdditionalMakepriExeParameters();


	/**
	 * Returns the value of the MultipleQualifiersPerDimensionFoundPath child.
	 *
	 * @return the value of the MultipleQualifiersPerDimensionFoundPath child.
	 */
	@Nonnull
	GenericAttributeValue<String> getMultipleQualifiersPerDimensionFoundPath();


	/**
	 * Returns the value of the IntermediateExtension child.
	 *
	 * @return the value of the IntermediateExtension child.
	 */
	@Nonnull
	@Required
	GenericAttributeValue<String> getIntermediateExtension();


	/**
	 * Returns the value of the ExitCode child.
	 *
	 * @return the value of the ExitCode child.
	 */
	@Nonnull
	GenericAttributeValue<String> getExitCode();


	/**
	 * Returns the value of the YieldDuringToolExecution child.
	 *
	 * @return the value of the YieldDuringToolExecution child.
	 */
	@Nonnull
	GenericAttributeValue<Boolean> getYieldDuringToolExecution();


	/**
	 * Returns the value of the UseCommandProcessor child.
	 *
	 * @return the value of the UseCommandProcessor child.
	 */
	@Nonnull
	GenericAttributeValue<Boolean> getUseCommandProcessor();


	/**
	 * Returns the value of the EchoOff child.
	 *
	 * @return the value of the EchoOff child.
	 */
	@Nonnull
	GenericAttributeValue<Boolean> getEchoOff();


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
	 * Returns the value of the EnvironmentVariables child.
	 *
	 * @return the value of the EnvironmentVariables child.
	 */
	@Nonnull
	GenericAttributeValue<String> getEnvironmentVariables();


	/**
	 * Returns the value of the Timeout child.
	 *
	 * @return the value of the Timeout child.
	 */
	@Nonnull
	GenericAttributeValue<String> getTimeout();


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
	 * Returns the value of the LogStandardErrorAsError child.
	 *
	 * @return the value of the LogStandardErrorAsError child.
	 */
	@Nonnull
	GenericAttributeValue<Boolean> getLogStandardErrorAsError();


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
