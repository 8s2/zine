package com.eightsidedsquare.zine.common.util.codec;

import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.DynamicOps;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;

public class AlternativeCodec<A> implements Codec<A> {
    private final List<Entry<A, ?>> entries;

    private AlternativeCodec(List<Entry<A, ?>> entries) {
        this.entries = entries;
    }

    public static <A, B> Entry<A, B> entry(Codec<B> codec, Function<A, Optional<B>> to, Function<B, A> from) {
        return new Entry<>(codec, to, from);
    }

    public static <A> Entry<A, A> entry(Codec<A> codec) {
        return new Entry<>(codec, Optional::of, Function.identity());
    }

    public static <A> Codec<A> create(List<Entry<A, ?>> entries) {
        return new AlternativeCodec<>(entries);
    }

    public static <A, B> Codec<A> create(Codec<A> codecA,
                                         Codec<B> codecB, Function<A, Optional<B>> toB, Function<B, A> fromB) {
        return create(List.of(
                entry(codecB, toB, fromB),
                entry(codecA)
        ));
    }

    public static <A, B, C> Codec<A> create(Codec<A> codecA,
                                            Codec<B> codecB, Function<A, Optional<B>> toB, Function<B, A> fromB,
                                            Codec<C> codecC, Function<A, Optional<C>> toC, Function<C, A> fromC) {
        return create(List.of(
                entry(codecB, toB, fromB),
                entry(codecC, toC, fromC),
                entry(codecA)
        ));
    }

    public static <A, B, C, D> Codec<A> create(Codec<A> codecA,
                                               Codec<B> codecB, Function<A, Optional<B>> toB, Function<B, A> fromB,
                                               Codec<C> codecC, Function<A, Optional<C>> toC, Function<C, A> fromC,
                                               Codec<D> codecD, Function<A, Optional<D>> toD, Function<D, A> fromD) {
        return create(List.of(
                entry(codecB, toB, fromB),
                entry(codecC, toC, fromC),
                entry(codecD, toD, fromD),
                entry(codecA)
        ));
    }

    public static <A, B, C, D, E> Codec<A> create(Codec<A> codecA,
                                                  Codec<B> codecB, Function<A, Optional<B>> toB, Function<B, A> fromB,
                                                  Codec<C> codecC, Function<A, Optional<C>> toC, Function<C, A> fromC,
                                                  Codec<D> codecD, Function<A, Optional<D>> toD, Function<D, A> fromD,
                                                  Codec<E> codecE, Function<A, Optional<E>> toE, Function<E, A> fromE) {
        return create(List.of(
                entry(codecB, toB, fromB),
                entry(codecC, toC, fromC),
                entry(codecD, toD, fromD),
                entry(codecE, toE, fromE),
                entry(codecA)
        ));
    }

    public static <A, B, C, D, E, F> Codec<A> create(Codec<A> codecA,
                                                     Codec<B> codecB, Function<A, Optional<B>> toB, Function<B, A> fromB,
                                                     Codec<C> codecC, Function<A, Optional<C>> toC, Function<C, A> fromC,
                                                     Codec<D> codecD, Function<A, Optional<D>> toD, Function<D, A> fromD,
                                                     Codec<E> codecE, Function<A, Optional<E>> toE, Function<E, A> fromE,
                                                     Codec<F> codecF, Function<A, Optional<F>> toF, Function<F, A> fromF) {
        return create(List.of(
                entry(codecB, toB, fromB),
                entry(codecC, toC, fromC),
                entry(codecD, toD, fromD),
                entry(codecE, toE, fromE),
                entry(codecF, toF, fromF),
                entry(codecA)
        ));
    }

    @Override
    public <T> DataResult<Pair<A, T>> decode(DynamicOps<T> ops, T input) {
        List<DataResult<Pair<A, T>>> dataResults = new ObjectArrayList<>(this.entries.size());
        for(Entry<A, ?> entry : this.entries) {
            DataResult<Pair<A, T>> dataResult = entry.decode(ops, input);
            if(dataResult.isSuccess()) {
                return dataResult;
            }
            dataResults.add(dataResult);
        }
        for(DataResult<Pair<A, T>> dataResult : dataResults) {
            if(dataResult.hasResultOrPartial()) {
                return dataResult;
            }
        }
        return DataResult.error(this.error(dataResults));
    }

    @Override
    public <T> DataResult<T> encode(A input, DynamicOps<T> ops, T prefix) {
        List<DataResult<T>> dataResults = new ObjectArrayList<>(this.entries.size());
        for(Entry<A, ?> entry : this.entries) {
            DataResult<T> dataResult = entry.encode(input, ops, prefix);
            if(dataResult.isSuccess()) {
                return dataResult;
            }
            dataResults.add(dataResult);
        }
        for(DataResult<T> dataResult : dataResults) {
            if(dataResult.hasResultOrPartial()) {
                return dataResult;
            }
        }
        return DataResult.error(this.error(dataResults));
    }

    private Supplier<String> error(List<? extends DataResult<?>> dataResults) {
        StringBuilder stringBuilder = new StringBuilder("Failed to parse alternative codec. ");
        for(int i = 0; i < dataResults.size(); i++) {
            stringBuilder.append(i + 1);
            stringBuilder.append(": ");
            stringBuilder.append(dataResults.get(i).error().orElseThrow().message());
            stringBuilder.append("; ");
        }
        return stringBuilder::toString;
    }

    public record Entry<A, B>(Codec<B> codec, Function<A, Optional<B>> to, Function<B, A> from) {
        public <T> DataResult<Pair<A, T>> decode(DynamicOps<T> ops, T input) {
            return this.codec.decode(ops, input).map(pair -> pair.mapFirst(this.from));
        }

        public <T> DataResult<T> encode(A input, DynamicOps<T> ops, T prefix) {
            return this.to.apply(input)
                    .map(mappedInput -> this.codec.encode(mappedInput, ops, prefix))
                    .orElseGet(this::error);
        }

        private <T> DataResult<T> error() {
            return DataResult.error(() -> "Failed to map input");
        }
    }
}