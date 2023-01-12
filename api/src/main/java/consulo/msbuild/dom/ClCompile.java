// Generated on Sat Jan 28 04:58:19 MSK 2017
// DTD/Schema  :    http://schemas.microsoft.com/developer/msbuild/2003

package consulo.msbuild.dom;

import java.util.List;

import javax.annotation.Nonnull;

import consulo.xml.util.xml.DomElement;
import consulo.xml.util.xml.GenericAttributeValue;
import consulo.xml.util.xml.GenericDomValue;

/**
 * http://schemas.microsoft.com/developer/msbuild/2003:ClCompileElemType interface.
 *
 * @author VISTALL
 */
public interface ClCompile extends DomElement, SimpleItem
{

	/**
	 * Returns the value of the Condition child.
	 * <pre>
	 * <h3>Attribute null:Condition documentation</h3>
	 * <!-- _locID_text="SimpleItemType_Condition" _locComment="" -->Optional expression evaluated to determine whether the items should be evaluated
	 * </pre>
	 *
	 * @return the value of the Condition child.
	 */
	@Nonnull
	GenericAttributeValue<String> getCondition();


	/**
	 * Returns the value of the Include child.
	 * <pre>
	 * <h3>Attribute null:Include documentation</h3>
	 * <!-- _locID_text="SimpleItemType_Include" _locComment="" -->Semi-colon separated list of files (wildcards are allowed) or other item names to include in this item list
	 * </pre>
	 *
	 * @return the value of the Include child.
	 */
	@Nonnull
	GenericAttributeValue<String> getInclude();


	/**
	 * Returns the value of the Exclude child.
	 * <pre>
	 * <h3>Attribute null:Exclude documentation</h3>
	 * <!-- _locID_text="SimpleItemType_Exclude" _locComment="" -->Semi-colon separated list of files (wildcards are allowed) or other item names to exclude from the Include list
	 * </pre>
	 *
	 * @return the value of the Exclude child.
	 */
	@Nonnull
	GenericAttributeValue<String> getExclude();


	/**
	 * Returns the value of the Remove child.
	 * <pre>
	 * <h3>Attribute null:Remove documentation</h3>
	 * <!-- _locID_text="SimpleItemType_Remove" _locComment="" -->Semi-colon separated list of files (wildcards are allowed) or other item names to remove from the existing list contents
	 * </pre>
	 *
	 * @return the value of the Remove child.
	 */
	@Nonnull
	GenericAttributeValue<String> getRemove();


	/**
	 * Returns the value of the Update child.
	 * <pre>
	 * <h3>Attribute null:Update documentation</h3>
	 * <!-- _locID_text="SimpleItemType_Remove" _locComment="" -->Semi-colon separated list of files (wildcards are allowed) or other item names to be updated with the metadata from contained in
	 * this xml element
	 * </pre>
	 *
	 * @return the value of the Update child.
	 */
	@Nonnull
	GenericAttributeValue<String> getUpdate();


	/**
	 * Returns the value of the Label child.
	 * <pre>
	 * <h3>Attribute null:Label documentation</h3>
	 * <!-- _locID_text="ImportGroupType_Label" _locComment="" -->Optional expression. Used to identify or order system and user elements
	 * </pre>
	 *
	 * @return the value of the Label child.
	 */
	@Nonnull
	GenericAttributeValue<String> getLabel();


	/**
	 * Returns the list of PrecompiledHeader children.
	 *
	 * @return the list of PrecompiledHeader children.
	 */
	@Nonnull
	List<PrecompiledHeader> getPrecompiledHeaders();

	/**
	 * Adds new child to the list of PrecompiledHeader children.
	 *
	 * @return created child
	 */
	PrecompiledHeader addPrecompiledHeader();


	/**
	 * Returns the list of AdditionalIncludeDirectories children.
	 *
	 * @return the list of AdditionalIncludeDirectories children.
	 */
	@Nonnull
	List<GenericDomValue<String>> getAdditionalIncludeDirectorieses();

	/**
	 * Adds new child to the list of AdditionalIncludeDirectories children.
	 *
	 * @return created child
	 */
	GenericDomValue<String> addAdditionalIncludeDirectories();


