package com.eightsidedsquare.zine.common.predicate;

import net.minecraft.advancements.criterion.DamageSourcePredicate;
import net.minecraft.advancements.criterion.EntityPredicate;
import net.minecraft.advancements.criterion.MinMaxBounds;
import org.jetbrains.annotations.Nullable;

public interface ZineDamagePredicate {

    default void zine$setDealt(MinMaxBounds.Doubles dealt) {
        throw new UnsupportedOperationException("Implemented via mixin.");
    }

    default void zine$setTaken(MinMaxBounds.Doubles taken) {
        throw new UnsupportedOperationException("Implemented via mixin.");
    }

    default void zine$setSourceEntity(@Nullable EntityPredicate sourceEntity) {
        throw new UnsupportedOperationException("Implemented via mixin.");
    }

    default void zine$setBlocked(@Nullable Boolean blocked) {
        throw new UnsupportedOperationException("Implemented via mixin.");
    }

    default void zine$setType(@Nullable DamageSourcePredicate type) {
        throw new UnsupportedOperationException("Implemented via mixin.");
    }

}
