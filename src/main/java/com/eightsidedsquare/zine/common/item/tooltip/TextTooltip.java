package com.eightsidedsquare.zine.common.item.tooltip;

import com.eightsidedsquare.zine.common.util.codec.SyncedCodec;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentSerialization;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.TooltipDisplay;

public record TextTooltip(Component text) implements TooltipComponent {
    public record Image(Component text) implements TooltipImage {
        public static final SyncedCodec<Image> TYPE = SyncedCodec.create(
                ComponentSerialization.CODEC.fieldOf("text"),
                ComponentSerialization.STREAM_CODEC,
                Image::text,
                Image::new
        );

        @Override
        public TooltipComponent getTooltip(ItemStack itemStack, TooltipDisplay display) {
            return new TextTooltip(this.text);
        }

        @Override
        public SyncedCodec<? extends TooltipImage> type() {
            return TYPE;
        }

        public static TooltipImage.Builder builder(Component text) {
            return () -> new Image(text);
        }
    }
}
