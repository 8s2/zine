package com.eightsidedsquare.zine.core;

import com.eightsidedsquare.zine.common.item.tooltip.TooltipImage;
import com.eightsidedsquare.zine.common.util.codec.SyncedCodec;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;

public interface ZineRegistries {
    ResourceKey<Registry<SyncedCodec<? extends TooltipImage>>> TOOLTIP_IMAGE = ZineMod.REGISTRY.registryKey("tooltip_image");
}
