package com.dci.intellij.dbn.editor.data.ui;

import com.dci.intellij.dbn.common.environment.EnvironmentManager;
import com.dci.intellij.dbn.common.message.MessageType;
import com.dci.intellij.dbn.common.util.MessageUtil;
import com.dci.intellij.dbn.editor.DBContentType;
import com.dci.intellij.dbn.object.common.DBSchemaObject;
import com.dci.intellij.dbn.options.ConfigId;
import com.dci.intellij.dbn.options.ProjectSettingsManager;
import com.intellij.openapi.project.Project;

import static com.dci.intellij.dbn.common.message.MessageCallback.conditional;

public class DatasetEditorReadonlyNotificationPanel extends DatasetEditorNotificationPanel{
    public DatasetEditorReadonlyNotificationPanel(final DBSchemaObject schemaObject) {
        super(isReadonly(schemaObject) ? MessageType.INFO : MessageType.WARNING);
        String environmentName = schemaObject.getEnvironmentType().getName();
        final Project project = schemaObject.getProject();

        if (isReadonly(schemaObject)) {
            setText("Readonly data - This editor is readonly to prevent accidental data changes in \"" + environmentName + "\" environments (check environment settings)");
            createActionLabel("Edit Mode", () -> MessageUtil.showQuestionDialog(project,
                    "Enable edit-mode",
                    "Are you sure you want to enable editing for " + schemaObject.getQualifiedNameWithType(),
                    new String[]{"Yes", "Cancel"}, 0,
                    (option) -> conditional(option == 0,
                            () -> {
                                EnvironmentManager environmentManager = EnvironmentManager.getInstance(project);
                                environmentManager.enableEditing(schemaObject, DBContentType.DATA);
                            })));
        } else {
            setText("Active edit-mode! (the environment \"" + environmentName + "\" is configured with readonly data to prevent accidental changes)");
            createActionLabel("Cancel Editing", () -> {
                EnvironmentManager environmentManager = EnvironmentManager.getInstance(project);
                environmentManager.disableEditing(schemaObject, DBContentType.DATA);
            });
        }

        createActionLabel("Settings", () -> {
            ProjectSettingsManager settingsManager = ProjectSettingsManager.getInstance(project);
            settingsManager.openProjectSettings(ConfigId.GENERAL);
        });
    }

    private static boolean isReadonly(DBSchemaObject schemaObject) {
        Project project = schemaObject.getProject();
        EnvironmentManager environmentManager = EnvironmentManager.getInstance(project);
        return environmentManager.isReadonly(schemaObject, DBContentType.DATA);
    }
}
