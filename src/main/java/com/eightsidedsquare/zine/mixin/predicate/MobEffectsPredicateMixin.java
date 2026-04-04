package com.eightsidedsquare.zine.mixin.predicate;

import com.eightsidedsquare.zine.common.predicate.ZineMobEffectsPredicate;
import com.eightsidedsquare.zine.common.util.ZineUtil;
import net.minecraft.advancements.criterion.MobEffectsPredicate;
import net.minecraft.core.Holder;
import net.minecraft.world.effect.MobEffect;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;

import java.util.Map;

@Mixin(MobEffectsPredicate.class)
public abstract class MobEffectsPredicateMixin implements ZineMobEffectsPredicate {

    @Shadow @Final @Mutable
    private Map<Holder<MobEffect>, MobEffectsPredicate.MobEffectInstancePredicate> effectMap;

    @Override
    public void zine$setEffects(Map<Holder<MobEffect>, MobEffectsPredicate.MobEffectInstancePredicate> effects) {
        this.effectMap = effects;
    }

    @Override
    public void zine$addEffect(Holder<MobEffect> effect, MobEffectsPredicate.MobEffectInstancePredicate data) {
        this.effectMap = ZineUtil.putOrUnfreeze(this.effectMap, effect, data);
    }
}
