package com.eightsidedsquare.zine.common.entity;

import net.minecraft.resources.Identifier;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.entity.EntitySpawnReason;

public final class SpawnReasonIds {
    public static final ExtraCodecs.LateBoundIdMapper<Identifier, EntitySpawnReason> ID_MAPPER = new ExtraCodecs.LateBoundIdMapper<>();

    private SpawnReasonIds() {
    }

    static {
        ID_MAPPER.put(Identifier.withDefaultNamespace("natural"), EntitySpawnReason.NATURAL);
        ID_MAPPER.put(Identifier.withDefaultNamespace("chunk_generation"), EntitySpawnReason.CHUNK_GENERATION);
        ID_MAPPER.put(Identifier.withDefaultNamespace("spawner"), EntitySpawnReason.SPAWNER);
        ID_MAPPER.put(Identifier.withDefaultNamespace("structure"), EntitySpawnReason.STRUCTURE);
        ID_MAPPER.put(Identifier.withDefaultNamespace("breeding"), EntitySpawnReason.BREEDING);
        ID_MAPPER.put(Identifier.withDefaultNamespace("mob_summoned"), EntitySpawnReason.MOB_SUMMONED);
        ID_MAPPER.put(Identifier.withDefaultNamespace("jockey"), EntitySpawnReason.JOCKEY);
        ID_MAPPER.put(Identifier.withDefaultNamespace("event"), EntitySpawnReason.EVENT);
        ID_MAPPER.put(Identifier.withDefaultNamespace("conversion"), EntitySpawnReason.CONVERSION);
        ID_MAPPER.put(Identifier.withDefaultNamespace("reinforcement"), EntitySpawnReason.REINFORCEMENT);
        ID_MAPPER.put(Identifier.withDefaultNamespace("triggered"), EntitySpawnReason.TRIGGERED);
        ID_MAPPER.put(Identifier.withDefaultNamespace("bucket"), EntitySpawnReason.BUCKET);
        ID_MAPPER.put(Identifier.withDefaultNamespace("spawn_item_use"), EntitySpawnReason.SPAWN_ITEM_USE);
        ID_MAPPER.put(Identifier.withDefaultNamespace("command"), EntitySpawnReason.COMMAND);
        ID_MAPPER.put(Identifier.withDefaultNamespace("dispenser"), EntitySpawnReason.DISPENSER);
        ID_MAPPER.put(Identifier.withDefaultNamespace("patrol"), EntitySpawnReason.PATROL);
        ID_MAPPER.put(Identifier.withDefaultNamespace("trial_spawner"), EntitySpawnReason.TRIAL_SPAWNER);
        ID_MAPPER.put(Identifier.withDefaultNamespace("load"), EntitySpawnReason.LOAD);
        ID_MAPPER.put(Identifier.withDefaultNamespace("dimension_travel"), EntitySpawnReason.DIMENSION_TRAVEL);
    }

}
