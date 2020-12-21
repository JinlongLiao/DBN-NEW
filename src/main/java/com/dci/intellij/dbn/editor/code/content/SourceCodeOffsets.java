package com.dci.intellij.dbn.editor.code.content;

import com.intellij.openapi.editor.RangeMarker;

import java.util.List;

public class SourceCodeOffsets {
    private final GuardedBlockMarkers guardedBlocks = new GuardedBlockMarkers();
    private int headerEndOffset = 0;

    public void addGuardedBlock(int startOffset, int endOffset) {
        guardedBlocks.addMarker(startOffset, endOffset);
    }

    public void setGuardedBlocks(List<RangeMarker> rangeMarkers) {
        this.guardedBlocks.apply(rangeMarkers);
    }

    public GuardedBlockMarkers getGuardedBlocks() {
        return this.guardedBlocks;
    }

    public int getHeaderEndOffset() {
        return this.headerEndOffset;
    }

    public void setHeaderEndOffset(int headerEndOffset) {
        this.headerEndOffset = headerEndOffset;
    }
}
