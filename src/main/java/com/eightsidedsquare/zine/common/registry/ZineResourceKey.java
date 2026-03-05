package com.eightsidedsquare.zine.common.registry;

import net.minecraft.network.chat.MutableComponent;

public interface ZineResourceKey<T> {

    default String zine$getTranslationKey() {
        throw new UnsupportedOperationException("Implemented via mixin.");
    }

    default MutableComponent zine$getName() {
        throw new UnsupportedOperationException("Implemented via mixin.");
    }

}
