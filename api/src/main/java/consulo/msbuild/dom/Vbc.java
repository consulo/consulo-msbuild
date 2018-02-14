// Generated on Sat Jan 28 04:58:20 MSK 2017
// DTD/Schema  :    http://schemas.microsoft.com/developer/msbuild/2003

package consulo.msbuild.dom;

import java.util.List;

import javax.annotation.Nonnull;

import com.intellij.util.xml.DomElement;
import com.intellij.util.xml.GenericAttributeValue;

/**
 * http://schemas.microsoft.com/developer/msbuild/2003:VbcElemType interface.
 *
 * @author VISTALL
 */
public interface Vbc extends DomElement, Task
{

	/**
	 * Returns the value of the AdditionalLibPaths child.
	 *
	 * @return the value of the AdditionalLibPaths child.
	 */
	@Nonnull
	GenericAttributeValue<String> getAdditionalLibPaths();


	/**
	 * Returns the value of the AddModules child.
	 *
	 * @return the value of the AddModules child.
	 */
	@Nonnull
	GenericAttributeValue<String> getAddModules();


	/**
	 * Returns the value of the BaseAddress child.
	 *
	 * @return the value of the BaseAddress child.
	 */
	@Nonnull
	GenericAttributeValue<String> getBaseAddress();


	/**
	 * Returns the value of the CodePage child.
	 *
	 * @return the value of the CodePage child.
	 */
	@Nonnull
	GenericAttributeValue<String> getCodePage();


	/**
	 * Returns the value of the DebugType child.
	 *
	 * @return the value of the DebugType child.
	 */
	@Nonnull
	GenericAttributeValue<String> getDebugType();


	/**
	 * Returns the value of the DefineConstants child.
	 *
	 * @return the value of the DefineConstants child.
	 */
	@Nonnull
	GenericAttributeValue<String> getDefineConstants();


	/**
	 * Returns the value of the DelaySign child.
	 *
	 * @return the value of the DelaySign child.
	 */
	@Nonnull
	GenericAttributeValue<Boolean> getDelaySign();


	/**
	 * Returns the value of the DisabledWarnings child.
	 *
	 * @return the value of the DisabledWarnings child.
	 */
	@Nonnull
	GenericAttributeValue<String> getDisabledWarnings();


	/**
	 * Returns the value of the DocumentationFile child.
	 *
	 * @return the value of the DocumentationFile child.
	 */
	@Nonnull
	GenericAttributeValue<String> getDocumentationFile();


	/**
	 * Returns the value of the EmitDebugInformation child.
	 *
	 * @return the value of the EmitDebugInformation child.
	 */
	@Nonnull
	GenericAttributeValue<Boolean> getEmitDebugInformation();


	/**
	 * Returns the value of the EnvironmentVariables child.
	 *
	 * @return the value of the EnvironmentVariables child.
	 */
	@Nonnull
	GenericAttributeValue<String> getEnvironmentVariables();


	/**
	 * Returns the value of the ErrorReport child.
	 *
	 * @return the value of the ErrorReport child.
	 */
	@Nonnull
	GenericAttributeValue<String> getErrorReport();


	/**
	 * Returns the value of the FileAlignment child.
	 *
	 * @return the value of the FileAlignment child.
	 */
	@Nonnull
	GenericAttributeValue<String> getFileAlignment();


	/**
	 * Returns the value of the GenerateDocumentation child.
	 *
	 * @return the value of the GenerateDocumentation child.
	 */
	@Nonnull
	GenericAttributeValue<Boolean> getGenerateDocumentation();


	/**
	 * Returns the value of the Imports child.
	 *
	 * @return the value of the Imports child.
	 */
	@Nonnull
	GenericAttributeValue<String> getImports();


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
	 * Returns the value of the LangVersion child.
	 *
	 * @return the value of the LangVersion child.
	 */
	@Nonnull
	GenericAttributeValue<String> getLangVersion();


