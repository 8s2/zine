package com.eightsidedsquare.zinetest.core;

import net.minecraft.item.Item;
import net.minecraft.item.SmithingTemplateItem;

public interface TestmodItems {

    Item TOURMALINE = TestmodInit.REGISTRY.item("tourmaline", new Item.Settings().trimMaterial(TestmodInit.TOURMALINE_TRIM_MATERIAL).zine$nameColor(0x22ff66));
    Item CHECKERED_ARMOR_TRIM_SMITHING_TEMPLATE = TestmodInit.REGISTRY.item("checkered_armor_trim_smithing_template", new Item.Settings(), SmithingTemplateItem::of);

    Item TOURMALINE_BLOCK = TestmodBlocks.TOURMALINE_BLOCK.asItem();
    Item GOO = TestmodBlocks.GOO.asItem();
    Item WOOD = TestmodBlocks.WOOD.asItem();
    Item RAINBOW = TestmodBlocks.RAINBOW.asItem();
    Item BIG_DIAMOND = TestmodBlocks.BIG_DIAMOND.asItem();

    static void init() {

    }

}
