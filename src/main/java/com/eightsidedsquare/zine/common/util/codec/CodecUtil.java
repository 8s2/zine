package com.eightsidedsquare.zine.common.util.codec;

import com.eightsidedsquare.zine.common.entity.SpawnReasonIds;
import com.mojang.datafixers.util.Either;
import com.mojang.datafixers.util.Function3;
import com.mojang.datafixers.util.Function4;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.util.Util;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.phys.AABB;
import org.apache.commons.lang3.mutable.*;
import org.joml.*;

import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Function;

public final class CodecUtil {
    public static final Codec<Character> CHARACTER = Codec.string(1, 1).xmap(string -> string.charAt(0), String::valueOf);
    public static final Codec<Integer> INT_STRING = Codec.STRING.comapFlatMap(
            string -> {
                int value;
                try {
                    value = Integer.parseInt(string);
                } catch (NumberFormatException e) {
                    return DataResult.error(() -> "Failed to parse int from " + string);
                }
                return DataResult.success(value);
            },
            String::valueOf
    );
    public static final Codec<Block> BLOCK = BuiltInRegistries.BLOCK.byNameCodec();
    public static final Codec<EntitySpawnReason> SPAWN_REASON = SpawnReasonIds.ID_MAPPER.codec(Identifier.CODEC);
    public static final Codec<AABB> AABB = Codec.DOUBLE.listOf().comapFlatMap(
            vertices -> Util.fixedSize(vertices, 6)
                    .map(list -> new AABB(list.getFirst(), list.get(1), list.get(2), list.get(3), list.get(4), list.get(5))),
            box -> List.of(box.minX, box.minY, box.minZ, box.maxX, box.maxY, box.maxZ)
    );
    public static final Codec<MutableBoolean> MUTABLE_BOOLEAN = Codec.BOOL.xmap(MutableBoolean::new, MutableBoolean::booleanValue);
    public static final Codec<MutableByte> MUTABLE_BYTE = Codec.BYTE.xmap(MutableByte::new, MutableByte::byteValue);
    public static final Codec<MutableShort> MUTABLE_SHORT = Codec.SHORT.xmap(MutableShort::new, MutableShort::shortValue);
    public static final Codec<MutableInt> MUTABLE_INT = Codec.INT.xmap(MutableInt::new, MutableInt::intValue);
    public static final Codec<MutableLong> MUTABLE_LONG = Codec.LONG.xmap(MutableLong::new, MutableLong::longValue);
    public static final Codec<MutableFloat> MUTABLE_FLOAT = Codec.FLOAT.xmap(MutableFloat::new, MutableFloat::floatValue);
    public static final Codec<MutableDouble> MUTABLE_DOUBLE = Codec.DOUBLE.xmap(MutableDouble::new, MutableDouble::doubleValue);
    public static final Codec<Vector2ic> VECTOR2I = vector2Codec(Codec.INT, Vector2i::new, Vector2ic::get);
    public static final Codec<Vector3ic> VECTOR3I = ExtraCodecs.VECTOR3I;
    public static final Codec<Vector4ic> VECTOR4I = vector4Codec(Codec.INT, Vector4i::new, Vector4ic::get);
    public static final Codec<Vector2Lc> VECTOR2L = vector2Codec(Codec.LONG, Vector2L::new, Vector2Lc::get);
    public static final Codec<Vector3Lc> VECTOR3L = vector3Codec(
            Codec.LONG,
            (x, y, z) -> new Vector3L().set(x, y, z),
            Vector3Lc::get
    );
    public static final Codec<Vector4Lc> VECTOR4L = vector4Codec(Codec.LONG, Vector4L::new, Vector4Lc::get);
    public static final Codec<Vector2dc> VECTOR2D = vector2Codec(Codec.DOUBLE, Vector2d::new, Vector2dc::get);
    public static final Codec<Vector3dc> VECTOR3D = vector3Codec(Codec.DOUBLE, Vector3d::new, Vector3dc::get);
    public static final Codec<Vector4dc> VECTOR4D = vector4Codec(Codec.DOUBLE, Vector4d::new, Vector4dc::get);
    public static final Codec<OptionalInt> OPTIONAL_INT = Codec.INT.xmap(OptionalInt::of, OptionalInt::getAsInt)
            .orElse(OptionalInt.empty());
    public static final Codec<OptionalDouble> OPTIONAL_DOUBLE = Codec.DOUBLE.xmap(OptionalDouble::of, OptionalDouble::getAsDouble)
            .orElse(OptionalDouble.empty());
    public static final Codec<OptionalLong> OPTIONAL_LONG = Codec.LONG.xmap(OptionalLong::of, OptionalLong::getAsLong)
            .orElse(OptionalLong.empty());

    /**
     * Creates a list codec that can deserialize single elements as a list,
     * and serialize lists of size 1 as a single element.
     * @param codec the codec of a single element
     * @param <A> the type of the list's element
     */
    public static <A> Codec<List<A>> listCodec(Codec<A> codec) {
        return Codec.either(codec, codec.listOf()).xmap(
                either -> either.map(List::of, list -> list),
                list -> list.size() == 1 ? Either.left(list.getFirst()) : Either.right(list)
        );
    }

