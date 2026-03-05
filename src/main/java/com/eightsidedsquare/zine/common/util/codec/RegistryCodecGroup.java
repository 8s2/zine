package com.eightsidedsquare.zine.common.util.codec;

import com.mojang.serialization.Codec;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.syncher.EntityDataSerializer;
import net.minecraft.resources.ResourceKey;

/**
 * <p>A RegistryCodecGroup holds codecs and packet codecs which can be useful when working with a dynamic registry.
 * <p>The Tracked subtype registers a tracked data handler for a registry entry.
 */
public interface RegistryCodecGroup<T> {

    static <T> RegistryCodecGroup<T> create(ResourceKey<Registry<T>> registryKey, Codec<T> codec, Codec<T> networkCodec) {
        return new RegistryCodecGroupImpl<>(registryKey, codec, networkCodec);
    }

    static <T> RegistryCodecGroup<T> create(ResourceKey<Registry<T>> registryKey, Codec<T> codec) {
        return new RegistryCodecGroupImpl<>(registryKey, codec, codec);
    }

    static <T> Serialized<T> createTracked(ResourceKey<Registry<T>> registryKey, Codec<T> codec, Codec<T> networkCodec) {
        return new RegistryCodecGroupImpl.SerializedImpl<>(registryKey, codec, networkCodec);
    }

    static <T> Serialized<T> createTracked(ResourceKey<Registry<T>> registryKey, Codec<T> codec) {
        return new RegistryCodecGroupImpl.SerializedImpl<>(registryKey, codec, codec);
    }

    ResourceKey<Registry<T>> key();

    Codec<T> codec();

    Codec<T> networkCodec();

    Codec<Holder<T>> holderCodec();

    StreamCodec<RegistryFriendlyByteBuf, Holder<T>> streamCodec();

    interface Serialized<T> extends RegistryCodecGroup<T> {

        EntityDataSerializer<Holder<T>> dataSerializer();

    }

}
