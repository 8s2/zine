package com.eightsidedsquare.zine.common.util.codec;

import com.mojang.serialization.MapCodec;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;

import java.util.function.Function;

public record SyncedCodec<T>(MapCodec<T> codec, StreamCodec<? super RegistryFriendlyByteBuf, T> streamCodec) {
    public <U> SyncedCodec<U> map(Function<? super T, ? extends U> to, Function<? super U, ? extends T> from) {
        return new SyncedCodec<>(this.codec.xmap(to, from), this.streamCodec.map(to, from));
    }
}
