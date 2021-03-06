package com.dci.intellij.dbn.code.common.style.options;

import com.dci.intellij.dbn.common.options.CompositeConfiguration;
import com.dci.intellij.dbn.common.options.Configuration;
import com.dci.intellij.dbn.common.options.ui.CompositeConfigurationEditorForm;
import org.jdom.Element;

public abstract class CodeStyleCustomSettings<P extends CodeStyleCustomSettings, T extends CompositeConfigurationEditorForm>
        extends CompositeConfiguration<P, T>{

    private CodeStyleCaseSettings caseSettings = createCaseSettings(this);
    private CodeStyleFormattingSettings formattingSettings = createAttributeSettings(this);

    protected CodeStyleCustomSettings(P parent) {
        super(parent);
    }

    protected abstract CodeStyleCaseSettings createCaseSettings(CodeStyleCustomSettings parent);
    protected abstract CodeStyleFormattingSettings createAttributeSettings(CodeStyleCustomSettings parent);

    public CodeStyleCaseSettings getCaseSettings() {
        return caseSettings;
    }

    public CodeStyleFormattingSettings getFormattingSettings() {
        return formattingSettings;
    }

    /*********************************************************
    *                     Configuration                     *
    *********************************************************/
    @Override
    protected Configuration[] createConfigurations() {
        return new Configuration[] {
                caseSettings,
                formattingSettings};
    }

    protected abstract String getElementName();

    @Override
    public void readConfiguration(Element element) {
        Element child = element.getChild(getElementName());
        if (child != null) {
            readConfiguration(child, caseSettings);
            readConfiguration(child, formattingSettings);
        }
    }

    @Override
    public void writeConfiguration(Element element) {
         Element child = new Element(getElementName());
         element.addContent(child);
         writeConfiguration(child, caseSettings);
         writeConfiguration(child, formattingSettings);
     }


}
