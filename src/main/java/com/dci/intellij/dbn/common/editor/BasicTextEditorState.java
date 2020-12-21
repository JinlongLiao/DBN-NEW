package com.dci.intellij.dbn.common.editor;

import com.dci.intellij.dbn.common.dispose.Failsafe;
import com.dci.intellij.dbn.common.thread.Read;
import com.dci.intellij.dbn.common.thread.Write;
import com.dci.intellij.dbn.common.util.DocumentUtil;
import com.intellij.codeInsight.folding.CodeFoldingManager;
import com.intellij.openapi.editor.*;
import com.intellij.openapi.editor.ex.util.EditorUtil;
import com.intellij.openapi.fileEditor.FileEditorState;
import com.intellij.openapi.fileEditor.FileEditorStateLevel;
import com.intellij.openapi.fileEditor.TextEditor;
import com.intellij.openapi.fileEditor.impl.text.CodeFoldingState;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.WriteExternalException;
import com.intellij.openapi.vfs.VirtualFile;
import org.jdom.Element;
import org.jetbrains.annotations.NotNull;

public class BasicTextEditorState implements FileEditorState {
    private int line;
    private int column;
    private int selectionStart;
    private int selectionEnd;
    private float verticalScrollProportion;
    private CodeFoldingState foldingState;

    public BasicTextEditorState() {
    }

    @Override
    public boolean canBeMergedWith(FileEditorState fileEditorState, FileEditorStateLevel fileEditorStateLevel) {
        return fileEditorState instanceof BasicTextEditorState;
    }

    public void readState(@NotNull Element sourceElement, final Project project, final VirtualFile virtualFile) {
        line = Integer.parseInt(sourceElement.getAttributeValue("line"));
        column = Integer.parseInt(sourceElement.getAttributeValue("column"));
        selectionStart = Integer.parseInt(sourceElement.getAttributeValue("selection-start"));
        selectionEnd = Integer.parseInt(sourceElement.getAttributeValue("selection-end"));
        verticalScrollProportion = Float.parseFloat(sourceElement.getAttributeValue("vertical-scroll-proportion"));

        Element foldingElement = sourceElement.getChild("folding");
        if (foldingElement != null) {
            Read.run(() -> {
                Document document = DocumentUtil.getDocument(virtualFile);
                CodeFoldingManager instance = CodeFoldingManager.getInstance(project);
                if (document != null) {
                    CodeFoldingState foldingState = instance.readFoldingState(foldingElement, document);
                    setFoldingState(foldingState);
                }
            });
        }

    }

