package com.eightsidedsquare.zine.common.util.codec;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.*;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.stream.Stream;

public class MutableObjectMapCodec<A> extends MapCodec<A> {

    private final MapCodec<A> baseCodec;
    private final List<MutableObjectCodec.Field<A, ?>> fields;

    private MutableObjectMapCodec(MapCodec<A> baseCodec, List<MutableObjectCodec.Field<A, ?>> fields) {
        this.baseCodec = baseCodec;
        this.fields = fields;
    }

    public static <A> Builder<A> builder(MapCodec<A> baseCodec) {
        return new Builder<>(baseCodec);
    }

    @Override
    public <T> Stream<T> keys(DynamicOps<T> ops) {
        List<Stream<T>> streams = new ArrayList<>();
        streams.add(this.baseCodec.keys(ops));
        this.fields.forEach(field -> streams.add(field.mapCodec().keys(ops)));
        return streams.stream().flatMap(Function.identity());
    }

    @Override
    public <T> DataResult<A> decode(DynamicOps<T> ops, MapLike<T> input) {
        DataResult<A> baseResult = this.baseCodec.decode(ops, input);
        if(!baseResult.hasResultOrPartial()) {
            return baseResult;
        }
        A instance = baseResult.getPartialOrThrow();
        for (MutableObjectCodec.Field<A, ?> field : this.fields) {
            field.decode(instance, ops, input);
        }
        return baseResult;
    }

    @Override
    public <T> RecordBuilder<T> encode(A input, DynamicOps<T> ops, RecordBuilder<T> prefix) {
        RecordBuilder<T> baseResult = this.baseCodec.encode(input, ops, prefix);
        for (MutableObjectCodec.Field<A, ?> field : this.fields) {
            field.encode(input, prefix, ops);
        }
        return baseResult;
    }

    public static class Builder<A> {
        private final MapCodec<A> baseCodec;
        private final ImmutableList.Builder<MutableObjectCodec.Field<A, ?>> fields = ImmutableList.builder();

        private Builder(MapCodec<A> baseCodec) {
            this.baseCodec = baseCodec;
        }

        public <T> Builder<A> field(MapCodec<T> mapCodec, Function<A, T> getter, BiConsumer<A, T> setter) {
            this.fields.add(new MutableObjectCodec.Field<>(mapCodec, getter, setter));
            return this;
        }

        public <T> Builder<A> field(String key, Codec<T> codec, Function<A, T> getter, BiConsumer<A, T> setter) {
            return this.field(codec.fieldOf(key), getter, setter);
        }

        public MutableObjectMapCodec<A> build() {
            return new MutableObjectMapCodec<>(this.baseCodec, this.fields.build());
        }
    }
}
