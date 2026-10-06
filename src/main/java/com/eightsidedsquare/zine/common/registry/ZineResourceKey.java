package com.eightsidedsquare.zine.common.registry;

import net.minecraft.core.Registry;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceKey;

public interface ZineResourceKey<T> {
    default String zine$getTranslationKey() {
        throw new UnsupportedOperationException("Implemented via mixin.");
    }

    default MutableComponent zine$getName() {
        throw new UnsupportedOperationException("Implemented via mixin.");
    }

    default <U> ResourceKey<U> zine$withRegistry(ResourceKey<? extends Registry<U>> registry) {
        throw new UnsupportedOperationException("Implemented via mixin.");
    }
}
