package com.eightsidedsquare.zine.client.item.tint;

public interface ZineCustomModelDataSource {

    default void zine$setIndex(int index) {
        throw new UnsupportedOperationException("Implemented via mixin.");
    }

    default void zine$setDefaultColor(int defaultColor) {
        throw new UnsupportedOperationException("Implemented via mixin.");
    }

}
