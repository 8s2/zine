package com.eightsidedsquare.zine.client.item;

import net.minecraft.client.render.item.model.SelectItemModel;
import net.minecraft.client.render.item.property.select.SelectProperty;

import java.util.List;

public interface ZineSelectItemModelUnbakedSwitch<P extends SelectProperty<T>, T> {

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
