// Generated on Sat Jan 28 04:58:19 MSK 2017
// DTD/Schema  :    http://schemas.microsoft.com/developer/msbuild/2003

package consulo.msbuild.dom;

import java.util.List;

import javax.annotation.Nonnull;

import consulo.xml.util.xml.DomElement;
import consulo.xml.util.xml.GenericAttributeValue;
import consulo.xml.util.xml.Required;

/**
 * http://schemas.microsoft.com/developer/msbuild/2003:LIBElemType interface.
 *
 * @author VISTALL
 */
public interface LIB extends DomElement, Task
{

	/**
	 * Returns the value of the AcceptableNonZeroExitCodes child.
	 *
	 * @return the value of the AcceptableNonZeroExitCodes child.
	 */
	@Nonnull
	GenericAttributeValue<String> getAcceptableNonZeroExitCodes();


	/**
	 * Returns the value of the ActiveToolSwitchesValues child.
	 *
	 * @return the value of the ActiveToolSwitchesValues child.
	 */
	@Nonnull
	GenericAttributeValue<String> getActiveToolSwitchesValues();


	/**
	 * Returns the value of the AdditionalDependencies child.
	 *
	 * @return the value of the AdditionalDependencies child.
	 */
	@Nonnull
	GenericAttributeValue<String> getAdditionalDependencies();


	/**
	 * Returns the value of the AdditionalLibraryDirectories child.
	 *
	 * @return the value of the AdditionalLibraryDirectories child.
	 */
	@Nonnull
	GenericAttributeValue<String> getAdditionalLibraryDirectories();


	/**
	 * Returns the value of the AdditionalOptions child.
	 *
	 * @return the value of the AdditionalOptions child.
	 */
	@Nonnull
	GenericAttributeValue<String> getAdditionalOptions();


	/**
	 * Returns the value of the DisplayLibrary child.
	 *
	 * @return the value of the DisplayLibrary child.
	 */
	@Nonnull
	GenericAttributeValue<String> getDisplayLibrary();


	/**
	 * Returns the value of the EnvironmentVariables child.
	 *
	 * @return the value of the EnvironmentVariables child.
	 */
	@Nonnull
	GenericAttributeValue<String> getEnvironmentVariables();


	/**
	 * Returns the value of the ErrorReporting child.
	 *
	 * @return the value of the ErrorReporting child.
	 */
	@Nonnull
	GenericAttributeValue<String> getErrorReporting();


	/**
	 * Returns the value of the ExcludedInputPaths child.
	 *
	 * @return the value of the ExcludedInputPaths child.
	 */
	@Nonnull
	GenericAttributeValue<String> getExcludedInputPaths();


	/**
	 * Returns the value of the ExportNamedFunctions child.
	 *
	 * @return the value of the ExportNamedFunctions child.
	 */
	@Nonnull
	GenericAttributeValue<String> getExportNamedFunctions();


	/**
	 * Returns the value of the ForceSymbolReferences child.
	 *
	 * @return the value of the ForceSymbolReferences child.
	 */
	@Nonnull
	GenericAttributeValue<String> getForceSymbolReferences();


	/**
	 * Returns the value of the IgnoreAllDefaultLibraries child.
	 *
	 * @return the value of the IgnoreAllDefaultLibraries child.
	 */
	@Nonnull
	GenericAttributeValue<Boolean> getIgnoreAllDefaultLibraries();


	/**
	 * Returns the value of the IgnoreSpecificDefaultLibraries child.
	 *
	 * @return the value of the IgnoreSpecificDefaultLibraries child.
	 */
	@Nonnull
	GenericAttributeValue<String> getIgnoreSpecificDefaultLibraries();


	/**
	 * Returns the value of the LinkLibraryDependencies child.
	 *
	 * @return the value of the LinkLibraryDependencies child.
	 */
	@Nonnull
	GenericAttributeValue<Boolean> getLinkLibraryDependencies();


	/**
	 * Returns the value of the LinkTimeCodeGeneration child.
	 *
	 * @return the value of the LinkTimeCodeGeneration child.
	 */
	@Nonnull
	GenericAttributeValue<Boolean> getLinkTimeCodeGeneration();


	/**
	 * Returns the value of the LogStandardErrorAsError child.
	 *
	 * @return the value of the LogStandardErrorAsError child.
	 */
	@Nonnull
	GenericAttributeValue<Boolean> getLogStandardErrorAsError();


	/**
	 * Returns the value of the MinimalRebuildFromTracking child.
	 *
	 * @return the value of the MinimalRebuildFromTracking child.
	 */
	@Nonnull
	GenericAttributeValue<Boolean> getMinimalRebuildFromTracking();


