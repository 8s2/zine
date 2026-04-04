package com.eightsidedsquare.zine.common.entity.variant;

import net.minecraft.core.ClientAsset;
import net.minecraft.world.entity.variant.ModelAndTexture;
import net.minecraft.world.entity.variant.SpawnPrioritySelectors;

public interface ZineAgeableMobVariant<T> {

    default void zine$setModelAndTexture(ModelAndTexture<T> modelAndTexture) {
        throw new UnsupportedOperationException("Implemented via mixin.");
    }

    default void zine$setBabyTexture(ClientAsset.ResourceTexture babyTexture) {
        throw new UnsupportedOperationException("Implemented via mixin.");
    }

    default void zine$setSpawnConditions(SpawnPrioritySelectors spawnConditions) {
        throw new UnsupportedOperationException("Implemented via mixin.");
    }

}
