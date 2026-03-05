package com.eightsidedsquare.zine.mixin.predicate;

import com.eightsidedsquare.zine.common.predicate.ZineMovementPredicate;
import net.minecraft.advancements.criterion.MinMaxBounds;
import net.minecraft.advancements.criterion.MovementPredicate;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(MovementPredicate.class)
public abstract class MovementPredicateMixin implements ZineMovementPredicate {

    @Shadow @Final @Mutable
    private MinMaxBounds.Doubles x;

    @Shadow @Final @Mutable
    private MinMaxBounds.Doubles y;

    @Shadow @Final @Mutable
    private MinMaxBounds.Doubles z;

    @Shadow @Final @Mutable
    private MinMaxBounds.Doubles speed;

    @Shadow @Final @Mutable
    private MinMaxBounds.Doubles horizontalSpeed;

    @Shadow @Final @Mutable
    private MinMaxBounds.Doubles verticalSpeed;

    @Shadow @Final @Mutable
    private MinMaxBounds.Doubles fallDistance;

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
    public void zine$setSpeed(MinMaxBounds.Doubles speed) {
        this.speed = speed;
    }

    @Override
    public void zine$setHorizontalSpeed(MinMaxBounds.Doubles horizontalSpeed) {
        this.horizontalSpeed = horizontalSpeed;
    }

    @Override
    public void zine$setVerticalSpeed(MinMaxBounds.Doubles verticalSpeed) {
        this.verticalSpeed = verticalSpeed;
    }

    @Override
    public void zine$setFallDistance(MinMaxBounds.Doubles fallDistance) {
        this.fallDistance = fallDistance;
    }

}
