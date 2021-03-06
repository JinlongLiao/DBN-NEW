package com.dci.intellij.dbn.common.dispose;

import com.intellij.openapi.Disposable;
import com.intellij.openapi.util.Disposer;
import com.intellij.openapi.util.UserDataHolderBase;
import org.jetbrains.annotations.Nullable;

public abstract class DisposableUserDataHolderBase extends UserDataHolderBase implements StatefulDisposable {
    private boolean disposed;

    public DisposableUserDataHolderBase() {
    }

    public DisposableUserDataHolderBase(@Nullable Disposable parent) {
        if (parent != null) {
            Disposer.register(parent, this);
        }
    }

    @Override
    public void dispose() {
        if (!disposed) {
            disposed = true;
            nullify();
            //
        }
    }

    public boolean isDisposed() {
        return this.disposed;
    }
}
