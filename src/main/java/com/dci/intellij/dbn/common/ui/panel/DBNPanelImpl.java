package com.dci.intellij.dbn.common.ui.panel;

import com.dci.intellij.dbn.common.dispose.SafeDisposer;
import com.intellij.ui.components.JBPanel;

public abstract class DBNPanelImpl extends JBPanel implements DBNPanel{
    private boolean disposed;

    @Override
    public final void dispose() {
        if (!disposed) {
            disposed = true;
            disposeInner();
            SafeDisposer.nullify(this);
        }
    }

    protected abstract void disposeInner();

    public boolean isDisposed() {
        return this.disposed;
    }
}
