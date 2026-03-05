package com.eightsidedsquare.zine.client.util;

public interface ZineMinecraft {

    default float zine$getTickProgress(boolean ignoreFreeze) {
        throw new UnsupportedOperationException("Implemented via mixin.");
    }

    default float zine$getTickProgress() {
        return this.zine$getTickProgress(false);
    }

    default float zine$getDynamicDeltaTicks() {
        throw new UnsupportedOperationException("Implemented via mixin.");
    }

    default float zine$getFixedDeltaTicks() {
        throw new UnsupportedOperationException("Implemented via mixin.");
    }
}
