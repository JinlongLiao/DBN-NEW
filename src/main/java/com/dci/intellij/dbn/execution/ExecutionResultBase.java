package com.dci.intellij.dbn.execution;

import com.dci.intellij.dbn.common.dispose.StatefulDisposable;
import com.dci.intellij.dbn.execution.common.result.ui.ExecutionResultForm;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class ExecutionResultBase<F extends ExecutionResultForm> extends StatefulDisposable.Base implements ExecutionResult<F> {
    private ExecutionResult<F> previous;

    @Nullable
    @Override
    public Object getData(@NotNull String dataId) {
        return null;
    }

    @Override
    public void disposeInner() {
        nullify();
    }

    @Override
    public ExecutionResult<F> getPrevious() {
        return this.previous;
    }

    @Override
    public void setPrevious(ExecutionResult<F> previous) {
        this.previous = previous;
    }
}
