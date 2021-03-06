package com.dci.intellij.dbn.common.action;

import com.intellij.ide.DataManager;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.DataContext;
import com.intellij.openapi.actionSystem.DataProvider;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.fileEditor.FileEditor;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.awt.*;

public interface Lookup {
    @Nullable
    static Project getProject(AnActionEvent e) {
        return e.getData(PlatformDataKeys.PROJECT);
    }

    @Nullable
    static VirtualFile getVirtualFile(@NotNull AnActionEvent e) {
        return e.getData(PlatformDataKeys.VIRTUAL_FILE);
    }

    @Nullable
    static VirtualFile getVirtualFile(@NotNull Component component) {
        DataContext dataContext = getDataContext(component);
        return PlatformDataKeys.VIRTUAL_FILE.getData(dataContext);
    }

    @Nullable
    static Editor getEditor(@NotNull AnActionEvent e) {
        return e.getData(PlatformDataKeys.EDITOR);
    }

    @Nullable
    static FileEditor getFileEditor(@NotNull AnActionEvent e) {
        return e.getData(PlatformDataKeys.FILE_EDITOR);
    }

    /**
     * @Deprecated use getProject(Component)
     */
    static Project getProject() {
        DataContext dataContext = DataManager.getInstance().getDataContextFromFocus().getResult();
        return PlatformDataKeys.PROJECT.getData(dataContext);
    }

    static Project getProject(Component component) {
        DataContext dataContext = getDataContext(component);
        return PlatformDataKeys.PROJECT.getData(dataContext);
    }

    static DataContext getDataContext(Component component) {
        return DataManager.getInstance().getDataContext(component);
    }

    @Nullable
    static Object getData(String dataId, DataProvider... dataProviders) {
        for (DataProvider dataProvider : dataProviders) {
            if (dataProvider != null) {
                Object data = dataProvider.getData(dataId);
                if (data != null) {
                    return data;
                }
            }
        }
        return null;
    }
}
