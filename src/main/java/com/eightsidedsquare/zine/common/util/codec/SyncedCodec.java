package com.eightsidedsquare.zine.common.util.codec;

import com.mojang.datafixers.util.*;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;

import java.util.function.BiFunction;
import java.util.function.Function;

public record SyncedCodec<A>(MapCodec<A> codec, StreamCodec<? super RegistryFriendlyByteBuf, A> streamCodec) {
    public <U> SyncedCodec<U> map(Function<? super A, ? extends U> to, Function<? super U, ? extends A> from) {
        return new SyncedCodec<>(this.codec.xmap(to, from), this.streamCodec.map(to, from));
    }

    public static <T> SyncedCodec<T> unit(T instance) {
        return new SyncedCodec<>(MapCodec.unit(instance), StreamCodec.unit(instance));
    }

    public static <A, T> SyncedCodec<A> create(
            MapCodec<T> codec,
            StreamCodec<? super RegistryFriendlyByteBuf, T> streamCodec,
            Function<A, T> getter,
            Function<T, A> constructor
    ) {
        return new SyncedCodec<>(
                RecordCodecBuilder.mapCodec(i -> i.group(
                        codec.forGetter(getter)
                ).apply(i, constructor)),
                StreamCodec.composite(
                        streamCodec,
                        getter,
                        constructor
                )
        );
    }

    public static <A, T1, T2> SyncedCodec<A> create(
            MapCodec<T1> codec1,
            StreamCodec<? super RegistryFriendlyByteBuf, T1> streamCodec1,
            Function<A, T1> getter1,
            MapCodec<T2> codec2,
            StreamCodec<? super RegistryFriendlyByteBuf, T2> streamCodec2,
            Function<A, T2> getter2,
            BiFunction<T1, T2, A> constructor
    ) {
        return new SyncedCodec<>(
                RecordCodecBuilder.mapCodec(i -> i.group(
                        codec1.forGetter(getter1),
                        codec2.forGetter(getter2)
                ).apply(i, constructor)),
                StreamCodec.composite(
                        streamCodec1,
                        getter1,
                        streamCodec2,
                        getter2,
                        constructor
                )
        );
    }

    public static <A, T1, T2, T3> SyncedCodec<A> create(
            MapCodec<T1> codec1,
            StreamCodec<? super RegistryFriendlyByteBuf, T1> streamCodec1,
            Function<A, T1> getter1,
            MapCodec<T2> codec2,
            StreamCodec<? super RegistryFriendlyByteBuf, T2> streamCodec2,
            Function<A, T2> getter2,
            MapCodec<T3> codec3,
            StreamCodec<? super RegistryFriendlyByteBuf, T3> streamCodec3,
            Function<A, T3> getter3,
            Function3<T1, T2, T3, A> constructor
    ) {
        return new SyncedCodec<>(
                RecordCodecBuilder.mapCodec(i -> i.group(
                        codec1.forGetter(getter1),
                        codec2.forGetter(getter2),
                        codec3.forGetter(getter3)
                ).apply(i, constructor)),
                StreamCodec.composite(
                        streamCodec1,
                        getter1,
                        streamCodec2,
                        getter2,
                        streamCodec3,
                        getter3,
                        constructor
                )
        );
    }

    public static <A, T1, T2, T3, T4> SyncedCodec<A> create(
            MapCodec<T1> codec1,
            StreamCodec<? super RegistryFriendlyByteBuf, T1> streamCodec1,
            Function<A, T1> getter1,
            MapCodec<T2> codec2,
            StreamCodec<? super RegistryFriendlyByteBuf, T2> streamCodec2,
            Function<A, T2> getter2,
            MapCodec<T3> codec3,
            StreamCodec<? super RegistryFriendlyByteBuf, T3> streamCodec3,
            Function<A, T3> getter3,
            MapCodec<T4> codec4,
            StreamCodec<? super RegistryFriendlyByteBuf, T4> streamCodec4,
            Function<A, T4> getter4,
            Function4<T1, T2, T3, T4, A> constructor
    ) {
        return new SyncedCodec<>(
                RecordCodecBuilder.mapCodec(i -> i.group(
                        codec1.forGetter(getter1),
                        codec2.forGetter(getter2),
                        codec3.forGetter(getter3),
                        codec4.forGetter(getter4)
                ).apply(i, constructor)),
                StreamCodec.composite(
                        streamCodec1,
                        getter1,
                        streamCodec2,
                        getter2,
                        streamCodec3,
                        getter3,
                        streamCodec4,
                        getter4,
                        constructor
                )
        );
    }

