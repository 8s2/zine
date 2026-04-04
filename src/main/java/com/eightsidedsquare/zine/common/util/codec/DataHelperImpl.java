package com.eightsidedsquare.zine.common.util.codec;

import com.eightsidedsquare.zine.common.util.network.StreamCodecUtil;
import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.UUIDUtil;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.nbt.Tag;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Function;

public class DataHelperImpl<T> implements DataHelper<T> {

    private final List<DataHelper<T>> fields;

    public DataHelperImpl(List<DataHelper<T>> fields) {
        this.fields = fields;
    }

    @Override
    public void read(ValueInput view, T object) {
        for (DataHelper<T> field : this.fields) {
            field.read(view, object);
        }
    }

    @Override
    public void write(ValueOutput view, T object) {
        for (DataHelper<T> field : this.fields) {
            field.write(view, object);
        }
    }

    @Override
    public <I extends RegistryFriendlyByteBuf> void read(I buf, T object) {
        for (DataHelper<T> field : this.fields) {
            field.read(buf, object);
        }
    }

    @Override
    public <I extends RegistryFriendlyByteBuf> void write(I buf, T object) {
        for (DataHelper<T> field : this.fields) {
            field.write(buf, object);
        }
    }

    static class BuilderImpl<T> implements Builder<T> {

        private final ImmutableList.Builder<DataHelper<T>> fields = ImmutableList.builder();

        private Builder<T> add(DataHelper<T> field) {
            this.fields.add(field);
            return this;
        }

        @Override
        public <F> FieldBuilder<F, T> field(@Nullable Codec<F> codec, @Nullable StreamCodec<? super RegistryFriendlyByteBuf, F> packetCodec, String key) {
            return (defaultValue, getter, setter) ->
                    this.add(new Field<>(codec, packetCodec, key, defaultValue, getter, setter));
        }

        @Override
        public <F> NullableFieldBuilder<F, T> nullableField(@Nullable Codec<F> codec, @Nullable StreamCodec<? super RegistryFriendlyByteBuf, F> packetCodec, String key) {
            return (defaultValue, getter, setter) -> this.field(
                            codec == null ? null : ExtraCodecs.optionalEmptyMap(codec),
                            packetCodec == null ? null : ByteBufCodecs.optional(packetCodec.cast()),
                            key
                    )
                    .apply(
                            t -> Optional.ofNullable(defaultValue.apply(t)),
                            t -> Optional.ofNullable(getter.apply(t)),
                            (t, f) -> setter.accept(t, f.orElse(null))
                    );
        }

        @Override
        public <F, L extends Collection<F>> ListFieldBuilder<F, L, T> listField(@Nullable Codec<L> codec, @Nullable StreamCodec<? super RegistryFriendlyByteBuf, L> packetCodec, String key) {
            return getter -> this.add(new ListField<>(codec, packetCodec, key, getter));
        }

        @Override
        public <F> ListFieldBuilder<F, List<F>, T> listFieldOf(@Nullable Codec<F> codec, @Nullable StreamCodec<? super RegistryFriendlyByteBuf, F> packetCodec, String key) {
            return this.listField(
                    codec == null ? null : codec.listOf(),
                    packetCodec == null ? null : ByteBufCodecs.collection(ArrayList::new, packetCodec),
                    key
            );
        }

        @Override
        public <K, V, M extends Map<K, V>> MapFieldBuilder<K, V, M, T> mapField(@Nullable Codec<M> codec, @Nullable StreamCodec<? super RegistryFriendlyByteBuf, M> packetCodec, String key) {
            return getter -> this.add(new MapField<>(codec, packetCodec, key, getter));
        }

        @Override
        public <K, V> MapFieldBuilder<K, V, Map<K, V>, T> mapFieldOf(@Nullable Codec<K> keyCodec, @Nullable Codec<V> elementCodec, @Nullable StreamCodec<? super RegistryFriendlyByteBuf, K> keyPacketCodec, @Nullable StreamCodec<? super RegistryFriendlyByteBuf, V> elementPacketCodec, String key) {
            return this.mapField(
                    keyCodec == null || elementCodec == null ? null : Codec.unboundedMap(keyCodec, elementCodec),
                    keyPacketCodec == null || elementPacketCodec == null ? null : ByteBufCodecs.map(HashMap::new, keyPacketCodec, elementPacketCodec),
                    key
            );
        }