	/**
	 * Returns the list of AdditionalUsingDirectories children.
	 *
	 * @return the list of AdditionalUsingDirectories children.
	 */
	@Nonnull
	List<GenericDomValue<String>> getAdditionalUsingDirectorieses();

	/**
	 * Adds new child to the list of AdditionalUsingDirectories children.
	 *
	 * @return created child
	 */
	GenericDomValue<String> addAdditionalUsingDirectories();


	/**
	 * Returns the list of CompileAsManaged children.
	 *
	 * @return the list of CompileAsManaged children.
	 */
	@Nonnull
	List<CompileAsManaged> getCompileAsManageds();

	/**
	 * Adds new child to the list of CompileAsManaged children.
	 *
	 * @return created child
	 */
	CompileAsManaged addCompileAsManaged();


	/**
	 * Returns the list of ErrorReporting children.
	 *
	 * @return the list of ErrorReporting children.
	 */
	@Nonnull
	List<GenericDomValue<String>> getErrorReportings();

	/**
	 * Adds new child to the list of ErrorReporting children.
	 *
	 * @return created child
	 */
	GenericDomValue<String> addErrorReporting();


	/**
	 * Returns the list of WarningLevel children.
	 *
	 * @return the list of WarningLevel children.
	 */
	@Nonnull
	List<GenericDomValue<String>> getWarningLevels();

	/**
	 * Adds new child to the list of WarningLevel children.
	 *
	 * @return created child
	 */
	GenericDomValue<String> addWarningLevel();


	/**
	 * Returns the list of MinimalRebuild children.
	 *
	 * @return the list of MinimalRebuild children.
	 */
	@Nonnull
	List<GenericDomValue<String>> getMinimalRebuilds();

	/**
	 * Adds new child to the list of MinimalRebuild children.
	 *
	 * @return created child
	 */
	GenericDomValue<String> addMinimalRebuild();


	/**
	 * Returns the list of DebugInformationFormat children.
	 *
	 * @return the list of DebugInformationFormat children.
	 */
	@Nonnull
	List<GenericDomValue<String>> getDebugInformationFormats();

	/**
	 * Adds new child to the list of DebugInformationFormat children.
	 *
	 * @return created child
	 */
	GenericDomValue<String> addDebugInformationFormat();


	/**
	 * Returns the list of PreprocessorDefinitions children.
	 *
	 * @return the list of PreprocessorDefinitions children.
	 */
	@Nonnull
	List<GenericDomValue<String>> getPreprocessorDefinitionses();

	/**
	 * Adds new child to the list of PreprocessorDefinitions children.
	 *
	 * @return created child
	 */
	GenericDomValue<String> addPreprocessorDefinitions();


	/**
	 * Returns the list of Optimization children.
	 *
	 * @return the list of Optimization children.
	 */
	@Nonnull
	List<GenericDomValue<String>> getOptimizations();

	/**
	 * Adds new child to the list of Optimization children.
	 *
	 * @return created child
	 */
	GenericDomValue<String> addOptimization();


	/**
	 * Returns the list of BasicRuntimeChecks children.
	 *
	 * @return the list of BasicRuntimeChecks children.
	 */
	@Nonnull
	List<GenericDomValue<String>> getBasicRuntimeCheckses();

	/**
	 * Adds new child to the list of BasicRuntimeChecks children.
	 *
	 * @return created child
	 */
	GenericDomValue<String> addBasicRuntimeChecks();


	/**
	 * Returns the list of RuntimeLibrary children.
	 *
	 * @return the list of RuntimeLibrary children.
	 */
	@Nonnull
	List<GenericDomValue<String>> getRuntimeLibraries();

	/**
	 * Adds new child to the list of RuntimeLibrary children.
	 *
	 * @return created child
	 */
	GenericDomValue<String> addRuntimeLibrary();


	/**
	 * Returns the list of FunctionLevelLinking children.
	 *
	 * @return the list of FunctionLevelLinking children.
	 */
	@Nonnull
	List<GenericDomValue<String>> getFunctionLevelLinkings();

