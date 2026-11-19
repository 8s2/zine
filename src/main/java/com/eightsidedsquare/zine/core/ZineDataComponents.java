package com.eightsidedsquare.zine.core;

import com.eightsidedsquare.zine.common.item.destroyedaction.ItemDestroyedAction;
import com.eightsidedsquare.zine.common.item.tooltip.TooltipImage;
import com.eightsidedsquare.zine.common.item.useaction.ItemUseAction;
import com.eightsidedsquare.zine.common.item.useduration.ItemUseDuration;
import com.eightsidedsquare.zine.common.item.useonaction.ItemUseOnAction;
import com.eightsidedsquare.zine.common.item.usetickaction.ItemUseTickAction;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.util.Unit;
import net.minecraft.world.item.ItemUseAnimation;

public final class ZineDataComponents {
    public static final DataComponentType<Integer> ITEM_NAME_COLOR = ZineMod.REGISTRY.dataComponent(
            "item_name_color",
            b -> b.persistent(ExtraCodecs.STRING_RGB_COLOR).networkSynchronized(ByteBufCodecs.VAR_INT)
    );
    public static final DataComponentType<TooltipImage> TOOLTIP_IMAGE = ZineMod.REGISTRY.dataComponent(
            "tooltip_image",
            b -> b.persistent(TooltipImage.CODEC).networkSynchronized(TooltipImage.STREAM_CODEC).cacheEncoding()
    );
    public static final DataComponentType<ItemUseAction> USE_ACTION = ZineMod.REGISTRY.dataComponent(
            "use_action",
            b -> b.persistent(ItemUseAction.CODEC).networkSynchronized(ItemUseAction.STREAM_CODEC).cacheEncoding()
    );
    public static final DataComponentType<ItemUseOnAction> USE_ON_ACTION = ZineMod.REGISTRY.dataComponent(
            "use_on_action",
            b -> b.persistent(ItemUseOnAction.CODEC).networkSynchronized(ItemUseOnAction.STREAM_CODEC).cacheEncoding()
    );
    public static final DataComponentType<ItemDestroyedAction> DESTROYED_ACTION = ZineMod.REGISTRY.dataComponent(
            "destroyed_action",
            b -> b.persistent(ItemDestroyedAction.CODEC).networkSynchronized(ItemDestroyedAction.STREAM_CODEC).cacheEncoding()
    );
    public static final DataComponentType<ItemUseTickAction> USE_TICK_ACTION = ZineMod.REGISTRY.dataComponent(
            "use_tick_action",
            b -> b.persistent(ItemUseTickAction.CODEC).networkSynchronized(ItemUseTickAction.STREAM_CODEC).cacheEncoding()
    );
    public static final DataComponentType<ItemUseDuration> USE_DURATION = ZineMod.REGISTRY.dataComponent(
            "use_duration",
            b -> b.persistent(ItemUseDuration.CODEC).networkSynchronized(ItemUseDuration.STREAM_CODEC).cacheEncoding()
    );
    public static final DataComponentType<ItemUseAnimation> USE_ANIMATION = ZineMod.REGISTRY.dataComponent(
            "use_animation",
            b -> b.persistent(ItemUseAnimation.CODEC).networkSynchronized(ItemUseAnimation.STREAM_CODEC)
    );
    public static final DataComponentType<Unit> USED_ON_RELEASE = ZineMod.REGISTRY.dataComponent(
            "used_on_release",
            b -> b.persistent(Unit.CODEC).networkSynchronized(Unit.STREAM_CODEC)
    );

    static void init() {
    }

    private ZineDataComponents() {
    }
}
