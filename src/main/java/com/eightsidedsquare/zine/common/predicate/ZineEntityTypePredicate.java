package com.eightsidedsquare.zine.common.predicate;

import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.world.entity.EntityType;

import java.util.Collection;

public interface ZineEntityTypePredicate {

    default void zine$setTypes(HolderSet<EntityType<?>> types) {
        throw new UnsupportedOperationException("Implemented via mixin.");
    }

    default void zine$addType(EntityType<?> type) {
        throw new UnsupportedOperationException("Implemented via mixin.");
    }

    default void zine$addType(Holder<EntityType<?>> type) {
        throw new UnsupportedOperationException("Implemented via mixin.");
    }

    default void zine$addTypes(HolderSet<EntityType<?>> types) {
        throw new UnsupportedOperationException("Implemented via mixin.");
    }

    default void zine$addTypes(Collection<EntityType<?>> types) {
        throw new UnsupportedOperationException("Implemented via mixin.");
    }

}