    public static <A, T1, T2, T3, T4, T5> SyncedCodec<A> create(
            MapCodec<T1> codec1,
            StreamCodec<? super RegistryFriendlyByteBuf, T1> streamCodec1,
            Function<A, T1> getter1,
            MapCodec<T2> codec2,
            StreamCodec<? super RegistryFriendlyByteBuf, T2> streamCodec2,
            Function<A, T2> getter2,
            MapCodec<T3> codec3,
            StreamCodec<? super RegistryFriendlyByteBuf, T3> streamCodec3,
            Function<A, T3> getter3,
            MapCodec<T4> codec4,
            StreamCodec<? super RegistryFriendlyByteBuf, T4> streamCodec4,
            Function<A, T4> getter4,
            MapCodec<T5> codec5,
            StreamCodec<? super RegistryFriendlyByteBuf, T5> streamCodec5,
            Function<A, T5> getter5,
            Function5<T1, T2, T3, T4, T5, A> constructor
    ) {
        return new SyncedCodec<>(
                RecordCodecBuilder.mapCodec(i -> i.group(
                        codec1.forGetter(getter1),
                        codec2.forGetter(getter2),
                        codec3.forGetter(getter3),
                        codec4.forGetter(getter4),
                        codec5.forGetter(getter5)
                ).apply(i, constructor)),
                StreamCodec.composite(
                        streamCodec1,
                        getter1,
                        streamCodec2,
                        getter2,
                        streamCodec3,
                        getter3,
                        streamCodec4,
                        getter4,
                        streamCodec5,
                        getter5,
                        constructor
                )
        );
    }

    public static <A, T1, T2, T3, T4, T5, T6> SyncedCodec<A> create(
            MapCodec<T1> codec1,
            StreamCodec<? super RegistryFriendlyByteBuf, T1> streamCodec1,
            Function<A, T1> getter1,
            MapCodec<T2> codec2,
            StreamCodec<? super RegistryFriendlyByteBuf, T2> streamCodec2,
            Function<A, T2> getter2,
            MapCodec<T3> codec3,
            StreamCodec<? super RegistryFriendlyByteBuf, T3> streamCodec3,
            Function<A, T3> getter3,
            MapCodec<T4> codec4,
            StreamCodec<? super RegistryFriendlyByteBuf, T4> streamCodec4,
            Function<A, T4> getter4,
            MapCodec<T5> codec5,
            StreamCodec<? super RegistryFriendlyByteBuf, T5> streamCodec5,
            Function<A, T5> getter5,
            MapCodec<T6> codec6,
            StreamCodec<? super RegistryFriendlyByteBuf, T6> streamCodec6,
            Function<A, T6> getter6,
            Function6<T1, T2, T3, T4, T5, T6, A> constructor
    ) {
        return new SyncedCodec<>(
                RecordCodecBuilder.mapCodec(i -> i.group(
                        codec1.forGetter(getter1),
                        codec2.forGetter(getter2),
                        codec3.forGetter(getter3),
                        codec4.forGetter(getter4),
                        codec5.forGetter(getter5),
                        codec6.forGetter(getter6)
                ).apply(i, constructor)),
                StreamCodec.composite(
                        streamCodec1,
                        getter1,
                        streamCodec2,
                        getter2,
                        streamCodec3,
                        getter3,
                        streamCodec4,
                        getter4,
                        streamCodec5,
                        getter5,
                        streamCodec6,
                        getter6,
                        constructor
                )
        );
    }

