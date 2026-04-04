package com.eightsidedsquare.zine.common.entity.spawn;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.variant.SpawnContext;
import net.minecraft.world.level.ServerLevelAccessor;

public interface ZineSpawnContext {

    static SpawnContext of(ServerLevelAccessor world, BlockPos pos, EntitySpawnReason spawnReason) {
        SpawnContext ctx = SpawnContext.create(world, pos);
        ctx.zine$setSpawnReason(spawnReason);
        return ctx;
    }

    static SpawnContext of(SpawnContext ctx, EntitySpawnReason spawnReason) {
        ctx.zine$setSpawnReason(spawnReason);
        return ctx;
    }

    default void zine$setSpawnReason(EntitySpawnReason spawnReason) {
        throw new UnsupportedOperationException("Implemented via mixin.");
    }

    default EntitySpawnReason zine$getSpawnReason() {
        throw new UnsupportedOperationException("Implemented via mixin.");
    }

}
