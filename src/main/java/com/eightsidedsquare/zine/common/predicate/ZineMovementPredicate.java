package com.eightsidedsquare.zine.common.predicate;

import net.minecraft.advancements.criterion.MinMaxBounds;

public interface ZineMovementPredicate {

    default void zine$setX(MinMaxBounds.Doubles x) {
        throw new UnsupportedOperationException("Implemented via mixin.");
    }

    default void zine$setY(MinMaxBounds.Doubles y) {
        throw new UnsupportedOperationException("Implemented via mixin.");
    }

    default void zine$setZ(MinMaxBounds.Doubles z) {
        throw new UnsupportedOperationException("Implemented via mixin.");
    }

    default void zine$setSpeed(MinMaxBounds.Doubles speed) {
        throw new UnsupportedOperationException("Implemented via mixin.");
    }

    default void zine$setHorizontalSpeed(MinMaxBounds.Doubles horizontalSpeed) {
        throw new UnsupportedOperationException("Implemented via mixin.");
    }

    default void zine$setVerticalSpeed(MinMaxBounds.Doubles verticalSpeed) {
        throw new UnsupportedOperationException("Implemented via mixin.");
    }

    default void zine$setFallDistance(MinMaxBounds.Doubles fallDistance) {
        throw new UnsupportedOperationException("Implemented via mixin.");
    }

}
