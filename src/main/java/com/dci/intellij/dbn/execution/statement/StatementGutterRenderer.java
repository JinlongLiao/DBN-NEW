package com.dci.intellij.dbn.execution.statement;

import com.dci.intellij.dbn.common.util.CommonUtil;
import com.dci.intellij.dbn.execution.statement.action.StatementGutterAction;
import com.dci.intellij.dbn.language.common.psi.ExecutablePsiElement;
import com.intellij.codeInsight.daemon.impl.ShowIntentionsPass;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.editor.markup.GutterIconRenderer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public class StatementGutterRenderer extends GutterIconRenderer {
    private StatementGutterAction action;
    public StatementGutterRenderer(ExecutablePsiElement executablePsiElement) {
        this.action = new StatementGutterAction(executablePsiElement);
    }

    @Override
    @NotNull
    public Icon getIcon() {
        return action.getIcon();
    }

    @Override
    public boolean isNavigateAction() {
        return true;
    }

    @Override
    @Nullable
    public AnAction getClickAction() {
        // TODO workaround for Idea 15 bug (showing gutter actions as intentions)
        return CommonUtil.isCalledThrough(ShowIntentionsPass.class) ? null : action;
    }

    @Override
    @Nullable
    public String getTooltipText() {
        return action.getTooltipText();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof StatementGutterRenderer) {
            StatementGutterRenderer renderer = (StatementGutterRenderer) obj;
            return action.equals(renderer.action);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return action.hashCode();
    }

    @NotNull
    @Override
    public Alignment getAlignment() {
        return Alignment.RIGHT;
    }
}
