package com.eightsidedsquare.zine.core;

import com.eightsidedsquare.zine.common.item.destroyedaction.ItemDestroyedAction;
import com.eightsidedsquare.zine.common.item.tooltip.TooltipImage;
import com.eightsidedsquare.zine.common.item.useaction.ItemUseAction;
import com.eightsidedsquare.zine.common.item.usecondition.ItemUseCondition;
import com.eightsidedsquare.zine.common.item.useduration.ItemUseDuration;
import com.eightsidedsquare.zine.common.item.useonaction.ItemUseOnAction;
import com.eightsidedsquare.zine.common.item.usetickaction.ItemUseTickAction;
import com.eightsidedsquare.zine.common.util.codec.SyncedCodec;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;

public final class ZineRegistries {
    public static final ResourceKey<Registry<SyncedCodec<? extends TooltipImage>>> TOOLTIP_IMAGE_TYPE = ZineMod.REGISTRY.registryKey("tooltip_image_type");
    public static final ResourceKey<Registry<SyncedCodec<? extends ItemUseAction>>> ITEM_USE_ACTION_TYPE = ZineMod.REGISTRY.registryKey("item_use_action_type");
    public static final ResourceKey<Registry<SyncedCodec<? extends ItemUseOnAction>>> ITEM_USE_ON_ACTION_TYPE = ZineMod.REGISTRY.registryKey("item_use_on_action_type");
    public static final ResourceKey<Registry<SyncedCodec<? extends ItemDestroyedAction>>> ITEM_DESTROYED_ACTION_TYPE = ZineMod.REGISTRY.registryKey("item_destroyed_action_type");
    public static final ResourceKey<Registry<SyncedCodec<? extends ItemUseCondition>>> ITEM_USE_CONDITION_TYPE = ZineMod.REGISTRY.registryKey("item_use_condition_type");
    public static final ResourceKey<Registry<SyncedCodec<? extends ItemUseTickAction>>> ITEM_USE_TICK_ACTION_TYPE = ZineMod.REGISTRY.registryKey("item_use_tick_action_type");
    public static final ResourceKey<Registry<SyncedCodec<? extends ItemUseDuration>>> ITEM_USE_DURATION_TYPE = ZineMod.REGISTRY.registryKey("item_use_duration_type");

    private ZineRegistries() {
    }
}