    public static <A, T1, T2, T3, T4, T5, T6, T7> SyncedCodec<A> create(
            MapCodec<T1> codec1,
            StreamCodec<? super RegistryFriendlyByteBuf, T1> streamCodec1,
            Function<A, T1> getter1,
            MapCodec<T2> codec2,
            StreamCodec<? super RegistryFriendlyByteBuf, T2> streamCodec2,
            Function<A, T2> getter2,
            MapCodec<T3> codec3,
            StreamCodec<? super RegistryFriendlyByteBuf, T3> streamCodec3,
            Function<A, T3> getter3,
            MapCodec<T4> codec4,
            StreamCodec<? super RegistryFriendlyByteBuf, T4> streamCodec4,
            Function<A, T4> getter4,
            MapCodec<T5> codec5,
            StreamCodec<? super RegistryFriendlyByteBuf, T5> streamCodec5,
            Function<A, T5> getter5,
            MapCodec<T6> codec6,
            StreamCodec<? super RegistryFriendlyByteBuf, T6> streamCodec6,
            Function<A, T6> getter6,
            MapCodec<T7> codec7,
            StreamCodec<? super RegistryFriendlyByteBuf, T7> streamCodec7,
            Function<A, T7> getter7,
            Function7<T1, T2, T3, T4, T5, T6, T7, A> constructor
    ) {
        return new SyncedCodec<>(
                RecordCodecBuilder.mapCodec(i -> i.group(
                        codec1.forGetter(getter1),
                        codec2.forGetter(getter2),
                        codec3.forGetter(getter3),
                        codec4.forGetter(getter4),
                        codec5.forGetter(getter5),
                        codec6.forGetter(getter6),
                        codec7.forGetter(getter7)
                ).apply(i, constructor)),
                StreamCodec.composite(
                        streamCodec1,
                        getter1,
                        streamCodec2,
                        getter2,
                        streamCodec3,
                        getter3,
                        streamCodec4,
                        getter4,
                        streamCodec5,
                        getter5,
                        streamCodec6,
                        getter6,
                        streamCodec7,
                        getter7,
                        constructor
                )
        );
    }

    public static <A, T1, T2, T3, T4, T5, T6, T7, T8> SyncedCodec<A> create(
            MapCodec<T1> codec1,
            StreamCodec<? super RegistryFriendlyByteBuf, T1> streamCodec1,
            Function<A, T1> getter1,
            MapCodec<T2> codec2,
            StreamCodec<? super RegistryFriendlyByteBuf, T2> streamCodec2,
            Function<A, T2> getter2,
            MapCodec<T3> codec3,
            StreamCodec<? super RegistryFriendlyByteBuf, T3> streamCodec3,
            Function<A, T3> getter3,
            MapCodec<T4> codec4,
            StreamCodec<? super RegistryFriendlyByteBuf, T4> streamCodec4,
            Function<A, T4> getter4,
            MapCodec<T5> codec5,
            StreamCodec<? super RegistryFriendlyByteBuf, T5> streamCodec5,
            Function<A, T5> getter5,
            MapCodec<T6> codec6,
            StreamCodec<? super RegistryFriendlyByteBuf, T6> streamCodec6,
            Function<A, T6> getter6,
            MapCodec<T7> codec7,
            StreamCodec<? super RegistryFriendlyByteBuf, T7> streamCodec7,
            Function<A, T7> getter7,
            MapCodec<T8> codec8,
            StreamCodec<? super RegistryFriendlyByteBuf, T8> streamCodec8,
            Function<A, T8> getter8,
            Function8<T1, T2, T3, T4, T5, T6, T7, T8, A> constructor
    ) {
        return new SyncedCodec<>(
                RecordCodecBuilder.mapCodec(i -> i.group(
                        codec1.forGetter(getter1),
                        codec2.forGetter(getter2),
                        codec3.forGetter(getter3),
                        codec4.forGetter(getter4),
                        codec5.forGetter(getter5),
                        codec6.forGetter(getter6),
                        codec7.forGetter(getter7),
                        codec8.forGetter(getter8)
                ).apply(i, constructor)),
                StreamCodec.composite(
                        streamCodec1,
                        getter1,
                        streamCodec2,
                        getter2,
                        streamCodec3,
                        getter3,
                        streamCodec4,
                        getter4,
                        streamCodec5,
                        getter5,
                        streamCodec6,
                        getter6,
                        streamCodec7,
                        getter7,
                        streamCodec8,
                        getter8,
                        constructor
                )
        );
    }

