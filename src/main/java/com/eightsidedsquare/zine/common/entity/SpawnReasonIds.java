package com.eightsidedsquare.zine.common.entity;

import net.minecraft.entity.SpawnReason;
import net.minecraft.util.Identifier;
import net.minecraft.util.dynamic.Codecs;

public final class SpawnReasonIds {

    public static final Codecs.IdMapper<Identifier, SpawnReason> IDS = new Codecs.IdMapper<>();

    private SpawnReasonIds() {
    }

    static {
        IDS.put(Identifier.ofVanilla("natural"), SpawnReason.NATURAL);
        IDS.put(Identifier.ofVanilla("chunk_generation"), SpawnReason.CHUNK_GENERATION);
        IDS.put(Identifier.ofVanilla("spawner"), SpawnReason.SPAWNER);
        IDS.put(Identifier.ofVanilla("structure"), SpawnReason.STRUCTURE);
        IDS.put(Identifier.ofVanilla("breeding"), SpawnReason.BREEDING);
        IDS.put(Identifier.ofVanilla("mob_summoned"), SpawnReason.MOB_SUMMONED);
        IDS.put(Identifier.ofVanilla("jockey"), SpawnReason.JOCKEY);
        IDS.put(Identifier.ofVanilla("event"), SpawnReason.EVENT);
        IDS.put(Identifier.ofVanilla("conversion"), SpawnReason.CONVERSION);
        IDS.put(Identifier.ofVanilla("reinforcement"), SpawnReason.REINFORCEMENT);
        IDS.put(Identifier.ofVanilla("triggered"), SpawnReason.TRIGGERED);
        IDS.put(Identifier.ofVanilla("bucket"), SpawnReason.BUCKET);
        IDS.put(Identifier.ofVanilla("spawn_item_use"), SpawnReason.SPAWN_ITEM_USE);
        IDS.put(Identifier.ofVanilla("command"), SpawnReason.COMMAND);
        IDS.put(Identifier.ofVanilla("dispenser"), SpawnReason.DISPENSER);
        IDS.put(Identifier.ofVanilla("patrol"), SpawnReason.PATROL);
        IDS.put(Identifier.ofVanilla("trial_spawner"), SpawnReason.TRIAL_SPAWNER);
        IDS.put(Identifier.ofVanilla("load"), SpawnReason.LOAD);
        IDS.put(Identifier.ofVanilla("dimension_travel"), SpawnReason.DIMENSION_TRAVEL);
    }

}
