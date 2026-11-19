package com.eightsidedsquare.zine.core;

import com.eightsidedsquare.zine.common.item.destroyedaction.ItemDestroyedAction;
import com.eightsidedsquare.zine.common.item.tooltip.TooltipImage;
import com.eightsidedsquare.zine.common.item.useaction.ItemUseAction;
import com.eightsidedsquare.zine.common.item.usecondition.ItemUseCondition;
import com.eightsidedsquare.zine.common.item.useduration.ItemUseDuration;
import com.eightsidedsquare.zine.common.item.useonaction.ItemUseOnAction;
import com.eightsidedsquare.zine.common.item.usetickaction.ItemUseTickAction;
import com.eightsidedsquare.zine.common.util.codec.SyncedCodec;
import net.fabricmc.fabric.api.event.registry.FabricRegistryBuilder;
import net.fabricmc.fabric.api.event.registry.RegistryAttribute;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;

public final class ZineBuiltinRegistries {
    public static final Registry<SyncedCodec<? extends TooltipImage>> TOOLTIP_IMAGE_TYPE = synced(ZineRegistries.TOOLTIP_IMAGE_TYPE);
    public static final Registry<SyncedCodec<? extends ItemUseAction>> ITEM_USE_ACTION_TYPE = synced(ZineRegistries.ITEM_USE_ACTION_TYPE);
    public static final Registry<SyncedCodec<? extends ItemUseOnAction>> ITEM_USE_ON_ACTION_TYPE = synced(ZineRegistries.ITEM_USE_ON_ACTION_TYPE);
    public static final Registry<SyncedCodec<? extends ItemDestroyedAction>> ITEM_DESTROYED_ACTION_TYPE = synced(ZineRegistries.ITEM_DESTROYED_ACTION_TYPE);
    public static final Registry<SyncedCodec<? extends ItemUseCondition>> ITEM_USE_CONDITION_TYPE = synced(ZineRegistries.ITEM_USE_CONDITION_TYPE);
    public static final Registry<SyncedCodec<? extends ItemUseTickAction>> ITEM_USE_TICK_ACTION_TYPE = synced(ZineRegistries.ITEM_USE_TICK_ACTION_TYPE);
    public static final Registry<SyncedCodec<? extends ItemUseDuration>> ITEM_USE_DURATION_TYPE = synced(ZineRegistries.ITEM_USE_DURATION_TYPE);

    static void init() {
    }

    private static <T> Registry<T> synced(ResourceKey<Registry<T>> registry) {
        return FabricRegistryBuilder.create(registry)
                .attribute(RegistryAttribute.SYNCED)
                .buildAndRegister();
    }

    private ZineBuiltinRegistries() {
    }
}
