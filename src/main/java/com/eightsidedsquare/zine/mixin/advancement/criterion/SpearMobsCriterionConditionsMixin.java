package com.eightsidedsquare.zine.mixin.advancement.criterion;

import com.eightsidedsquare.zine.common.criterion.ZinePlayerCriterionConditions;
import com.eightsidedsquare.zine.common.criterion.ZineSpearMobsCriterionConditions;
import net.minecraft.advancement.criterion.SpearMobsCriterion;
import net.minecraft.predicate.entity.LootContextPredicate;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;

import java.util.Optional;

@Mixin(SpearMobsCriterion.Conditions.class)
public abstract class SpearMobsCriterionConditionsMixin implements ZinePlayerCriterionConditions,
        ZineSpearMobsCriterionConditions {

    @Shadow @Final @Mutable
    private Optional<LootContextPredicate> player;

    @Shadow @Final @Mutable
    private Optional<Integer> count;

    @Override
    public void zine$setPlayer(@Nullable LootContextPredicate player) {
        this.player = Optional.ofNullable(player);
    }

    @Override
    public void zine$setCount(@Nullable Integer count) {
        this.count = Optional.ofNullable(count);
    }
}
