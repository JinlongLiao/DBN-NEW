package com.dci.intellij.dbn.code.common.style.formatting;

import com.dci.intellij.dbn.code.common.style.DBLCodeStyleManager;
import com.dci.intellij.dbn.code.common.style.options.CodeStyleCustomSettings;
import com.dci.intellij.dbn.common.dispose.AlreadyDisposedException;
import com.dci.intellij.dbn.common.util.CommonUtil;
import com.dci.intellij.dbn.common.util.DocumentUtil;
import com.dci.intellij.dbn.language.common.DBLanguage;
import com.dci.intellij.dbn.language.common.DBLanguageDialect;
import com.dci.intellij.dbn.language.common.psi.PsiUtil;
import com.intellij.formatting.Block;
import com.intellij.formatting.FormattingModel;
import com.intellij.formatting.FormattingModelBuilder;
import com.intellij.formatting.FormattingModelProvider;
import com.intellij.lang.ASTNode;
import com.intellij.lang.Language;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.codeStyle.CodeStyleSettings;
import com.intellij.psi.impl.source.codeStyle.CodeFormatterFacade;
import org.jetbrains.annotations.NotNull;

public class DBLFormattingModelBuilder implements FormattingModelBuilder {

    @Override
    @NotNull
    public FormattingModel createModel(final PsiElement element, final CodeStyleSettings codeStyleSettings) {
        DBLanguage language = (DBLanguage) PsiUtil.getLanguage(element);

        PsiFile psiFile = element.getContainingFile();
        Document document = DocumentUtil.getDocument(psiFile);
        if (document != null && document.getTextLength() != psiFile.getTextLength()) {
            // TODO check why this happens (during startup)
            throw AlreadyDisposedException.INSTANCE;
        }


        CodeStyleCustomSettings settings = language.getCodeStyleSettings(element.getProject());

        boolean deliberate = CommonUtil.isCalledThrough(CodeFormatterFacade.class);
        if (deliberate && settings.getCaseSettings().isEnabled()) {
            DBLCodeStyleManager.getInstance(element.getProject()).formatCase(element.getContainingFile());
        }

        Block rootBlock = deliberate && settings.getFormattingSettings().isEnabled() ?
                new FormattingBlock(codeStyleSettings, settings, element, null, 0) :
                new PassiveFormattingBlock(element);
        return FormattingModelProvider.createFormattingModelForPsiFile(psiFile, rootBlock, codeStyleSettings);
    }

    /**
     * @Deprecated
     */
    private static CodeStyleCustomSettings getCodeStyleSettings(PsiFile psiFile) {
        Language language = psiFile.getLanguage();
        Project project = psiFile.getProject();
        if (language instanceof DBLanguage) {
            DBLanguage dbLanguage = (DBLanguage) language;
            return dbLanguage.getCodeStyleSettings(project);
        } else if (language instanceof DBLanguageDialect) {
            DBLanguageDialect languageDialect = (DBLanguageDialect) language;
            DBLanguage dbLanguage = languageDialect.getBaseLanguage();
            return dbLanguage.getCodeStyleSettings(project);
        }
        return null;
    }

    @Override
    public TextRange getRangeAffectingIndent(PsiFile psiFile, int i, ASTNode astNode) {
        return astNode.getTextRange();
    }
}