    public static <A, T1, T2, T3, T4, T5, T6, T7, T8, T9> SyncedCodec<A> create(
            MapCodec<T1> codec1,
            StreamCodec<? super RegistryFriendlyByteBuf, T1> streamCodec1,
            Function<A, T1> getter1,
            MapCodec<T2> codec2,
            StreamCodec<? super RegistryFriendlyByteBuf, T2> streamCodec2,
            Function<A, T2> getter2,
            MapCodec<T3> codec3,
            StreamCodec<? super RegistryFriendlyByteBuf, T3> streamCodec3,
            Function<A, T3> getter3,
            MapCodec<T4> codec4,
            StreamCodec<? super RegistryFriendlyByteBuf, T4> streamCodec4,
            Function<A, T4> getter4,
            MapCodec<T5> codec5,
            StreamCodec<? super RegistryFriendlyByteBuf, T5> streamCodec5,
            Function<A, T5> getter5,
            MapCodec<T6> codec6,
            StreamCodec<? super RegistryFriendlyByteBuf, T6> streamCodec6,
            Function<A, T6> getter6,
            MapCodec<T7> codec7,
            StreamCodec<? super RegistryFriendlyByteBuf, T7> streamCodec7,
            Function<A, T7> getter7,
            MapCodec<T8> codec8,
            StreamCodec<? super RegistryFriendlyByteBuf, T8> streamCodec8,
            Function<A, T8> getter8,
            MapCodec<T9> codec9,
            StreamCodec<? super RegistryFriendlyByteBuf, T9> streamCodec9,
            Function<A, T9> getter9,
            Function9<T1, T2, T3, T4, T5, T6, T7, T8, T9, A> constructor
    ) {
        return new SyncedCodec<>(
                RecordCodecBuilder.mapCodec(i -> i.group(
                        codec1.forGetter(getter1),
                        codec2.forGetter(getter2),
                        codec3.forGetter(getter3),
                        codec4.forGetter(getter4),
                        codec5.forGetter(getter5),
                        codec6.forGetter(getter6),
                        codec7.forGetter(getter7),
                        codec8.forGetter(getter8),
                        codec9.forGetter(getter9)
                ).apply(i, constructor)),
                StreamCodec.composite(
                        streamCodec1,
                        getter1,
                        streamCodec2,
                        getter2,
                        streamCodec3,
                        getter3,
                        streamCodec4,
                        getter4,
                        streamCodec5,
                        getter5,
                        streamCodec6,
                        getter6,
                        streamCodec7,
                        getter7,
                        streamCodec8,
                        getter8,
                        streamCodec9,
                        getter9,
                        constructor
                )
        );
    }

