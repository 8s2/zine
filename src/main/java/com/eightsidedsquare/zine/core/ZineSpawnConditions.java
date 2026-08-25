package com.eightsidedsquare.zine.core;

import com.eightsidedsquare.zine.common.entity.spawn.*;

public final class ZineSpawnConditions {
    static void init() {
        ZineMod.REGISTRY.spawnCondition("all_of", AllOfCheck.CODEC);
        ZineMod.REGISTRY.spawnCondition("any_of", AnyOfCheck.CODEC);
        ZineMod.REGISTRY.spawnCondition("attribute", AttributeCheck.CODEC);
        ZineMod.REGISTRY.spawnCondition("dimension", DimensionCheck.CODEC);
        ZineMod.REGISTRY.spawnCondition("inverted", InvertedCheck.CODEC);
        ZineMod.REGISTRY.spawnCondition("noise", NoiseCheck.CODEC);
        ZineMod.REGISTRY.spawnCondition("random", RandomCheck.CODEC);
        ZineMod.REGISTRY.spawnCondition("spawn_reason", SpawnReasonCheck.CODEC);
    }

    private ZineSpawnConditions() {
    }
}
