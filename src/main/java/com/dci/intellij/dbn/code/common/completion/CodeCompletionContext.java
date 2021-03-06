package com.dci.intellij.dbn.code.common.completion;

import com.dci.intellij.dbn.code.common.completion.options.CodeCompletionSettings;
import com.dci.intellij.dbn.code.common.completion.options.filter.CodeCompletionFilterSettings;
import com.dci.intellij.dbn.code.common.style.options.ProjectCodeStyleSettings;
import com.dci.intellij.dbn.common.util.StringUtil;
import com.dci.intellij.dbn.connection.ConnectionHandler;
import com.dci.intellij.dbn.connection.ConnectionHandlerRef;
import com.dci.intellij.dbn.language.common.DBLanguage;
import com.dci.intellij.dbn.language.common.DBLanguageDialect;
import com.dci.intellij.dbn.language.common.DBLanguagePsiFile;
import com.dci.intellij.dbn.language.common.PsiFileRef;
import com.dci.intellij.dbn.language.common.psi.BasePsiElement;
import com.dci.intellij.dbn.language.common.psi.PsiUtil;
import com.dci.intellij.dbn.language.sql.SQLLanguage;
import com.dci.intellij.dbn.options.ProjectSettings;
import com.dci.intellij.dbn.options.ProjectSettingsManager;
import com.intellij.codeInsight.completion.CompletionParameters;
import com.intellij.codeInsight.completion.CompletionResultSet;
import com.intellij.codeInsight.completion.CompletionType;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.progress.ProcessCanceledException;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.PsiComment;
import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class CodeCompletionContext {
    private boolean extended;
    private final PsiFileRef<DBLanguagePsiFile> file;
    private final ConnectionHandlerRef connectionHandler;
    private final ProjectCodeStyleSettings codeStyleSettings;
    private final CodeCompletionSettings codeCompletionSettings;
    private final CompletionParameters parameters;
    private final CompletionResultSet result;
    private final double databaseVersion;
    private final String userInput;
    private final PsiElement elementAtCaret;
    private final boolean newLine;


    public CodeCompletionContext(DBLanguagePsiFile file, CompletionParameters parameters, CompletionResultSet result) {
        this.file = PsiFileRef.of(file);
        this.parameters = parameters;
        this.result = result;
        this.extended = parameters.getCompletionType() == CompletionType.SMART;
        this.connectionHandler = ConnectionHandlerRef.from(file.getConnectionHandler());

        PsiElement position = parameters.getPosition();
        if (position instanceof PsiComment) {
            throw new ProcessCanceledException();
        }
        int offset = parameters.getOffset();
        userInput = calcUserInput(position, offset);

        ProjectSettings projectSettings = ProjectSettingsManager.getSettings(file.getProject());
        codeStyleSettings = projectSettings.getCodeStyleSettings();
        codeCompletionSettings = projectSettings.getCodeCompletionSettings();

        elementAtCaret = calcElementAtCaret(file, position);

        databaseVersion = file.getDatabaseVersion();
        newLine = calcNewLine(parameters, offset);
    }

    private static PsiElement calcElementAtCaret(DBLanguagePsiFile file, PsiElement position) {
        PsiElement elementAtCaret = position instanceof BasePsiElement ? (BasePsiElement) position : PsiUtil.lookupLeafAtOffset(file, position.getTextOffset());
        elementAtCaret = elementAtCaret == null ? file : elementAtCaret;
        return elementAtCaret;
    }

    private static String calcUserInput(PsiElement position, int offset) {
        if (offset > position.getTextOffset()) {
            return position.getText().substring(0, offset - position.getTextOffset());
        }
        return null;
    }

    private static boolean calcNewLine(CompletionParameters parameters, int offset) {
        Document document = parameters.getEditor().getDocument();
        int lineNumber = document.getLineNumber(offset);
        int lineStartOffset = document.getLineStartOffset(lineNumber);
        int lineEndOffset = Math.min(offset, document.getTextLength());
        if (lineStartOffset < lineEndOffset) {
            String text = document.getText(new TextRange(lineStartOffset, lineEndOffset));
            return !StringUtil.containsWhitespaces(text.trim());
        }
        return true;
    }

    @Nullable
    public ConnectionHandler getConnectionHandler() {
        return ConnectionHandlerRef.get(connectionHandler);
    }

    public CodeCompletionFilterSettings getCodeCompletionFilterSettings() {
        return codeCompletionSettings.getFilterSettings().getFilterSettings(extended);
    }

    @NotNull
    public DBLanguagePsiFile getFile() {
        return file.ensure();
    }

    @NotNull
    public DBLanguage getLanguage() {
        DBLanguageDialect languageDialect = getFile().getLanguageDialect();
        return languageDialect == null ? SQLLanguage.INSTANCE : languageDialect.getBaseLanguage();
    }

    public boolean isExtended() {
        return this.extended;
    }

    public ProjectCodeStyleSettings getCodeStyleSettings() {
        return this.codeStyleSettings;
    }

    public CodeCompletionSettings getCodeCompletionSettings() {
        return this.codeCompletionSettings;
    }

    public CompletionParameters getParameters() {
        return this.parameters;
    }

    public CompletionResultSet getResult() {
        return this.result;
    }

    public double getDatabaseVersion() {
        return this.databaseVersion;
    }

    public String getUserInput() {
        return this.userInput;
    }

    public PsiElement getElementAtCaret() {
        return this.elementAtCaret;
    }

    public boolean isNewLine() {
        return this.newLine;
    }

    public void setExtended(boolean extended) {
        this.extended = extended;
    }
}
