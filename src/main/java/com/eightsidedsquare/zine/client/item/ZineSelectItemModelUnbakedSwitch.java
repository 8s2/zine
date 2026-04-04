package com.eightsidedsquare.zine.client.item;

import net.minecraft.client.renderer.item.SelectItemModel;
import net.minecraft.client.renderer.item.properties.select.SelectItemModelProperty;

import java.util.List;

public interface ZineSelectItemModelUnbakedSwitch<P extends SelectItemModelProperty<T>, T> {

    default void zine$setProperty(P property) {
        throw new UnsupportedOperationException("Implemented via mixin.");
    }

    default void zine$setCases(List<SelectItemModel.SwitchCase<T>> cases) {
        throw new UnsupportedOperationException("Implemented via mixin.");
    }

    default void zine$addCases(List<SelectItemModel.SwitchCase<T>> cases) {
        throw new UnsupportedOperationException("Implemented via mixin.");
    }

    default void zine$addCase(SelectItemModel.SwitchCase<T> switchCase) {
        throw new UnsupportedOperationException("Implemented via mixin.");
    }

}