    public void writeState(Element targetElement, Project project) {
        targetElement.setAttribute("line", Integer.toString(line));
        targetElement.setAttribute("column", Integer.toString(column));
        targetElement.setAttribute("selection-start", Integer.toString(selectionStart));
        targetElement.setAttribute("selection-end", Integer.toString(selectionEnd));
        targetElement.setAttribute("vertical-scroll-proportion", Float.toString(verticalScrollProportion));
        if (foldingState != null) {
            Element foldingElement = new Element("folding");
            targetElement.addContent(foldingElement);
            try {
                CodeFoldingManager.getInstance(project).writeFoldingState(foldingState, foldingElement);
            } catch (WriteExternalException ignore) { // TODO
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void loadFromEditor(@NotNull FileEditorStateLevel level, @NotNull final TextEditor textEditor) {
        Editor editor = textEditor.getEditor();
        SelectionModel selectionModel = editor.getSelectionModel();
        LogicalPosition logicalPosition = editor.getCaretModel().getLogicalPosition();

        line = logicalPosition.line;
        column = logicalPosition.column;

        if(FileEditorStateLevel.FULL == level) {
            selectionStart = selectionModel.getSelectionStart();
            selectionEnd = selectionModel.getSelectionEnd();
            Project project = editor.getProject();
            if (project != null && !editor.isDisposed()) {
                foldingState = CodeFoldingManager.getInstance(project).saveFoldingState(editor);
            }

/*
            new WriteActionRunner() {
                @Override
                public void run() {
                    Editor editor = textEditor.getEditor();
                    Project project = editor.getProject();
                    if (project != null && !editor.isDisposed()) {
                        try {
                            PsiDocumentManager.getInstance(project).commitDocument(editor.getDocument());
                        } catch (ProcessCanceledException ignore) {
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        foldingState = CodeFoldingManager.getInstance(project).saveFoldingState(editor);
                    }
                }
            }.start();
*/
        }
        verticalScrollProportion = level != FileEditorStateLevel.UNDO ? EditorUtil.calcVerticalScrollProportion(editor) : -1F;
    }

    public void applyToEditor(@NotNull TextEditor textEditor) {
        final Editor editor = textEditor.getEditor();
        SelectionModel selectionModel = editor.getSelectionModel();

        LogicalPosition logicalPosition = new LogicalPosition(line, column);
        editor.getCaretModel().moveToLogicalPosition(logicalPosition);
        selectionModel.removeSelection();
        editor.getScrollingModel().scrollToCaret(ScrollType.RELATIVE);
        if (verticalScrollProportion != -1F)
            EditorUtil.setVerticalScrollProportion(editor, verticalScrollProportion);
        final Document document = editor.getDocument();
        if (selectionStart == selectionEnd) {
            selectionModel.removeSelection();
        } else {
            int selectionStart = Math.min(this.selectionStart, document.getTextLength());
            int selectionEnd = Math.min(this.selectionEnd, document.getTextLength());
            selectionModel.setSelection(selectionStart, selectionEnd);
        }
        editor.getScrollingModel().scrollToCaret(ScrollType.RELATIVE);

        if (foldingState != null) {
            Write.run(() -> {
                Project project = Failsafe.nd(editor.getProject());
                //PsiDocumentManager.getInstance(project).commitDocument(document);
                editor.getFoldingModel().runBatchFoldingOperation(
                        () -> {
                            CodeFoldingManager foldingManager = CodeFoldingManager.getInstance(project);
                            foldingManager.restoreFoldingState(editor, foldingState);
                        }
                );
            });
            //editor.getFoldingModel().runBatchFoldingOperation(runnable);
        }
    }

    public int getLine() {
        return this.line;
    }

    public int getColumn() {
        return this.column;
    }

    public int getSelectionStart() {
        return this.selectionStart;
    }

    public int getSelectionEnd() {
        return this.selectionEnd;
    }

    public float getVerticalScrollProportion() {
        return this.verticalScrollProportion;
    }

    public CodeFoldingState getFoldingState() {
        return this.foldingState;
    }

    public void setLine(int line) {
        this.line = line;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public void setSelectionStart(int selectionStart) {
        this.selectionStart = selectionStart;
    }

    public void setSelectionEnd(int selectionEnd) {
        this.selectionEnd = selectionEnd;
    }

    public void setVerticalScrollProportion(float verticalScrollProportion) {
        this.verticalScrollProportion = verticalScrollProportion;
    }

    public void setFoldingState(CodeFoldingState foldingState) {
        this.foldingState = foldingState;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof BasicTextEditorState)) return false;
        final BasicTextEditorState other = (BasicTextEditorState) o;
        if (!other.canEqual((Object) this)) return false;
        if (this.getLine() != other.getLine()) return false;
        if (this.getColumn() != other.getColumn()) return false;
        if (this.getSelectionStart() != other.getSelectionStart()) return false;
        if (this.getSelectionEnd() != other.getSelectionEnd()) return false;
        if (Float.compare(this.getVerticalScrollProportion(), other.getVerticalScrollProportion()) != 0) return false;
        final Object this$foldingState = this.getFoldingState();
        final Object other$foldingState = other.getFoldingState();
        if (this$foldingState == null ? other$foldingState != null : !this$foldingState.equals(other$foldingState))
            return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof BasicTextEditorState;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        result = result * PRIME + this.getLine();
        result = result * PRIME + this.getColumn();
        result = result * PRIME + this.getSelectionStart();
        result = result * PRIME + this.getSelectionEnd();
        result = result * PRIME + Float.floatToIntBits(this.getVerticalScrollProportion());
        final Object $foldingState = this.getFoldingState();
        result = result * PRIME + ($foldingState == null ? 43 : $foldingState.hashCode());
        return result;
    }

    public String toString() {
        return "BasicTextEditorState(line=" + this.getLine() + ", column=" + this.getColumn() + ", selectionStart=" + this.getSelectionStart() + ", selectionEnd=" + this.getSelectionEnd() + ", verticalScrollProportion=" + this.getVerticalScrollProportion() + ", foldingState=" + this.getFoldingState() + ")";
    }
}
