package com.dci.intellij.dbn.data.record;

import javax.swing.*;

public class RecordViewInfo {
    private final String title;
    private final Icon icon;

    public RecordViewInfo(String title, Icon icon) {
        this.title = title;
        this.icon = icon;
    }

    public String getTitle() {
        return this.title;
    }

    public Icon getIcon() {
        return this.icon;
    }
}
