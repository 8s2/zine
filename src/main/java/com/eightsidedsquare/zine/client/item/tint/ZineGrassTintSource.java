package com.eightsidedsquare.zine.client.item.tint;

public interface ZineGrassTintSource {

    default void zine$setTemperature(float temperature) {
        throw new UnsupportedOperationException("Implemented via mixin.");
    }

    default void zine$setDownfall(float downfall) {
        throw new UnsupportedOperationException("Implemented via mixin.");
    }

}
