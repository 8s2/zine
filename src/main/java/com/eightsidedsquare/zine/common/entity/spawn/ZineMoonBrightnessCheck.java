package com.eightsidedsquare.zine.common.entity.spawn;

import net.minecraft.advancements.predicates.MinMaxBounds;

public interface ZineMoonBrightnessCheck {
    default void zine$setRange(MinMaxBounds.Doubles range) {
        throw new UnsupportedOperationException("Implemented via mixin.");
    }
}
