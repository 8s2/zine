package com.eightsidedsquare.zine.common.entity.spawn;

import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.spawn.SpawnContext;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ServerWorldAccess;

public interface ZineSpawnContext {

    static SpawnContext of(ServerWorldAccess world, BlockPos pos, SpawnReason spawnReason) {
        SpawnContext ctx = SpawnContext.of(world, pos);
        ctx.zine$setSpawnReason(spawnReason);
        return ctx;
    }

    static SpawnContext of(SpawnContext ctx, SpawnReason spawnReason) {
        ctx.zine$setSpawnReason(spawnReason);
        return ctx;
    }

    default void zine$setSpawnReason(SpawnReason spawnReason) {
        throw new UnsupportedOperationException("Implemented via mixin.");
    }

    default SpawnReason zine$getSpawnReason() {
        throw new UnsupportedOperationException("Implemented via mixin.");
    }

}