	/**
	 * Returns the value of the MinimumRequiredVersion child.
	 *
	 * @return the value of the MinimumRequiredVersion child.
	 */
	@Nonnull
	GenericAttributeValue<String> getMinimumRequiredVersion();


	/**
	 * Returns the value of the ModuleDefinitionFile child.
	 *
	 * @return the value of the ModuleDefinitionFile child.
	 */
	@Nonnull
	GenericAttributeValue<String> getModuleDefinitionFile();


	/**
	 * Returns the value of the OutputFile child.
	 *
	 * @return the value of the OutputFile child.
	 */
	@Nonnull
	GenericAttributeValue<String> getOutputFile();


	/**
	 * Returns the value of the PathOverride child.
	 *
	 * @return the value of the PathOverride child.
	 */
	@Nonnull
	GenericAttributeValue<String> getPathOverride();


	/**
	 * Returns the value of the RemoveObjects child.
	 *
	 * @return the value of the RemoveObjects child.
	 */
	@Nonnull
	GenericAttributeValue<String> getRemoveObjects();


	/**
	 * Returns the value of the SkippedExecution child.
	 *
	 * @return the value of the SkippedExecution child.
	 */
	@Nonnull
	GenericAttributeValue<Boolean> getSkippedExecution();


	/**
	 * Returns the value of the Sources child.
	 *
	 * @return the value of the Sources child.
	 */
	@Nonnull
	@Required
	GenericAttributeValue<String> getSources();


	/**
	 * Returns the value of the SourcesCompiled child.
	 *
	 * @return the value of the SourcesCompiled child.
	 */
	@Nonnull
	GenericAttributeValue<String> getSourcesCompiled();


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
	 * Returns the value of the SubSystem child.
	 *
	 * @return the value of the SubSystem child.
	 */
	@Nonnull
	GenericAttributeValue<String> getSubSystem();


	/**
	 * Returns the value of the SuppressStartupBanner child.
	 *
	 * @return the value of the SuppressStartupBanner child.
	 */
	@Nonnull
	GenericAttributeValue<Boolean> getSuppressStartupBanner();


	/**
	 * Returns the value of the TargetMachine child.
	 *
	 * @return the value of the TargetMachine child.
	 */
	@Nonnull
	GenericAttributeValue<String> getTargetMachine();


	/**
	 * Returns the value of the Timeout child.
	 *
	 * @return the value of the Timeout child.
	 */
	@Nonnull
	GenericAttributeValue<String> getTimeout();


	/**
	 * Returns the value of the TLogReadFiles child.
	 *
	 * @return the value of the TLogReadFiles child.
	 */
	@Nonnull
	GenericAttributeValue<String> getTLogReadFiles();


	/**
	 * Returns the value of the TLogWriteFiles child.
	 *
	 * @return the value of the TLogWriteFiles child.
	 */
	@Nonnull
	GenericAttributeValue<String> getTLogWriteFiles();


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
	 * Returns the value of the TrackedInputFilesToIgnore child.
	 *
	 * @return the value of the TrackedInputFilesToIgnore child.
	 */
	@Nonnull
	GenericAttributeValue<String> getTrackedInputFilesToIgnore();


	/**
	 * Returns the value of the TrackedOutputFilesToIgnore child.
	 *
	 * @return the value of the TrackedOutputFilesToIgnore child.
	 */
	@Nonnull
	GenericAttributeValue<String> getTrackedOutputFilesToIgnore();


	/**
	 * Returns the value of the TrackerLogDirectory child.
	 *
	 * @return the value of the TrackerLogDirectory child.
	 */
	@Nonnull
	GenericAttributeValue<String> getTrackerLogDirectory();


	/**
	 * Returns the value of the TrackFileAccess child.
	 *
	 * @return the value of the TrackFileAccess child.
	 */
	@Nonnull
	GenericAttributeValue<Boolean> getTrackFileAccess();


	/**
	 * Returns the value of the TreatLibWarningAsErrors child.
	 *
	 * @return the value of the TreatLibWarningAsErrors child.
	 */
	@Nonnull
	GenericAttributeValue<Boolean> getTreatLibWarningAsErrors();


	/**
	 * Returns the value of the UseUnicodeResponseFiles child.
	 *
	 * @return the value of the UseUnicodeResponseFiles child.
	 */
	@Nonnull
	GenericAttributeValue<Boolean> getUseUnicodeResponseFiles();


	/**
	 * Returns the value of the Verbose child.
	 *
	 * @return the value of the Verbose child.
	 */
	@Nonnull
	GenericAttributeValue<Boolean> getVerbose();


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
