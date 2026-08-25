package com.eightsidedsquare.zinetest.core;

import com.eightsidedsquare.zinetest.core.references.TestmodBlockItemIds;
import com.eightsidedsquare.zinetest.core.references.TestmodItemIds;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SmithingTemplateItem;

public interface TestmodItems {
    Item TOURMALINE = Testmod.REGISTRY.item(TestmodItemIds.TOURMALINE, new Item.Properties().trimMaterial(Testmod.TOURMALINE_TRIM_MATERIAL).zine$nameColor(0x22ff66));
    Item CHECKERED_ARMOR_TRIM_SMITHING_TEMPLATE = Testmod.REGISTRY.item(TestmodItemIds.CHECKERED_ARMOR_TRIM_SMITHING_TEMPLATE, SmithingTemplateItem::createArmorTrimTemplate, new Item.Properties());
    Item TOURMALINE_BLOCK = Testmod.REGISTRY.blockItem(TestmodBlockItemIds.TOURMALINE_BLOCK, TestmodBlocks.TOURMALINE_BLOCK);
    Item WOOD = Testmod.REGISTRY.blockItem(TestmodBlockItemIds.WOOD, TestmodBlocks.WOOD);
    Item RAINBOW = Testmod.REGISTRY.blockItem(TestmodBlockItemIds.RAINBOW, TestmodBlocks.RAINBOW);
    Item BIG_DIAMOND = Testmod.REGISTRY.blockItem(TestmodBlockItemIds.BIG_DIAMOND, TestmodBlocks.BIG_DIAMOND);

    static void init() {

    }

}