	/**
	 * Returns the value of the VBRuntime child.
	 *
	 * @return the value of the VBRuntime child.
	 */
	@Nonnull
	GenericAttributeValue<String> getVBRuntime();


	/**
	 * Returns the value of the LinkResources child.
	 *
	 * @return the value of the LinkResources child.
	 */
	@Nonnull
	GenericAttributeValue<String> getLinkResources();


	/**
	 * Returns the value of the LogStandardErrorAsError child.
	 *
	 * @return the value of the LogStandardErrorAsError child.
	 */
	@Nonnull
	GenericAttributeValue<Boolean> getLogStandardErrorAsError();


	/**
	 * Returns the value of the MainEntryPoint child.
	 *
	 * @return the value of the MainEntryPoint child.
	 */
	@Nonnull
	GenericAttributeValue<String> getMainEntryPoint();


	/**
	 * Returns the value of the ModuleAssemblyName child.
	 *
	 * @return the value of the ModuleAssemblyName child.
	 */
	@Nonnull
	GenericAttributeValue<String> getModuleAssemblyName();


	/**
	 * Returns the value of the NoConfig child.
	 *
	 * @return the value of the NoConfig child.
	 */
	@Nonnull
	GenericAttributeValue<Boolean> getNoConfig();


	/**
	 * Returns the value of the NoLogo child.
	 *
	 * @return the value of the NoLogo child.
	 */
	@Nonnull
	GenericAttributeValue<Boolean> getNoLogo();


	/**
	 * Returns the value of the NoStandardLib child.
	 *
	 * @return the value of the NoStandardLib child.
	 */
	@Nonnull
	GenericAttributeValue<Boolean> getNoStandardLib();


	/**
	 * Returns the value of the NoVBRuntimeReference child.
	 *
	 * @return the value of the NoVBRuntimeReference child.
	 */
	@Nonnull
	GenericAttributeValue<Boolean> getNoVBRuntimeReference();


	/**
	 * Returns the value of the NoWarnings child.
	 *
	 * @return the value of the NoWarnings child.
	 */
	@Nonnull
	GenericAttributeValue<Boolean> getNoWarnings();


	/**
	 * Returns the value of the NoWin32Manifest child.
	 *
	 * @return the value of the NoWin32Manifest child.
	 */
	@Nonnull
	GenericAttributeValue<Boolean> getNoWin32Manifest();


	/**
	 * Returns the value of the Optimize child.
	 *
	 * @return the value of the Optimize child.
	 */
	@Nonnull
	GenericAttributeValue<Boolean> getOptimize();


	/**
	 * Returns the value of the OptionCompare child.
	 *
	 * @return the value of the OptionCompare child.
	 */
	@Nonnull
	GenericAttributeValue<String> getOptionCompare();


	/**
	 * Returns the value of the OptionExplicit child.
	 *
	 * @return the value of the OptionExplicit child.
	 */
	@Nonnull
	GenericAttributeValue<Boolean> getOptionExplicit();


	/**
	 * Returns the value of the OptionInfer child.
	 *
	 * @return the value of the OptionInfer child.
	 */
	@Nonnull
	GenericAttributeValue<Boolean> getOptionInfer();


	/**
	 * Returns the value of the OptionStrict child.
	 *
	 * @return the value of the OptionStrict child.
	 */
	@Nonnull
	GenericAttributeValue<Boolean> getOptionStrict();


	/**
	 * Returns the value of the OptionStrictType child.
	 *
	 * @return the value of the OptionStrictType child.
	 */
	@Nonnull
	GenericAttributeValue<String> getOptionStrictType();


	/**
	 * Returns the value of the OutputAssembly child.
	 *
	 * @return the value of the OutputAssembly child.
	 */
	@Nonnull
	GenericAttributeValue<String> getOutputAssembly();