    /**
     * Creates a list codec using {@link #listCodec(Codec)} with validation to prevent empty lists.
     * @param codec the codec of a single element
     * @param <A> the type of the list's element
     */
    public static <A> Codec<List<A>> nonEmptyListCodec(Codec<A> codec) {
        return listCodec(codec).validate(list -> list.isEmpty() ? DataResult.error(() -> "Empty list") : DataResult.success(list));
    }

    public static <O, T> Codec<O> codec(RecordCodecBuilder<O, T> field, Function<T, O> function) {
        return RecordCodecBuilder.create(instance -> instance.group(field).apply(instance, function));
    }

    public static <O, T> MapCodec<O> mapCodec(RecordCodecBuilder<O, T> field, Function<T, O> function) {
        return RecordCodecBuilder.mapCodec(instance -> instance.group(field).apply(instance, function));
    }

    public static Codec<Block> blockCodecWithProperties(Property<?>... properties) {
        return BLOCK.validate(block -> {
            Collection<Property<?>> blockProperties = block.getStateDefinition().getProperties();
            for (Property<?> property : properties) {
                if(!blockProperties.contains(property)) {
                    return DataResult.error(() -> block + " does not contain property " + property);
                }
            }
            return DataResult.success(block);
        });
    }

    public static <A> MapCodec<List<A>> grammaticalListMapCodec(MapCodec<A> singleCodec, MapCodec<List<A>> listCodec) {
        return Codec.mapEither(singleCodec, listCodec).xmap(
                either -> either.map(List::of, Function.identity()),
                list -> list.size() == 1 ? Either.left(list.getFirst()) : Either.right(list)
        );
    }

    public static <A> MapCodec<List<A>> grammaticalListMapCodec(String singularName, String pluralName, Codec<A> singleCodec, Codec<List<A>> listCodec) {
        return grammaticalListMapCodec(singleCodec.fieldOf(singularName), listCodec.fieldOf(pluralName));
    }

    public static <A> MapCodec<List<A>> grammaticalListMapCodec(String singularName, String pluralName, Codec<A> singleCodec) {
        return grammaticalListMapCodec(singleCodec.fieldOf(singularName), singleCodec.listOf().fieldOf(pluralName));
    }

    public static <A> MapCodec<List<A>> grammaticalListMapCodec(String singularName, Codec<A> singleCodec, Codec<List<A>> listCodec) {
        return grammaticalListMapCodec(singularName, singularName + "s", singleCodec, listCodec);
    }

    public static <A> MapCodec<List<A>> grammaticalListMapCodec(String singularName, Codec<A> singleCodec) {
        return grammaticalListMapCodec(singularName, singleCodec, singleCodec.listOf());
    }

    public static Codec<Block> blockCodecWithPropertiesOf(Block block) {
        return blockCodecWithProperties(block.getStateDefinition().getProperties().toArray(new Property<?>[0]));
    }

    public static <T> Codec<MutableObject<T>> mutable(Codec<T> codec) {
        return codec.xmap(MutableObject::new, MutableObject::get);
    }

    public static <C, V> Codec<V> vector2Codec(
            Codec<C> componentCodec,
            BiFunction<C, C, ? extends V> toVector,
            BiFunction<V, Integer, C> getter
    ) {
        return componentCodec
                .listOf()
                .comapFlatMap(
                        list -> Util.fixedSize(list, 2).map(cs -> toVector.apply(
                                cs.getFirst(),
                                cs.get(1)
                        )),
                        v -> List.of(
                                getter.apply(v, 0),
                                getter.apply(v, 1)
                        )
                );
    }

    public static <C, V> Codec<V> vector3Codec(
            Codec<C> componentCodec,
            Function3<C, C, C, ? extends V> toVector,
            BiFunction<V, Integer, C> getter
    ) {
        return componentCodec
                .listOf()
                .comapFlatMap(
                        list -> Util.fixedSize(list, 3).map(cs -> toVector.apply(
                                cs.getFirst(),
                                cs.get(1),
                                cs.get(2)
                        )),
                        v -> List.of(
                                getter.apply(v, 0),
                                getter.apply(v, 1),
                                getter.apply(v, 2)
                        )
                );
    }

    public static <C, V> Codec<V> vector4Codec(
            Codec<C> componentCodec,
            Function4<C, C, C, C, ? extends V> toVector,
            BiFunction<V, Integer, C> getter
    ) {
        return componentCodec
                .listOf()
                .comapFlatMap(
                        list -> Util.fixedSize(list, 4).map(cs -> toVector.apply(
                                cs.getFirst(),
                                cs.get(1),
                                cs.get(2),
                                cs.get(3)
                        )),
                        v -> List.of(
                                getter.apply(v, 0),
                                getter.apply(v, 1),
                                getter.apply(v, 2),
                                getter.apply(v, 3)
                        )
                );
    }

    private CodecUtil() {
    }
}
