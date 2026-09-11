package com.eightsidedsquare.zinetest.core;

import com.eightsidedsquare.zine.common.registry.holder.ItemHolder;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SmithingTemplateItem;

public interface TestmodItems {
    ItemHolder TOURMALINE = Testmod.REGISTRY.item("tourmaline", new Item.Properties().trimMaterial(Testmod.TOURMALINE_TRIM_MATERIAL).zine$nameColor(0x22ff66));
    ItemHolder CHECKERED_ARMOR_TRIM_SMITHING_TEMPLATE = Testmod.REGISTRY.item("checkered_armor_trim_smithing_template", SmithingTemplateItem::createArmorTrimTemplate, new Item.Properties());

    static void init() {
    }
}
