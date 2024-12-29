// Generated on Sat Jan 28 04:58:19 MSK 2017
// DTD/Schema  :    http://schemas.microsoft.com/developer/msbuild/2003

package consulo.msbuild.dom;

import java.util.List;

import jakarta.annotation.Nonnull;

import consulo.xml.util.xml.DomElement;
import consulo.xml.util.xml.GenericAttributeValue;
import consulo.xml.util.xml.Required;

/**
 * http://schemas.microsoft.com/developer/msbuild/2003:ResolveAssemblyReferenceElemType interface.
 *
 * @author VISTALL
 */
public interface ResolveAssemblyReference extends DomElement, Task
{

	/**
	 * Returns the value of the AllowedAssemblyExtensions child.
	 *
	 * @return the value of the AllowedAssemblyExtensions child.
	 */
	@Nonnull
	GenericAttributeValue<String> getAllowedAssemblyExtensions();


	/**
	 * Returns the value of the AllowedGlobalAssemblyNamePrefix child.
	 *
	 * @return the value of the AllowedGlobalAssemblyNamePrefix child.
	 */
	@Nonnull
	GenericAttributeValue<String> getAllowedGlobalAssemblyNamePrefix();


	/**
	 * Returns the value of the AllowedRelatedFileExtensions child.
	 *
	 * @return the value of the AllowedRelatedFileExtensions child.
	 */
	@Nonnull
	GenericAttributeValue<String> getAllowedRelatedFileExtensions();


	/**
	 * Returns the value of the AppConfigFile child.
	 *
	 * @return the value of the AppConfigFile child.
	 */
	@Nonnull
	GenericAttributeValue<String> getAppConfigFile();


	/**
	 * Returns the value of the Assemblies child.
	 *
	 * @return the value of the Assemblies child.
	 */
	@Nonnull
	GenericAttributeValue<String> getAssemblies();


	/**
	 * Returns the value of the AssemblyFiles child.
	 *
	 * @return the value of the AssemblyFiles child.
	 */
	@Nonnull
	GenericAttributeValue<String> getAssemblyFiles();


	/**
	 * Returns the value of the AutoUnify child.
	 *
	 * @return the value of the AutoUnify child.
	 */
	@Nonnull
	GenericAttributeValue<Boolean> getAutoUnify();


	/**
	 * Returns the value of the CandidateAssemblyFiles child.
	 *
	 * @return the value of the CandidateAssemblyFiles child.
	 */
	@Nonnull
	GenericAttributeValue<String> getCandidateAssemblyFiles();


	/**
	 * Returns the value of the FilesWritten child.
	 *
	 * @return the value of the FilesWritten child.
	 */
	@Nonnull
	GenericAttributeValue<String> getFilesWritten();


	/**
	 * Returns the value of the FindDependencies child.
	 *
	 * @return the value of the FindDependencies child.
	 */
	@Nonnull
	GenericAttributeValue<Boolean> getFindDependencies();


	/**
	 * Returns the value of the FindRelatedFiles child.
	 *
	 * @return the value of the FindRelatedFiles child.
	 */
	@Nonnull
	GenericAttributeValue<Boolean> getFindRelatedFiles();


	/**
	 * Returns the value of the FindSatellites child.
	 *
	 * @return the value of the FindSatellites child.
	 */
	@Nonnull
	GenericAttributeValue<Boolean> getFindSatellites();


	/**
	 * Returns the value of the FindSerializationAssemblies child.
	 *
	 * @return the value of the FindSerializationAssemblies child.
	 */
	@Nonnull
	GenericAttributeValue<Boolean> getFindSerializationAssemblies();


	/**
	 * Returns the value of the FullFrameworkAssemblyTables child.
	 *
	 * @return the value of the FullFrameworkAssemblyTables child.
	 */
	@Nonnull
	GenericAttributeValue<String> getFullFrameworkAssemblyTables();


	/**
	 * Returns the value of the FullFrameworkFolders child.
	 *
	 * @return the value of the FullFrameworkFolders child.
	 */
	@Nonnull
	GenericAttributeValue<String> getFullFrameworkFolders();


