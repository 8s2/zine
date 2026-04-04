package com.eightsidedsquare.zine.common.predicate;

import net.minecraft.advancements.criterion.MobEffectsPredicate;
import net.minecraft.core.Holder;
import net.minecraft.world.effect.MobEffect;

import java.util.Map;

public interface ZineMobEffectsPredicate {

    default void zine$setEffects(Map<Holder<MobEffect>, MobEffectsPredicate.MobEffectInstancePredicate> effects) {
        throw new UnsupportedOperationException("Implemented via mixin.");
    }

    default void zine$addEffect(Holder<MobEffect> effect, MobEffectsPredicate.MobEffectInstancePredicate data) {
        throw new UnsupportedOperationException("Implemented via mixin.");
    }

    default void zine$addEffect(Holder<MobEffect> effect) {
        this.zine$addEffect(effect, new MobEffectsPredicate.MobEffectInstancePredicate());
    }

}