    public static <A, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> SyncedCodec<A> create(
            MapCodec<T1> codec1,
            StreamCodec<? super RegistryFriendlyByteBuf, T1> streamCodec1,
            Function<A, T1> getter1,
            MapCodec<T2> codec2,
            StreamCodec<? super RegistryFriendlyByteBuf, T2> streamCodec2,
            Function<A, T2> getter2,
            MapCodec<T3> codec3,
            StreamCodec<? super RegistryFriendlyByteBuf, T3> streamCodec3,
            Function<A, T3> getter3,
            MapCodec<T4> codec4,
            StreamCodec<? super RegistryFriendlyByteBuf, T4> streamCodec4,
            Function<A, T4> getter4,
            MapCodec<T5> codec5,
            StreamCodec<? super RegistryFriendlyByteBuf, T5> streamCodec5,
            Function<A, T5> getter5,
            MapCodec<T6> codec6,
            StreamCodec<? super RegistryFriendlyByteBuf, T6> streamCodec6,
            Function<A, T6> getter6,
            MapCodec<T7> codec7,
            StreamCodec<? super RegistryFriendlyByteBuf, T7> streamCodec7,
            Function<A, T7> getter7,
            MapCodec<T8> codec8,
            StreamCodec<? super RegistryFriendlyByteBuf, T8> streamCodec8,
            Function<A, T8> getter8,
            MapCodec<T9> codec9,
            StreamCodec<? super RegistryFriendlyByteBuf, T9> streamCodec9,
            Function<A, T9> getter9,
            MapCodec<T10> codec10,
            StreamCodec<? super RegistryFriendlyByteBuf, T10> streamCodec10,
            Function<A, T10> getter10,
            Function10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, A> constructor
    ) {
        return new SyncedCodec<>(
                RecordCodecBuilder.mapCodec(i -> i.group(
                        codec1.forGetter(getter1),
                        codec2.forGetter(getter2),
                        codec3.forGetter(getter3),
                        codec4.forGetter(getter4),
                        codec5.forGetter(getter5),
                        codec6.forGetter(getter6),
                        codec7.forGetter(getter7),
                        codec8.forGetter(getter8),
                        codec9.forGetter(getter9),
                        codec10.forGetter(getter10)
                ).apply(i, constructor)),
                StreamCodec.composite(
                        streamCodec1,
                        getter1,
                        streamCodec2,
                        getter2,
                        streamCodec3,
                        getter3,
                        streamCodec4,
                        getter4,
                        streamCodec5,
                        getter5,
                        streamCodec6,
                        getter6,
                        streamCodec7,
                        getter7,
                        streamCodec8,
                        getter8,
                        streamCodec9,
                        getter9,
                        streamCodec10,
                        getter10,
                        constructor
                )
        );
    }

    public static <A, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> SyncedCodec<A> create(
            MapCodec<T1> codec1,
            StreamCodec<? super RegistryFriendlyByteBuf, T1> streamCodec1,
            Function<A, T1> getter1,
            MapCodec<T2> codec2,
            StreamCodec<? super RegistryFriendlyByteBuf, T2> streamCodec2,
            Function<A, T2> getter2,
            MapCodec<T3> codec3,
            StreamCodec<? super RegistryFriendlyByteBuf, T3> streamCodec3,
            Function<A, T3> getter3,
            MapCodec<T4> codec4,
            StreamCodec<? super RegistryFriendlyByteBuf, T4> streamCodec4,
            Function<A, T4> getter4,
            MapCodec<T5> codec5,
            StreamCodec<? super RegistryFriendlyByteBuf, T5> streamCodec5,
            Function<A, T5> getter5,
            MapCodec<T6> codec6,
            StreamCodec<? super RegistryFriendlyByteBuf, T6> streamCodec6,
            Function<A, T6> getter6,
            MapCodec<T7> codec7,
            StreamCodec<? super RegistryFriendlyByteBuf, T7> streamCodec7,
            Function<A, T7> getter7,
            MapCodec<T8> codec8,
            StreamCodec<? super RegistryFriendlyByteBuf, T8> streamCodec8,
            Function<A, T8> getter8,
            MapCodec<T9> codec9,
            StreamCodec<? super RegistryFriendlyByteBuf, T9> streamCodec9,
            Function<A, T9> getter9,
            MapCodec<T10> codec10,
            StreamCodec<? super RegistryFriendlyByteBuf, T10> streamCodec10,
            Function<A, T10> getter10,
            MapCodec<T11> codec11,
            StreamCodec<? super RegistryFriendlyByteBuf, T11> streamCodec11,
            Function<A, T11> getter11,
            Function11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, A> constructor
    ) {
        return new SyncedCodec<>(
                RecordCodecBuilder.mapCodec(i -> i.group(
                        codec1.forGetter(getter1),
                        codec2.forGetter(getter2),
                        codec3.forGetter(getter3),
                        codec4.forGetter(getter4),
                        codec5.forGetter(getter5),
                        codec6.forGetter(getter6),
                        codec7.forGetter(getter7),
                        codec8.forGetter(getter8),
                        codec9.forGetter(getter9),
                        codec10.forGetter(getter10),
                        codec11.forGetter(getter11)
                ).apply(i, constructor)),
                StreamCodec.composite(
                        streamCodec1,
                        getter1,
                        streamCodec2,
                        getter2,
                        streamCodec3,
                        getter3,
                        streamCodec4,
                        getter4,
                        streamCodec5,
                        getter5,
                        streamCodec6,
                        getter6,
                        streamCodec7,
                        getter7,
                        streamCodec8,
                        getter8,
                        streamCodec9,
                        getter9,
                        streamCodec10,
                        getter10,
                        streamCodec11,
                        getter11,
                        constructor
                )
        );
    }

