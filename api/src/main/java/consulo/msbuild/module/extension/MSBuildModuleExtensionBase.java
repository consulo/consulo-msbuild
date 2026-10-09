package consulo.msbuild.module.extension;

import consulo.annotation.access.RequiredReadAction;
import consulo.localize.LocalizeValue;
import consulo.module.content.layer.ModuleRootLayer;
import consulo.module.content.layer.extension.ModuleExtensionBase;
import consulo.msbuild.localize.MSBuildLocalize;
import consulo.ui.Label;
import consulo.ui.annotation.RequiredUIAccess;
import consulo.ui.util.FormBuilder;
import consulo.virtualFileSystem.util.VirtualFileUtil;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import org.jdom.Element;

import java.util.Objects;

/**
 * @author VISTALL
 * @since 2026-10-10
 */
public abstract class MSBuildModuleExtensionBase<T extends MSBuildModuleExtensionBase<T>> extends ModuleExtensionBase<T> {
    private static final String ATTR_PROJECT_FILE = "project-file";
    private static final String ATTR_CONFIGURATION = "configuration";
    private static final String ATTR_PLATFORM = "platform";
    private static final String ATTR_OUTPUT = "output";
    private static final String ATTR_EXECUTABLE = "executable";

    @Nullable
    protected String myProjectFilePath;
    @Nullable
    protected String myConfiguration;
    @Nullable
    protected String myPlatform;
    @Nullable
    protected String myOutputPath;
    @Nullable
    protected String myExecutablePath;

    protected MSBuildModuleExtensionBase(@Nonnull String id, @Nonnull ModuleRootLayer rootLayer) {
        super(id, rootLayer);
    }

    @Nullable
    public String getProjectFilePath() {
        return myProjectFilePath;
    }

    public void setProjectFilePath(@Nullable String projectFilePath) {
        myProjectFilePath = projectFilePath;
    }

    @Nullable
    public String getConfiguration() {
        return myConfiguration;
    }

    public void setConfiguration(@Nullable String configuration) {
        myConfiguration = configuration;
    }

    @Nullable
    public String getPlatform() {
        return myPlatform;
    }

    public void setPlatform(@Nullable String platform) {
        myPlatform = platform;
    }

    @Nullable
    public String getOutputPath() {
        return myOutputPath;
    }

    public void setOutputPath(@Nullable String outputPath) {
        myOutputPath = outputPath;
    }

    @Nullable
    public String getExecutablePath() {
        return myExecutablePath;
    }

    public void setExecutablePath(@Nullable String executablePath) {
        myExecutablePath = executablePath;
    }

    @RequiredReadAction
    @Override
    public void commit(@Nonnull T mutableModuleExtension) {
        super.commit(mutableModuleExtension);
        myProjectFilePath = mutableModuleExtension.getProjectFilePath();
        myConfiguration = mutableModuleExtension.getConfiguration();
        myPlatform = mutableModuleExtension.getPlatform();
        myOutputPath = mutableModuleExtension.getOutputPath();
        myExecutablePath = mutableModuleExtension.getExecutablePath();
    }

    protected boolean isModifiedImpl(@Nonnull T originExtension) {
        return myIsEnabled != originExtension.isEnabled()
            || !Objects.equals(myProjectFilePath, originExtension.getProjectFilePath())
            || !Objects.equals(myConfiguration, originExtension.getConfiguration())
            || !Objects.equals(myPlatform, originExtension.getPlatform())
            || !Objects.equals(myOutputPath, originExtension.getOutputPath())
            || !Objects.equals(myExecutablePath, originExtension.getExecutablePath());
    }

    @Override
    protected void getStateImpl(@Nonnull Element element) {
        super.getStateImpl(element);
        setPathAttribute(element, ATTR_PROJECT_FILE, myProjectFilePath);
        setAttribute(element, ATTR_CONFIGURATION, myConfiguration);
        setAttribute(element, ATTR_PLATFORM, myPlatform);
        setPathAttribute(element, ATTR_OUTPUT, myOutputPath);
        setPathAttribute(element, ATTR_EXECUTABLE, myExecutablePath);
    }

    @RequiredReadAction
    @Override
    protected void loadStateImpl(@Nonnull Element element) {
        super.loadStateImpl(element);
        myProjectFilePath = getPathAttribute(element, ATTR_PROJECT_FILE);
        myConfiguration = element.getAttributeValue(ATTR_CONFIGURATION);
        myPlatform = element.getAttributeValue(ATTR_PLATFORM);
        myOutputPath = getPathAttribute(element, ATTR_OUTPUT);
        myExecutablePath = getPathAttribute(element, ATTR_EXECUTABLE);
    }

    @Nonnull
    @RequiredUIAccess
    protected FormBuilder createProjectFormBuilder() {
        String configuration = myConfiguration != null ? myConfiguration : "";
        String platform = myPlatform != null ? myPlatform : "";
        return FormBuilder.create()
            .addLabeled(MSBuildLocalize.moduleExtensionProjectFileLabel(), Label.create(LocalizeValue.of(valueOrEmpty(myProjectFilePath))))
            .addLabeled(MSBuildLocalize.moduleExtensionConfigurationLabel(), Label.create(LocalizeValue.of(configuration + "|" + platform)))
            .addLabeled(MSBuildLocalize.moduleExtensionExecutableLabel(), Label.create(LocalizeValue.of(valueOrEmpty(myExecutablePath))));
    }

    @Nonnull
    protected static String valueOrEmpty(@Nullable String value) {
        return value != null ? value : "";
    }

    protected static void setAttribute(@Nonnull Element element, @Nonnull String name, @Nullable String value) {
        if (value != null) {
            element.setAttribute(name, value);
        }
    }

    protected static void setPathAttribute(@Nonnull Element element, @Nonnull String name, @Nullable String path) {
        if (path != null) {
            element.setAttribute(name, VirtualFileUtil.pathToUrl(path));
        }
    }

    @Nullable
    protected static String getPathAttribute(@Nonnull Element element, @Nonnull String name) {
        String url = element.getAttributeValue(name);
        return url != null ? VirtualFileUtil.urlToPath(url) : null;
    }
}
