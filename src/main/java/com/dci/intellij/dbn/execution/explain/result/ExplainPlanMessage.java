package com.dci.intellij.dbn.execution.explain.result;

import com.dci.intellij.dbn.common.message.MessageType;
import com.dci.intellij.dbn.connection.ConnectionHandler;
import com.dci.intellij.dbn.execution.common.message.ConsoleMessage;
import com.intellij.openapi.util.Disposer;
import com.intellij.openapi.vfs.VirtualFile;

public class ExplainPlanMessage extends ConsoleMessage {

    private final ExplainPlanResult explainPlanResult;

    public ExplainPlanMessage(ExplainPlanResult explainPlanResult, MessageType messageType) {
        super(messageType, explainPlanResult.getErrorMessage());
        this.explainPlanResult = explainPlanResult;

        Disposer.register(this, explainPlanResult);
    }

    public VirtualFile getVirtualFile() {
        return explainPlanResult.getVirtualFile();
    }

    @Deprecated
    public void navigateToEditor(boolean requestFocus) {
        //executionResult.getExecutionProcessor().navigateToEditor(requestFocus);
    }

    public ConnectionHandler getConnectionHandler() {
        return explainPlanResult.getConnectionHandler();
    }

    public ExplainPlanResult getExplainPlanResult() {
        return this.explainPlanResult;
    }
}
