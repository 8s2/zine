package com.eightsidedsquare.zine.common.predicate;

import net.minecraft.advancements.predicates.MinMaxBounds;

public interface ZineLightPredicate {
    default void zine$setLight(MinMaxBounds.Ints light) {
        throw new UnsupportedOperationException("Implemented via mixin.");
    }
}
