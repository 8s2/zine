package com.eightsidedsquare.zine.common.text;

import net.minecraft.network.chat.Style;
import org.jetbrains.annotations.ApiStatus;

public interface ZineStyle {

    default Style zine$copy() {
        throw new UnsupportedOperationException("Implemented via mixin.");
    }

    default Style zine$withOutlineColor(int outlineColor) {
        throw new UnsupportedOperationException("Implemented via mixin.");
    }

    default int zine$getOutlineColor() {
        throw new UnsupportedOperationException("Implemented via mixin.");
    }

    default boolean zine$hasOutline() {
        return (this.zine$getOutlineColor() & 0xff000000) != 0;
    }

    @ApiStatus.Internal
    default void zine$setOutlineColor(int outlineColor) {
        throw new UnsupportedOperationException("Implemented via mixin.");
    }

}