    public static <A, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> SyncedCodec<A> create(
            MapCodec<T1> codec1,
            StreamCodec<? super RegistryFriendlyByteBuf, T1> streamCodec1,
            Function<A, T1> getter1,
            MapCodec<T2> codec2,
            StreamCodec<? super RegistryFriendlyByteBuf, T2> streamCodec2,
            Function<A, T2> getter2,
            MapCodec<T3> codec3,
            StreamCodec<? super RegistryFriendlyByteBuf, T3> streamCodec3,
            Function<A, T3> getter3,
            MapCodec<T4> codec4,
            StreamCodec<? super RegistryFriendlyByteBuf, T4> streamCodec4,
            Function<A, T4> getter4,
            MapCodec<T5> codec5,
            StreamCodec<? super RegistryFriendlyByteBuf, T5> streamCodec5,
            Function<A, T5> getter5,
            MapCodec<T6> codec6,
            StreamCodec<? super RegistryFriendlyByteBuf, T6> streamCodec6,
            Function<A, T6> getter6,
            MapCodec<T7> codec7,
            StreamCodec<? super RegistryFriendlyByteBuf, T7> streamCodec7,
            Function<A, T7> getter7,
            MapCodec<T8> codec8,
            StreamCodec<? super RegistryFriendlyByteBuf, T8> streamCodec8,
            Function<A, T8> getter8,
            MapCodec<T9> codec9,
            StreamCodec<? super RegistryFriendlyByteBuf, T9> streamCodec9,
            Function<A, T9> getter9,
            MapCodec<T10> codec10,
            StreamCodec<? super RegistryFriendlyByteBuf, T10> streamCodec10,
            Function<A, T10> getter10,
            MapCodec<T11> codec11,
            StreamCodec<? super RegistryFriendlyByteBuf, T11> streamCodec11,
            Function<A, T11> getter11,
            MapCodec<T12> codec12,
            StreamCodec<? super RegistryFriendlyByteBuf, T12> streamCodec12,
            Function<A, T12> getter12,
            Function12<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, A> constructor
    ) {
        return new SyncedCodec<>(
                RecordCodecBuilder.mapCodec(i -> i.group(
                        codec1.forGetter(getter1),
                        codec2.forGetter(getter2),
                        codec3.forGetter(getter3),
                        codec4.forGetter(getter4),
                        codec5.forGetter(getter5),
                        codec6.forGetter(getter6),
                        codec7.forGetter(getter7),
                        codec8.forGetter(getter8),
                        codec9.forGetter(getter9),
                        codec10.forGetter(getter10),
                        codec11.forGetter(getter11),
                        codec12.forGetter(getter12)
                ).apply(i, constructor)),
                StreamCodec.composite(
                        streamCodec1,
                        getter1,
                        streamCodec2,
                        getter2,
                        streamCodec3,
                        getter3,
                        streamCodec4,
                        getter4,
                        streamCodec5,
                        getter5,
                        streamCodec6,
                        getter6,
                        streamCodec7,
                        getter7,
                        streamCodec8,
                        getter8,
                        streamCodec9,
                        getter9,
                        streamCodec10,
                        getter10,
                        streamCodec11,
                        getter11,
                        streamCodec12,
                        getter12,
                        constructor
                )
        );
    }

