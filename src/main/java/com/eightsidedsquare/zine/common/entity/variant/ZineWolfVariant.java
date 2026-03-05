package com.eightsidedsquare.zine.common.entity.variant;

import net.minecraft.core.ClientAsset;
import net.minecraft.world.entity.animal.wolf.WolfVariant;
import net.minecraft.world.entity.variant.SpawnPrioritySelectors;

public interface ZineWolfVariant {

    default void zine$setAdultInfo(WolfVariant.AssetInfo adultInfo) {
        throw new UnsupportedOperationException("Implemented via mixin.");
    }

    default void zine$setBabyInfo(WolfVariant.AssetInfo babyInfo) {
        throw new UnsupportedOperationException("Implemented via mixin.");
    }

    default void zine$setSpawnConditions(SpawnPrioritySelectors spawnConditions) {
        throw new UnsupportedOperationException("Implemented via mixin.");
    }

    interface ZineAssetInfo {

        default void zine$setWild(ClientAsset.ResourceTexture wild) {
            throw new UnsupportedOperationException("Implemented via mixin.");
        }

        default void zine$setTame(ClientAsset.ResourceTexture tame) {
            throw new UnsupportedOperationException("Implemented via mixin.");
        }

        default void zine$setAngry(ClientAsset.ResourceTexture angry) {
            throw new UnsupportedOperationException("Implemented via mixin.");
        }

    }

}
