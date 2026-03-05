package com.eightsidedsquare.zine.common.predicate;

import net.minecraft.advancements.criterion.*;
import org.jetbrains.annotations.Nullable;

public interface ZineEntityPredicate {

    default void zine$setType(@Nullable EntityTypePredicate type) {
        throw new UnsupportedOperationException("Implemented via mixin.");
    }

    default void zine$setDistance(@Nullable DistancePredicate distance) {
        throw new UnsupportedOperationException("Implemented via mixin.");
    }

    default void zine$setMovement(@Nullable MovementPredicate movement) {
        throw new UnsupportedOperationException("Implemented via mixin.");
    }

    default void zine$setLocation(EntityPredicate.LocationWrapper location) {
        throw new UnsupportedOperationException("Implemented via mixin.");
    }

    default void zine$setEffects(@Nullable MobEffectsPredicate effects) {
        throw new UnsupportedOperationException("Implemented via mixin.");
    }

    default void zine$setNbt(@Nullable NbtPredicate nbt) {
        throw new UnsupportedOperationException("Implemented via mixin.");
    }

    default void zine$setFlags(@Nullable EntityFlagsPredicate flags) {
        throw new UnsupportedOperationException("Implemented via mixin.");
    }

    default void zine$setEquipment(@Nullable EntityEquipmentPredicate equipment) {
        throw new UnsupportedOperationException("Implemented via mixin.");
    }

    default void zine$setTypeSpecific(@Nullable EntitySubPredicate typeSpecific) {
        throw new UnsupportedOperationException("Implemented via mixin.");
    }

    default void zine$setPeriodicTick(@Nullable Integer periodicTick) {
        throw new UnsupportedOperationException("Implemented via mixin.");
    }

    default void zine$setVehicle(@Nullable EntityPredicate vehicle) {
        throw new UnsupportedOperationException("Implemented via mixin.");
    }

    default void zine$setPassenger(@Nullable EntityPredicate passenger) {
        throw new UnsupportedOperationException("Implemented via mixin.");
    }

    default void zine$setTargetedEntity(@Nullable EntityPredicate targetedEntity) {
        throw new UnsupportedOperationException("Implemented via mixin.");
    }

    default void zine$setTeam(@Nullable String team) {
        throw new UnsupportedOperationException("Implemented via mixin.");
    }

    default void zine$setSlots(@Nullable SlotsPredicate slots) {
        throw new UnsupportedOperationException("Implemented via mixin.");
    }

    default void zine$setComponents(DataComponentMatchers components) {
        throw new UnsupportedOperationException("Implemented via mixin.");
    }
}
