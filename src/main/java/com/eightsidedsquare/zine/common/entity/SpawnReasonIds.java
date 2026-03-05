package com.eightsidedsquare.zine.common.entity;

import net.minecraft.resources.Identifier;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.entity.EntitySpawnReason;

public final class SpawnReasonIds {

    public static final ExtraCodecs.LateBoundIdMapper<Identifier, EntitySpawnReason> IDS = new ExtraCodecs.LateBoundIdMapper<>();

    private SpawnReasonIds() {
    }

    static {
        IDS.put(Identifier.withDefaultNamespace("natural"), EntitySpawnReason.NATURAL);
        IDS.put(Identifier.withDefaultNamespace("chunk_generation"), EntitySpawnReason.CHUNK_GENERATION);
        IDS.put(Identifier.withDefaultNamespace("spawner"), EntitySpawnReason.SPAWNER);
        IDS.put(Identifier.withDefaultNamespace("structure"), EntitySpawnReason.STRUCTURE);
        IDS.put(Identifier.withDefaultNamespace("breeding"), EntitySpawnReason.BREEDING);
        IDS.put(Identifier.withDefaultNamespace("mob_summoned"), EntitySpawnReason.MOB_SUMMONED);
        IDS.put(Identifier.withDefaultNamespace("jockey"), EntitySpawnReason.JOCKEY);
        IDS.put(Identifier.withDefaultNamespace("event"), EntitySpawnReason.EVENT);
        IDS.put(Identifier.withDefaultNamespace("conversion"), EntitySpawnReason.CONVERSION);
        IDS.put(Identifier.withDefaultNamespace("reinforcement"), EntitySpawnReason.REINFORCEMENT);
        IDS.put(Identifier.withDefaultNamespace("triggered"), EntitySpawnReason.TRIGGERED);
        IDS.put(Identifier.withDefaultNamespace("bucket"), EntitySpawnReason.BUCKET);
        IDS.put(Identifier.withDefaultNamespace("spawn_item_use"), EntitySpawnReason.SPAWN_ITEM_USE);
        IDS.put(Identifier.withDefaultNamespace("command"), EntitySpawnReason.COMMAND);
        IDS.put(Identifier.withDefaultNamespace("dispenser"), EntitySpawnReason.DISPENSER);
        IDS.put(Identifier.withDefaultNamespace("patrol"), EntitySpawnReason.PATROL);
        IDS.put(Identifier.withDefaultNamespace("trial_spawner"), EntitySpawnReason.TRIAL_SPAWNER);
        IDS.put(Identifier.withDefaultNamespace("load"), EntitySpawnReason.LOAD);
        IDS.put(Identifier.withDefaultNamespace("dimension_travel"), EntitySpawnReason.DIMENSION_TRAVEL);
    }

}
