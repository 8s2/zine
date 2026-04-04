package com.eightsidedsquare.zine.mixin.predicate;

import com.eightsidedsquare.zine.common.predicate.ZineEntityPredicate;
import com.eightsidedsquare.zine.common.predicate.ZineEntityPredicateLocation;
import net.minecraft.advancements.criterion.*;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;

import java.util.Optional;

@Mixin(EntityPredicate.class)
public abstract class EntityPredicateMixin implements ZineEntityPredicate, ZineEntityPredicateLocation {

    @Shadow @Final @Mutable
    private Optional<EntityTypePredicate> entityType;

    @Shadow @Final @Mutable
    private Optional<DistancePredicate> distanceToPlayer;

    @Shadow @Final @Mutable
    private Optional<MovementPredicate> movement;

    @Shadow @Final @Mutable
    private EntityPredicate.LocationWrapper location;

    @Shadow @Final @Mutable
    private Optional<MobEffectsPredicate> effects;

    @Shadow @Final @Mutable
    private Optional<NbtPredicate> nbt;

    @Shadow @Final @Mutable
    private Optional<EntityFlagsPredicate> flags;

    @Shadow @Final @Mutable
    private Optional<EntityEquipmentPredicate> equipment;

    @Shadow @Final @Mutable
    private Optional<EntitySubPredicate> subPredicate;

    @Shadow @Final @Mutable
    private Optional<Integer> periodicTick;

    @Shadow @Final @Mutable
    private Optional<EntityPredicate> vehicle;

    @Shadow @Final @Mutable
    private Optional<EntityPredicate> passenger;

    @Shadow @Final @Mutable
    private Optional<EntityPredicate> targetedEntity;

    @Shadow @Final @Mutable
    private Optional<String> team;

    @Shadow @Final @Mutable
    private Optional<SlotsPredicate> slots;

    @Shadow @Final @Mutable
    private DataComponentMatchers components;

    @Override
    public void zine$setType(@Nullable EntityTypePredicate type) {
        this.entityType = Optional.ofNullable(type);
    }

    @Override
    public void zine$setDistance(@Nullable DistancePredicate distance) {
        this.distanceToPlayer = Optional.ofNullable(distance);
    }

    @Override
    public void zine$setMovement(@Nullable MovementPredicate movement) {
        this.movement = Optional.ofNullable(movement);
    }

    @Override
    public void zine$setLocation(EntityPredicate.LocationWrapper location) {
        this.location = location;
    }

    @Override
    public void zine$setEffects(@Nullable MobEffectsPredicate effects) {
        this.effects = Optional.ofNullable(effects);
    }

    @Override
    public void zine$setNbt(@Nullable NbtPredicate nbt) {
        this.nbt = Optional.ofNullable(nbt);
    }

    @Override
    public void zine$setFlags(@Nullable EntityFlagsPredicate flags) {
        this.flags = Optional.ofNullable(flags);
    }

    @Override
    public void zine$setEquipment(@Nullable EntityEquipmentPredicate equipment) {
        this.equipment = Optional.ofNullable(equipment);
    }

    @Override
    public void zine$setTypeSpecific(@Nullable EntitySubPredicate typeSpecific) {
        this.subPredicate = Optional.ofNullable(typeSpecific);
    }

    @Override
    public void zine$setPeriodicTick(@Nullable Integer periodicTick) {
        this.periodicTick = Optional.ofNullable(periodicTick);
    }

    @Override
    public void zine$setVehicle(@Nullable EntityPredicate vehicle) {
        this.vehicle = Optional.ofNullable(vehicle);
    }

    @Override
    public void zine$setPassenger(@Nullable EntityPredicate passenger) {
        this.passenger = Optional.ofNullable(passenger);
    }

    @Override
    public void zine$setTargetedEntity(@Nullable EntityPredicate targetedEntity) {
        this.targetedEntity = Optional.ofNullable(targetedEntity);
    }

    @Override
    public void zine$setTeam(@Nullable String team) {
        this.team = Optional.ofNullable(team);
    }

    @Override
    public void zine$setSlots(@Nullable SlotsPredicate slots) {
        this.slots = Optional.ofNullable(slots);
    }

    @Override
    public void zine$setComponents(DataComponentMatchers components) {
        this.components = components;
    }

    @Override
    public void zine$setLocated(@Nullable LocationPredicate located) {
        this.location.zine$setLocated(located);
    }

    @Override
    public void zine$setSteppingOn(@Nullable LocationPredicate steppingOn) {
        this.location.zine$setSteppingOn(steppingOn);
    }

    @Override
    public void zine$setAffectsMovement(@Nullable LocationPredicate affectsMovement) {
        this.location.zine$setAffectsMovement(affectsMovement);
    }
}
