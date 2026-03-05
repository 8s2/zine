package com.eightsidedsquare.zine.mixin.predicate;

import com.eightsidedsquare.zine.common.predicate.ZineLightPredicate;
import net.minecraft.advancements.criterion.LightPredicate;
import net.minecraft.advancements.criterion.MinMaxBounds;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(LightPredicate.class)
public abstract class LightPredicateMixin implements ZineLightPredicate {

    @Shadow @Final @Mutable
    private MinMaxBounds.Ints composite;

    @Override
    public void zine$setRange(MinMaxBounds.Ints range) {
        this.composite = range;
    }
}
