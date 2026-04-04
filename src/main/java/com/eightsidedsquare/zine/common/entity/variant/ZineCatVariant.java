package com.eightsidedsquare.zine.common.entity.variant;

import net.minecraft.core.ClientAsset;
import net.minecraft.world.entity.variant.SpawnPrioritySelectors;

public interface ZineCatVariant {

    default void zine$setAdultAssetInfo(ClientAsset.ResourceTexture adultAssetInfo) {
        throw new UnsupportedOperationException("Implemented via mixin.");
    }

    default void zine$setBabyAssetInfo(ClientAsset.ResourceTexture babyAssetInfo) {
        throw new UnsupportedOperationException("Implemented via mixin.");
    }

    default void zine$setSpawnConditions(SpawnPrioritySelectors spawnConditions) {
        throw new UnsupportedOperationException("Implemented via mixin.");
    }

}
