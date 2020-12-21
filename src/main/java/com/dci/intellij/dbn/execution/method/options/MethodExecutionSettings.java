package com.dci.intellij.dbn.execution.method.options;

import com.dci.intellij.dbn.common.options.BasicProjectConfiguration;
import com.dci.intellij.dbn.common.options.setting.SettingsSupport;
import com.dci.intellij.dbn.common.options.ui.ConfigurationEditorForm;
import com.dci.intellij.dbn.common.project.ProjectSupplier;
import com.dci.intellij.dbn.execution.common.options.ExecutionEngineSettings;
import com.dci.intellij.dbn.execution.common.options.ExecutionTimeoutSettings;
import com.dci.intellij.dbn.execution.method.options.ui.MethodExecutionSettingsForm;
import org.jdom.Element;
import org.jetbrains.annotations.NotNull;

public class MethodExecutionSettings extends BasicProjectConfiguration<ExecutionEngineSettings, ConfigurationEditorForm> implements ExecutionTimeoutSettings, ProjectSupplier {
    private int executionTimeout = 30;
    private int debugExecutionTimeout = 600;
    private int parameterHistorySize = 10;

    public MethodExecutionSettings(ExecutionEngineSettings parent) {
        super(parent);
    }

    @Override
    public String getDisplayName() {
        return "Method execution settings";
    }

    @Override
    public String getHelpTopic() {
        return "executionEngine";
    }

    /****************************************************
     *                   Configuration                  *
     ****************************************************/
    @Override
    @NotNull
    public ConfigurationEditorForm createConfigurationEditor() {
        return new MethodExecutionSettingsForm(this);
    }

    @Override
    public String getConfigElementName() {
        return "method-execution";
    }

    @Override
    public void readConfiguration(Element element) {
        executionTimeout = SettingsSupport.getInteger(element, "execution-timeout", executionTimeout);
        debugExecutionTimeout = SettingsSupport.getInteger(element, "debug-execution-timeout", debugExecutionTimeout);
        parameterHistorySize = SettingsSupport.getInteger(element, "parameter-history-size", parameterHistorySize);

    }

    @Override
    public void writeConfiguration(Element element) {
        SettingsSupport.setInteger(element, "execution-timeout", executionTimeout);
        SettingsSupport.setInteger(element, "debug-execution-timeout", debugExecutionTimeout);
        SettingsSupport.setInteger(element, "parameter-history-size", parameterHistorySize);
    }

    public int getExecutionTimeout() {
        return this.executionTimeout;
    }

    public int getDebugExecutionTimeout() {
        return this.debugExecutionTimeout;
    }

    public int getParameterHistorySize() {
        return this.parameterHistorySize;
    }

    public void setExecutionTimeout(int executionTimeout) {
        this.executionTimeout = executionTimeout;
    }

    public void setDebugExecutionTimeout(int debugExecutionTimeout) {
        this.debugExecutionTimeout = debugExecutionTimeout;
    }

    public void setParameterHistorySize(int parameterHistorySize) {
        this.parameterHistorySize = parameterHistorySize;
    }
}
