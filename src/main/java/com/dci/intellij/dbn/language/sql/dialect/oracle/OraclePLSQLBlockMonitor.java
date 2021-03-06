package com.dci.intellij.dbn.language.sql.dialect.oracle;

import java.util.ArrayDeque;
import java.util.Deque;

public abstract class OraclePLSQLBlockMonitor {
    public enum Marker {
        BEGIN,
        CREATE,
        DECLARE,
        METHOD,
        CASE}

    private Deque<Marker> stack = new ArrayDeque<Marker>();


    public void start(Marker marker) {
        if (!stack.isEmpty()) {
            stack.clear();
        }
        stack.push(marker);
        lexerStart();
    }

    public void mark(Marker marker) {
        stack.push(marker);
    }

    public boolean end(boolean force) {
        if (force) {
            stack.clear();
        } else {
            Marker marker = stack.poll();
            if (marker == Marker.BEGIN) {
                if (!stack.isEmpty()) {
                    Marker previousMarker = stack.peek();
                    if (previousMarker != Marker.BEGIN) {
                        stack.poll();
                    }
                }
            } else {
                while (marker == Marker.METHOD) {
                    marker = stack.poll();
                }
            }
        }

        if (stack.isEmpty()) {
            lexerEnd();
            return true;
        }

        return false;
    }


    protected abstract void lexerStart();
    protected abstract void lexerEnd();

    public void reset() {
        stack.clear();
    }

}
