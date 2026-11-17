package com.eightsidedsquare.zine.core;

import com.eightsidedsquare.zine.common.item.ondestroyedaction.OnDestroyedItemAction;
import com.eightsidedsquare.zine.common.item.tooltip.TooltipImage;
import com.eightsidedsquare.zine.common.item.useaction.UseItemAction;
import com.eightsidedsquare.zine.common.item.useonaction.UseOnItemAction;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.util.ExtraCodecs;

public final class ZineDataComponents {
    public static final DataComponentType<Integer> ITEM_NAME_COLOR = ZineMod.REGISTRY.dataComponent(
            "item_name_color",
            b -> b.persistent(ExtraCodecs.STRING_RGB_COLOR).networkSynchronized(ByteBufCodecs.VAR_INT)
    );
    public static final DataComponentType<TooltipImage> TOOLTIP_IMAGE = ZineMod.REGISTRY.dataComponent(
            "tooltip_image",
            b -> b.persistent(TooltipImage.CODEC).networkSynchronized(TooltipImage.STREAM_CODEC).cacheEncoding()
    );
    public static final DataComponentType<UseItemAction> USE_ITEM_ACTION = ZineMod.REGISTRY.dataComponent(
            "use_item_action",
            b -> b.persistent(UseItemAction.CODEC).networkSynchronized(UseItemAction.STREAM_CODEC).cacheEncoding()
    );
    public static final DataComponentType<UseOnItemAction> USE_ON_ITEM_ACTION = ZineMod.REGISTRY.dataComponent(
            "use_on_item_action",
            b -> b.persistent(UseOnItemAction.CODEC).networkSynchronized(UseOnItemAction.STREAM_CODEC).cacheEncoding()
    );
    public static final DataComponentType<OnDestroyedItemAction> ON_DESTROYED_ITEM_ACTION = ZineMod.REGISTRY.dataComponent(
            "on_destroyed_item_action",
            b -> b.persistent(OnDestroyedItemAction.CODEC).networkSynchronized(OnDestroyedItemAction.STREAM_CODEC).cacheEncoding()
    );

    static void init() {
    }

    private ZineDataComponents() {
    }
}
