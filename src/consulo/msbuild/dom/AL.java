// Generated on Sat Jan 28 04:58:18 MSK 2017
// DTD/Schema  :    http://schemas.microsoft.com/developer/msbuild/2003

package consulo.msbuild.dom;

import java.util.List;

import org.jetbrains.annotations.NotNull;
import com.intellij.util.xml.DomElement;
import com.intellij.util.xml.GenericAttributeValue;
import com.intellij.util.xml.Required;

/**
 * http://schemas.microsoft.com/developer/msbuild/2003:ALElemType interface.
 *
 * @author VISTALL
 */
public interface AL extends DomElement, Task
{

	/**
	 * Returns the value of the AlgorithmId child.
	 *
	 * @return the value of the AlgorithmId child.
	 */
	@NotNull
	GenericAttributeValue<String> getAlgorithmId();


	/**
	 * Returns the value of the BaseAddress child.
	 *
	 * @return the value of the BaseAddress child.
	 */
	@NotNull
	GenericAttributeValue<String> getBaseAddress();


	/**
	 * Returns the value of the CompanyName child.
	 *
	 * @return the value of the CompanyName child.
	 */
	@NotNull
	GenericAttributeValue<String> getCompanyName();


	/**
	 * Returns the value of the Configuration child.
	 *
	 * @return the value of the Configuration child.
	 */
	@NotNull
	GenericAttributeValue<String> getConfiguration();


	/**
	 * Returns the value of the Copyright child.
	 *
	 * @return the value of the Copyright child.
	 */
	@NotNull
	GenericAttributeValue<String> getCopyright();


	/**
	 * Returns the value of the Culture child.
	 *
	 * @return the value of the Culture child.
	 */
	@NotNull
	GenericAttributeValue<String> getCulture();


	/**
	 * Returns the value of the DelaySign child.
	 *
	 * @return the value of the DelaySign child.
	 */
	@NotNull
	GenericAttributeValue<Boolean> getDelaySign();


	/**
	 * Returns the value of the Description child.
	 *
	 * @return the value of the Description child.
	 */
	@NotNull
	GenericAttributeValue<String> getDescription();


	/**
	 * Returns the value of the EmbedResources child.
	 *
	 * @return the value of the EmbedResources child.
	 */
	@NotNull
	GenericAttributeValue<String> getEmbedResources();


	/**
	 * Returns the value of the EnvironmentVariables child.
	 *
	 * @return the value of the EnvironmentVariables child.
	 */
	@NotNull
	GenericAttributeValue<String> getEnvironmentVariables();


	/**
	 * Returns the value of the EvidenceFile child.
	 *
	 * @return the value of the EvidenceFile child.
	 */
	@NotNull
	GenericAttributeValue<String> getEvidenceFile();


	/**
	 * Returns the value of the FileVersion child.
	 *
	 * @return the value of the FileVersion child.
	 */
	@NotNull
	GenericAttributeValue<String> getFileVersion();


	/**
	 * Returns the value of the Flags child.
	 *
	 * @return the value of the Flags child.
	 */
	@NotNull
	GenericAttributeValue<String> getFlags();


	/**
	 * Returns the value of the GenerateFullPaths child.
	 *
	 * @return the value of the GenerateFullPaths child.
	 */
	@NotNull
	GenericAttributeValue<Boolean> getGenerateFullPaths();


	/**
	 * Returns the value of the KeyContainer child.
	 *
	 * @return the value of the KeyContainer child.
	 */
	@NotNull
	GenericAttributeValue<String> getKeyContainer();


	/**
	 * Returns the value of the KeyFile child.
	 *
	 * @return the value of the KeyFile child.
	 */
	@NotNull
	GenericAttributeValue<String> getKeyFile();


	/**
	 * Returns the value of the LinkResources child.
	 *
	 * @return the value of the LinkResources child.
	 */
	@NotNull
	GenericAttributeValue<String> getLinkResources();


	/**
	 * Returns the value of the LogStandardErrorAsError child.
	 *
	 * @return the value of the LogStandardErrorAsError child.
	 */
	@NotNull
	GenericAttributeValue<Boolean> getLogStandardErrorAsError();


