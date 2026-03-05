package com.eightsidedsquare.zine.common.util.network;

import com.google.common.collect.ImmutableList;
import io.netty.buffer.ByteBuf;
import net.minecraft.core.IdMapper;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.core.component.TypedDataComponent;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.SkipPacketDecoderException;
import net.minecraft.network.VarInt;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.util.ByIdMap;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateHolder;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.AABB;
import org.apache.commons.lang3.mutable.*;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.function.Predicate;
import java.util.function.Supplier;

public final class StreamCodecUtil {

    public static final StreamCodec<FriendlyByteBuf, BlockState> BLOCK_STATE = state(Block.BLOCK_STATE_REGISTRY, Block::getId);
    public static final StreamCodec<FriendlyByteBuf, FluidState> FLUID_STATE = state(Fluid.FLUID_STATE_REGISTRY);
    public static final StreamCodec<RegistryFriendlyByteBuf, DataComponentMap> COMPONENT_MAP = TypedDataComponent.STREAM_CODEC.apply(ByteBufCodecs.list()).map(
            components -> {
                DataComponentMap.Builder builder = DataComponentMap.builder();
                components.forEach(builder::zine$add);
                return builder.build();
            },
            componentMap -> ImmutableList.copyOf(componentMap.iterator())
    );
    public static final StreamCodec<ByteBuf, AABB> AABB = StreamCodec.composite(
            ByteBufCodecs.DOUBLE,
            box -> box.minX,
            ByteBufCodecs.DOUBLE,
            box -> box.minY,
            ByteBufCodecs.DOUBLE,
            box -> box.minZ,
            ByteBufCodecs.DOUBLE,
            box -> box.maxX,
            ByteBufCodecs.DOUBLE,
            box -> box.maxY,
            ByteBufCodecs.DOUBLE,
            box -> box.maxZ,
            AABB::new
    );
    public static final StreamCodec<ByteBuf, BoundingBox> BOUNDING_BOX = StreamCodec.composite(
            ByteBufCodecs.VAR_INT,
            BoundingBox::minX,
            ByteBufCodecs.VAR_INT,
            BoundingBox::minY,
            ByteBufCodecs.VAR_INT,
            BoundingBox::minZ,
            ByteBufCodecs.VAR_INT,
            BoundingBox::maxX,
            ByteBufCodecs.VAR_INT,
            BoundingBox::maxY,
            ByteBufCodecs.VAR_INT,
            BoundingBox::maxZ,
            BoundingBox::new
    );
    public static final StreamCodec<ByteBuf, MutableBoolean> MUTABLE_BOOLEAN = ByteBufCodecs.BOOL.map(MutableBoolean::new, MutableBoolean::booleanValue);
    public static final StreamCodec<ByteBuf, MutableByte> MUTABLE_BYTE = ByteBufCodecs.BYTE.map(MutableByte::new, MutableByte::byteValue);
    public static final StreamCodec<ByteBuf, MutableShort> MUTABLE_SHORT = ByteBufCodecs.SHORT.map(MutableShort::new, MutableShort::shortValue);
    public static final StreamCodec<ByteBuf, MutableInt> MUTABLE_INT = ByteBufCodecs.INT.map(MutableInt::new, MutableInt::intValue);
    public static final StreamCodec<ByteBuf, MutableInt> MUTABLE_VAR_INT = ByteBufCodecs.VAR_INT.map(MutableInt::new, MutableInt::intValue);
    public static final StreamCodec<ByteBuf, MutableLong> MUTABLE_LONG = ByteBufCodecs.LONG.map(MutableLong::new, MutableLong::longValue);
    public static final StreamCodec<ByteBuf, MutableLong> MUTABLE_VAR_LONG = ByteBufCodecs.VAR_LONG.map(MutableLong::new, MutableLong::longValue);
    public static final StreamCodec<ByteBuf, MutableFloat> MUTABLE_FLOAT = ByteBufCodecs.FLOAT.map(MutableFloat::new, MutableFloat::floatValue);
    public static final StreamCodec<ByteBuf, MutableDouble> MUTABLE_DOUBLE = ByteBufCodecs.DOUBLE.map(MutableDouble::new, MutableDouble::doubleValue);
    public static final StreamCodec<ByteBuf, OptionalInt> OPTIONAL_INT = primitiveOptional(ByteBufCodecs.INT, OptionalInt::of, OptionalInt::empty, OptionalInt::isPresent, OptionalInt::getAsInt);
    public static final StreamCodec<ByteBuf, OptionalInt> OPTIONAL_VAR_INT = primitiveOptional(ByteBufCodecs.VAR_INT, OptionalInt::of, OptionalInt::empty, OptionalInt::isPresent, OptionalInt::getAsInt);
    public static final StreamCodec<ByteBuf, OptionalLong> OPTIONAL_LONG = primitiveOptional(ByteBufCodecs.LONG, OptionalLong::of, OptionalLong::empty, OptionalLong::isPresent, OptionalLong::getAsLong);
    public static final StreamCodec<ByteBuf, OptionalLong> OPTIONAL_VAR_LONG = primitiveOptional(ByteBufCodecs.VAR_LONG, OptionalLong::of, OptionalLong::empty, OptionalLong::isPresent, OptionalLong::getAsLong);
    public static final StreamCodec<ByteBuf, OptionalDouble> OPTIONAL_DOUBLE = primitiveOptional(ByteBufCodecs.DOUBLE, OptionalDouble::of, OptionalDouble::empty, OptionalDouble::isPresent, OptionalDouble::getAsDouble);

