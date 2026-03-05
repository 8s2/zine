package com.eightsidedsquare.zine.core;

import net.minecraft.core.component.DataComponentType;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.util.ExtraCodecs;

public interface ZineDataComponents {

    DataComponentType<Integer> ITEM_NAME_COLOR = ZineMod.REGISTRY.dataComponent("item_name_color", b ->
            b.persistent(ExtraCodecs.STRING_RGB_COLOR).networkSynchronized(ByteBufCodecs.VAR_INT)
    );

    static void init() {

    }

}
