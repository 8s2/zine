package com.eightsidedsquare.zine.common.predicate;

import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;

import java.util.List;

public interface ZineLootItemCondition {
    default Iterable<LootItemCondition> zine$iterate() {
        return List.of((LootItemCondition) this);
    }
}
