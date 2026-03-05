package com.eightsidedsquare.zine.client.item;

import net.minecraft.client.renderer.item.ItemModel;
import net.minecraft.client.renderer.item.RangeSelectItemModel;
import net.minecraft.client.renderer.item.properties.numeric.RangeSelectItemModelProperty;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public interface ZineUnbakedRangeSelectItemModel {

    default void zine$setProperty(RangeSelectItemModelProperty property) {
        throw new UnsupportedOperationException("Implemented via mixin.");
    }

    default void zine$setScale(float scale) {
        throw new UnsupportedOperationException("Implemented via mixin.");
    }

    default void zine$setFallback(@Nullable ItemModel.Unbaked fallback) {
        throw new UnsupportedOperationException("Implemented via mixin.");
    }

    default void zine$addEntry(RangeSelectItemModel.Entry entry) {
        throw new UnsupportedOperationException("Implemented via mixin.");
    }

    default void zine$addEntries(List<RangeSelectItemModel.Entry> entries) {
        throw new UnsupportedOperationException("Implemented via mixin.");
    }

}
