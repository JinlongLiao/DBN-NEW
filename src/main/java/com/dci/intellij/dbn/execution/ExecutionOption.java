package com.dci.intellij.dbn.execution;

import com.dci.intellij.dbn.common.property.Property;

public enum ExecutionOption implements Property {
    ENABLE_LOGGING,
    COMMIT_AFTER_EXECUTION;


    private final int index = Property.idx(this);

    @Override
    public int index() {
        return index;
    }

}
