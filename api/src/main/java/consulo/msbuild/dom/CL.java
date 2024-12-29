// Generated on Sat Jan 28 04:58:18 MSK 2017
// DTD/Schema  :    http://schemas.microsoft.com/developer/msbuild/2003

package consulo.msbuild.dom;

import java.util.List;

import jakarta.annotation.Nonnull;

import consulo.xml.util.xml.DomElement;
import consulo.xml.util.xml.GenericAttributeValue;
import consulo.xml.util.xml.Required;

/**
 * http://schemas.microsoft.com/developer/msbuild/2003:CLElemType interface.
 *
 * @author VISTALL
 */
public interface CL extends DomElement, Task
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
	 * Returns the value of the AdditionalIncludeDirectories child.
	 *
	 * @return the value of the AdditionalIncludeDirectories child.
	 */
	@Nonnull
	GenericAttributeValue<String> getAdditionalIncludeDirectories();


	/**
	 * Returns the value of the AdditionalOptions child.
	 *
	 * @return the value of the AdditionalOptions child.
	 */
	@Nonnull
	GenericAttributeValue<String> getAdditionalOptions();


	/**
	 * Returns the value of the AdditionalUsingDirectories child.
	 *
	 * @return the value of the AdditionalUsingDirectories child.
	 */
	@Nonnull
	GenericAttributeValue<String> getAdditionalUsingDirectories();


	/**
	 * Returns the value of the AssemblerListingLocation child.
	 *
	 * @return the value of the AssemblerListingLocation child.
	 */
	@Nonnull
	GenericAttributeValue<String> getAssemblerListingLocation();


	/**
	 * Returns the value of the AssemblerOutput child.
	 *
	 * @return the value of the AssemblerOutput child.
	 */
	@Nonnull
	GenericAttributeValue<String> getAssemblerOutput();


	/**
	 * Returns the value of the BasicRuntimeChecks child.
	 *
	 * @return the value of the BasicRuntimeChecks child.
	 */
	@Nonnull
	GenericAttributeValue<String> getBasicRuntimeChecks();


	/**
	 * Returns the value of the BrowseInformation child.
	 *
	 * @return the value of the BrowseInformation child.
	 */
	@Nonnull
	GenericAttributeValue<Boolean> getBrowseInformation();


	/**
	 * Returns the value of the BrowseInformationFile child.
	 *
	 * @return the value of the BrowseInformationFile child.
	 */
	@Nonnull
	GenericAttributeValue<String> getBrowseInformationFile();


	/**
	 * Returns the value of the BufferSecurityCheck child.
	 *
	 * @return the value of the BufferSecurityCheck child.
	 */
	@Nonnull
	GenericAttributeValue<Boolean> getBufferSecurityCheck();


	/**
	 * Returns the value of the CallingConvention child.
	 *
	 * @return the value of the CallingConvention child.
	 */
	@Nonnull
	GenericAttributeValue<String> getCallingConvention();


	/**
	 * Returns the value of the CompileAs child.
	 *
	 * @return the value of the CompileAs child.
	 */
	@Nonnull
	GenericAttributeValue<String> getCompileAs();


	/**
	 * Returns the value of the CompileAsManaged child.
	 *
	 * @return the value of the CompileAsManaged child.
	 */
	@Nonnull
	GenericAttributeValue<String> getCompileAsManaged();


	/**
	 * Returns the value of the CreateHotpatchableImage child.
	 *
	 * @return the value of the CreateHotpatchableImage child.
	 */
	@Nonnull
	GenericAttributeValue<Boolean> getCreateHotpatchableImage();


	/**
	 * Returns the value of the DebugInformationFormat child.
	 *
	 * @return the value of the DebugInformationFormat child.
	 */
	@Nonnull
	GenericAttributeValue<String> getDebugInformationFormat();


	/**
	 * Returns the value of the DisableLanguageExtensions child.
	 *
	 * @return the value of the DisableLanguageExtensions child.
	 */
	@Nonnull
	GenericAttributeValue<Boolean> getDisableLanguageExtensions();


	/**
	 * Returns the value of the DisableSpecificWarnings child.
	 *
	 * @return the value of the DisableSpecificWarnings child.
	 */
	@Nonnull
	GenericAttributeValue<String> getDisableSpecificWarnings();


	/**
	 * Returns the value of the EnableEnhancedInstructionSet child.
	 *
	 * @return the value of the EnableEnhancedInstructionSet child.
	 */
	@Nonnull
	GenericAttributeValue<String> getEnableEnhancedInstructionSet();


	/**
	 * Returns the value of the EnableFiberSafeOptimizations child.
	 *
	 * @return the value of the EnableFiberSafeOptimizations child.
	 */
	@Nonnull
	GenericAttributeValue<Boolean> getEnableFiberSafeOptimizations();


	/**
	 * Returns the value of the EnablePREfast child.
	 *
	 * @return the value of the EnablePREfast child.
	 */
	@Nonnull
	GenericAttributeValue<Boolean> getEnablePREfast();


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
	 * Returns the value of the ExceptionHandling child.
	 *
	 * @return the value of the ExceptionHandling child.
	 */
	@Nonnull
	GenericAttributeValue<String> getExceptionHandling();


	/**
	 * Returns the value of the ExcludedInputPaths child.
	 *
	 * @return the value of the ExcludedInputPaths child.
	 */
	@Nonnull
	GenericAttributeValue<String> getExcludedInputPaths();


	/**
	 * Returns the value of the ExpandAttributedSource child.
	 *
	 * @return the value of the ExpandAttributedSource child.
	 */
	@Nonnull
	GenericAttributeValue<Boolean> getExpandAttributedSource();


	/**
	 * Returns the value of the FavorSizeOrSpeed child.
	 *
	 * @return the value of the FavorSizeOrSpeed child.
	 */
	@Nonnull
	GenericAttributeValue<String> getFavorSizeOrSpeed();


	/**
	 * Returns the value of the FloatingPointExceptions child.
	 *
	 * @return the value of the FloatingPointExceptions child.
	 */
	@Nonnull
	GenericAttributeValue<Boolean> getFloatingPointExceptions();


	/**
	 * Returns the value of the FloatingPointModel child.
	 *
	 * @return the value of the FloatingPointModel child.
	 */
	@Nonnull
	GenericAttributeValue<String> getFloatingPointModel();


	/**
	 * Returns the value of the ForceConformanceInForLoopScope child.
	 *
	 * @return the value of the ForceConformanceInForLoopScope child.
	 */
	@Nonnull
	GenericAttributeValue<Boolean> getForceConformanceInForLoopScope();


	/**
	 * Returns the value of the ForcedIncludeFiles child.
	 *
	 * @return the value of the ForcedIncludeFiles child.
	 */
	@Nonnull
	GenericAttributeValue<String> getForcedIncludeFiles();


	/**
	 * Returns the value of the ForcedUsingFiles child.
	 *
	 * @return the value of the ForcedUsingFiles child.
	 */
	@Nonnull
	GenericAttributeValue<String> getForcedUsingFiles();


	/**
	 * Returns the value of the FunctionLevelLinking child.
	 *
	 * @return the value of the FunctionLevelLinking child.
	 */
	@Nonnull
	GenericAttributeValue<Boolean> getFunctionLevelLinking();


	/**
	 * Returns the value of the GenerateXMLDocumentationFiles child.
	 *
	 * @return the value of the GenerateXMLDocumentationFiles child.
	 */
	@Nonnull
	GenericAttributeValue<Boolean> getGenerateXMLDocumentationFiles();


	/**
	 * Returns the value of the IgnoreStandardIncludePath child.
	 *
	 * @return the value of the IgnoreStandardIncludePath child.
	 */
	@Nonnull
	GenericAttributeValue<Boolean> getIgnoreStandardIncludePath();


	/**
	 * Returns the value of the InlineFunctionExpansion child.
	 *
	 * @return the value of the InlineFunctionExpansion child.
	 */
	@Nonnull
	GenericAttributeValue<String> getInlineFunctionExpansion();


	/**
	 * Returns the value of the IntrinsicFunctions child.
	 *
	 * @return the value of the IntrinsicFunctions child.
	 */
	@Nonnull
	GenericAttributeValue<Boolean> getIntrinsicFunctions();


	/**
	 * Returns the value of the LogStandardErrorAsError child.
	 *
	 * @return the value of the LogStandardErrorAsError child.
	 */
	@Nonnull
	GenericAttributeValue<Boolean> getLogStandardErrorAsError();


	/**
	 * Returns the value of the MinimalRebuild child.
	 *
	 * @return the value of the MinimalRebuild child.
	 */
	@Nonnull
	GenericAttributeValue<Boolean> getMinimalRebuild();


	/**
	 * Returns the value of the MinimalRebuildFromTracking child.
	 *
	 * @return the value of the MinimalRebuildFromTracking child.
	 */
	@Nonnull
	GenericAttributeValue<Boolean> getMinimalRebuildFromTracking();


	/**
	 * Returns the value of the MultiProcessorCompilation child.
	 *
	 * @return the value of the MultiProcessorCompilation child.
	 */
	@Nonnull
	GenericAttributeValue<Boolean> getMultiProcessorCompilation();


	/**
	 * Returns the value of the ObjectFileName child.
	 *
	 * @return the value of the ObjectFileName child.
	 */
	@Nonnull
	GenericAttributeValue<String> getObjectFileName();


	/**
	 * Returns the value of the ObjectFiles child.
	 *
	 * @return the value of the ObjectFiles child.
	 */
	@Nonnull
	GenericAttributeValue<String> getObjectFiles();


	/**
	 * Returns the value of the OmitDefaultLibName child.
	 *
	 * @return the value of the OmitDefaultLibName child.
	 */
	@Nonnull
	GenericAttributeValue<Boolean> getOmitDefaultLibName();


	/**
	 * Returns the value of the OmitFramePointers child.
	 *
	 * @return the value of the OmitFramePointers child.
	 */
	@Nonnull
	GenericAttributeValue<Boolean> getOmitFramePointers();


	/**
	 * Returns the value of the OpenMPSupport child.
	 *
	 * @return the value of the OpenMPSupport child.
	 */
	@Nonnull
	GenericAttributeValue<Boolean> getOpenMPSupport();


	/**
	 * Returns the value of the Optimization child.
	 *
	 * @return the value of the Optimization child.
	 */
	@Nonnull
	GenericAttributeValue<String> getOptimization();


	/**
	 * Returns the value of the PathOverride child.
	 *
	 * @return the value of the PathOverride child.
	 */
	@Nonnull
	GenericAttributeValue<String> getPathOverride();


	/**
	 * Returns the value of the PrecompiledHeader child.
	 *
	 * @return the value of the PrecompiledHeader child.
	 */
	@Nonnull
	GenericAttributeValue<String> getPrecompiledHeader();


	/**
	 * Returns the value of the PrecompiledHeaderFile child.
	 *
	 * @return the value of the PrecompiledHeaderFile child.
	 */
	@Nonnull
	GenericAttributeValue<String> getPrecompiledHeaderFile();


	/**
	 * Returns the value of the PrecompiledHeaderOutputFile child.
	 *
	 * @return the value of the PrecompiledHeaderOutputFile child.
	 */
	@Nonnull
	GenericAttributeValue<String> getPrecompiledHeaderOutputFile();


	/**
	 * Returns the value of the PreprocessKeepComments child.
	 *
	 * @return the value of the PreprocessKeepComments child.
	 */
	@Nonnull
	GenericAttributeValue<Boolean> getPreprocessKeepComments();


	/**
	 * Returns the value of the PreprocessorDefinitions child.
	 *
	 * @return the value of the PreprocessorDefinitions child.
	 */
	@Nonnull
	GenericAttributeValue<String> getPreprocessorDefinitions();


	/**
	 * Returns the value of the PreprocessOutput child.
	 *
	 * @return the value of the PreprocessOutput child.
	 */
	@Nonnull
	GenericAttributeValue<String> getPreprocessOutput();


	/**
	 * Returns the value of the PreprocessSuppressLineNumbers child.
	 *
	 * @return the value of the PreprocessSuppressLineNumbers child.
	 */
	@Nonnull
	GenericAttributeValue<Boolean> getPreprocessSuppressLineNumbers();


	/**
	 * Returns the value of the PreprocessToFile child.
	 *
	 * @return the value of the PreprocessToFile child.
	 */
	@Nonnull
	GenericAttributeValue<Boolean> getPreprocessToFile();


	/**
	 * Returns the value of the ProcessorNumber child.
	 *
	 * @return the value of the ProcessorNumber child.
	 */
	@Nonnull
	GenericAttributeValue<String> getProcessorNumber();


	/**
	 * Returns the value of the ProgramDataBaseFileName child.
	 *
	 * @return the value of the ProgramDataBaseFileName child.
	 */
	@Nonnull
	GenericAttributeValue<String> getProgramDataBaseFileName();


	/**
	 * Returns the value of the RuntimeLibrary child.
	 *
	 * @return the value of the RuntimeLibrary child.
	 */
	@Nonnull
	GenericAttributeValue<String> getRuntimeLibrary();


	/**
	 * Returns the value of the RuntimeTypeInfo child.
	 *
	 * @return the value of the RuntimeTypeInfo child.
	 */
	@Nonnull
	GenericAttributeValue<Boolean> getRuntimeTypeInfo();


	/**
	 * Returns the value of the ShowIncludes child.
	 *
	 * @return the value of the ShowIncludes child.
	 */
	@Nonnull
	GenericAttributeValue<Boolean> getShowIncludes();


	/**
	 * Returns the value of the SkippedExecution child.
	 *
	 * @return the value of the SkippedExecution child.
	 */
	@Nonnull
	GenericAttributeValue<Boolean> getSkippedExecution();


	/**
	 * Returns the value of the SmallerTypeCheck child.
	 *
	 * @return the value of the SmallerTypeCheck child.
	 */
	@Nonnull
	GenericAttributeValue<Boolean> getSmallerTypeCheck();


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
	 * Returns the value of the StringPooling child.
	 *
	 * @return the value of the StringPooling child.
	 */
	@Nonnull
	GenericAttributeValue<Boolean> getStringPooling();


	/**
	 * Returns the value of the StructMemberAlignment child.
	 *
	 * @return the value of the StructMemberAlignment child.
	 */
	@Nonnull
	GenericAttributeValue<String> getStructMemberAlignment();


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
	 * Returns the value of the TreatSpecificWarningsAsErrors child.
	 *
	 * @return the value of the TreatSpecificWarningsAsErrors child.
	 */
	@Nonnull
	GenericAttributeValue<String> getTreatSpecificWarningsAsErrors();


	/**
	 * Returns the value of the TreatWarningAsError child.
	 *
	 * @return the value of the TreatWarningAsError child.
	 */
	@Nonnull
	GenericAttributeValue<Boolean> getTreatWarningAsError();


	/**
	 * Returns the value of the TreatWChar_tAsBuiltInType child.
	 *
	 * @return the value of the TreatWChar_tAsBuiltInType child.
	 */
	@Nonnull
	GenericAttributeValue<Boolean> getTreatWChar_tAsBuiltInType();


	/**
	 * Returns the value of the UndefineAllPreprocessorDefinitions child.
	 *
	 * @return the value of the UndefineAllPreprocessorDefinitions child.
	 */
	@Nonnull
	GenericAttributeValue<Boolean> getUndefineAllPreprocessorDefinitions();


	/**
	 * Returns the value of the UndefinePreprocessorDefinitions child.
	 *
	 * @return the value of the UndefinePreprocessorDefinitions child.
	 */
	@Nonnull
	GenericAttributeValue<String> getUndefinePreprocessorDefinitions();


	/**
	 * Returns the value of the UseFullPaths child.
	 *
	 * @return the value of the UseFullPaths child.
	 */
	@Nonnull
	GenericAttributeValue<Boolean> getUseFullPaths();


	/**
	 * Returns the value of the UseUnicodeForAssemblerListing child.
	 *
	 * @return the value of the UseUnicodeForAssemblerListing child.
	 */
	@Nonnull
	GenericAttributeValue<Boolean> getUseUnicodeForAssemblerListing();


	/**
	 * Returns the value of the WarningLevel child.
	 *
	 * @return the value of the WarningLevel child.
	 */
	@Nonnull
	GenericAttributeValue<String> getWarningLevel();


	/**
	 * Returns the value of the WholeProgramOptimization child.
	 *
	 * @return the value of the WholeProgramOptimization child.
	 */
	@Nonnull
	GenericAttributeValue<Boolean> getWholeProgramOptimization();


	/**
	 * Returns the value of the XMLDocumentationFileName child.
	 *
	 * @return the value of the XMLDocumentationFileName child.
	 */
	@Nonnull
	GenericAttributeValue<String> getXMLDocumentationFileName();


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
