package com.eightsidedsquare.zine.common.predicate;

import net.minecraft.core.component.DataComponentExactPredicate;
import net.minecraft.core.component.predicates.DataComponentPredicate;

import java.util.Map;

public interface ZineDataComponentMatchers {

    default void zine$setExact(DataComponentExactPredicate exact) {
        throw new UnsupportedOperationException("Implemented via mixin.");
    }

    default void zine$setPartial(Map<DataComponentPredicate.Type<?>, DataComponentPredicate> partial) {
        throw new UnsupportedOperationException("Implemented via mixin.");
    }

    default void zine$addPartial(DataComponentPredicate.Type<?> type, DataComponentPredicate predicate) {
        throw new UnsupportedOperationException("Implemented via mixin.");
    }

}
