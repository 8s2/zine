package com.eightsidedsquare.zine.common.item.tooltip;

import com.eightsidedsquare.zine.common.util.codec.SyncedCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentSerialization;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.TooltipDisplay;

public record TextTooltip(Component text) implements TooltipComponent {
    public record Image(Component text) implements TooltipImage {
        public static final SyncedCodec<Image> TYPE = new SyncedCodec<>(
                RecordCodecBuilder.mapCodec(i -> i.group(
                        ComponentSerialization.CODEC.fieldOf("text").forGetter(Image::text)
                ).apply(i, Image::new)),
                ComponentSerialization.STREAM_CODEC.map(Image::new, Image::text)
        );

        @Override
        public TooltipComponent getTooltipImage(ItemStack itemStack, TooltipDisplay display) {
            return new TextTooltip(this.text);
        }

        @Override
        public SyncedCodec<? extends TooltipImage> type() {
            return TYPE;
        }
    }
}
