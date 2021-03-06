package com.dci.intellij.dbn.common.ui.table;

import com.dci.intellij.dbn.common.dispose.StatefulDisposable;
import com.dci.intellij.dbn.common.event.ApplicationEvents;
import com.dci.intellij.dbn.language.common.WeakRef;
import com.intellij.openapi.editor.colors.EditorColorsListener;
import com.intellij.openapi.editor.colors.EditorColorsManager;
import com.intellij.openapi.editor.colors.EditorColorsScheme;
import com.intellij.openapi.util.Disposer;
import com.intellij.util.ui.UIUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public abstract class DBNTableGutter<T extends DBNTableWithGutter> extends JList implements StatefulDisposable, EditorColorsListener {
    private boolean disposed;
    private final WeakRef<T> table;

    public DBNTableGutter(T table) {
        super(table.getModel().getListModel());
        this.table = WeakRef.of(table);
        int rowHeight = table.getRowHeight();
        if (rowHeight != 0) setFixedCellHeight(rowHeight);
        setBackground(UIUtil.getPanelBackground());

        setCellRenderer(createCellRenderer());

        ApplicationEvents.subscribe(this, EditorColorsManager.TOPIC, this);
        Disposer.register(table, this);
    }

    protected abstract ListCellRenderer createCellRenderer();

    @Override
    public void globalSchemeChange(@Nullable EditorColorsScheme scheme) {
        setCellRenderer(createCellRenderer());
    }

    @Override
    public ListModel<?> getModel() {
        ListModel<?> cachedModel = super.getModel();

        if (this.table != null) {
            // only after initialisation
            T table = getTable();
            ListModel<?> listModel = table.getModel().getListModel();
            if (listModel != null && listModel != cachedModel) {
                setModel(listModel);
            }
            return listModel;
        }
        return cachedModel;
    }

    @NotNull
    public T getTable() {
        return table.ensure();
    }

    @Override
    public void dispose() {
        if (!disposed) {
            disposed = true;
            nullify();
        }
    }

    public boolean isDisposed() {
        return this.disposed;
    }
}
