package com.eightsidedsquare.zine.common.criterion;

import org.jetbrains.annotations.Nullable;

public interface ZineSpearMobsCriterionConditions {

    default void zine$setCount(@Nullable Integer count) {
        throw new UnsupportedOperationException("Implemented via mixin.");
    }

}
