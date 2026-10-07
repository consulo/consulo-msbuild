package consulo.msbuild.daemon.impl.step;

import consulo.application.Application;
import consulo.msbuild.MSBuildProjectFile;
import consulo.msbuild.daemon.impl.MSBuildDaemonContext;
import consulo.msbuild.daemon.impl.message.model.MSBuildEvaluatedItem;
import consulo.msbuild.daemon.impl.message.model.RunProjectResponse;
import consulo.msbuild.solution.model.WProject;
import consulo.virtualFileSystem.VirtualFile;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * @author VISTALL
 * @since 20/01/2021
 * <p>
 * Replacement of AnalyzeOldProjectItemsStep - it's load also metadata, which required for bulding solution view
 */
public class AnalyzeProjectItemsStep extends BaseRunProjectStep {
    public static final String[] ITEMS = {"None", "Compile", "EmbeddedResource", "Resource", "Item"};

    private final String[] myItemTypes;

    public AnalyzeProjectItemsStep(WProject wProject) {
        this(wProject, getItemTypes(Application.get(), wProject.getVirtualFile()));
    }

    private AnalyzeProjectItemsStep(WProject wProject, String[] itemTypes) {
        super(wProject, itemTypes, new String[]{"_GenerateRestoreProjectSpec"});
        myItemTypes = itemTypes;
    }

    @Nonnull
    public static String[] getItemTypes(@Nonnull Application application, @Nullable VirtualFile projectFile) {
        MSBuildProjectFile projectFileKind = projectFile == null ? null : MSBuildProjectFile.findByExtension(application, projectFile.getExtension());
        if (projectFileKind == null || projectFileKind.getItemTypes().isEmpty()) {
            return ITEMS;
        }

        Set<String> itemTypes = new LinkedHashSet<>(Arrays.asList(ITEMS));
        itemTypes.addAll(projectFileKind.getItemTypes());
        return itemTypes.toArray(String[]::new);
    }

    @Nonnull
    @Override
    public String getProjectStepText() {
        return "Analyzing ''{0}'' Project Items";
    }

    @Override
    public void handleResponse(@Nonnull MSBuildDaemonContext context, @Nonnull RunProjectResponse runProjectResponse) {
        for (String item : myItemTypes) {
            MSBuildEvaluatedItem[] items = runProjectResponse.Result.items.get(item);
            if (items == null) {
                continue;
            }

            context.addProjectItems(myWProject, items);
        }
    }
}
