package com.eightsidedsquare.zine.core;

import com.eightsidedsquare.zine.common.item.ondestroyedaction.OnDestroyedItemAction;
import com.eightsidedsquare.zine.common.item.tooltip.TooltipImage;
import com.eightsidedsquare.zine.common.item.useaction.UseItemAction;
import com.eightsidedsquare.zine.common.item.useonaction.UseOnItemAction;
import com.eightsidedsquare.zine.common.util.codec.SyncedCodec;
import net.fabricmc.fabric.api.event.registry.FabricRegistryBuilder;
import net.fabricmc.fabric.api.event.registry.RegistryAttribute;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;

public final class ZineBuiltinRegistries {
    public static final Registry<SyncedCodec<? extends TooltipImage>> TOOLTIP_IMAGE = synced(ZineRegistries.TOOLTIP_IMAGE);
    public static final Registry<SyncedCodec<? extends UseItemAction>> USE_ITEM_ACTION = synced(ZineRegistries.USE_ITEM_ACTION);
    public static final Registry<SyncedCodec<? extends UseOnItemAction>> USE_ON_ITEM_ACTION = synced(ZineRegistries.USE_ON_ITEM_ACTION);
    public static final Registry<SyncedCodec<? extends OnDestroyedItemAction>> ON_DESTROYED_ITEM_ACTION = synced(ZineRegistries.ON_DESTROYED_ITEM_ACTION);

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
