package com.eightsidedsquare.zine.common.level.structure;

import net.minecraft.core.Holder;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

public interface ZineFeaturePoolElement {
    default Holder<PlacedFeature> zine$getFeature() {
        throw new UnsupportedOperationException("Implemented via mixin.");
    }

    default void zine$setFeature(Holder<PlacedFeature> feature) {
        throw new UnsupportedOperationException("Implemented via mixin.");
    }
}
