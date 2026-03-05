package com.eightsidedsquare.zine.mixin.entity.spawn;

import com.eightsidedsquare.zine.common.entity.spawn.ZineSpawnContext;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.variant.SpawnContext;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(SpawnContext.class)
public abstract class SpawnContextMixin implements ZineSpawnContext {

    @Unique
    private EntitySpawnReason spawnReason = EntitySpawnReason.NATURAL;

    @Override
    public void zine$setSpawnReason(EntitySpawnReason spawnReason) {
        this.spawnReason = spawnReason;
    }

    @Override
    public EntitySpawnReason zine$getSpawnReason() {
        return this.spawnReason;
    }
}
