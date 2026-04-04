package com.eightsidedsquare.zine.mixin.predicate;

import com.eightsidedsquare.zine.common.predicate.ZineContextAwarePredicate;
import com.eightsidedsquare.zine.common.util.ZineUtil;
import net.minecraft.advancements.criterion.ContextAwarePredicate;
import net.minecraft.util.Util;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;

import java.util.List;
import java.util.function.Predicate;

@Mixin(ContextAwarePredicate.class)
public abstract class ContextAwarePredicateMixin implements ZineContextAwarePredicate {

    @Shadow @Final @Mutable
    private List<LootItemCondition> conditions;

    @Shadow @Final @Mutable
    private Predicate<LootContext> compositePredicates;

    @Override
    public List<LootItemCondition> zine$getConditions() {
        return this.conditions;
    }

    @Override
    public void zine$setConditions(List<LootItemCondition> conditions) {
        this.conditions = conditions;
        this.compositePredicates = Util.allOf(conditions);
    }

    @Override
    public void zine$addCondition(LootItemCondition condition) {
        this.conditions = ZineUtil.addOrUnfreeze(this.conditions, condition);
        this.compositePredicates = Util.allOf(this.conditions);
    }

    @Override
    public void zine$addConditions(List<LootItemCondition> conditions) {
        this.conditions = ZineUtil.addAllOrUnfreeze(this.conditions, conditions);
        this.compositePredicates = Util.allOf(this.conditions);
    }
}
