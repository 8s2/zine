package com.eightsidedsquare.zine.mixin.predicate;

import com.eightsidedsquare.zine.common.predicate.ZineEntityPredicateLocation;
import net.minecraft.advancements.criterion.EntityPredicate;
import net.minecraft.advancements.criterion.LocationPredicate;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;

import java.util.Optional;

@Mixin(EntityPredicate.LocationWrapper.class)
public abstract class EntityPredicateLocationWrapperMixin implements ZineEntityPredicateLocation {

    @Shadow @Final @Mutable
    private Optional<LocationPredicate> located;

    @Shadow @Final @Mutable
    private Optional<LocationPredicate> steppingOn;

    @Shadow @Final @Mutable
    private Optional<LocationPredicate> affectsMovement;

    @Override
    public void zine$setLocated(@Nullable LocationPredicate located) {
        this.located = Optional.ofNullable(located);
    }

    @Override
    public void zine$setSteppingOn(@Nullable LocationPredicate steppingOn) {
        this.steppingOn = Optional.ofNullable(steppingOn);
    }

    @Override
    public void zine$setAffectsMovement(@Nullable LocationPredicate affectsMovement) {
        this.affectsMovement = Optional.ofNullable(affectsMovement);
    }
}