	/**
	 * Returns the value of the MainEntryPoint child.
	 *
	 * @return the value of the MainEntryPoint child.
	 */
	@NotNull
	GenericAttributeValue<String> getMainEntryPoint();


	/**
	 * Returns the value of the OutputAssembly child.
	 *
	 * @return the value of the OutputAssembly child.
	 */
	@NotNull
	@Required
	GenericAttributeValue<String> getOutputAssembly();


	/**
	 * Returns the value of the Platform child.
	 *
	 * @return the value of the Platform child.
	 */
	@NotNull
	GenericAttributeValue<String> getPlatform();


	/**
	 * Returns the value of the ProductName child.
	 *
	 * @return the value of the ProductName child.
	 */
	@NotNull
	GenericAttributeValue<String> getProductName();


	/**
	 * Returns the value of the ProductVersion child.
	 *
	 * @return the value of the ProductVersion child.
	 */
	@NotNull
	GenericAttributeValue<String> getProductVersion();


	/**
	 * Returns the value of the ResponseFiles child.
	 *
	 * @return the value of the ResponseFiles child.
	 */
	@NotNull
	GenericAttributeValue<String> getResponseFiles();


	/**
	 * Returns the value of the SdkToolsPath child.
	 *
	 * @return the value of the SdkToolsPath child.
	 */
	@NotNull
	GenericAttributeValue<String> getSdkToolsPath();


	/**
	 * Returns the value of the SourceModules child.
	 *
	 * @return the value of the SourceModules child.
	 */
	@NotNull
	GenericAttributeValue<String> getSourceModules();


	/**
	 * Returns the value of the StandardErrorImportance child.
	 *
	 * @return the value of the StandardErrorImportance child.
	 */
	@NotNull
	GenericAttributeValue<String> getStandardErrorImportance();


	/**
	 * Returns the value of the StandardOutputImportance child.
	 *
	 * @return the value of the StandardOutputImportance child.
	 */
	@NotNull
	GenericAttributeValue<String> getStandardOutputImportance();


	/**
	 * Returns the value of the TargetType child.
	 *
	 * @return the value of the TargetType child.
	 */
	@NotNull
	GenericAttributeValue<String> getTargetType();


	/**
	 * Returns the value of the TemplateFile child.
	 *
	 * @return the value of the TemplateFile child.
	 */
	@NotNull
	GenericAttributeValue<String> getTemplateFile();


	/**
	 * Returns the value of the Timeout child.
	 *
	 * @return the value of the Timeout child.
	 */
	@NotNull
	GenericAttributeValue<String> getTimeout();


	/**
	 * Returns the value of the Title child.
	 *
	 * @return the value of the Title child.
	 */
	@NotNull
	GenericAttributeValue<String> getTitle();


	/**
	 * Returns the value of the ToolExe child.
	 *
	 * @return the value of the ToolExe child.
	 */
	@NotNull
	GenericAttributeValue<String> getToolExe();


	/**
	 * Returns the value of the ToolPath child.
	 *
	 * @return the value of the ToolPath child.
	 */
	@NotNull
	GenericAttributeValue<String> getToolPath();


	/**
	 * Returns the value of the Trademark child.
	 *
	 * @return the value of the Trademark child.
	 */
	@NotNull
	GenericAttributeValue<String> getTrademark();


	/**
	 * Returns the value of the Version child.
	 *
	 * @return the value of the Version child.
	 */
	@NotNull
	GenericAttributeValue<String> getVersion();


	/**
	 * Returns the value of the Win32Icon child.
	 *
	 * @return the value of the Win32Icon child.
	 */
	@NotNull
	GenericAttributeValue<String> getWin32Icon();


	/**
	 * Returns the value of the Win32Resource child.
	 *
	 * @return the value of the Win32Resource child.
	 */
	@NotNull
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
	@NotNull
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
	@NotNull
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
	@NotNull
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
	@NotNull
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
	@NotNull
	List<Output> getOutputs();

	/**
	 * Adds new child to the list of Output children.
	 *
	 * @return created child
	 */
	Output addOutput();


}