package com.dci.intellij.dbn.common.environment.options;

import com.dci.intellij.dbn.common.environment.EnvironmentType;
import com.dci.intellij.dbn.common.environment.EnvironmentTypeBundle;
import com.dci.intellij.dbn.common.environment.EnvironmentTypeId;
import com.dci.intellij.dbn.common.environment.options.ui.EnvironmentSettingsForm;
import com.dci.intellij.dbn.common.options.BasicProjectConfiguration;
import com.dci.intellij.dbn.common.options.ui.ConfigurationEditorForm;
import com.dci.intellij.dbn.options.general.GeneralProjectSettings;
import org.jdom.Element;
import org.jetbrains.annotations.NotNull;

public class EnvironmentSettings extends BasicProjectConfiguration<GeneralProjectSettings, ConfigurationEditorForm> {
    private EnvironmentTypeBundle environmentTypes = new EnvironmentTypeBundle(EnvironmentTypeBundle.DEFAULT);
    private EnvironmentVisibilitySettings visibilitySettings = new EnvironmentVisibilitySettings();

    public EnvironmentSettings(GeneralProjectSettings parent) {
        super(parent);
    }

    @NotNull
    @Override
    public ConfigurationEditorForm createConfigurationEditor() {
        return new EnvironmentSettingsForm(this);
    }

    public EnvironmentTypeBundle getEnvironmentTypes() {
        return environmentTypes;
    }

    @NotNull
    public EnvironmentType getEnvironmentType(EnvironmentTypeId environmentTypeId) {
        return environmentTypes.getEnvironmentType(environmentTypeId);
    }

    public boolean setEnvironmentTypes(EnvironmentTypeBundle environmentTypes) {
        boolean changed = !this.environmentTypes.equals(environmentTypes);
        this.environmentTypes = new EnvironmentTypeBundle(environmentTypes);
        return changed;
    }

    public EnvironmentVisibilitySettings getVisibilitySettings() {
        return visibilitySettings;
    }

    public void setVisibilitySettings(EnvironmentVisibilitySettings visibilitySettings) {
        this.visibilitySettings = visibilitySettings;
    }

    @Override
    public String getConfigElementName() {
        return "environment";
    }

    @Override
    public void readConfiguration(Element element) {
        Element environmentTypesElement = element.getChild("environment-types");
        if (environmentTypesElement != null) {
            environmentTypes.clear();
            for (Object o : environmentTypesElement.getChildren()) {
                Element environmentTypeElement = (Element) o;
                EnvironmentType environmentType = new EnvironmentType();
                environmentType.readConfiguration(environmentTypeElement);
                environmentTypes.add(environmentType);
            }
        }

        Element visibilitySettingsElement = element.getChild("visibility-settings");
        if (visibilitySettingsElement != null) {
            visibilitySettings.readConfiguration(visibilitySettingsElement);
        }
    }

    @Override
    public void writeConfiguration(Element element) {
        Element environmentTypesElement = new Element("environment-types");
        element.addContent(environmentTypesElement);
        for (EnvironmentType environmentType : environmentTypes) {
            Element itemElement = new Element("environment-type");
            environmentType.writeConfiguration(itemElement);
            environmentTypesElement.addContent(itemElement);
        }

        Element visibilitySettingsElement = new Element("visibility-settings");
        element.addContent(visibilitySettingsElement);
        visibilitySettings.writeConfiguration(visibilitySettingsElement);
    }
}
