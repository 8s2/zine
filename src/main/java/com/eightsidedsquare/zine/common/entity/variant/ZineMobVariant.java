package com.eightsidedsquare.zine.common.entity.variant;

import net.minecraft.entity.spawn.SpawnConditionSelectors;
import net.minecraft.util.AssetInfo;

public interface ZineMobVariant {

    default void zine$setAssetInfo(AssetInfo.TextureAssetInfo assetInfo) {
        throw new UnsupportedOperationException("Implemented via mixin.");
    }

    default void zine$setSpawnConditions(SpawnConditionSelectors spawnConditions) {
        throw new UnsupportedOperationException("Implemented via mixin.");
    }

}
