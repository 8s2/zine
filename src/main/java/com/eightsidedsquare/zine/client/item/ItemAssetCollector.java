package com.eightsidedsquare.zine.client.item;

import net.minecraft.client.renderer.item.ClientItem;
import net.minecraft.client.renderer.item.ItemModel;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.ItemLike;

import java.util.function.BiConsumer;

@FunctionalInterface
public interface ItemAssetCollector extends BiConsumer<Identifier, ClientItem> {

    default void accept(Identifier id, ItemModel.Unbaked unbaked) {
        this.accept(id, new ClientItem(unbaked, ClientItem.Properties.DEFAULT));
    }

    default void accept(ItemLike item, ClientItem itemAsset) {
        this.accept(BuiltInRegistries.ITEM.getKey(item.asItem()), itemAsset);
    }

    default void accept(ItemLike item, ItemModel.Unbaked unbaked) {
        this.accept(BuiltInRegistries.ITEM.getKey(item.asItem()), unbaked);
    }

}