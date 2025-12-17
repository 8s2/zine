package com.eightsidedsquare.zine.common.util.codec;

import com.mojang.serialization.Codec;
import net.minecraft.block.BlockState;
import net.minecraft.component.ComponentMap;
import net.minecraft.nbt.NbtElement;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.storage.ReadView;
import net.minecraft.storage.WriteView;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Function;

/**
 * <p>Utility interface for writing and reading data to objects with via
 * {@link ReadView}, {@link WriteView}, and {@link RegistryByteBuf}.
 *
 * <p>Offers a setup similar to the {@link com.mojang.serialization.codecs.RecordCodecBuilder},
 * except it mutates a given instance rather than creating a new one.
 *
 * <p>This is made particularly for use in tandem with
 * <a href="https://github.com/Ladysnake/Cardinal-Components-API">Cardinal Components API.</a>
 *
 * <p>As an example, consider the following class with fields, getter, and setter methods.
 * <pre>{@code
 * public class MyObject {
 *
 *     private final List<BlockPos> positions = new ArrayList<>();
 *     @Nullable
 *     private Text name;
 *     private int weight;
 *
 *     public List<BlockPos> getPositions() {
 *         return this.positions;
 *     }
 *
 *     @Nullable
 *     public Text getName() {
 *         return this.name;
 *     }
 *
 *     public void setName(@Nullable Text name) {
 *         this.name = name;
 *     }
 *
 *     public int getWeight() {
 *         return this.weight;
 *     }
 *
 *     public void setWeight(int weight) {
 *         this.weight = weight;
 *     }
 *
 * }
 * }</pre>
 * Let's say that <strong>MyObject</strong> is required to implement methods for reading and writing to data and byte buffers:
 * <pre>{@code
 * public void readData(ReadView view) {}
 *
 * public void writeData(WriteView view) {}
 *
 * public void readFromBuf(RegistryByteBuf buf) {}
 *
 * public void writeToBuf(RegistryByteBuf buf) {}
 * }</pre>
 * <p>Manually handling each field for each method can become quite cumbersome, especially as the number of fields grow.
 * <p>This is where a DataHelper can be built to handle all four methods at once:
 * <pre>{@code
 * static final DataHelper<MyObject> DATA_HELPER = DataHelper.<MyObject>builder()
 *         .listFieldOf(BlockPos.CODEC, BlockPos.PACKET_CODEC, "positions")
 *         .apply(MyObject::getPositions)
 *         .nullableField(TextCodecs.CODEC, TextCodecs.UNLIMITED_REGISTRY_PACKET_CODEC, "name")
 *         .apply(MyObject::getName, MyObject::setName)
 *         .intField("weight")
 *         .apply(0, MyObject::getWeight, MyObject::setWeight)
 *         .build();
 * }</pre>
 * <p>The building process follows a pattern wherein first a field type is declared
 * (with a codec, packet codec, and key for data encoding),
 * followed by an {@code apply} method call which provides a getter method reference
 * (fields that aren't collections or maps must also provide a default value and setter method reference).
 * <p>See the methods of {@link Builder} for all the supported field types.
 * <p>The codec parameter can be {@code null} to prevent data reading and writing,
 * and the packet codec parameter can be {@code null} to prevent buf reading and writing.
 * Both cannot be null, however. There are overloaded builder methods for adding fields without codecs or packet codecs.
 * <p>For this example, the DataHelper is a static constant
 * as the field types for <strong>MyObject</strong> are consistent.
 * An instance field of DataHelper might be needed for objects with generic type(s)
 * in order to handle type-specific codecs and packet codecs.
 * <p>Finally, the DataHelper can be applied to each method as follows:
 * <pre>{@code
 * public void readData(ReadView view) {
 *     DATA_HELPER.read(view, this);
 * }
 *
 * public void writeData(WriteView view) {
 *     DATA_HELPER.write(view, this);
 * }
 *
 * public void readFromBuf(RegistryByteBuf buf) {
 *     DATA_HELPER.read(buf, this);
 * }
 *
 * public void writeToBuf(RegistryByteBuf buf) {
 *     DATA_HELPER.write(buf, this);
 * }
 * }</pre>
 * @see DataHelperImpl
 */
public interface DataHelper<T> {

