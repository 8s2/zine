package com.eightsidedsquare.zine.client.atlas;

public interface ZineNativeImage {
    default long zine$getPointer() {
        throw new UnsupportedOperationException("Implemented via mixin.");
    }
}
