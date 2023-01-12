// Generated on Sat Jan 28 04:58:19 MSK 2017
// DTD/Schema  :    http://schemas.microsoft.com/developer/msbuild/2003

package consulo.msbuild.dom;

import java.util.List;

import javax.annotation.Nonnull;

import consulo.xml.util.xml.DomElement;
import consulo.xml.util.xml.GenericAttributeValue;
import consulo.xml.util.xml.Required;

/**
 * http://schemas.microsoft.com/developer/msbuild/2003:LinkElemType interface.
 *
 * @author VISTALL
 */
public interface Link extends DomElement, Task
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
	 * Returns the value of the AdditionalManifestDependencies child.
	 *
	 * @return the value of the AdditionalManifestDependencies child.
	 */
	@Nonnull
	GenericAttributeValue<String> getAdditionalManifestDependencies();


	/**
	 * Returns the value of the AdditionalOptions child.
	 *
	 * @return the value of the AdditionalOptions child.
	 */
	@Nonnull
	GenericAttributeValue<String> getAdditionalOptions();


	/**
	 * Returns the value of the AddModuleNamesToAssembly child.
	 *
	 * @return the value of the AddModuleNamesToAssembly child.
	 */
	@Nonnull
	GenericAttributeValue<String> getAddModuleNamesToAssembly();


	/**
	 * Returns the value of the AllowIsolation child.
	 *
	 * @return the value of the AllowIsolation child.
	 */
	@Nonnull
	GenericAttributeValue<Boolean> getAllowIsolation();


	/**
	 * Returns the value of the AssemblyDebug child.
	 *
	 * @return the value of the AssemblyDebug child.
	 */
	@Nonnull
	GenericAttributeValue<Boolean> getAssemblyDebug();


	/**
	 * Returns the value of the AssemblyLinkResource child.
	 *
	 * @return the value of the AssemblyLinkResource child.
	 */
	@Nonnull
	GenericAttributeValue<String> getAssemblyLinkResource();


	/**
	 * Returns the value of the BaseAddress child.
	 *
	 * @return the value of the BaseAddress child.
	 */
	@Nonnull
	GenericAttributeValue<String> getBaseAddress();


	/**
	 * Returns the value of the CLRImageType child.
	 *
	 * @return the value of the CLRImageType child.
	 */
	@Nonnull
	GenericAttributeValue<String> getCLRImageType();


	/**
	 * Returns the value of the CLRSupportLastError child.
	 *
	 * @return the value of the CLRSupportLastError child.
	 */
	@Nonnull
	GenericAttributeValue<String> getCLRSupportLastError();


	/**
	 * Returns the value of the CLRThreadAttribute child.
	 *
	 * @return the value of the CLRThreadAttribute child.
	 */
	@Nonnull
	GenericAttributeValue<String> getCLRThreadAttribute();


	/**
	 * Returns the value of the CLRUnmanagedCodeCheck child.
	 *
	 * @return the value of the CLRUnmanagedCodeCheck child.
	 */
	@Nonnull
	GenericAttributeValue<Boolean> getCLRUnmanagedCodeCheck();


	/**
	 * Returns the value of the CreateHotPatchableImage child.
	 *
	 * @return the value of the CreateHotPatchableImage child.
	 */
	@Nonnull
	GenericAttributeValue<String> getCreateHotPatchableImage();


	/**
	 * Returns the value of the DataExecutionPrevention child.
	 *
	 * @return the value of the DataExecutionPrevention child.
	 */
	@Nonnull
	GenericAttributeValue<Boolean> getDataExecutionPrevention();


	/**
	 * Returns the value of the DelayLoadDLLs child.
	 *
	 * @return the value of the DelayLoadDLLs child.
	 */
	@Nonnull
	GenericAttributeValue<String> getDelayLoadDLLs();


	/**
	 * Returns the value of the DelaySign child.
	 *
	 * @return the value of the DelaySign child.
	 */
	@Nonnull
	GenericAttributeValue<Boolean> getDelaySign();


	/**
	 * Returns the value of the Driver child.
	 *
	 * @return the value of the Driver child.
	 */
	@Nonnull
	GenericAttributeValue<String> getDriver();


	/**
	 * Returns the value of the EmbedManagedResourceFile child.
	 *
	 * @return the value of the EmbedManagedResourceFile child.
	 */
	@Nonnull
	GenericAttributeValue<String> getEmbedManagedResourceFile();


	/**
	 * Returns the value of the EnableCOMDATFolding child.
	 *
	 * @return the value of the EnableCOMDATFolding child.
	 */
	@Nonnull
	GenericAttributeValue<Boolean> getEnableCOMDATFolding();


	/**
	 * Returns the value of the EnableUAC child.
	 *
	 * @return the value of the EnableUAC child.
	 */
	@Nonnull
	GenericAttributeValue<Boolean> getEnableUAC();


	/**
	 * Returns the value of the EntryPointSymbol child.
	 *
	 * @return the value of the EntryPointSymbol child.
	 */
	@Nonnull
	GenericAttributeValue<String> getEntryPointSymbol();


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
	 * Returns the value of the FixedBaseAddress child.
	 *
	 * @return the value of the FixedBaseAddress child.
	 */
	@Nonnull
	GenericAttributeValue<Boolean> getFixedBaseAddress();


	/**
	 * Returns the value of the ForceFileOutput child.
	 *
	 * @return the value of the ForceFileOutput child.
	 */
	@Nonnull
	GenericAttributeValue<String> getForceFileOutput();


	/**
	 * Returns the value of the ForceSymbolReferences child.
	 *
	 * @return the value of the ForceSymbolReferences child.
	 */
	@Nonnull
	GenericAttributeValue<String> getForceSymbolReferences();


	/**
	 * Returns the value of the FunctionOrder child.
	 *
	 * @return the value of the FunctionOrder child.
	 */
	@Nonnull
	GenericAttributeValue<String> getFunctionOrder();


	/**
	 * Returns the value of the GenerateDebugInformation child.
	 *
	 * @return the value of the GenerateDebugInformation child.
	 */
	@Nonnull
	GenericAttributeValue<Boolean> getGenerateDebugInformation();


	/**
	 * Returns the value of the GenerateManifest child.
	 *
	 * @return the value of the GenerateManifest child.
	 */
	@Nonnull
	GenericAttributeValue<Boolean> getGenerateManifest();


	/**
	 * Returns the value of the GenerateMapFile child.
	 *
	 * @return the value of the GenerateMapFile child.
	 */
	@Nonnull
	GenericAttributeValue<Boolean> getGenerateMapFile();


	/**
	 * Returns the value of the HeapCommitSize child.
	 *
	 * @return the value of the HeapCommitSize child.
	 */
	@Nonnull
	GenericAttributeValue<String> getHeapCommitSize();


	/**
	 * Returns the value of the HeapReserveSize child.
	 *
	 * @return the value of the HeapReserveSize child.
	 */
	@Nonnull
	GenericAttributeValue<String> getHeapReserveSize();


	/**
	 * Returns the value of the IgnoreAllDefaultLibraries child.
	 *
	 * @return the value of the IgnoreAllDefaultLibraries child.
	 */
	@Nonnull
	GenericAttributeValue<Boolean> getIgnoreAllDefaultLibraries();


	/**
	 * Returns the value of the IgnoreEmbeddedIDL child.
	 *
	 * @return the value of the IgnoreEmbeddedIDL child.
	 */
	@Nonnull
	GenericAttributeValue<Boolean> getIgnoreEmbeddedIDL();


	/**
	 * Returns the value of the IgnoreImportLibrary child.
	 *
	 * @return the value of the IgnoreImportLibrary child.
	 */
	@Nonnull
	GenericAttributeValue<Boolean> getIgnoreImportLibrary();


	/**
	 * Returns the value of the IgnoreSpecificDefaultLibraries child.
	 *
	 * @return the value of the IgnoreSpecificDefaultLibraries child.
	 */
	@Nonnull
	GenericAttributeValue<String> getIgnoreSpecificDefaultLibraries();


	/**
	 * Returns the value of the ImageHasSafeExceptionHandlers child.
	 *
	 * @return the value of the ImageHasSafeExceptionHandlers child.
	 */
	@Nonnull
	GenericAttributeValue<Boolean> getImageHasSafeExceptionHandlers();


	/**
	 * Returns the value of the ImportLibrary child.
	 *
	 * @return the value of the ImportLibrary child.
	 */
	@Nonnull
	GenericAttributeValue<String> getImportLibrary();


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
	 * Returns the value of the LargeAddressAware child.
	 *
	 * @return the value of the LargeAddressAware child.
	 */
	@Nonnull
	GenericAttributeValue<Boolean> getLargeAddressAware();


	/**
	 * Returns the value of the LinkDLL child.
	 *
	 * @return the value of the LinkDLL child.
	 */
	@Nonnull
	GenericAttributeValue<Boolean> getLinkDLL();


	/**
	 * Returns the value of the LinkErrorReporting child.
	 *
	 * @return the value of the LinkErrorReporting child.
	 */
	@Nonnull
	GenericAttributeValue<String> getLinkErrorReporting();


	/**
	 * Returns the value of the LinkIncremental child.
	 *
	 * @return the value of the LinkIncremental child.
	 */
	@Nonnull
	GenericAttributeValue<Boolean> getLinkIncremental();


	/**
	 * Returns the value of the LinkLibraryDependencies child.
	 *
	 * @return the value of the LinkLibraryDependencies child.
	 */
	@Nonnull
	GenericAttributeValue<Boolean> getLinkLibraryDependencies();


	/**
	 * Returns the value of the LinkStatus child.
	 *
	 * @return the value of the LinkStatus child.
	 */
	@Nonnull
	GenericAttributeValue<Boolean> getLinkStatus();


	/**
	 * Returns the value of the LinkTimeCodeGeneration child.
	 *
	 * @return the value of the LinkTimeCodeGeneration child.
	 */
	@Nonnull
	GenericAttributeValue<String> getLinkTimeCodeGeneration();


	/**
	 * Returns the value of the LogStandardErrorAsError child.
	 *
	 * @return the value of the LogStandardErrorAsError child.
	 */
	@Nonnull
	GenericAttributeValue<Boolean> getLogStandardErrorAsError();


	/**
	 * Returns the value of the ManifestFile child.
	 *
	 * @return the value of the ManifestFile child.
	 */
	@Nonnull
	GenericAttributeValue<String> getManifestFile();


	/**
	 * Returns the value of the MapExports child.
	 *
	 * @return the value of the MapExports child.
	 */
	@Nonnull
	GenericAttributeValue<Boolean> getMapExports();


	/**
	 * Returns the value of the MapFileName child.
	 *
	 * @return the value of the MapFileName child.
	 */
	@Nonnull
	GenericAttributeValue<String> getMapFileName();


	/**
	 * Returns the value of the MergedIDLBaseFileName child.
	 *
	 * @return the value of the MergedIDLBaseFileName child.
	 */
	@Nonnull
	GenericAttributeValue<String> getMergedIDLBaseFileName();


	/**
	 * Returns the value of the MergeSections child.
	 *
	 * @return the value of the MergeSections child.
	 */
	@Nonnull
	GenericAttributeValue<String> getMergeSections();


	/**
	 * Returns the value of the MidlCommandFile child.
	 *
	 * @return the value of the MidlCommandFile child.
	 */
	@Nonnull
	GenericAttributeValue<String> getMidlCommandFile();


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
	 * Returns the value of the MSDOSStubFileName child.
	 *
	 * @return the value of the MSDOSStubFileName child.
	 */
	@Nonnull
	GenericAttributeValue<String> getMSDOSStubFileName();


	/**
	 * Returns the value of the NoEntryPoint child.
	 *
	 * @return the value of the NoEntryPoint child.
	 */
	@Nonnull
	GenericAttributeValue<Boolean> getNoEntryPoint();


	/**
	 * Returns the value of the ObjectFiles child.
	 *
	 * @return the value of the ObjectFiles child.
	 */
	@Nonnull
	GenericAttributeValue<String> getObjectFiles();


	/**
	 * Returns the value of the OptimizeReferences child.
	 *
	 * @return the value of the OptimizeReferences child.
	 */
	@Nonnull
	GenericAttributeValue<Boolean> getOptimizeReferences();


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
	 * Returns the value of the PerUserRedirection child.
	 *
	 * @return the value of the PerUserRedirection child.
	 */
	@Nonnull
	GenericAttributeValue<Boolean> getPerUserRedirection();


	/**
	 * Returns the value of the PreprocessOutput child.
	 *
	 * @return the value of the PreprocessOutput child.
	 */
	@Nonnull
	GenericAttributeValue<String> getPreprocessOutput();


	/**
	 * Returns the value of the PreventDllBinding child.
	 *
	 * @return the value of the PreventDllBinding child.
	 */
	@Nonnull
	GenericAttributeValue<Boolean> getPreventDllBinding();


	/**
	 * Returns the value of the Profile child.
	 *
	 * @return the value of the Profile child.
	 */
	@Nonnull
	GenericAttributeValue<Boolean> getProfile();


	/**
	 * Returns the value of the ProfileGuidedDatabase child.
	 *
	 * @return the value of the ProfileGuidedDatabase child.
	 */
	@Nonnull
	GenericAttributeValue<String> getProfileGuidedDatabase();


	/**
	 * Returns the value of the ProgramDatabaseFile child.
	 *
	 * @return the value of the ProgramDatabaseFile child.
	 */
	@Nonnull
	GenericAttributeValue<String> getProgramDatabaseFile();


	/**
	 * Returns the value of the RandomizedBaseAddress child.
	 *
	 * @return the value of the RandomizedBaseAddress child.
	 */
	@Nonnull
	GenericAttributeValue<Boolean> getRandomizedBaseAddress();


	/**
	 * Returns the value of the RegisterOutput child.
	 *
	 * @return the value of the RegisterOutput child.
	 */
	@Nonnull
	GenericAttributeValue<Boolean> getRegisterOutput();


	/**
	 * Returns the value of the SectionAlignment child.
	 *
	 * @return the value of the SectionAlignment child.
	 */
	@Nonnull
	GenericAttributeValue<String> getSectionAlignment();


	/**
	 * Returns the value of the SetChecksum child.
	 *
	 * @return the value of the SetChecksum child.
	 */
	@Nonnull
	GenericAttributeValue<Boolean> getSetChecksum();


	/**
	 * Returns the value of the ShowProgress child.
	 *
	 * @return the value of the ShowProgress child.
	 */
	@Nonnull
	GenericAttributeValue<String> getShowProgress();


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
	 * Returns the value of the SpecifySectionAttributes child.
	 *
	 * @return the value of the SpecifySectionAttributes child.
	 */
	@Nonnull
	GenericAttributeValue<String> getSpecifySectionAttributes();


	/**
	 * Returns the value of the StackCommitSize child.
	 *
	 * @return the value of the StackCommitSize child.
	 */
	@Nonnull
	GenericAttributeValue<String> getStackCommitSize();


	/**
	 * Returns the value of the StackReserveSize child.
	 *
	 * @return the value of the StackReserveSize child.
	 */
	@Nonnull
	GenericAttributeValue<String> getStackReserveSize();


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
	 * Returns the value of the StripPrivateSymbols child.
	 *
	 * @return the value of the StripPrivateSymbols child.
	 */
	@Nonnull
	GenericAttributeValue<String> getStripPrivateSymbols();


	/**
	 * Returns the value of the SubSystem child.
	 *
	 * @return the value of the SubSystem child.
	 */
	@Nonnull
	GenericAttributeValue<String> getSubSystem();


	/**
	 * Returns the value of the SupportNobindOfDelayLoadedDLL child.
	 *
	 * @return the value of the SupportNobindOfDelayLoadedDLL child.
	 */
	@Nonnull
	GenericAttributeValue<Boolean> getSupportNobindOfDelayLoadedDLL();


	/**
	 * Returns the value of the SupportUnloadOfDelayLoadedDLL child.
	 *
	 * @return the value of the SupportUnloadOfDelayLoadedDLL child.
	 */
	@Nonnull
	GenericAttributeValue<Boolean> getSupportUnloadOfDelayLoadedDLL();


	/**
	 * Returns the value of the SuppressStartupBanner child.
	 *
	 * @return the value of the SuppressStartupBanner child.
	 */
	@Nonnull
	GenericAttributeValue<Boolean> getSuppressStartupBanner();


	/**
	 * Returns the value of the SwapRunFromCD child.
	 *
	 * @return the value of the SwapRunFromCD child.
	 */
	@Nonnull
	GenericAttributeValue<Boolean> getSwapRunFromCD();


	/**
	 * Returns the value of the SwapRunFromNET child.
	 *
	 * @return the value of the SwapRunFromNET child.
	 */
	@Nonnull
	GenericAttributeValue<Boolean> getSwapRunFromNET();


	/**
	 * Returns the value of the TargetMachine child.
	 *
	 * @return the value of the TargetMachine child.
	 */
	@Nonnull
	GenericAttributeValue<String> getTargetMachine();


	/**
	 * Returns the value of the TerminalServerAware child.
	 *
	 * @return the value of the TerminalServerAware child.
	 */
	@Nonnull
	GenericAttributeValue<Boolean> getTerminalServerAware();


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
	 * Returns the value of the TreatLinkerWarningAsErrors child.
	 *
	 * @return the value of the TreatLinkerWarningAsErrors child.
	 */
	@Nonnull
	GenericAttributeValue<Boolean> getTreatLinkerWarningAsErrors();


	/**
	 * Returns the value of the TurnOffAssemblyGeneration child.
	 *
	 * @return the value of the TurnOffAssemblyGeneration child.
	 */
	@Nonnull
	GenericAttributeValue<Boolean> getTurnOffAssemblyGeneration();


	/**
	 * Returns the value of the TypeLibraryFile child.
	 *
	 * @return the value of the TypeLibraryFile child.
	 */
	@Nonnull
	GenericAttributeValue<String> getTypeLibraryFile();


	/**
	 * Returns the value of the TypeLibraryResourceID child.
	 *
	 * @return the value of the TypeLibraryResourceID child.
	 */
	@Nonnull
	GenericAttributeValue<String> getTypeLibraryResourceID();


	/**
	 * Returns the value of the UACExecutionLevel child.
	 *
	 * @return the value of the UACExecutionLevel child.
	 */
	@Nonnull
	GenericAttributeValue<String> getUACExecutionLevel();


	/**
	 * Returns the value of the UACUIAccess child.
	 *
	 * @return the value of the UACUIAccess child.
	 */
	@Nonnull
	GenericAttributeValue<Boolean> getUACUIAccess();


	/**
	 * Returns the value of the UseLibraryDependencyInputs child.
	 *
	 * @return the value of the UseLibraryDependencyInputs child.
	 */
	@Nonnull
	GenericAttributeValue<Boolean> getUseLibraryDependencyInputs();


	/**
	 * Returns the value of the Version child.
	 *
	 * @return the value of the Version child.
	 */
	@Nonnull
	GenericAttributeValue<String> getVersion();


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
