package com.eightsidedsquare.zine.common.util.codec;

import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.block.Block;
import net.minecraft.entity.SpawnReason;
import net.minecraft.registry.Registries;
import net.minecraft.state.property.Property;
import net.minecraft.storage.ReadView;
import net.minecraft.text.TextColor;
import net.minecraft.util.Identifier;
import net.minecraft.util.dynamic.Codecs;
import net.minecraft.util.math.EulerAngle;

import java.util.*;
import java.util.function.Function;

public final class CodecUtil {

    public static final Codec<Character> CHARACTER = Codec.string(1, 1).xmap(
            string -> string.charAt(0),
            String::valueOf
    );
    public static final Codec<EulerAngle> EULER_ANGLE = RecordCodecBuilder.create(instance -> instance.group(
            Codec.FLOAT.optionalFieldOf("pitch", 0f).forGetter(EulerAngle::pitch),
            Codec.FLOAT.optionalFieldOf("yaw", 0f).forGetter(EulerAngle::yaw),
            Codec.FLOAT.optionalFieldOf("roll", 0f).forGetter(EulerAngle::roll)
    ).apply(instance, EulerAngle::new));
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
    public static final Codec<Block> BLOCK = Registries.BLOCK.getCodec();
    public static final Codec<Integer> COLOR = AlternativeCodec.create(
            Codecs.RGB,
            TextColor.CODEC,
            color -> Optional.of(TextColor.fromRgb(color)),
            textColor -> textColor.getRgb() | 0xff000000
    );
    public static final Codecs.IdMapper<Identifier, SpawnReason> SPAWN_REASON_IDS = createSpawnReasonIds();
    public static final Codec<SpawnReason> SPAWN_REASON = SPAWN_REASON_IDS.getCodec(Identifier.CODEC);

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
            Collection<Property<?>> blockProperties = block.getStateManager().getProperties();
            for (Property<?> property : properties) {
                if(!blockProperties.contains(property)) {
                    return DataResult.error(() -> block + " does not contain property " + property);
                }
            }
            return DataResult.success(block);
        });
    }

    public static Codec<Block> blockCodecWithPropertiesOf(Block block) {
        return blockCodecWithProperties(block.getStateManager().getProperties().toArray(new Property<?>[0]));
    }

    public static <T> void readToList(ReadView view, String key, List<T> list, Codec<? extends Collection<? extends T>> codec) {
        list.clear();
        view.read(key, codec).ifPresent(list::addAll);
    }

    public static <K, V> void readToMap(ReadView view, String key, Map<K, V> map, Codec<? extends Map<? extends K, ? extends V>> codec) {
        map.clear();
        view.read(key, codec).ifPresent(map::putAll);
    }

    private static Codecs.IdMapper<Identifier, SpawnReason> createSpawnReasonIds() {
        Codecs.IdMapper<Identifier, SpawnReason> ids = new Codecs.IdMapper<>();
        ids.put(Identifier.ofVanilla("natural"), SpawnReason.NATURAL);
        ids.put(Identifier.ofVanilla("chunk_generation"), SpawnReason.CHUNK_GENERATION);
        ids.put(Identifier.ofVanilla("spawner"), SpawnReason.SPAWNER);
        ids.put(Identifier.ofVanilla("structure"), SpawnReason.STRUCTURE);
        ids.put(Identifier.ofVanilla("breeding"), SpawnReason.BREEDING);
        ids.put(Identifier.ofVanilla("mob_summoned"), SpawnReason.MOB_SUMMONED);
        ids.put(Identifier.ofVanilla("jockey"), SpawnReason.JOCKEY);
        ids.put(Identifier.ofVanilla("event"), SpawnReason.EVENT);
        ids.put(Identifier.ofVanilla("conversion"), SpawnReason.CONVERSION);
        ids.put(Identifier.ofVanilla("reinforcement"), SpawnReason.REINFORCEMENT);
        ids.put(Identifier.ofVanilla("triggered"), SpawnReason.TRIGGERED);
        ids.put(Identifier.ofVanilla("bucket"), SpawnReason.BUCKET);
        ids.put(Identifier.ofVanilla("spawn_item_use"), SpawnReason.SPAWN_ITEM_USE);
        ids.put(Identifier.ofVanilla("command"), SpawnReason.COMMAND);
        ids.put(Identifier.ofVanilla("dispenser"), SpawnReason.DISPENSER);
        ids.put(Identifier.ofVanilla("patrol"), SpawnReason.PATROL);
        ids.put(Identifier.ofVanilla("trial_spawner"), SpawnReason.TRIAL_SPAWNER);
        ids.put(Identifier.ofVanilla("load"), SpawnReason.LOAD);
        ids.put(Identifier.ofVanilla("dimension_travel"), SpawnReason.DIMENSION_TRAVEL);
        return ids;
    }

    private CodecUtil() {
    }
}
