package com.eightsidedsquare.zine.common.util.codec;

import com.mojang.serialization.Codec;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityDataRegistry;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.syncher.EntityDataSerializer;
import net.minecraft.resources.RegistryFixedCodec;
import net.minecraft.resources.ResourceKey;

public record RegistryCodecGroupImpl<T>(ResourceKey<Registry<T>> key,
                                        Codec<T> codec,
                                        Codec<T> networkCodec,
                                        Codec<Holder<T>> holderCodec,
                                        StreamCodec<RegistryFriendlyByteBuf, Holder<T>> streamCodec)
        implements RegistryCodecGroup<T> {

    public RegistryCodecGroupImpl(ResourceKey<Registry<T>> registryKey, Codec<T> codec, Codec<T> networkCodec) {
        this(
                registryKey,
                codec,
                networkCodec,
                RegistryFixedCodec.create(registryKey),
                ByteBufCodecs.holderRegistry(registryKey)
        );
    }

    public record SerializedImpl<T>(ResourceKey<Registry<T>> key,
                                    Codec<T> codec,
                                    Codec<T> networkCodec,
                                    Codec<Holder<T>> holderCodec,
                                    StreamCodec<RegistryFriendlyByteBuf, Holder<T>> streamCodec,
                                    EntityDataSerializer<Holder<T>> dataSerializer)
            implements Serialized<T> {

        public SerializedImpl(ResourceKey<Registry<T>> registryKey,
                              Codec<T> codec,
                              Codec<T> networkCodec,
                              Codec<Holder<T>> entryCodec,
                              StreamCodec<RegistryFriendlyByteBuf, Holder<T>> packetCodec) {
            this(registryKey, codec, networkCodec, entryCodec, packetCodec, EntityDataSerializer.forValueType(packetCodec));
            FabricEntityDataRegistry.register(registryKey.identifier(), this.dataSerializer);
        }

        public SerializedImpl(ResourceKey<Registry<T>> registryKey, Codec<T> codec, Codec<T> networkCodec) {
            this(
                    registryKey,
                    codec,
                    networkCodec,
                    RegistryFixedCodec.create(registryKey),
                    ByteBufCodecs.holderRegistry(registryKey)
            );
        }
    }
}
