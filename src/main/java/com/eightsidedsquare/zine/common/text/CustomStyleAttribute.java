package com.eightsidedsquare.zine.common.text;

import com.eightsidedsquare.zine.core.ZineRegistries;
import com.mojang.serialization.Codec;
import net.minecraft.util.Util;
import net.minecraft.util.dynamic.CodecCache;

public final class CustomStyleAttribute<T> {

    @SuppressWarnings("Convert2MethodRef")
    public static final Codec<CustomStyleAttribute<?>> CODEC = Codec.lazyInitialized(() -> ZineRegistries.CUSTOM_STYLE_ATTRIBUTES.getCodec());

    private static final CodecCache CACHE = new CodecCache(512);
    private final Codec<T> codec;

    public CustomStyleAttribute(Codec<T> codec, boolean cache) {
        this.codec = cache ? CACHE.wrap(codec) : codec;
    }

    public Codec<T> getCodec() {
        return this.codec;
    }

    @Override
    public String toString() {
        return Util.registryValueToString(ZineRegistries.CUSTOM_STYLE_ATTRIBUTES, this);
    }
}
