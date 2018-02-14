// Generated on Sat Jan 28 04:58:19 MSK 2017
// DTD/Schema  :    http://schemas.microsoft.com/developer/msbuild/2003

package consulo.msbuild.dom;

import java.util.List;

import javax.annotation.Nonnull;

import com.intellij.util.xml.DomElement;
import com.intellij.util.xml.GenericAttributeValue;

/**
 * http://schemas.microsoft.com/developer/msbuild/2003:GenerateBootstrapperElemType interface.
 *
 * @author VISTALL
 */
public interface GenerateBootstrapper extends DomElement, Task
{

	/**
	 * Returns the value of the ApplicationFile child.
	 *
	 * @return the value of the ApplicationFile child.
	 */
	@Nonnull
	GenericAttributeValue<String> getApplicationFile();


	/**
	 * Returns the value of the ApplicationName child.
	 *
	 * @return the value of the ApplicationName child.
	 */
	@Nonnull
	GenericAttributeValue<String> getApplicationName();


	/**
	 * Returns the value of the ApplicationRequiresElevation child.
	 *
	 * @return the value of the ApplicationRequiresElevation child.
	 */
	@Nonnull
	GenericAttributeValue<Boolean> getApplicationRequiresElevation();


	/**
	 * Returns the value of the ApplicationUrl child.
	 *
	 * @return the value of the ApplicationUrl child.
	 */
	@Nonnull
	GenericAttributeValue<String> getApplicationUrl();


	/**
	 * Returns the value of the BootstrapperComponentFiles child.
	 *
	 * @return the value of the BootstrapperComponentFiles child.
	 */
	@Nonnull
	GenericAttributeValue<String> getBootstrapperComponentFiles();


	/**
	 * Returns the value of the BootstrapperItems child.
	 *
	 * @return the value of the BootstrapperItems child.
	 */
	@Nonnull
	GenericAttributeValue<String> getBootstrapperItems();


	/**
	 * Returns the value of the BootstrapperKeyFile child.
	 *
	 * @return the value of the BootstrapperKeyFile child.
	 */
	@Nonnull
	GenericAttributeValue<String> getBootstrapperKeyFile();


	/**
	 * Returns the value of the ComponentsLocation child.
	 *
	 * @return the value of the ComponentsLocation child.
	 */
	@Nonnull
	GenericAttributeValue<String> getComponentsLocation();


	/**
	 * Returns the value of the ComponentsUrl child.
	 *
	 * @return the value of the ComponentsUrl child.
	 */
	@Nonnull
	GenericAttributeValue<String> getComponentsUrl();


	/**
	 * Returns the value of the CopyComponents child.
	 *
	 * @return the value of the CopyComponents child.
	 */
	@Nonnull
	GenericAttributeValue<Boolean> getCopyComponents();


	/**
	 * Returns the value of the Culture child.
	 *
	 * @return the value of the Culture child.
	 */
	@Nonnull
	GenericAttributeValue<String> getCulture();


	/**
	 * Returns the value of the FallbackCulture child.
	 *
	 * @return the value of the FallbackCulture child.
	 */
	@Nonnull
	GenericAttributeValue<String> getFallbackCulture();


	/**
	 * Returns the value of the OutputPath child.
	 *
	 * @return the value of the OutputPath child.
	 */
	@Nonnull
	GenericAttributeValue<String> getOutputPath();


	/**
	 * Returns the value of the Path child.
	 *
	 * @return the value of the Path child.
	 */
	@Nonnull
	GenericAttributeValue<String> getPath();


	/**
	 * Returns the value of the SupportUrl child.
	 *
	 * @return the value of the SupportUrl child.
	 */
	@Nonnull
	GenericAttributeValue<String> getSupportUrl();


	/**
	 * Returns the value of the Validate child.
	 *
	 * @return the value of the Validate child.
	 */
	@Nonnull
	GenericAttributeValue<Boolean> getValidate();


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