        @Override
        public FieldBuilder<Boolean, T> booleanField(String key) {
            return this.field(Codec.BOOL, ByteBufCodecs.BOOL, key);
        }

        @Override
        public FieldBuilder<Byte, T> byteField(String key) {
            return this.field(Codec.BYTE, ByteBufCodecs.BYTE, key);
        }

        @Override
        public FieldBuilder<Short, T> shortField(String key) {
            return this.field(Codec.SHORT, ByteBufCodecs.SHORT, key);
        }

        @Override
        public FieldBuilder<Integer, T> intField(String key) {
            return this.field(Codec.INT, ByteBufCodecs.VAR_INT, key);
        }

        @Override
        public FieldBuilder<Long, T> longField(String key) {
            return this.field(Codec.LONG, ByteBufCodecs.VAR_LONG, key);
        }

        @Override
        public FieldBuilder<Float, T> floatField(String key) {
            return this.field(Codec.FLOAT, ByteBufCodecs.FLOAT, key);
        }

        @Override
        public FieldBuilder<Double, T> doubleField(String key) {
            return this.field(Codec.DOUBLE, ByteBufCodecs.DOUBLE, key);
        }

        @Override
        public FieldBuilder<String, T> stringField(String key) {
            return this.field(Codec.STRING, ByteBufCodecs.STRING_UTF8, key);
        }

        @Override
        public FieldBuilder<Tag, T> nbtElementField(String key) {
            return this.field(ExtraCodecs.NBT, ByteBufCodecs.TAG, key);
        }

        @Override
        public FieldBuilder<UUID, T> uuidField(String key) {
            return this.field(UUIDUtil.CODEC, UUIDUtil.STREAM_CODEC, key);
        }

        @Override
        public FieldBuilder<BlockPos, T> blockPosField(String key) {
            return this.field(BlockPos.CODEC, BlockPos.STREAM_CODEC, key);
        }

        @Override
        public FieldBuilder<BlockState, T> blockStateField(String key) {
            return this.field(BlockState.CODEC, StreamCodecUtil.BLOCK_STATE, key);
        }

        @Override
        public FieldBuilder<DataComponentMap, T> componentMapField(String key) {
            return this.field(DataComponentMap.CODEC, StreamCodecUtil.COMPONENT_MAP, key);
        }

        @Override
        public DataHelper<T> build() {
            return new DataHelperImpl<>(this.fields.build());
        }
    }

    static abstract class AbstractField<F, T> implements DataHelper<T> {
        @Nullable final Codec<F> codec;
        @Nullable final StreamCodec<? super RegistryFriendlyByteBuf, F> packetCodec;
        final String key;
        final Function<T, F> getter;

        AbstractField(@Nullable Codec<F> codec, @Nullable StreamCodec<? super RegistryFriendlyByteBuf, F> packetCodec, String key, Function<T, F> getter) {
            if(codec == null && packetCodec == null) {
                throw new IllegalArgumentException("Both codec and packet codec cannot be null for field " + key);
            }
            this.codec = codec;
            this.packetCodec = packetCodec;
            this.key = key;
            this.getter = getter;
        }

        abstract void read(ValueInput view, Codec<F> codec, T object);

        abstract void write(ValueOutput view, Codec<F> codec, T object);

        abstract <I extends RegistryFriendlyByteBuf> void read(I buf, StreamCodec<? super RegistryFriendlyByteBuf, F> packetCodec, T object);

        abstract <I extends RegistryFriendlyByteBuf> void write(I buf, StreamCodec<? super RegistryFriendlyByteBuf, F> packetCodec, T object);

        @Override
        public final void read(ValueInput view, T object) {
            if(this.codec != null) {
                this.read(view, this.codec, object);
            }
        }

        @Override
        public final void write(ValueOutput view, T object) {
            if(this.codec != null) {
                this.write(view, this.codec, object);
            }
        }