    /**
     * Creates a {@link Builder} for creating a DataHelper with one or more fields.
     * @apiNote The generic type will likely revert to {@code <Object>} causing issues,
     * so it's best to prefix the {@code builder()} method call with your intended type
     * (i.e. {@code DataHelper.<MyObject>builder()})
     */
    static <T> Builder<T> builder() {
        return new DataHelperImpl.BuilderImpl<>();
    }

    void read(ReadView view, T object);

    void write(WriteView view, T object);

    <I extends RegistryByteBuf> void read(I buf, T object);

    <I extends RegistryByteBuf> void write(I buf, T object);

    interface Builder<T> {

        /**
         * Adds a field to the data helper builder.
         * @param codec the codec of the field
         * @param packetCodec the packet codec of the field
         * @param key mapping used when encoding to {@link WriteView} and decoding from {@link ReadView}
         * @param <F> type of the field
         * @apiNote {@link FieldBuilder#apply(Object, Function, BiConsumer)} must be called afterward to continue building the data helper
         */
        <F> FieldBuilder<F, T> field(@Nullable Codec<F> codec,
                                     @Nullable PacketCodec<? super RegistryByteBuf, F> packetCodec,
                                     String key);

        /**
         * Adds a non-syncing field to the data helper builder.
         * @param codec the codec of the field
         * @param key mapping used when encoding to {@link WriteView} and decoding from {@link ReadView}
         * @param <F> type of the field
         * @apiNote {@link FieldBuilder#apply(Object, Function, BiConsumer)} must be called afterward to continue building the data helper
         */
        default <F> FieldBuilder<F, T> field(Codec<F> codec, String key) {
            return this.field(codec, null, key);
        }

        /**
         * Adds a non-serializing field to the data helper builder.
         * @param packetCodec the packet codec of the field
         * @param <F> type of the field
         * @apiNote {@link FieldBuilder#apply(Object, Function, BiConsumer)} must be called afterward to continue building the data helper
         */
        default <F> FieldBuilder<F, T> field(PacketCodec<? super RegistryByteBuf, F> packetCodec) {
            return this.field(null, packetCodec, "");
        }

        /**
         * <p>Adds a nullable field to the data helper builder.
         * <p>Internally, values are wrapped in an {@link java.util.Optional} in order to handle null values properly.
         * @param codec the codec of the field
         * @param packetCodec the packet codec of the field
         * @param key mapping used when encoding to {@link WriteView} and decoding from {@link ReadView}
         * @param <F> type of the field
         * @apiNote {@link FieldBuilder#apply(Object, Function, BiConsumer)} must be called afterward to continue building the data helper
         */
        <F> NullableFieldBuilder<F, T> nullableField(@Nullable Codec<F> codec, @Nullable PacketCodec<? super RegistryByteBuf, F> packetCodec, String key);

        /**
         * Adds a non-syncing nullable field to the data helper builder.
         * <p>Internally, values are wrapped in an {@link java.util.Optional} in order to handle null values properly.
         * @param codec the codec of the field
         * @param key mapping used when encoding to {@link WriteView} and decoding from {@link ReadView}
         * @param <F> type of the field
         * @apiNote {@link FieldBuilder#apply(Object, Function, BiConsumer)} must be called afterward to continue building the data helper
         */
        default <F> NullableFieldBuilder<F, T> nullableField(Codec<F> codec, String key) {
            return this.nullableField(codec, null, key);
        }

        /**
         * Adds a non-serializing nullable field to the data helper builder.
         * <p>Internally, values are wrapped in an {@link java.util.Optional} in order to handle null values properly.
         * @param packetCodec the packet codec of the field
         * @param <F> type of the field
         * @apiNote {@link FieldBuilder#apply(Object, Function, BiConsumer)} must be called afterward to continue building the data helper
         */
        default <F> NullableFieldBuilder<F, T> nullableField(PacketCodec<? super RegistryByteBuf, F> packetCodec) {
            return this.nullableField(null, packetCodec, "");
        }

        /**
         * Adds a list field to the data helper builder
         * @param codec the codec of the list field
         * @param packetCodec the packet codec of the list field
         * @param key mapping used when encoding to {@link WriteView} and decoding from {@link ReadView}
         * @param <F> type of the list field element
         * @param <L> type of the list field
         * @apiNote {@link ListFieldBuilder#apply(Function)} must be called afterward to continue building the data helper
         */
        <F, L extends Collection<F>> ListFieldBuilder<F, L, T> listField(@Nullable Codec<L> codec,
                                                                         @Nullable PacketCodec<? super RegistryByteBuf, L> packetCodec,
                                                                         String key);