    public static <A, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> SyncedCodec<A> create(
            MapCodec<T1> codec1,
            StreamCodec<? super RegistryFriendlyByteBuf, T1> streamCodec1,
            Function<A, T1> getter1,
            MapCodec<T2> codec2,
            StreamCodec<? super RegistryFriendlyByteBuf, T2> streamCodec2,
            Function<A, T2> getter2,
            MapCodec<T3> codec3,
            StreamCodec<? super RegistryFriendlyByteBuf, T3> streamCodec3,
            Function<A, T3> getter3,
            MapCodec<T4> codec4,
            StreamCodec<? super RegistryFriendlyByteBuf, T4> streamCodec4,
            Function<A, T4> getter4,
            MapCodec<T5> codec5,
            StreamCodec<? super RegistryFriendlyByteBuf, T5> streamCodec5,
            Function<A, T5> getter5,
            MapCodec<T6> codec6,
            StreamCodec<? super RegistryFriendlyByteBuf, T6> streamCodec6,
            Function<A, T6> getter6,
            MapCodec<T7> codec7,
            StreamCodec<? super RegistryFriendlyByteBuf, T7> streamCodec7,
            Function<A, T7> getter7,
            MapCodec<T8> codec8,
            StreamCodec<? super RegistryFriendlyByteBuf, T8> streamCodec8,
            Function<A, T8> getter8,
            MapCodec<T9> codec9,
            StreamCodec<? super RegistryFriendlyByteBuf, T9> streamCodec9,
            Function<A, T9> getter9,
            MapCodec<T10> codec10,
            StreamCodec<? super RegistryFriendlyByteBuf, T10> streamCodec10,
            Function<A, T10> getter10,
            MapCodec<T11> codec11,
            StreamCodec<? super RegistryFriendlyByteBuf, T11> streamCodec11,
            Function<A, T11> getter11,
            MapCodec<T12> codec12,
            StreamCodec<? super RegistryFriendlyByteBuf, T12> streamCodec12,
            Function<A, T12> getter12,
            MapCodec<T13> codec13,
            StreamCodec<? super RegistryFriendlyByteBuf, T13> streamCodec13,
            Function<A, T13> getter13,
            Function13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, A> constructor
    ) {
        return new SyncedCodec<>(
                RecordCodecBuilder.mapCodec(i -> i.group(
                        codec1.forGetter(getter1),
                        codec2.forGetter(getter2),
                        codec3.forGetter(getter3),
                        codec4.forGetter(getter4),
                        codec5.forGetter(getter5),
                        codec6.forGetter(getter6),
                        codec7.forGetter(getter7),
                        codec8.forGetter(getter8),
                        codec9.forGetter(getter9),
                        codec10.forGetter(getter10),
                        codec11.forGetter(getter11),
                        codec12.forGetter(getter12),
                        codec13.forGetter(getter13)
                ).apply(i, constructor)),
                StreamCodec.composite(
                        streamCodec1,
                        getter1,
                        streamCodec2,
                        getter2,
                        streamCodec3,
                        getter3,
                        streamCodec4,
                        getter4,
                        streamCodec5,
                        getter5,
                        streamCodec6,
                        getter6,
                        streamCodec7,
                        getter7,
                        streamCodec8,
                        getter8,
                        streamCodec9,
                        getter9,
                        streamCodec10,
                        getter10,
                        streamCodec11,
                        getter11,
                        streamCodec12,
                        getter12,
                        streamCodec13,
                        getter13,
                        constructor
                )
        );
    }

