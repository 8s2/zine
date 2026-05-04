package com.eightsidedsquare.zine.common.predicate;

import com.mojang.serialization.Codec;
import net.minecraft.advancements.predicates.entity.EntitySubPredicate;

import java.util.Map;

public interface ZineEntityPredicate {
    default Map<Codec<? extends EntitySubPredicate>, EntitySubPredicate> zine$getParts() {
        throw new UnsupportedOperationException("Implemented via mixin.");
    }

    default void zine$setParts(Map<Codec<? extends EntitySubPredicate>, EntitySubPredicate> parts) {
        throw new UnsupportedOperationException("Implemented via mixin.");
    }

    default <T extends EntitySubPredicate> void zine$addPart(Codec<T> codec, T predicate) {
        throw new UnsupportedOperationException("Implemented via mixin.");
    }

    default void zine$addParts(Map<Codec<? extends EntitySubPredicate>, EntitySubPredicate> parts) {
        throw new UnsupportedOperationException("Implemented via mixin.");
    }
}