        @Override
        public final <I extends RegistryFriendlyByteBuf> void read(I buf, T object) {
            if(this.packetCodec != null) {
                this.read(buf, this.packetCodec, object);
            }
        }

        @Override
        public final <I extends RegistryFriendlyByteBuf> void write(I buf, T object) {
            if(this.packetCodec != null) {
                this.write(buf, this.packetCodec, object);
            }
        }
    }

    static final class Field<F, T> extends AbstractField<F, T> {
        private final Function<T, F> defaultValueGetter;
        private final BiConsumer<T, F> setter;

        Field(@Nullable Codec<F> codec, @Nullable StreamCodec<? super RegistryFriendlyByteBuf, F> packetCodec, String key, Function<T, F> defaultValueGetter, Function<T, F> getter, BiConsumer<T, F> setter) {
            super(codec, packetCodec, key, getter);
            this.defaultValueGetter = defaultValueGetter;
            this.setter = setter;
        }

        @Override
        void read(ValueInput view, Codec<F> codec, T object) {
            this.setter.accept(object, view.read(this.key, codec).orElse(this.defaultValueGetter.apply(object)));
        }

        @Override
        void write(ValueOutput view, Codec<F> codec, T object) {
            view.store(this.key, codec, this.getter.apply(object));
        }

        @Override
        <I extends RegistryFriendlyByteBuf> void read(I buf, StreamCodec<? super RegistryFriendlyByteBuf, F> packetCodec, T object) {
            this.setter.accept(object, packetCodec.decode(buf));
        }

        @Override
        <I extends RegistryFriendlyByteBuf> void write(I buf, StreamCodec<? super RegistryFriendlyByteBuf, F> packetCodec, T object) {
            packetCodec.encode(buf, this.getter.apply(object));
        }
    }

    static final class ListField<F, L extends Collection<F>, T> extends AbstractField<L, T> {

        ListField(@Nullable Codec<L> codec, @Nullable StreamCodec<? super RegistryFriendlyByteBuf, L> packetCodec, String key, Function<T, L> getter) {
            super(codec, packetCodec, key, getter);
        }

        @Override
        void read(ValueInput view, Codec<L> codec, T object) {
            L list = this.getter.apply(object);
            list.clear();
            view.read(this.key, codec).ifPresent(list::addAll);
        }

        @Override
        void write(ValueOutput view, Codec<L> codec, T object) {
            view.store(this.key, codec, this.getter.apply(object));
        }

        @Override
        <I extends RegistryFriendlyByteBuf> void read(I buf, StreamCodec<? super RegistryFriendlyByteBuf, L> packetCodec, T object) {
            L list = this.getter.apply(object);
            list.clear();
            list.addAll(packetCodec.decode(buf));
        }

        @Override
        <I extends RegistryFriendlyByteBuf> void write(I buf, StreamCodec<? super RegistryFriendlyByteBuf, L> packetCodec, T object) {
            packetCodec.encode(buf, this.getter.apply(object));
        }
    }

    static final class MapField<K, V, M extends Map<K, V>, T> extends AbstractField<M, T> {

        MapField(@Nullable Codec<M> codec, @Nullable StreamCodec<? super RegistryFriendlyByteBuf, M> packetCodec, String key, Function<T, M> getter) {
            super(codec, packetCodec, key, getter);
        }

        @Override
        void read(ValueInput view, Codec<M> codec, T object) {
            M map = this.getter.apply(object);
            map.clear();
            view.read(this.key, codec).ifPresent(map::putAll);
        }

        @Override
        void write(ValueOutput view, Codec<M> codec, T object) {
            view.store(this.key, codec, this.getter.apply(object));
        }

        @Override
        <I extends RegistryFriendlyByteBuf> void read(I buf, StreamCodec<? super RegistryFriendlyByteBuf, M> packetCodec, T object) {
            M map = this.getter.apply(object);
            map.clear();
            map.putAll(packetCodec.decode(buf));
        }

        @Override
        <I extends RegistryFriendlyByteBuf> void write(I buf, StreamCodec<? super RegistryFriendlyByteBuf, M> packetCodec, T object) {
            packetCodec.encode(buf, this.getter.apply(object));
        }

    }
}
