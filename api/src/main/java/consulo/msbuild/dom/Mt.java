// Generated on Sat Jan 28 04:58:19 MSK 2017
// DTD/Schema  :    http://schemas.microsoft.com/developer/msbuild/2003

package consulo.msbuild.dom;

import java.util.List;

import javax.annotation.Nonnull;

import consulo.xml.util.xml.DomElement;
import consulo.xml.util.xml.GenericAttributeValue;

/**
 * http://schemas.microsoft.com/developer/msbuild/2003:MtElemType interface.
 *
 * @author VISTALL
 */
public interface Mt extends DomElement, Task
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
	 * Returns the value of the AdditionalManifestFiles child.
	 *
	 * @return the value of the AdditionalManifestFiles child.
	 */
	@Nonnull
	GenericAttributeValue<String> getAdditionalManifestFiles();


	/**
	 * Returns the value of the AdditionalOptions child.
	 *
	 * @return the value of the AdditionalOptions child.
	 */
	@Nonnull
	GenericAttributeValue<String> getAdditionalOptions();


	/**
	 * Returns the value of the AssemblyIdentity child.
	 *
	 * @return the value of the AssemblyIdentity child.
	 */
	@Nonnull
	GenericAttributeValue<String> getAssemblyIdentity();


	/**
	 * Returns the value of the ComponentFileName child.
	 *
	 * @return the value of the ComponentFileName child.
	 */
	@Nonnull
	GenericAttributeValue<String> getComponentFileName();


	/**
	 * Returns the value of the EmbedManifest child.
	 *
	 * @return the value of the EmbedManifest child.
	 */
	@Nonnull
	GenericAttributeValue<Boolean> getEmbedManifest();


	/**
	 * Returns the value of the EnvironmentVariables child.
	 *
	 * @return the value of the EnvironmentVariables child.
	 */
	@Nonnull
	GenericAttributeValue<String> getEnvironmentVariables();


	/**
	 * Returns the value of the ExcludedInputPaths child.
	 *
	 * @return the value of the ExcludedInputPaths child.
	 */
	@Nonnull
	GenericAttributeValue<String> getExcludedInputPaths();


	/**
	 * Returns the value of the GenerateCatalogFiles child.
	 *
	 * @return the value of the GenerateCatalogFiles child.
	 */
	@Nonnull
	GenericAttributeValue<Boolean> getGenerateCatalogFiles();


	/**
	 * Returns the value of the GenerateCategoryTags child.
	 *
	 * @return the value of the GenerateCategoryTags child.
	 */
	@Nonnull
	GenericAttributeValue<Boolean> getGenerateCategoryTags();


	/**
	 * Returns the value of the InputResourceManifests child.
	 *
	 * @return the value of the InputResourceManifests child.
	 */
	@Nonnull
	GenericAttributeValue<String> getInputResourceManifests();


	/**
	 * Returns the value of the LogStandardErrorAsError child.
	 *
	 * @return the value of the LogStandardErrorAsError child.
	 */
	@Nonnull
	GenericAttributeValue<Boolean> getLogStandardErrorAsError();


	/**
	 * Returns the value of the ManifestFromManagedAssembly child.
	 *
	 * @return the value of the ManifestFromManagedAssembly child.
	 */
	@Nonnull
	GenericAttributeValue<String> getManifestFromManagedAssembly();


	/**
	 * Returns the value of the MinimalRebuildFromTracking child.
	 *
	 * @return the value of the MinimalRebuildFromTracking child.
	 */
	@Nonnull
	GenericAttributeValue<Boolean> getMinimalRebuildFromTracking();


	/**
	 * Returns the value of the OutputManifestFile child.
	 *
	 * @return the value of the OutputManifestFile child.
	 */
	@Nonnull
	GenericAttributeValue<String> getOutputManifestFile();


	/**
	 * Returns the value of the OutputResourceManifests child.
	 *
	 * @return the value of the OutputResourceManifests child.
	 */
	@Nonnull
	GenericAttributeValue<String> getOutputResourceManifests();


	/**
	 * Returns the value of the PathOverride child.
	 *
	 * @return the value of the PathOverride child.
	 */
	@Nonnull
	GenericAttributeValue<String> getPathOverride();


	/**
	 * Returns the value of the RegistrarScriptFile child.
	 *
	 * @return the value of the RegistrarScriptFile child.
	 */
	@Nonnull
	GenericAttributeValue<String> getRegistrarScriptFile();


	/**
	 * Returns the value of the ReplacementsFile child.
	 *
	 * @return the value of the ReplacementsFile child.
	 */
	@Nonnull
	GenericAttributeValue<String> getReplacementsFile();


	/**
	 * Returns the value of the ResourceOutputFileName child.
	 *
	 * @return the value of the ResourceOutputFileName child.
	 */
	@Nonnull
	GenericAttributeValue<String> getResourceOutputFileName();


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
	 * Returns the value of the SuppressDependencyElement child.
	 *
	 * @return the value of the SuppressDependencyElement child.
	 */
	@Nonnull
	GenericAttributeValue<Boolean> getSuppressDependencyElement();


	/**
	 * Returns the value of the SuppressStartupBanner child.
	 *
	 * @return the value of the SuppressStartupBanner child.
	 */
	@Nonnull
	GenericAttributeValue<Boolean> getSuppressStartupBanner();


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
	 * Returns the value of the TypeLibraryFile child.
	 *
	 * @return the value of the TypeLibraryFile child.
	 */
	@Nonnull
	GenericAttributeValue<String> getTypeLibraryFile();


	/**
	 * Returns the value of the UpdateFileHashes child.
	 *
	 * @return the value of the UpdateFileHashes child.
	 */
	@Nonnull
	GenericAttributeValue<Boolean> getUpdateFileHashes();


	/**
	 * Returns the value of the UpdateFileHashesSearchPath child.
	 *
	 * @return the value of the UpdateFileHashesSearchPath child.
	 */
	@Nonnull
	GenericAttributeValue<String> getUpdateFileHashesSearchPath();


	/**
	 * Returns the value of the VerboseOutput child.
	 *
	 * @return the value of the VerboseOutput child.
	 */
	@Nonnull
	GenericAttributeValue<Boolean> getVerboseOutput();


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
