package com.eightsidedsquare.zine.common.world.structure;

import net.minecraft.core.Holder;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

public interface ZineFeaturePoolElement {

    default Holder<PlacedFeature> zine$getFeature() {
        throw new UnsupportedOperationException("Implemented via mixin.");
    }

    default void zine$setFeature(Holder<PlacedFeature> feature) {
        throw new UnsupportedOperationException("Implemented via mixin.");
    }

    default CompoundTag zine$getNbt() {
        throw new UnsupportedOperationException("Implemented via mixin.");
    }

    default void zine$setNbt(CompoundTag nbt) {
        throw new UnsupportedOperationException("Implemented via mixin.");
    }

}
