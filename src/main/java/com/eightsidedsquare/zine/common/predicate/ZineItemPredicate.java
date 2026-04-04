package com.eightsidedsquare.zine.common.predicate;

import net.minecraft.advancements.criterion.DataComponentMatchers;
import net.minecraft.advancements.criterion.MinMaxBounds;
import net.minecraft.core.HolderSet;
import net.minecraft.world.item.Item;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;

public interface ZineItemPredicate {

    default void zine$setItems(@Nullable HolderSet<Item> items) {
        throw new UnsupportedOperationException("Implemented via mixin.");
    }

    default void zine$addItem(Item item) {
        throw new UnsupportedOperationException("Implemented via mixin.");
    }

    default void zine$addItems(Collection<Item> items) {
        throw new UnsupportedOperationException("Implemented via mixin.");
    }

    default void zine$setCount(MinMaxBounds.Ints count) {
        throw new UnsupportedOperationException("Implemented via mixin.");
    }

    default void zine$setComponents(DataComponentMatchers components) {
        throw new UnsupportedOperationException("Implemented via mixin.");
    }

}