	/**
	 * Adds new child to the list of FunctionLevelLinking children.
	 *
	 * @return created child
	 */
	GenericDomValue<String> addFunctionLevelLinking();


	/**
	 * Returns the list of FloatingPointModel children.
	 *
	 * @return the list of FloatingPointModel children.
	 */
	@Nonnull
	List<GenericDomValue<String>> getFloatingPointModels();

	/**
	 * Adds new child to the list of FloatingPointModel children.
	 *
	 * @return created child
	 */
	GenericDomValue<String> addFloatingPointModel();


	/**
	 * Returns the list of IntrinsicFunctions children.
	 *
	 * @return the list of IntrinsicFunctions children.
	 */
	@Nonnull
	List<GenericDomValue<String>> getIntrinsicFunctionses();

	/**
	 * Adds new child to the list of IntrinsicFunctions children.
	 *
	 * @return created child
	 */
	GenericDomValue<String> addIntrinsicFunctions();


	/**
	 * Returns the list of PrecompiledHeaderFile children.
	 *
	 * @return the list of PrecompiledHeaderFile children.
	 */
	@Nonnull
	List<GenericDomValue<String>> getPrecompiledHeaderFiles();

	/**
	 * Adds new child to the list of PrecompiledHeaderFile children.
	 *
	 * @return created child
	 */
	GenericDomValue<String> addPrecompiledHeaderFile();


	/**
	 * Returns the list of MultiProcessorCompilation children.
	 *
	 * @return the list of MultiProcessorCompilation children.
	 */
	@Nonnull
	List<GenericDomValue<String>> getMultiProcessorCompilations();

	/**
	 * Adds new child to the list of MultiProcessorCompilation children.
	 *
	 * @return created child
	 */
	GenericDomValue<String> addMultiProcessorCompilation();


	/**
	 * Returns the list of UseUnicodeForAssemblerListing children.
	 *
	 * @return the list of UseUnicodeForAssemblerListing children.
	 */
	@Nonnull
	List<GenericDomValue<String>> getUseUnicodeForAssemblerListings();

	/**
	 * Adds new child to the list of UseUnicodeForAssemblerListing children.
	 *
	 * @return created child
	 */
	GenericDomValue<String> addUseUnicodeForAssemblerListing();


	/**
	 * Returns the list of UndefinePreprocessorDefinitions children.
	 *
	 * @return the list of UndefinePreprocessorDefinitions children.
	 */
	@Nonnull
	List<GenericDomValue<String>> getUndefinePreprocessorDefinitionses();

	/**
	 * Adds new child to the list of UndefinePreprocessorDefinitions children.
	 *
	 * @return created child
	 */
	GenericDomValue<String> addUndefinePreprocessorDefinitions();


	/**
	 * Returns the list of StringPooling children.
	 *
	 * @return the list of StringPooling children.
	 */
	@Nonnull
	List<GenericDomValue<String>> getStringPoolings();

	/**
	 * Adds new child to the list of StringPooling children.
	 *
	 * @return created child
	 */
	GenericDomValue<String> addStringPooling();


	/**
	 * Returns the list of BrowseInformation children.
	 *
	 * @return the list of BrowseInformation children.
	 */
	@Nonnull
	List<GenericDomValue<String>> getBrowseInformations();

	/**
	 * Adds new child to the list of BrowseInformation children.
	 *
	 * @return created child
	 */
	GenericDomValue<String> addBrowseInformation();


	/**
	 * Returns the list of FloatingPointExceptions children.
	 *
	 * @return the list of FloatingPointExceptions children.
	 */
	@Nonnull
	List<GenericDomValue<String>> getFloatingPointExceptionses();

	/**
	 * Adds new child to the list of FloatingPointExceptions children.
	 *
	 * @return created child
	 */
	GenericDomValue<String> addFloatingPointExceptions();


	/**
	 * Returns the list of CreateHotpatchableImage children.
	 *
	 * @return the list of CreateHotpatchableImage children.
	 */
	@Nonnull
	List<GenericDomValue<String>> getCreateHotpatchableImages();

