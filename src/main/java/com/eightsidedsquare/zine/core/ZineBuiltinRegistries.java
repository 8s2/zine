package com.eightsidedsquare.zine.core;

import com.eightsidedsquare.zine.common.item.tooltip.TooltipImage;
import com.eightsidedsquare.zine.common.util.codec.SyncedCodec;
import net.fabricmc.fabric.api.event.registry.FabricRegistryBuilder;
import net.fabricmc.fabric.api.event.registry.RegistryAttribute;
import net.minecraft.core.Registry;

public final class ZineBuiltinRegistries {
    public static final Registry<SyncedCodec<? extends TooltipImage>> TOOLTIP_IMAGE = FabricRegistryBuilder.create(ZineRegistries.TOOLTIP_IMAGE)
            .attribute(RegistryAttribute.SYNCED)
            .buildAndRegister();

    static void init() {
    }

    private ZineBuiltinRegistries() {
    }
}
