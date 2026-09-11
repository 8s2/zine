package com.eightsidedsquare.zine.common.registry.holder;

import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;

public record ItemHolder(Item item, ResourceKey<Item> id) implements IdSupplier<ResourceKey<Item>>, ItemLike {
    public ItemStack getDefaultInstance() {
        return this.item.getDefaultInstance();
    }

    @Override
    public Item asItem() {
        return this.item;
    }

    @SuppressWarnings("deprecation")
    public Holder<Item> holder() {
        return this.item.builtInRegistryHolder();
    }
}
