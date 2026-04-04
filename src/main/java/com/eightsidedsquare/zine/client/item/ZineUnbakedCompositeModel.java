package com.eightsidedsquare.zine.client.item;

import net.minecraft.client.renderer.item.ItemModel;

import java.util.List;

public interface ZineUnbakedCompositeModel {

    default void zine$addModel(ItemModel.Unbaked model) {
        throw new UnsupportedOperationException("Implemented via mixin.");
    }

    default void zine$addModels(List<ItemModel.Unbaked> models) {
        throw new UnsupportedOperationException("Implemented via mixin.");
    }

    default void zine$setModels(List<ItemModel.Unbaked> models) {
        throw new UnsupportedOperationException("Implemented via mixin.");
    }

}