	/**
	 * Adds new child to the list of CreateHotpatchableImage children.
	 *
	 * @return created child
	 */
	GenericDomValue<String> addCreateHotpatchableImage();


	/**
	 * Returns the list of RuntimeTypeInfo children.
	 *
	 * @return the list of RuntimeTypeInfo children.
	 */
	@Nonnull
	List<GenericDomValue<String>> getRuntimeTypeInfos();

	/**
	 * Adds new child to the list of RuntimeTypeInfo children.
	 *
	 * @return created child
	 */
	GenericDomValue<String> addRuntimeTypeInfo();


	/**
	 * Returns the list of OpenMPSupport children.
	 *
	 * @return the list of OpenMPSupport children.
	 */
	@Nonnull
	List<GenericDomValue<String>> getOpenMPSupports();

	/**
	 * Adds new child to the list of OpenMPSupport children.
	 *
	 * @return created child
	 */
	GenericDomValue<String> addOpenMPSupport();


	/**
	 * Returns the list of CallingConvention children.
	 *
	 * @return the list of CallingConvention children.
	 */
	@Nonnull
	List<GenericDomValue<String>> getCallingConventions();

	/**
	 * Adds new child to the list of CallingConvention children.
	 *
	 * @return created child
	 */
	GenericDomValue<String> addCallingConvention();


	/**
	 * Returns the list of DisableSpecificWarnings children.
	 *
	 * @return the list of DisableSpecificWarnings children.
	 */
	@Nonnull
	List<GenericDomValue<String>> getDisableSpecificWarningses();

	/**
	 * Adds new child to the list of DisableSpecificWarnings children.
	 *
	 * @return created child
	 */
	GenericDomValue<String> addDisableSpecificWarnings();


	/**
	 * Returns the list of ForcedIncludeFiles children.
	 *
	 * @return the list of ForcedIncludeFiles children.
	 */
	@Nonnull
	List<GenericDomValue<String>> getForcedIncludeFileses();

	/**
	 * Adds new child to the list of ForcedIncludeFiles children.
	 *
	 * @return created child
	 */
	GenericDomValue<String> addForcedIncludeFiles();


	/**
	 * Returns the list of ForcedUsingFiles children.
	 *
	 * @return the list of ForcedUsingFiles children.
	 */
	@Nonnull
	List<GenericDomValue<String>> getForcedUsingFileses();

	/**
	 * Adds new child to the list of ForcedUsingFiles children.
	 *
	 * @return created child
	 */
	GenericDomValue<String> addForcedUsingFiles();


	/**
	 * Returns the list of ShowIncludes children.
	 *
	 * @return the list of ShowIncludes children.
	 */
	@Nonnull
	List<GenericDomValue<String>> getShowIncludeses();

	/**
	 * Adds new child to the list of ShowIncludes children.
	 *
	 * @return created child
	 */
	GenericDomValue<String> addShowIncludes();


	/**
	 * Returns the list of UseFullPaths children.
	 *
	 * @return the list of UseFullPaths children.
	 */
	@Nonnull
	List<GenericDomValue<String>> getUseFullPathses();

	/**
	 * Adds new child to the list of UseFullPaths children.
	 *
	 * @return created child
	 */
	GenericDomValue<String> addUseFullPaths();


	/**
	 * Returns the list of OmitDefaultLibName children.
	 *
	 * @return the list of OmitDefaultLibName children.
	 */
	@Nonnull
	List<GenericDomValue<String>> getOmitDefaultLibNames();

	/**
	 * Adds new child to the list of OmitDefaultLibName children.
	 *
	 * @return created child
	 */
	GenericDomValue<String> addOmitDefaultLibName();


	/**
	 * Returns the list of TreatSpecificWarningsAsErrors children.
	 *
	 * @return the list of TreatSpecificWarningsAsErrors children.
	 */
	@Nonnull
	List<GenericDomValue<String>> getTreatSpecificWarningsAsErrorses();

	/**
	 * Adds new child to the list of TreatSpecificWarningsAsErrors children.
	 *
	 * @return created child
	 */
	GenericDomValue<String> addTreatSpecificWarningsAsErrors();


}
