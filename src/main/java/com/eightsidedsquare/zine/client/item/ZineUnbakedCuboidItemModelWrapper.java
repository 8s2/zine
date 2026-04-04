package com.eightsidedsquare.zine.client.item;

import com.mojang.math.Transformation;
import net.minecraft.client.color.item.ItemTintSource;
import net.minecraft.resources.Identifier;
import org.jspecify.annotations.Nullable;

import java.util.List;

public interface ZineUnbakedCuboidItemModelWrapper {

    default void zine$addTint(ItemTintSource tint) {
        throw new UnsupportedOperationException("Implemented via mixin.");
    }

    default void zine$addTints(List<ItemTintSource> tints) {
        throw new UnsupportedOperationException("Implemented via mixin.");
    }

    default void zine$setTints(List<ItemTintSource> tints) {
        throw new UnsupportedOperationException("Implemented via mixin.");
    }

    default void zine$setModel(Identifier model) {
        throw new UnsupportedOperationException("Implemented via mixin.");
    }

    default void zine$setTransformation(@Nullable Transformation transformation) {
        throw new UnsupportedOperationException("Implemented via mixin.");
    }

}
