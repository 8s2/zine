package com.eightsidedsquare.zine.mixin.predicate;

import com.eightsidedsquare.zine.common.predicate.ZineDistancePredicate;
import net.minecraft.advancements.criterion.DistancePredicate;
import net.minecraft.advancements.criterion.MinMaxBounds;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(DistancePredicate.class)
public abstract class DistancePredicateMixin implements ZineDistancePredicate {

    @Shadow @Final @Mutable
    private MinMaxBounds.Doubles x;

    @Shadow @Final @Mutable
    private MinMaxBounds.Doubles y;

    @Shadow @Final @Mutable
    private MinMaxBounds.Doubles z;

    @Shadow @Final @Mutable
    private MinMaxBounds.Doubles horizontal;

    @Shadow @Final @Mutable
    private MinMaxBounds.Doubles absolute;

    @Override
    public void zine$setX(MinMaxBounds.Doubles x) {
        this.x = x;
    }

    @Override
    public void zine$setY(MinMaxBounds.Doubles y) {
        this.y = y;
    }

    @Override
    public void zine$setZ(MinMaxBounds.Doubles z) {
        this.z = z;
    }

    @Override
    public void zine$setHorizontal(MinMaxBounds.Doubles horizontal) {
        this.horizontal = horizontal;
    }

    @Override
    public void zine$setAbsolute(MinMaxBounds.Doubles absolute) {
        this.absolute = absolute;
    }
}