        /**
         * Adds a non-syncing list field to the data helper builder
         * @param codec the codec of the list field
         * @param key mapping used when encoding to {@link WriteView} and decoding from {@link ReadView}
         * @param <F> type of the list field element
         * @param <L> type of the list field
         * @apiNote {@link ListFieldBuilder#apply(Function)} must be called afterward to continue building the data helper
         */
        default <F, L extends Collection<F>> ListFieldBuilder<F, L, T> listField(Codec<L> codec, String key) {
            return this.listField(codec, null, key);
        }

        /**
         * Adds a non-serializing list field to the data helper builder
         * @param packetCodec the packet codec of the list field
         * @param <F> type of the list field element
         * @param <L> type of the list field
         * @apiNote {@link ListFieldBuilder#apply(Function)} must be called afterward to continue building the data helper
         */
        default <F, L extends Collection<F>> ListFieldBuilder<F, L, T> listField(PacketCodec<? super RegistryByteBuf, L> packetCodec) {
            return this.listField(null, packetCodec, "");
        }

        /**
         * <p>Adds a list field to the data helper builder.
         * <p>The codec and packet codec are converted to list types.
         * @param codec the codec of an element of the list field
         * @param packetCodec the packet codec of an element of the list field
         * @param key mapping used when encoding to {@link WriteView} and decoding from {@link ReadView}
         * @param <F> type of the list field element
         * @apiNote {@link ListFieldBuilder#apply(Function)} must be called afterward to continue building the data helper
         */
        <F> ListFieldBuilder<F, List<F>, T> listFieldOf(@Nullable Codec<F> codec,
                                                        @Nullable PacketCodec<? super RegistryByteBuf, F> packetCodec,
                                                        String key);

        /**
         * <p>Adds a non-syncing list field to the data helper builder.
         * <p>The codec is converted to a list type.
         * @param codec the codec of an element of the list field
         * @param key mapping used when encoding to {@link WriteView} and decoding from {@link ReadView}
         * @param <F> type of the list field element
         * @apiNote {@link ListFieldBuilder#apply(Function)} must be called afterward to continue building the data helper
         */
        default <F> ListFieldBuilder<F, List<F>, T> listFieldOf(Codec<F> codec, String key) {
            return this.listFieldOf(codec, null, key);
        }

        /**
         * <p>Adds a non-serializing list field to the data helper builder.
         * <p>The packet codec is converted to a list type.
         * @param packetCodec the packet codec of an element of the list field
         * @param <F> type of the list field element
         * @apiNote {@link ListFieldBuilder#apply(Function)} must be called afterward to continue building the data helper
         */
        default <F> ListFieldBuilder<F, List<F>, T> listFieldOf(PacketCodec<? super RegistryByteBuf, F> packetCodec) {
            return this.listFieldOf(null, packetCodec, "");
        }

        /**
         * Adds a map field to the data helper builder
         * @param codec the codec of the map field
         * @param packetCodec the packet codec of the map field
         * @param key mapping used when encoding to {@link WriteView} and decoding from {@link ReadView}
         * @param <K> type of the map field key
         * @param <V> type of the map field value
         * @param <M> type of the map field
         * @apiNote {@link MapFieldBuilder#apply(Function)} must be called afterward to continue building the data helper
         */
        <K, V, M extends Map<K, V>> MapFieldBuilder<K, V, M, T> mapField(@Nullable Codec<M> codec,
                                                                         @Nullable PacketCodec<? super RegistryByteBuf, M> packetCodec,
                                                                         String key);

        /**
         * Adds a non-syncing map field to the data helper builder
         * @param codec the codec of the map field
         * @param key mapping used when encoding to {@link WriteView} and decoding from {@link ReadView}
         * @param <K> type of the map field key
         * @param <V> type of the map field value
         * @param <M> type of the map field
         * @apiNote {@link MapFieldBuilder#apply(Function)} must be called afterward to continue building the data helper
         */
        default <K, V, M extends Map<K, V>> MapFieldBuilder<K, V, M, T> mapField(Codec<M> codec, String key) {
            return this.mapField(codec, null, key);
        }

