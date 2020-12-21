package com.dci.intellij.dbn.connection;

import com.dci.intellij.dbn.common.util.EnumerationUtil;

public enum ConnectionType{
    MAIN("Main", 0),
    POOL("Pool", 1),
    SESSION("Session", 2),
    DEBUG("Debug", 3),
    DEBUGGER("Debugger", 4),
    TEST("Test", 5)
    ;

    private final String name;
    private final int priority;

    ConnectionType(String name, int priority) {
        this.name = name;
        this.priority = priority;
    }

    public boolean isOneOf(ConnectionType... connectionTypes){
        return EnumerationUtil.isOneOf(this, connectionTypes);
    }

    public boolean matches(ConnectionType... connectionTypes){
        return connectionTypes == null || connectionTypes.length == 0 || isOneOf(connectionTypes);
    }

    public String getName() {
        return this.name;
    }

    public int getPriority() {
        return this.priority;
    }
}
