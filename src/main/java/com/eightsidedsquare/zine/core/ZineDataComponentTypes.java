package com.eightsidedsquare.zine.core;

import net.minecraft.component.ComponentType;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.util.dynamic.Codecs;

public interface ZineDataComponentTypes {

    ComponentType<Integer> ITEM_NAME_COLOR = ZineMod.REGISTRY.dataComponent("item_name_color", builder ->
            builder.codec(Codecs.HEX_RGB).packetCodec(PacketCodecs.VAR_INT)
    );

    static void init() {

    }

}
