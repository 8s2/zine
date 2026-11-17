package com.eightsidedsquare.zine.core;

import com.eightsidedsquare.zine.common.item.ondestroyedaction.OnDestroyedItemAction;
import com.eightsidedsquare.zine.common.item.tooltip.TooltipImage;
import com.eightsidedsquare.zine.common.item.useaction.UseItemAction;
import com.eightsidedsquare.zine.common.item.usecondition.ItemUseCondition;
import com.eightsidedsquare.zine.common.item.useonaction.UseOnItemAction;
import com.eightsidedsquare.zine.common.util.codec.SyncedCodec;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;

public final class ZineRegistries {
    public static final ResourceKey<Registry<SyncedCodec<? extends TooltipImage>>> TOOLTIP_IMAGE = ZineMod.REGISTRY.registryKey("tooltip_image");
    public static final ResourceKey<Registry<SyncedCodec<? extends UseItemAction>>> USE_ITEM_ACTION = ZineMod.REGISTRY.registryKey("use_item_action");
    public static final ResourceKey<Registry<SyncedCodec<? extends UseOnItemAction>>> USE_ON_ITEM_ACTION = ZineMod.REGISTRY.registryKey("use_on_item_action");
    public static final ResourceKey<Registry<SyncedCodec<? extends OnDestroyedItemAction>>> ON_DESTROYED_ITEM_ACTION = ZineMod.REGISTRY.registryKey("on_destroyed_item_action");
    public static final ResourceKey<Registry<SyncedCodec<? extends ItemUseCondition>>> ITEM_USE_CONDITION = ZineMod.REGISTRY.registryKey("item_use_condition");

    private ZineRegistries() {
    }
}
