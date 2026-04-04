package com.eightsidedsquare.zine.common.entity.variant;

import net.minecraft.core.ClientAsset;
import net.minecraft.world.entity.variant.SpawnPrioritySelectors;

public interface ZineFrogVariant {

    default void zine$setAssetInfo(ClientAsset.ResourceTexture assetInfo) {
        throw new UnsupportedOperationException("Implemented via mixin.");
    }

    default void zine$setSpawnConditions(SpawnPrioritySelectors spawnConditions) {
        throw new UnsupportedOperationException("Implemented via mixin.");
    }

}
