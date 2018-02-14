// Generated on Sat Jan 28 04:58:18 MSK 2017
// DTD/Schema  :    http://schemas.microsoft.com/developer/msbuild/2003

package consulo.msbuild.dom;

import java.util.List;

import javax.annotation.Nonnull;

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
	@Nonnull
	GenericAttributeValue<String> getAlgorithmId();


	/**
	 * Returns the value of the BaseAddress child.
	 *
	 * @return the value of the BaseAddress child.
	 */
	@Nonnull
	GenericAttributeValue<String> getBaseAddress();


	/**
	 * Returns the value of the CompanyName child.
	 *
	 * @return the value of the CompanyName child.
	 */
	@Nonnull
	GenericAttributeValue<String> getCompanyName();


	/**
	 * Returns the value of the Configuration child.
	 *
	 * @return the value of the Configuration child.
	 */
	@Nonnull
	GenericAttributeValue<String> getConfiguration();


	/**
	 * Returns the value of the Copyright child.
	 *
	 * @return the value of the Copyright child.
	 */
	@Nonnull
	GenericAttributeValue<String> getCopyright();


	/**
	 * Returns the value of the Culture child.
	 *
	 * @return the value of the Culture child.
	 */
	@Nonnull
	GenericAttributeValue<String> getCulture();


	/**
	 * Returns the value of the DelaySign child.
	 *
	 * @return the value of the DelaySign child.
	 */
	@Nonnull
	GenericAttributeValue<Boolean> getDelaySign();


	/**
	 * Returns the value of the Description child.
	 *
	 * @return the value of the Description child.
	 */
	@Nonnull
	GenericAttributeValue<String> getDescription();


	/**
	 * Returns the value of the EmbedResources child.
	 *
	 * @return the value of the EmbedResources child.
	 */
	@Nonnull
	GenericAttributeValue<String> getEmbedResources();


	/**
	 * Returns the value of the EnvironmentVariables child.
	 *
	 * @return the value of the EnvironmentVariables child.
	 */
	@Nonnull
	GenericAttributeValue<String> getEnvironmentVariables();


	/**
	 * Returns the value of the EvidenceFile child.
	 *
	 * @return the value of the EvidenceFile child.
	 */
	@Nonnull
	GenericAttributeValue<String> getEvidenceFile();


	/**
	 * Returns the value of the FileVersion child.
	 *
	 * @return the value of the FileVersion child.
	 */
	@Nonnull
	GenericAttributeValue<String> getFileVersion();


	/**
	 * Returns the value of the Flags child.
	 *
	 * @return the value of the Flags child.
	 */
	@Nonnull
	GenericAttributeValue<String> getFlags();


	/**
	 * Returns the value of the GenerateFullPaths child.
	 *
	 * @return the value of the GenerateFullPaths child.
	 */
	@Nonnull
	GenericAttributeValue<Boolean> getGenerateFullPaths();


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
	 * Returns the value of the OutputAssembly child.
	 *
	 * @return the value of the OutputAssembly child.
	 */
	@Nonnull
	@Required
	GenericAttributeValue<String> getOutputAssembly();


	/**
	 * Returns the value of the Platform child.
	 *
	 * @return the value of the Platform child.
	 */
	@Nonnull
	GenericAttributeValue<String> getPlatform();


	/**
	 * Returns the value of the ProductName child.
	 *
	 * @return the value of the ProductName child.
	 */
	@Nonnull
	GenericAttributeValue<String> getProductName();


	/**
	 * Returns the value of the ProductVersion child.
	 *
	 * @return the value of the ProductVersion child.
	 */
	@Nonnull
	GenericAttributeValue<String> getProductVersion();


	/**
	 * Returns the value of the ResponseFiles child.
	 *
	 * @return the value of the ResponseFiles child.
	 */
	@Nonnull
	GenericAttributeValue<String> getResponseFiles();


	/**
	 * Returns the value of the SdkToolsPath child.
	 *
	 * @return the value of the SdkToolsPath child.
	 */
	@Nonnull
	GenericAttributeValue<String> getSdkToolsPath();


	/**
	 * Returns the value of the SourceModules child.
	 *
	 * @return the value of the SourceModules child.
	 */
	@Nonnull
	GenericAttributeValue<String> getSourceModules();


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
	 * Returns the value of the TargetType child.
	 *
	 * @return the value of the TargetType child.
	 */
	@Nonnull
	GenericAttributeValue<String> getTargetType();


	/**
	 * Returns the value of the TemplateFile child.
	 *
	 * @return the value of the TemplateFile child.
	 */
	@Nonnull
	GenericAttributeValue<String> getTemplateFile();


	/**
	 * Returns the value of the Timeout child.
	 *
	 * @return the value of the Timeout child.
	 */
	@Nonnull
	GenericAttributeValue<String> getTimeout();


	/**
	 * Returns the value of the Title child.
	 *
	 * @return the value of the Title child.
	 */
	@Nonnull
	GenericAttributeValue<String> getTitle();


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
	 * Returns the value of the Trademark child.
	 *
	 * @return the value of the Trademark child.
	 */
	@Nonnull
	GenericAttributeValue<String> getTrademark();


	/**
	 * Returns the value of the Version child.
	 *
	 * @return the value of the Version child.
	 */
	@Nonnull
	GenericAttributeValue<String> getVersion();


	/**
	 * Returns the value of the Win32Icon child.
	 *
	 * @return the value of the Win32Icon child.
	 */
	@Nonnull
	GenericAttributeValue<String> getWin32Icon();


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
