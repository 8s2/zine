package com.eightsidedsquare.zine.common.item.tooltip;

import com.eightsidedsquare.zine.common.util.codec.SyncedCodec;
import com.google.common.collect.ImmutableList;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.TooltipDisplay;
import org.jspecify.annotations.Nullable;

import java.util.List;

public record CompositeTooltip(List<TooltipComponent> tooltips) implements TooltipComponent {
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private final ImmutableList.Builder<TooltipComponent> tooltips = ImmutableList.builder();

        public Builder with(Component text) {
            this.tooltips.add(new TextTooltip(text));
            return this;
        }

        public Builder with(TooltipComponent tooltip) {
            this.tooltips.add(tooltip);
            return this;
        }

        public CompositeTooltip build() {
            return new CompositeTooltip(this.tooltips.build());
        }
    }

    public record Image(List<TooltipImage> contents) implements TooltipImage {
        public static final SyncedCodec<Image> TYPE = new SyncedCodec<>(
                ExtraCodecs.nonEmptyList(TooltipImage.CODEC.listOf()).fieldOf("contents"),
                TooltipImage.STREAM_CODEC.apply(ByteBufCodecs.list())
        ).map(Image::new, Image::contents);

        @Override
        public @Nullable TooltipComponent getTooltipImage(ItemStack itemStack, TooltipDisplay display) {
            CompositeTooltip.Builder builder = CompositeTooltip.builder();
            for (TooltipImage image : this.contents) {
                if (image.canShow(display)) {
                    TooltipComponent tooltip = image.getTooltipImage(itemStack, display);
                    if (tooltip != null) {
                        builder.with(tooltip);
                    }
                }
            }
            CompositeTooltip tooltip = builder.build();
            return tooltip.tooltips().isEmpty() ? null : builder.build();
        }

        @Override
        public boolean canShow(TooltipDisplay display) {
            for (TooltipImage image : this.contents) {
                if (image.canShow(display)) {
                    return true;
                }
            }
            return false;
        }

        @Override
        public SyncedCodec<? extends TooltipImage> type() {
            return TYPE;
        }
    }
}
