package com.eightsidedsquare.zine.common.util.codec;

import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.*;

import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Function;

public class MutableObjectCodec<A> implements Codec<A> {

    private final Codec<A> baseCodec;
    private final List<Field<A, ?>> fields;

    private MutableObjectCodec(Codec<A> baseCodec, List<Field<A, ?>> fields) {
        this.baseCodec = baseCodec;
        this.fields = fields;
    }

    public static <A> Builder<A> builder(Codec<A> baseCodec) {
        return new Builder<>(baseCodec);
    }

    @Override
    public <T> DataResult<Pair<A, T>> decode(DynamicOps<T> ops, T input) {
        DataResult<Pair<A, T>> baseResult = this.baseCodec.decode(ops, input);
        if(!baseResult.hasResultOrPartial()) {
            return baseResult;
        }
        Pair<A, T> pair = baseResult.getPartialOrThrow();
        for (Field<A, ?> field : this.fields) {
            field.decode(pair, ops);
        }
        return baseResult;
    }

    @Override
    public <T> DataResult<T> encode(A input, DynamicOps<T> ops, T prefix) {
        DataResult<T> baseResult = this.baseCodec.encode(input, ops, prefix);
        if(!baseResult.hasResultOrPartial()) {
            return baseResult;
        }
        RecordBuilder<T> recordBuilder = ops.mapBuilder();
        for (Field<A, ?> field : this.fields) {
            field.encode(input, recordBuilder, ops);
        }
        return recordBuilder.build(baseResult);
    }

    public static class Builder<A> {
        private final Codec<A> baseCodec;
        private final ImmutableList.Builder<Field<A, ?>> fields = ImmutableList.builder();

        private Builder(Codec<A> baseCodec) {
            this.baseCodec = baseCodec;
        }

        public <T> Builder<A> field(MapCodec<T> mapCodec, Function<A, T> getter, BiConsumer<A, T> setter) {
            this.fields.add(new Field<>(mapCodec, getter, setter));
            return this;
        }

        public <T> Builder<A> field(String key, Codec<T> codec, Function<A, T> getter, BiConsumer<A, T> setter) {
            return this.field(codec.fieldOf(key), getter, setter);
        }

        public MutableObjectCodec<A> build() {
            return new MutableObjectCodec<>(this.baseCodec, this.fields.build());
        }
    }

    record Field<A, V>(MapCodec<V> mapCodec, Function<A, V> getter, BiConsumer<A, V> setter) {
        <T> void decode(Pair<A, T> pair, DynamicOps<T> ops) {
            ops.getMap(pair.getSecond())
                    .flatMap(map -> this.mapCodec.decode(ops, map))
                    .ifSuccess(value -> this.setter.accept(pair.getFirst(), value));
        }

        <T> void decode(A instance, DynamicOps<T> ops, MapLike<T> input) {
            this.mapCodec.decode(ops, input).ifSuccess(value -> this.setter.accept(instance, value));
        }

        <T> void encode(A input, RecordBuilder<T> recordBuilder, DynamicOps<T> ops) {
            this.mapCodec.encode(this.getter.apply(input), ops, recordBuilder);
        }
    }
}
