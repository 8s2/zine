package com.eightsidedsquare.zine.common.predicate;

import net.minecraft.advancements.criterion.ItemPredicate;
import net.minecraft.world.entity.EquipmentSlot;
import org.jetbrains.annotations.Nullable;

public interface ZineEntityEquipmentPredicate {

    default void zine$setHead(@Nullable ItemPredicate head) {
        throw new UnsupportedOperationException("Implemented via mixin.");
    }

    default void zine$setChest(@Nullable ItemPredicate chest) {
        throw new UnsupportedOperationException("Implemented via mixin.");
    }

    default void zine$setLegs(@Nullable ItemPredicate legs) {
        throw new UnsupportedOperationException("Implemented via mixin.");
    }

    default void zine$setFeet(@Nullable ItemPredicate feet) {
        throw new UnsupportedOperationException("Implemented via mixin.");
    }

    default void zine$setBody(@Nullable ItemPredicate body) {
        throw new UnsupportedOperationException("Implemented via mixin.");
    }

    default void zine$setMainhand(@Nullable ItemPredicate mainhand) {
        throw new UnsupportedOperationException("Implemented via mixin.");
    }

    default void zine$setOffhand(@Nullable ItemPredicate offhand) {
        throw new UnsupportedOperationException("Implemented via mixin.");
    }

    default void zine$set(EquipmentSlot slot, @Nullable ItemPredicate itemPredicate) {
        switch (slot) {
            case MAINHAND -> this.zine$setMainhand(itemPredicate);
            case OFFHAND -> this.zine$setOffhand(itemPredicate);
            case FEET -> this.zine$setFeet(itemPredicate);
            case LEGS -> this.zine$setLegs(itemPredicate);
            case CHEST -> this.zine$setChest(itemPredicate);
            case HEAD -> this.zine$setHead(itemPredicate);
            case BODY -> this.zine$setBody(itemPredicate);
        }
    }

}
