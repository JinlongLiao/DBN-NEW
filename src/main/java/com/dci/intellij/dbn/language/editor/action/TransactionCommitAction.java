package com.dci.intellij.dbn.language.editor.action;

import com.dci.intellij.dbn.common.Icons;
import com.dci.intellij.dbn.connection.ConnectionHandler;
import com.dci.intellij.dbn.connection.jdbc.DBNConnection;
import com.dci.intellij.dbn.connection.transaction.DatabaseTransactionManager;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.NotNull;

public class TransactionCommitAction extends TransactionEditorAction {
    public TransactionCommitAction() {
        super("Commit", "Commit changes", Icons.CONNECTION_COMMIT);
    }

    @Override
    protected void actionPerformed(@NotNull AnActionEvent e, @NotNull Project project) {
        ConnectionHandler connectionHandler = getConnectionHandler(e);
        if (connectionHandler != null) {
            DBNConnection connection = getConnection(e);
            if (connection != null) {
                DatabaseTransactionManager transactionManager = DatabaseTransactionManager.getInstance(project);
                transactionManager.commit(connectionHandler, connection, true, false, null);
            }
        }
    }
}