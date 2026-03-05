package com.eightsidedsquare.zine.client.item;

import net.minecraft.client.renderer.item.ItemModel;
import net.minecraft.client.renderer.item.SelectItemModel;
import net.minecraft.client.renderer.item.properties.select.SelectItemModelProperty;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public interface ZineUnbakedSelectItemModel {

    default void zine$setFallback(@Nullable ItemModel.Unbaked fallback) {
        throw new UnsupportedOperationException("Implemented via mixin.");
    }

    default void zine$setSwitch(SelectItemModel.UnbakedSwitch<?, ?> unbakedSwitch) {
        throw new UnsupportedOperationException("Implemented via mixin.");
    }

    default <P extends SelectItemModelProperty<T>, T> void zine$addCase(SelectItemModelProperty.Type<P, T> type, List<T> values, ItemModel.Unbaked model) {
        throw new UnsupportedOperationException("Implemented via mixin.");
    }

    default <P extends SelectItemModelProperty<T>, T> void zine$addCases(SelectItemModelProperty.Type<P, T> type, List<SelectItemModel.SwitchCase<T>> cases) {
        throw new UnsupportedOperationException("Implemented via mixin.");
    }

}
