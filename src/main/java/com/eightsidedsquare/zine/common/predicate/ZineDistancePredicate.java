package com.eightsidedsquare.zine.common.predicate;

import net.minecraft.advancements.criterion.MinMaxBounds;

public interface ZineDistancePredicate {

    default void zine$setX(MinMaxBounds.Doubles x) {
        throw new UnsupportedOperationException("Implemented via mixin.");
    }

    default void zine$setY(MinMaxBounds.Doubles y) {
        throw new UnsupportedOperationException("Implemented via mixin.");
    }

    default void zine$setZ(MinMaxBounds.Doubles z) {
        throw new UnsupportedOperationException("Implemented via mixin.");
    }

    default void zine$setHorizontal(MinMaxBounds.Doubles horizontal) {
        throw new UnsupportedOperationException("Implemented via mixin.");
    }

    default void zine$setAbsolute(MinMaxBounds.Doubles absolute) {
        throw new UnsupportedOperationException("Implemented via mixin.");
    }

}
