package com.dci.intellij.dbn.common.dispose;

import com.intellij.openapi.Disposable;
import com.intellij.openapi.util.Disposer;
import org.jetbrains.annotations.NotNull;

public interface StatefulDisposable extends Disposable {

    default boolean isDisposed() {return false;}

    default void checkDisposed() {
        if (isDisposed()) throw AlreadyDisposedException.INSTANCE;
    }

    default void nullify() {
        SafeDisposer.nullify(this);
    }

    abstract class Base implements StatefulDisposable {
        private boolean disposed;

        public Base() {
        }

        public Base(@NotNull Disposable parent) {
            if (Failsafe.check(parent)) {
                Disposer.register(parent, this);
            }
        }

        @Override
        public final void dispose() {
            if (!disposed) {
                disposed = true;
                disposeInner();
            }
        }

        protected abstract void disposeInner();

        public boolean isDisposed() {
            return this.disposed;
        }
    }
}
