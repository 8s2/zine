package com.eightsidedsquare.zine.common.predicate;

import net.minecraft.advancements.criterion.MinMaxBounds;

public interface ZineLightPredicate {

    default void zine$setRange(MinMaxBounds.Ints range) {
        throw new UnsupportedOperationException("Implemented via mixin.");
    }

}