        /**
         * Adds a non-serializing map field to the data helper builder
         * @param packetCodec the packet codec of the map field
         * @param <K> type of the map field key
         * @param <V> type of the map field value
         * @param <M> type of the map field
         * @apiNote {@link MapFieldBuilder#apply(Function)} must be called afterward to continue building the data helper
         */
        default <K, V, M extends Map<K, V>> MapFieldBuilder<K, V, M, T> mapField(PacketCodec<? super RegistryByteBuf, M> packetCodec) {
            return this.mapField(null, packetCodec, "");
        }

        /**
         * <p>Adds a map field to the data helper builder.
         * <p>The codecs and packet codecs are used to create a codec and packet codec for the map field.
         * @param keyCodec the codec of the map field key
         * @param elementCodec the codec of the map field element
         * @param keyPacketCodec the packet codec of the map field key
         * @param elementPacketCodec the packet codec of the map field element
         * @param key mapping used when encoding to {@link WriteView} and decoding from {@link ReadView}
         * @param <K> type of the map field key
         * @param <V> type of the map field value
         * @apiNote {@link MapFieldBuilder#apply(Function)} must be called afterward to continue building the data helper
         */
        <K, V> MapFieldBuilder<K, V, Map<K, V>, T> mapFieldOf(@Nullable Codec<K> keyCodec,
                                                              @Nullable Codec<V> elementCodec,
                                                              @Nullable PacketCodec<? super RegistryByteBuf, K> keyPacketCodec,
                                                              @Nullable PacketCodec<? super RegistryByteBuf, V> elementPacketCodec,
                                                              String key);

        /**
         * <p>Adds a non-syncing map field to the data helper builder.
         * <p>The codecs are used to create a codec for the map field.
         * @param keyCodec the codec of the map field key
         * @param elementCodec the codec of the map field element
         * @param key mapping used when encoding to {@link WriteView} and decoding from {@link ReadView}
         * @param <K> type of the map field key
         * @param <V> type of the map field value
         * @apiNote {@link MapFieldBuilder#apply(Function)} must be called afterward to continue building the data helper
         */
        default <K, V> MapFieldBuilder<K, V, Map<K, V>, T> mapFieldOf(Codec<K> keyCodec,
                                                                      Codec<V> elementCodec,
                                                                      String key) {
            return this.mapFieldOf(keyCodec, elementCodec, null, null, key);
        }

        /**
         * <p>Adds a non-serializing map field to the data helper builder.
         * <p>The packet codecs are used to create a packet codec for the map field.
         * @param keyPacketCodec the packet codec of the map field key
         * @param elementPacketCodec the packet codec of the map field element
         * @param <K> type of the map field key
         * @param <V> type of the map field value
         * @apiNote {@link MapFieldBuilder#apply(Function)} must be called afterward to continue building the data helper
         */
        default <K, V> MapFieldBuilder<K, V, Map<K, V>, T> mapFieldOf(PacketCodec<? super RegistryByteBuf, K> keyPacketCodec,
                                                                      PacketCodec<? super RegistryByteBuf, V> elementPacketCodec) {
            return this.mapFieldOf(null, null, keyPacketCodec, elementPacketCodec, "");
        }

        /**
         * Adds a boolean field to the data helper builder
         * @param key mapping used when encoding to {@link WriteView} and decoding from {@link ReadView}
         * @apiNote {@link FieldBuilder#apply(Object, Function, BiConsumer)} must be called afterward to continue building the data helper
         */
        FieldBuilder<Boolean, T> booleanField(String key);

        /**
         * Adds a byte field to the data helper builder
         * @param key mapping used when encoding to {@link WriteView} and decoding from {@link ReadView}
         * @apiNote {@link FieldBuilder#apply(Object, Function, BiConsumer)} must be called afterward to continue building the data helper
         */
        FieldBuilder<Byte, T> byteField(String key);

        /**
         * Adds a short field to the data helper builder
         * @param key mapping used when encoding to {@link WriteView} and decoding from {@link ReadView}
         * @apiNote {@link FieldBuilder#apply(Object, Function, BiConsumer)} must be called afterward to continue building the data helper
         */
        FieldBuilder<Short, T> shortField(String key);