    public static <B, V> StreamCodec<B, MutableObject<V>> mutable(StreamCodec<B, V> streamCodec) {
        return streamCodec.map(MutableObject::new, MutableObject::get);
    }

    public static <T extends StateHolder<?, ?>> StreamCodec<FriendlyByteBuf, T> state(IdMapper<T> idList, @Nullable Function<T, Integer> idGetter) {
        Function<T, Integer> finalIdGetter = idGetter != null ? idGetter : state -> {
            int id = idList.getId(state);
            return id == -1 ? 0 : id;
        };
        return StreamCodec.ofMember(
                (state, buf) -> buf.writeVarInt(finalIdGetter.apply(state)),
                buf -> {
                    int id = buf.readVarInt();
                    T state = idList.byId(id);
                    if(state == null) {
                        throw new SkipPacketDecoderException("Unknown state with id " + id);
                    }
                    return state;
                }
        );
    }

    public static <T extends StateHolder<?, ?>> StreamCodec<FriendlyByteBuf, T> state(IdMapper<T> idList) {
        return state(idList, null);
    }

    public static <B extends ByteBuf, K, V, M extends Map<K, V>> StreamCodec<B, M> dispatchedMap(
            IntFunction<? extends M> factory,
            StreamCodec<? super B, K> keyCodec,
            Function<K, StreamCodec<? super B, V>> valueCodecFunction,
            int maxSize) {
        return new StreamCodec<>() {
            @Override
            public void encode(B buf, M map) {
                ByteBufCodecs.writeCount(buf, map.size(), maxSize);
                map.forEach((k, v) -> {
                    keyCodec.encode(buf, k);
                    valueCodecFunction.apply(k).encode(buf, v);
                });
            }

            @Override
            public M decode(B buf) {
                int size = ByteBufCodecs.readCount(buf, maxSize);
                M map = factory.apply(Math.min(size, 65536));
                for (int i = 0; i < size; i++) {
                    K key = keyCodec.decode(buf);
                    map.put(key, valueCodecFunction.apply(key).decode(buf));
                }
                return map;
            }
        };
    }

    public static <B extends ByteBuf, K, V, M extends Map<K, V>> StreamCodec<B, M> dispatchedMap(
            IntFunction<? extends M> factory,
            StreamCodec<? super B, K> keyCodec,
            Function<K, StreamCodec<? super B, V>> valueCodecFunction) {
        return dispatchedMap(factory, keyCodec, valueCodecFunction, Integer.MAX_VALUE);
    }

    public static <T extends Enum<T>> StreamCodec<ByteBuf, T> ofEnum(T[] values) {
        return ByteBufCodecs.idMapper(
                ByIdMap.continuous(T::ordinal, values, ByIdMap.OutOfBoundsStrategy.ZERO),
                T::ordinal
        );
    }

    @SuppressWarnings("unchecked")
    public static <B extends ByteBuf, T> StreamCodec<B, T> sealedDispatch(
            List<StreamCodec<? super B, ? extends T>> streamCodecs,
            Function<T, StreamCodec<? super B, ? extends T>> streamCodecFunction) {
        return new StreamCodec<>() {
            @Override
            public T decode(B input) {
                StreamCodec<? super B, T> streamCodec = (StreamCodec<? super B, T>) streamCodecs.get(VarInt.read(input));
                return streamCodec.decode(input);
            }

            @Override
            public void encode(B output, T value) {
                StreamCodec<? super B, T> streamCodec = (StreamCodec<? super B, T>) streamCodecFunction.apply(value);
                VarInt.write(output, streamCodecs.indexOf(streamCodec));
                streamCodec.encode(output, value);
            }
        };
    }

    public static <P, O> StreamCodec<ByteBuf, O> primitiveOptional(
            StreamCodec<ByteBuf, P> streamCodec,
            Function<P, O> presentFactory,
            Supplier<O> emptyFactory,
            Predicate<O> presentPredicate,
            Function<O, P> valueGetter
    ) {
        return new StreamCodec<>() {
            @Override
            public O decode(ByteBuf input) {
                return input.readBoolean() ? presentFactory.apply(streamCodec.decode(input)) : emptyFactory.get();
            }

            @Override
            public void encode(ByteBuf output, O value) {
                if (presentPredicate.test(value)) {
                    output.writeBoolean(true);
                    streamCodec.encode(output, valueGetter.apply(value));
                }else {
                    output.writeBoolean(false);
                }
            }
        };
    }

    private StreamCodecUtil() {
    }
}
