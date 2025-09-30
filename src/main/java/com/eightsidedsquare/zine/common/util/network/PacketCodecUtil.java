package com.eightsidedsquare.zine.common.util.network;

import com.google.common.collect.ImmutableList;
import io.netty.buffer.ByteBuf;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.component.Component;
import net.minecraft.component.ComponentMap;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.handler.PacketDecoderException;
import net.minecraft.state.State;
import net.minecraft.util.collection.IdList;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.Box;
import org.apache.commons.lang3.mutable.*;
import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.function.Function;
import java.util.function.IntFunction;

public final class PacketCodecUtil {

    public static final PacketCodec<PacketByteBuf, BlockState> BLOCK_STATE = state(Block.STATE_IDS, Block::getRawIdFromState);
    public static final PacketCodec<PacketByteBuf, FluidState> FLUID_STATE = state(Fluid.STATE_IDS);
    public static final PacketCodec<RegistryByteBuf, ComponentMap> COMPONENT_MAP = Component.PACKET_CODEC.collect(PacketCodecs.toList()).xmap(
            components -> {
                ComponentMap.Builder builder = ComponentMap.builder();
                components.forEach(builder::zine$add);
                return builder.build();
            },
            componentMap -> ImmutableList.copyOf(componentMap.iterator())
    );
    public static final PacketCodec<ByteBuf, Box> BOX = PacketCodec.tuple(
            PacketCodecs.DOUBLE,
            box -> box.minX,
            PacketCodecs.DOUBLE,
            box -> box.minY,
            PacketCodecs.DOUBLE,
            box -> box.minZ,
            PacketCodecs.DOUBLE,
            box -> box.maxX,
            PacketCodecs.DOUBLE,
            box -> box.maxY,
            PacketCodecs.DOUBLE,
            box -> box.maxZ,
            Box::new
    );
    public static final PacketCodec<ByteBuf, BlockBox> BLOCK_BOX = PacketCodec.tuple(
            PacketCodecs.VAR_INT,
            BlockBox::getMinX,
            PacketCodecs.VAR_INT,
            BlockBox::getMinY,
            PacketCodecs.VAR_INT,
            BlockBox::getMinZ,
            PacketCodecs.VAR_INT,
            BlockBox::getMaxX,
            PacketCodecs.VAR_INT,
            BlockBox::getMaxY,
            PacketCodecs.VAR_INT,
            BlockBox::getMaxZ,
            BlockBox::new
    );
    public static final PacketCodec<ByteBuf, MutableBoolean> MUTABLE_BOOLEAN = PacketCodecs.BOOLEAN.xmap(MutableBoolean::new, MutableBoolean::booleanValue);
    public static final PacketCodec<ByteBuf, MutableByte> MUTABLE_BYTE = PacketCodecs.BYTE.xmap(MutableByte::new, MutableByte::byteValue);
    public static final PacketCodec<ByteBuf, MutableShort> MUTABLE_SHORT = PacketCodecs.SHORT.xmap(MutableShort::new, MutableShort::shortValue);
    public static final PacketCodec<ByteBuf, MutableInt> MUTABLE_INT = PacketCodecs.INTEGER.xmap(MutableInt::new, MutableInt::intValue);
    public static final PacketCodec<ByteBuf, MutableInt> MUTABLE_VAR_INT = PacketCodecs.VAR_INT.xmap(MutableInt::new, MutableInt::intValue);
    public static final PacketCodec<ByteBuf, MutableLong> MUTABLE_LONG = PacketCodecs.LONG.xmap(MutableLong::new, MutableLong::longValue);
    public static final PacketCodec<ByteBuf, MutableLong> MUTABLE_VAR_LONG = PacketCodecs.VAR_LONG.xmap(MutableLong::new, MutableLong::longValue);
    public static final PacketCodec<ByteBuf, MutableFloat> MUTABLE_FLOAT = PacketCodecs.FLOAT.xmap(MutableFloat::new, MutableFloat::floatValue);
    public static final PacketCodec<ByteBuf, MutableDouble> MUTABLE_DOUBLE = PacketCodecs.DOUBLE.xmap(MutableDouble::new, MutableDouble::doubleValue);

    public static <B, V> PacketCodec<B, MutableObject<V>> mutable(PacketCodec<B, V> packetCodec) {
        return packetCodec.xmap(MutableObject::new, MutableObject::getValue);
    }

    public static <T extends State<?, ?>> PacketCodec<PacketByteBuf, T> state(IdList<T> idList, @Nullable Function<T, Integer> idGetter) {
        Function<T, Integer> finalIdGetter = idGetter != null ? idGetter : state -> {
            int id = idList.getRawId(state);
            return id == -1 ? 0 : id;
        };
        return PacketCodec.of(
                (state, buf) -> buf.writeVarInt(finalIdGetter.apply(state)),
                buf -> {
                    int id = buf.readVarInt();
                    T state = idList.get(id);
                    if(state == null) {
                        throw new PacketDecoderException("Unknown state with id " + id);
                    }
                    return state;
                }
        );
    }

    public static <T extends State<?, ?>> PacketCodec<PacketByteBuf, T> state(IdList<T> idList) {
        return state(idList, null);
    }

    public static <B extends ByteBuf, K, V, M extends Map<K, V>> PacketCodec<B, M> dispatchedMap(
            IntFunction<? extends M> factory,
            PacketCodec<? super B, K> keyCodec,
            Function<K, PacketCodec<? super B, V>> valueCodecFunction,
            int maxSize) {
        return new PacketCodec<>() {
            @Override
            public void encode(B buf, M map) {
                PacketCodecs.writeCollectionSize(buf, map.size(), maxSize);
                map.forEach((k, v) -> {
                    keyCodec.encode(buf, k);
                    valueCodecFunction.apply(k).encode(buf, v);
                });
            }

            @Override
            public M decode(B buf) {
                int size = PacketCodecs.readCollectionSize(buf, maxSize);
                M map = factory.apply(Math.min(size, 65536));
                for (int i = 0; i < size; i++) {
                    K key = keyCodec.decode(buf);
                    map.put(key, valueCodecFunction.apply(key).decode(buf));
                }
                return map;
            }
        };
    }

    public static <B extends ByteBuf, K, V, M extends Map<K, V>> PacketCodec<B, M> dispatchedMap(
            IntFunction<? extends M> factory,
            PacketCodec<? super B, K> keyCodec,
            Function<K, PacketCodec<? super B, V>> valueCodecFunction) {
        return dispatchedMap(factory, keyCodec, valueCodecFunction, Integer.MAX_VALUE);
    }

    private PacketCodecUtil() {
    }
}
