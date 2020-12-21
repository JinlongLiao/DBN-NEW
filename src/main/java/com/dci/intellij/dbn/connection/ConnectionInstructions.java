package com.dci.intellij.dbn.connection;

public class ConnectionInstructions {
    private boolean allowAutoConnect;
    private boolean allowAutoInit;

    public ConnectionInstructions() {
    }

    public boolean isAllowAutoConnect() {
        return this.allowAutoConnect;
    }

    public boolean isAllowAutoInit() {
        return this.allowAutoInit;
    }

    public void setAllowAutoConnect(boolean allowAutoConnect) {
        this.allowAutoConnect = allowAutoConnect;
    }

    public void setAllowAutoInit(boolean allowAutoInit) {
        this.allowAutoInit = allowAutoInit;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof ConnectionInstructions)) return false;
        final ConnectionInstructions other = (ConnectionInstructions) o;
        if (!other.canEqual((Object) this)) return false;
        if (this.isAllowAutoConnect() != other.isAllowAutoConnect()) return false;
        if (this.isAllowAutoInit() != other.isAllowAutoInit()) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof ConnectionInstructions;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        result = result * PRIME + (this.isAllowAutoConnect() ? 79 : 97);
        result = result * PRIME + (this.isAllowAutoInit() ? 79 : 97);
        return result;
    }

    public String toString() {
        return "ConnectionInstructions(allowAutoConnect=" + this.isAllowAutoConnect() + ", allowAutoInit=" + this.isAllowAutoInit() + ")";
    }
}
