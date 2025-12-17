package com.eightsidedsquare.zine.common.predicate;

import org.jetbrains.annotations.Nullable;

public interface ZineEntityFlagsPredicate {

    default void zine$setOnGround(@Nullable Boolean onGround) {
        throw new UnsupportedOperationException("Implemented via mixin.");
    }

    default void zine$setOnFire(@Nullable Boolean onFire) {
        throw new UnsupportedOperationException("Implemented via mixin.");
    }

    default void zine$setSneaking(@Nullable Boolean sneaking) {
        throw new UnsupportedOperationException("Implemented via mixin.");
    }

    default void zine$setSprinting(@Nullable Boolean sprinting) {
        throw new UnsupportedOperationException("Implemented via mixin.");
    }

    default void zine$setSwimming(@Nullable Boolean swimming) {
        throw new UnsupportedOperationException("Implemented via mixin.");
    }

    default void zine$setFlying(@Nullable Boolean flying) {
        throw new UnsupportedOperationException("Implemented via mixin.");
    }

    default void zine$setBaby(@Nullable Boolean baby) {
        throw new UnsupportedOperationException("Implemented via mixin.");
    }

    default void zine$setInWater(@Nullable Boolean inWater) {
        throw new UnsupportedOperationException("Implemented via mixin.");
    }

    default void zine$setFallFlying(@Nullable Boolean fallFlying) {
        throw new UnsupportedOperationException("Implemented via mixin.");
    }

}