        /**
         * Adds an int field to the data helper builder
         * @param key mapping used when encoding to {@link WriteView} and decoding from {@link ReadView}
         * @apiNote {@link FieldBuilder#apply(Object, Function, BiConsumer)} must be called afterward to continue building the data helper
         */
        FieldBuilder<Integer, T> intField(String key);

        /**
         * Adds a long field to the data helper builder
         * @param key mapping used when encoding to {@link WriteView} and decoding from {@link ReadView}
         * @apiNote {@link FieldBuilder#apply(Object, Function, BiConsumer)} must be called afterward to continue building the data helper
         */
        FieldBuilder<Long, T> longField(String key);

        /**
         * Adds a float field to the data helper builder
         * @param key mapping used when encoding to {@link WriteView} and decoding from {@link ReadView}
         * @apiNote {@link FieldBuilder#apply(Object, Function, BiConsumer)} must be called afterward to continue building the data helper
         */
        FieldBuilder<Float, T> floatField(String key);

        /**
         * Adds a double field to the data helper builder
         * @param key mapping used when encoding to {@link WriteView} and decoding from {@link ReadView}
         * @apiNote {@link FieldBuilder#apply(Object, Function, BiConsumer)} must be called afterward to continue building the data helper
         */
        FieldBuilder<Double, T> doubleField(String key);

        /**
         * Adds a string field to the data helper builder
         * @param key mapping used when encoding to {@link WriteView} and decoding from {@link ReadView}
         * @apiNote {@link FieldBuilder#apply(Object, Function, BiConsumer)} must be called afterward to continue building the data helper
         */
        FieldBuilder<String, T> stringField(String key);

        /**
         * Adds a {@link BlockPos} field to the data helper builder
         * @param key mapping used when encoding to {@link WriteView} and decoding from {@link ReadView}
         * @apiNote {@link FieldBuilder#apply(Object, Function, BiConsumer)} must be called afterward to continue building the data helper
         */
        FieldBuilder<NbtElement, T> nbtElementField(String key);

        /**
         * Adds a {@link BlockPos} field to the data helper builder
         * @param key mapping used when encoding to {@link WriteView} and decoding from {@link ReadView}
         * @apiNote {@link FieldBuilder#apply(Object, Function, BiConsumer)} must be called afterward to continue building the data helper
         */
        FieldBuilder<UUID, T> uuidField(String key);

        /**
         * Adds a {@link BlockPos} field to the data helper builder
         * @param key mapping used when encoding to {@link WriteView} and decoding from {@link ReadView}
         * @apiNote {@link FieldBuilder#apply(Object, Function, BiConsumer)} must be called afterward to continue building the data helper
         */
        FieldBuilder<BlockPos, T> blockPosField(String key);

        /**
         * Adds a {@link BlockState} field to the data helper builder
         * @param key mapping used when encoding to {@link WriteView} and decoding from {@link ReadView}
         * @apiNote {@link FieldBuilder#apply(Object, Function, BiConsumer)} must be called afterward to continue building the data helper
         */
        FieldBuilder<BlockState, T> blockStateField(String key);

        /**
         * Adds a {@link ComponentMap} field to the data helper builder
         * @param key mapping used when encoding to {@link WriteView} and decoding from {@link ReadView}
         * @apiNote {@link FieldBuilder#apply(Object, Function, BiConsumer)} must be called afterward to continue building the data helper
         */
        FieldBuilder<ComponentMap, T> componentMapField(String key);

        /**
         * Builds the data helper
         */
        DataHelper<T> build();
    }

    interface FieldBuilder<F, T> {
        Builder<T> apply(Function<T, F> defaultValueGetter, Function<T, F> getter, BiConsumer<T, F> setter);

        default Builder<T> apply(F defaultValue, Function<T, F> getter, BiConsumer<T, F> setter) {
            return this.apply(obj -> defaultValue, getter, setter);
        }
    }

    interface NullableFieldBuilder<F, T> extends FieldBuilder<F, T> {
        default Builder<T> apply(Function<T, F> getter, BiConsumer<T, F> setter) {
            return this.apply(obj -> null, getter, setter);
        }
    }

    interface ListFieldBuilder<F, L extends Collection<F>, T> {
        Builder<T> apply(Function<T, L> getter);
    }

    interface MapFieldBuilder<K, V, M extends Map<K, V>, T> {
        Builder<T> apply(Function<T, M> getter);
    }

}