    public static <A, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> SyncedCodec<A> create(
            MapCodec<T1> codec1,
            StreamCodec<? super RegistryFriendlyByteBuf, T1> streamCodec1,
            Function<A, T1> getter1,
            MapCodec<T2> codec2,
            StreamCodec<? super RegistryFriendlyByteBuf, T2> streamCodec2,
            Function<A, T2> getter2,
            MapCodec<T3> codec3,
            StreamCodec<? super RegistryFriendlyByteBuf, T3> streamCodec3,
            Function<A, T3> getter3,
            MapCodec<T4> codec4,
            StreamCodec<? super RegistryFriendlyByteBuf, T4> streamCodec4,
            Function<A, T4> getter4,
            MapCodec<T5> codec5,
            StreamCodec<? super RegistryFriendlyByteBuf, T5> streamCodec5,
            Function<A, T5> getter5,
            MapCodec<T6> codec6,
            StreamCodec<? super RegistryFriendlyByteBuf, T6> streamCodec6,
            Function<A, T6> getter6,
            MapCodec<T7> codec7,
            StreamCodec<? super RegistryFriendlyByteBuf, T7> streamCodec7,
            Function<A, T7> getter7,
            MapCodec<T8> codec8,
            StreamCodec<? super RegistryFriendlyByteBuf, T8> streamCodec8,
            Function<A, T8> getter8,
            MapCodec<T9> codec9,
            StreamCodec<? super RegistryFriendlyByteBuf, T9> streamCodec9,
            Function<A, T9> getter9,
            MapCodec<T10> codec10,
            StreamCodec<? super RegistryFriendlyByteBuf, T10> streamCodec10,
            Function<A, T10> getter10,
            MapCodec<T11> codec11,
            StreamCodec<? super RegistryFriendlyByteBuf, T11> streamCodec11,
            Function<A, T11> getter11,
            MapCodec<T12> codec12,
            StreamCodec<? super RegistryFriendlyByteBuf, T12> streamCodec12,
            Function<A, T12> getter12,
            MapCodec<T13> codec13,
            StreamCodec<? super RegistryFriendlyByteBuf, T13> streamCodec13,
            Function<A, T13> getter13,
            MapCodec<T14> codec14,
            StreamCodec<? super RegistryFriendlyByteBuf, T14> streamCodec14,
            Function<A, T14> getter14,
            Function14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, A> constructor
    ) {
        return new SyncedCodec<>(
                RecordCodecBuilder.mapCodec(i -> i.group(
                        codec1.forGetter(getter1),
                        codec2.forGetter(getter2),
                        codec3.forGetter(getter3),
                        codec4.forGetter(getter4),
                        codec5.forGetter(getter5),
                        codec6.forGetter(getter6),
                        codec7.forGetter(getter7),
                        codec8.forGetter(getter8),
                        codec9.forGetter(getter9),
                        codec10.forGetter(getter10),
                        codec11.forGetter(getter11),
                        codec12.forGetter(getter12),
                        codec13.forGetter(getter13),
                        codec14.forGetter(getter14)
                ).apply(i, constructor)),
                StreamCodec.composite(
                        streamCodec1,
                        getter1,
                        streamCodec2,
                        getter2,
                        streamCodec3,
                        getter3,
                        streamCodec4,
                        getter4,
                        streamCodec5,
                        getter5,
                        streamCodec6,
                        getter6,
                        streamCodec7,
                        getter7,
                        streamCodec8,
                        getter8,
                        streamCodec9,
                        getter9,
                        streamCodec10,
                        getter10,
                        streamCodec11,
                        getter11,
                        streamCodec12,
                        getter12,
                        streamCodec13,
                        getter13,
                        streamCodec14,
                        getter14,
                        constructor
                )
        );
    }

    public interface Type<T> {
        SyncedCodec<? extends T> type();
    }
}
