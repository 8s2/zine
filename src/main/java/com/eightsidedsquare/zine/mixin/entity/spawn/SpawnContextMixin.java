package com.eightsidedsquare.zine.mixin.entity.spawn;

import com.eightsidedsquare.zine.common.entity.spawn.ZineSpawnContext;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.spawn.SpawnContext;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(SpawnContext.class)
public abstract class SpawnContextMixin implements ZineSpawnContext {

    @Unique
    private SpawnReason spawnReason = SpawnReason.NATURAL;

    @Override
    public void zine$setSpawnReason(SpawnReason spawnReason) {
        this.spawnReason = spawnReason;
    }

    @Override
    public SpawnReason zine$getSpawnReason() {
        return this.spawnReason;
    }
}