	/**
	 * Returns the value of the Platform child.
	 *
	 * @return the value of the Platform child.
	 */
	@Nonnull
	GenericAttributeValue<String> getPlatform();


	/**
	 * Returns the value of the References child.
	 *
	 * @return the value of the References child.
	 */
	@Nonnull
	GenericAttributeValue<String> getReferences();


	/**
	 * Returns the value of the RemoveIntegerChecks child.
	 *
	 * @return the value of the RemoveIntegerChecks child.
	 */
	@Nonnull
	GenericAttributeValue<Boolean> getRemoveIntegerChecks();


	/**
	 * Returns the value of the Resources child.
	 *
	 * @return the value of the Resources child.
	 */
	@Nonnull
	GenericAttributeValue<String> getResources();


	/**
	 * Returns the value of the ResponseFiles child.
	 *
	 * @return the value of the ResponseFiles child.
	 */
	@Nonnull
	GenericAttributeValue<String> getResponseFiles();


	/**
	 * Returns the value of the RootNamespace child.
	 *
	 * @return the value of the RootNamespace child.
	 */
	@Nonnull
	GenericAttributeValue<String> getRootNamespace();


	/**
	 * Returns the value of the SdkPath child.
	 *
	 * @return the value of the SdkPath child.
	 */
	@Nonnull
	GenericAttributeValue<String> getSdkPath();


	/**
	 * Returns the value of the Sources child.
	 *
	 * @return the value of the Sources child.
	 */
	@Nonnull
	GenericAttributeValue<String> getSources();


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
	 * Returns the value of the TargetCompactFramework child.
	 *
	 * @return the value of the TargetCompactFramework child.
	 */
	@Nonnull
	GenericAttributeValue<Boolean> getTargetCompactFramework();


	/**
	 * Returns the value of the TargetType child.
	 *
	 * @return the value of the TargetType child.
	 */
	@Nonnull
	GenericAttributeValue<String> getTargetType();


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
	 * Returns the value of the TreatWarningsAsErrors child.
	 *
	 * @return the value of the TreatWarningsAsErrors child.
	 */
	@Nonnull
	GenericAttributeValue<Boolean> getTreatWarningsAsErrors();


	/**
	 * Returns the value of the UseHostCompilerIfAvailable child.
	 *
	 * @return the value of the UseHostCompilerIfAvailable child.
	 */
	@Nonnull
	GenericAttributeValue<Boolean> getUseHostCompilerIfAvailable();


	/**
	 * Returns the value of the Utf8Output child.
	 *
	 * @return the value of the Utf8Output child.
	 */
	@Nonnull
	GenericAttributeValue<Boolean> getUtf8Output();


	/**
	 * Returns the value of the Verbosity child.
	 *
	 * @return the value of the Verbosity child.
	 */
	@Nonnull
	GenericAttributeValue<String> getVerbosity();


	/**
	 * Returns the value of the WarningsAsErrors child.
	 *
	 * @return the value of the WarningsAsErrors child.
	 */
	@Nonnull
	GenericAttributeValue<String> getWarningsAsErrors();


	/**
	 * Returns the value of the WarningsNotAsErrors child.
	 *
	 * @return the value of the WarningsNotAsErrors child.
	 */
	@Nonnull
	GenericAttributeValue<String> getWarningsNotAsErrors();


	/**
	 * Returns the value of the Win32Icon child.
	 *
	 * @return the value of the Win32Icon child.
	 */
	@Nonnull
	GenericAttributeValue<String> getWin32Icon();


	/**
	 * Returns the value of the Win32Manifest child.
	 *
	 * @return the value of the Win32Manifest child.
	 */
	@Nonnull
	GenericAttributeValue<String> getWin32Manifest();


	/**
	 * Returns the value of the Win32Resource child.
	 *
	 * @return the value of the Win32Resource child.
	 */
	@Nonnull
	GenericAttributeValue<String> getWin32Resource();


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
