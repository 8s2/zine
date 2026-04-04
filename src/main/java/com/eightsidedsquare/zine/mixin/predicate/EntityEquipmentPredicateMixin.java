package com.eightsidedsquare.zine.mixin.predicate;

import com.eightsidedsquare.zine.common.predicate.ZineEntityEquipmentPredicate;
import net.minecraft.advancements.criterion.EntityEquipmentPredicate;
import net.minecraft.advancements.criterion.ItemPredicate;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;

import java.util.Optional;

@Mixin(EntityEquipmentPredicate.class)
public abstract class EntityEquipmentPredicateMixin implements ZineEntityEquipmentPredicate {

    @Shadow @Final @Mutable
    private Optional<ItemPredicate> head;

    @Shadow @Final @Mutable
    private Optional<ItemPredicate> chest;

    @Shadow @Final @Mutable
    private Optional<ItemPredicate> legs;

    @Shadow @Final @Mutable
    private Optional<ItemPredicate> feet;

    @Shadow @Final @Mutable
    private Optional<ItemPredicate> body;

    @Shadow @Final @Mutable
    private Optional<ItemPredicate> mainhand;

    @Shadow @Final @Mutable
    private Optional<ItemPredicate> offhand;

    @Override
    public void zine$setHead(@Nullable ItemPredicate head) {
        this.head = Optional.ofNullable(head);
    }

    @Override
    public void zine$setChest(@Nullable ItemPredicate chest) {
        this.chest = Optional.ofNullable(chest);
    }

    @Override
    public void zine$setLegs(@Nullable ItemPredicate legs) {
        this.legs = Optional.ofNullable(legs);
    }

    @Override
    public void zine$setFeet(@Nullable ItemPredicate feet) {
        this.feet = Optional.ofNullable(feet);
    }

    @Override
    public void zine$setBody(@Nullable ItemPredicate body) {
        this.body = Optional.ofNullable(body);
    }

    @Override
    public void zine$setMainhand(@Nullable ItemPredicate mainhand) {
        this.mainhand = Optional.ofNullable(mainhand);
    }

    @Override
    public void zine$setOffhand(@Nullable ItemPredicate offhand) {
        this.offhand = Optional.ofNullable(offhand);
    }
}
