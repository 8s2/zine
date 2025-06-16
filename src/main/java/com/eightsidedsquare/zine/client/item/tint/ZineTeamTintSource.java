package com.eightsidedsquare.zine.client.item.tint;

public interface ZineTeamTintSource {

    default void zine$setDefaultColor(int defaultColor) {
        throw new UnsupportedOperationException("Implemented via mixin.");
    }

}
