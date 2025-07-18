package com.eightsidedsquare.zine.mixin.advancement.criterion;

import com.eightsidedsquare.zine.common.criterion.ZineEffectsChangedCriterionConditions;
import com.eightsidedsquare.zine.common.criterion.ZinePlayerCriterionConditions;
import net.minecraft.advancement.criterion.EffectsChangedCriterion;
import net.minecraft.predicate.entity.EntityEffectPredicate;
import net.minecraft.predicate.entity.LootContextPredicate;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;

import java.util.Optional;

@Mixin(EffectsChangedCriterion.Conditions.class)
public abstract class EffectsChangedCriterionConditionsMixin implements ZinePlayerCriterionConditions,
        ZineEffectsChangedCriterionConditions {

    @Shadow @Final @Mutable
    private Optional<LootContextPredicate> player;

    @Shadow @Final @Mutable
    private Optional<EntityEffectPredicate> effects;

    @Shadow @Final @Mutable
    private Optional<LootContextPredicate> source;

    @Override
    public void zine$setPlayer(@Nullable LootContextPredicate player) {
        this.player = Optional.ofNullable(player);
    }

    @Override
    public void zine$setEffects(@Nullable EntityEffectPredicate effects) {
        this.effects = Optional.ofNullable(effects);
    }

    @Override
    public void zine$setSource(@Nullable LootContextPredicate source) {
        this.source = Optional.ofNullable(source);
    }
}
