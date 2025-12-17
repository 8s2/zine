package com.eightsidedsquare.zine.mixin.predicate;

import com.eightsidedsquare.zine.common.predicate.ZineEntityFlagsPredicate;
import net.minecraft.predicate.entity.EntityFlagsPredicate;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;

import java.util.Optional;

@Mixin(EntityFlagsPredicate.class)
public abstract class EntityFlagsPredicateMixin implements ZineEntityFlagsPredicate {

    @Shadow @Final @Mutable
    private Optional<Boolean> isOnGround;

    @Shadow @Final @Mutable
    private Optional<Boolean> isOnFire;

    @Shadow @Final @Mutable
    private Optional<Boolean> isSneaking;

    @Shadow @Final @Mutable
    private Optional<Boolean> isSprinting;

    @Shadow @Final @Mutable
    private Optional<Boolean> isFlying;

    @Shadow @Final @Mutable
    private Optional<Boolean> isSwimming;

    @Shadow @Final @Mutable
    private Optional<Boolean> isBaby;

    @Shadow @Final @Mutable
    private Optional<Boolean> isInWater;

    @Shadow @Final @Mutable
    private Optional<Boolean> isFallFlying;

    @Override
    public void zine$setOnGround(@Nullable Boolean onGround) {
        this.isOnGround = Optional.ofNullable(onGround);
    }

    @Override
    public void zine$setOnFire(@Nullable Boolean onFire) {
        this.isOnFire = Optional.ofNullable(onFire);
    }

    @Override
    public void zine$setSneaking(@Nullable Boolean sneaking) {
        this.isSneaking = Optional.ofNullable(sneaking);
    }

    @Override
    public void zine$setSprinting(@Nullable Boolean sprinting) {
        this.isSprinting = Optional.ofNullable(sprinting);
    }

    @Override
    public void zine$setFlying(@Nullable Boolean flying) {
        this.isFlying = Optional.ofNullable(flying);
    }

    @Override
    public void zine$setSwimming(@Nullable Boolean swimming) {
        this.isSwimming = Optional.ofNullable(swimming);
    }

    @Override
    public void zine$setBaby(@Nullable Boolean baby) {
        this.isBaby = Optional.ofNullable(baby);
    }

    @Override
    public void zine$setInWater(@Nullable Boolean inWater) {
        this.isInWater = Optional.ofNullable(inWater);
    }

    @Override
    public void zine$setFallFlying(@Nullable Boolean fallFlying) {
        this.isFallFlying = Optional.ofNullable(fallFlying);
    }
}
