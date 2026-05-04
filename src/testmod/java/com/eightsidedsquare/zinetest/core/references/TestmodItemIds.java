package com.eightsidedsquare.zinetest.core.references;

import com.eightsidedsquare.zinetest.core.Testmod;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;

public interface TestmodItemIds {
    ResourceKey<Item> TOURMALINE = create("tourmaline");
    ResourceKey<Item> CHECKERED_ARMOR_TRIM_SMITHING_TEMPLATE = create("checkered_armor_trim_smithing_template");

    private static ResourceKey<Item> create(String name) {
        return Testmod.REGISTRY.key(Registries.ITEM, name);
    }
}