	/**
	 * Returns the value of the FullTargetFrameworkSubsetNames child.
	 *
	 * @return the value of the FullTargetFrameworkSubsetNames child.
	 */
	@Nonnull
	GenericAttributeValue<String> getFullTargetFrameworkSubsetNames();


	/**
	 * Returns the value of the IgnoreDefaultInstalledAssemblySubsetTables child.
	 *
	 * @return the value of the IgnoreDefaultInstalledAssemblySubsetTables child.
	 */
	@Nonnull
	GenericAttributeValue<Boolean> getIgnoreDefaultInstalledAssemblySubsetTables();


	/**
	 * Returns the value of the IgnoreDefaultInstalledAssemblyTables child.
	 *
	 * @return the value of the IgnoreDefaultInstalledAssemblyTables child.
	 */
	@Nonnull
	GenericAttributeValue<Boolean> getIgnoreDefaultInstalledAssemblyTables();


	/**
	 * Returns the value of the InstalledAssemblySubsetTables child.
	 *
	 * @return the value of the InstalledAssemblySubsetTables child.
	 */
	@Nonnull
	GenericAttributeValue<String> getInstalledAssemblySubsetTables();


	/**
	 * Returns the value of the InstalledAssemblyTables child.
	 *
	 * @return the value of the InstalledAssemblyTables child.
	 */
	@Nonnull
	GenericAttributeValue<String> getInstalledAssemblyTables();


	/**
	 * Returns the value of the ProfileName child.
	 *
	 * @return the value of the ProfileName child.
	 */
	@Nonnull
	GenericAttributeValue<String> getProfileName();


	/**
	 * Returns the value of the PublicKeysRestrictedForGlobalLocation child.
	 *
	 * @return the value of the PublicKeysRestrictedForGlobalLocation child.
	 */
	@Nonnull
	GenericAttributeValue<String> getPublicKeysRestrictedForGlobalLocation();


	/**
	 * Returns the value of the SearchPaths child.
	 *
	 * @return the value of the SearchPaths child.
	 */
	@Nonnull
	@Required
	GenericAttributeValue<String> getSearchPaths();


	/**
	 * Returns the value of the Silent child.
	 *
	 * @return the value of the Silent child.
	 */
	@Nonnull
	GenericAttributeValue<Boolean> getSilent();


	/**
	 * Returns the value of the StateFile child.
	 *
	 * @return the value of the StateFile child.
	 */
	@Nonnull
	GenericAttributeValue<String> getStateFile();


	/**
	 * Returns the value of the TargetedRuntimeVersion child.
	 *
	 * @return the value of the TargetedRuntimeVersion child.
	 */
	@Nonnull
	GenericAttributeValue<String> getTargetedRuntimeVersion();


	/**
	 * Returns the value of the TargetFrameworkDirectories child.
	 *
	 * @return the value of the TargetFrameworkDirectories child.
	 */
	@Nonnull
	GenericAttributeValue<String> getTargetFrameworkDirectories();


	/**
	 * Returns the value of the TargetFrameworkMoniker child.
	 *
	 * @return the value of the TargetFrameworkMoniker child.
	 */
	@Nonnull
	GenericAttributeValue<String> getTargetFrameworkMoniker();


	/**
	 * Returns the value of the TargetFrameworkMonikerDisplayName child.
	 *
	 * @return the value of the TargetFrameworkMonikerDisplayName child.
	 */
	@Nonnull
	GenericAttributeValue<String> getTargetFrameworkMonikerDisplayName();


	/**
	 * Returns the value of the TargetFrameworkSubsets child.
	 *
	 * @return the value of the TargetFrameworkSubsets child.
	 */
	@Nonnull
	GenericAttributeValue<String> getTargetFrameworkSubsets();


	/**
	 * Returns the value of the TargetFrameworkVersion child.
	 *
	 * @return the value of the TargetFrameworkVersion child.
	 */
	@Nonnull
	GenericAttributeValue<String> getTargetFrameworkVersion();


	/**
	 * Returns the value of the TargetProcessorArchitecture child.
	 *
	 * @return the value of the TargetProcessorArchitecture child.
	 */
	@Nonnull
	GenericAttributeValue<String> getTargetProcessorArchitecture();


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
