package com.dci.intellij.dbn.data.editor.ui;

import com.dci.intellij.dbn.common.event.ProjectEvents;
import com.dci.intellij.dbn.common.thread.Dispatch;
import com.dci.intellij.dbn.common.thread.Progress;
import com.dci.intellij.dbn.common.ui.Borders;
import com.dci.intellij.dbn.common.ui.DBNFormImpl;
import com.dci.intellij.dbn.common.ui.KeyAdapter;
import com.dci.intellij.dbn.common.ui.KeyUtil;
import com.intellij.ide.DataManager;
import com.intellij.openapi.actionSystem.*;
import com.intellij.openapi.fileEditor.FileEditorManagerEvent;
import com.intellij.openapi.fileEditor.FileEditorManagerListener;
import com.intellij.openapi.keymap.KeymapUtil;
import com.intellij.openapi.ui.popup.JBPopup;
import com.intellij.openapi.util.Disposer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.HashSet;
import java.util.Set;

public abstract class TextFieldPopupProviderForm extends DBNFormImpl implements KeyAdapter, TextFieldPopupProvider {
    protected TextFieldWithPopup<?> editorComponent;
    private final boolean autoPopup;
    private final boolean buttonVisible;
    private boolean enabled = true;
    private JLabel button;
    private JBPopup popup;
    private final Set<AnAction> actions = new HashSet<>();

    TextFieldPopupProviderForm(TextFieldWithPopup<?> editorComponent, boolean autoPopup, boolean buttonVisible) {
        super(editorComponent, editorComponent.getProject());
        this.editorComponent = editorComponent;
        this.autoPopup = autoPopup;
        this.buttonVisible = buttonVisible;
        ProjectEvents.subscribe(ensureProject(), this, FileEditorManagerListener.FILE_EDITOR_MANAGER, fileEditorManagerListener);
    }

    private final FileEditorManagerListener fileEditorManagerListener = new FileEditorManagerListener() {
        @Override
        public void selectionChanged(@NotNull FileEditorManagerEvent event) {
            hidePopup();
        }
    };

    public TextFieldWithPopup<?> getEditorComponent() {
        return editorComponent;
    }

    public JTextField getTextField() {
        return editorComponent.getTextField();
    }

    /**
     * Create the popup and return it.
     * If the popup shouldn't show-up for some reason (e.g. empty completion actions),
     * than this method should return null
     */
    @Nullable
    public abstract JBPopup createPopup();

    @Override
    public final String getKeyShortcutDescription() {
        return KeymapUtil.getShortcutsText(getShortcuts());
    }

    @Override
    public final Shortcut[] getShortcuts() {
        return KeyUtil.getShortcuts(getKeyShortcutName());
    }

    protected abstract String getKeyShortcutName();

    void registerAction(AnAction action) {
        actions.add(action);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (!e.isConsumed()) {
            for (AnAction action : actions) {
                if (KeyUtil.match(action.getShortcutSet().getShortcuts(), e)) {
                    DataContext dataContext = DataManager.getInstance().getDataContext(this.getComponent());
                    ActionManager actionManager = ActionManager.getInstance();
                    AnActionEvent actionEvent = new AnActionEvent(null, dataContext, "", action.getTemplatePresentation(), actionManager, 2);
                    action.actionPerformed(actionEvent);
                    e.consume();
                    return;
                }
            }
        }
    }

    public void preparePopup() {}

    boolean isPreparingPopup = false;
    @Override
    public void showPopup() {
        if (isPreparingPopup) return;

        isPreparingPopup = true;
        Progress.prompt(getProject(), "Loading " + getDescription(), true, (progress) -> {
            preparePopup();
            if (progress.isCanceled()) {
                isPreparingPopup = false;
                return;
            }

            Dispatch.run(() -> {
                try {
                    if (!isShowingPopup()) {
                        popup = createPopup();
                        if (popup != null) {
                            Disposer.register(TextFieldPopupProviderForm.this, popup);

                            JPanel panel = (JPanel) popup.getContent();
                            panel.setBorder(Borders.COMPONENT_LINE_BORDER);

                            editorComponent.clearSelection();

                            if (editorComponent.isShowing()) {
                                Point location = editorComponent.getLocationOnScreen();
                                location.setLocation(location.getX() + 4, location.getY() + editorComponent.getHeight() + 4);
                                popup.showInScreenCoordinates(editorComponent, location);
                                //cellEditor.highlight(TextCellEditor.HIGHLIGHT_TYPE_POPUP);
                            }
                        }
                    }
                } finally {
                    isPreparingPopup = false;
                }
            });
        });
    }

    @Override
    public void hidePopup() {
        if (isShowingPopup()) {
            Dispatch.run(() -> {
                if (isShowingPopup()) {
                    popup.cancel();
                    popup = null;
                }
            });
        }
    }

    @Override
    public boolean isShowingPopup() {
        return popup != null && popup.isVisible();
    }


    @Nullable
    @Override
    public JComponent getPreferredFocusedComponent() {
        return null;
    }

    public boolean isAutoPopup() {
        return this.autoPopup;
    }

    public boolean isButtonVisible() {
        return this.buttonVisible;
    }

    public boolean isEnabled() {
        return this.enabled;
    }

    public JLabel getButton() {
        return this.button;
    }

    public JBPopup getPopup() {
        return this.popup;
    }

    public Set<AnAction> getActions() {
        return this.actions;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public void setButton(JLabel button) {
        this.button = button;
    }
}
