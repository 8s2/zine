package com.eightsidedsquare.zine.common.predicate;

import net.minecraft.advancements.predicates.DamageSourcePredicate;
import net.minecraft.advancements.predicates.MinMaxBounds;
import net.minecraft.advancements.predicates.entity.EntityPredicate;
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
