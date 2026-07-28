package com.eightsidedsquare.zine.mixin.predicate;

import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.world.level.storage.loot.predicates.CompositeLootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(CompositeLootItemCondition.class)
public abstract class CompositeLootItemConditionMixin implements LootItemConditionMixin {
    @Shadow
    @Final
    protected HolderSet<LootItemCondition> terms;

    @Override
    public Iterable<LootItemCondition> zine$iterate() {
        return this.terms.stream().map(Holder::value).toList();
    }
}
