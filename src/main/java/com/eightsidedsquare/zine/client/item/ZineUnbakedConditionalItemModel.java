package com.eightsidedsquare.zine.client.item;

import net.minecraft.client.renderer.item.ItemModel;
import net.minecraft.client.renderer.item.properties.conditional.ConditionalItemModelProperty;

public interface ZineUnbakedConditionalItemModel {

    default void zine$setProperty(ConditionalItemModelProperty property) {
        throw new UnsupportedOperationException("Implemented via mixin.");
    }

    default void zine$setTrueModel(ItemModel.Unbaked model) {
        throw new UnsupportedOperationException("Implemented via mixin.");
    }

    default void zine$setFalseModel(ItemModel.Unbaked model) {
        throw new UnsupportedOperationException("Implemented via mixin.");
    }

}
