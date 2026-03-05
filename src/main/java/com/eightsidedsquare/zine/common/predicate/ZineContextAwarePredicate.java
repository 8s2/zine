package com.eightsidedsquare.zine.common.predicate;

import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;

import java.util.List;

public interface ZineContextAwarePredicate {

    default List<LootItemCondition> zine$getConditions() {
        throw new UnsupportedOperationException("Implemented via mixin.");
    }

    default void zine$setConditions(List<LootItemCondition> conditions) {
        throw new UnsupportedOperationException("Implemented via mixin.");
    }

    default void zine$addCondition(LootItemCondition condition) {
        throw new UnsupportedOperationException("Implemented via mixin.");
    }

    default void zine$addConditions(List<LootItemCondition> conditions) {
        throw new UnsupportedOperationException("Implemented via mixin.");
    }

}
