package com.eightsidedsquare.zine.common.item.tooltip;

import com.eightsidedsquare.zine.common.util.codec.SyncedCodec;
import com.google.common.collect.ImmutableList;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.TooltipDisplay;
import org.jspecify.annotations.Nullable;

import java.util.List;
import java.util.Objects;

public record CompositeTooltip(List<TooltipComponent> tooltips) implements TooltipComponent {
    public record Image(List<TooltipImage> contents) implements TooltipImage {
        public static final SyncedCodec<Image> TYPE = new SyncedCodec<>(
                ExtraCodecs.nonEmptyList(TooltipImage.CODEC.listOf()).fieldOf("contents"),
                TooltipImage.STREAM_CODEC.apply(ByteBufCodecs.list())
        ).map(Image::new, Image::contents);

        @Override
        public @Nullable TooltipComponent getTooltip(ItemStack itemStack, TooltipDisplay display) {
            List<TooltipComponent> tooltips = this.contents.stream()
                    .map(image -> image.getTooltip(itemStack, display))
                    .filter(Objects::nonNull)
                    .toList();
            return tooltips.isEmpty() ? null : new CompositeTooltip(tooltips);
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

        public static Builder builder() {
            return new Builder();
        }

        public static class Builder implements TooltipImage.Builder {
            private final ImmutableList.Builder<TooltipImage> contents = ImmutableList.builder();

            public Builder add(TooltipImage image) {
                this.contents.add(image);
                return this;
            }

            public Builder add(TooltipImage.Builder builder) {
                return this.add(builder.build());
            }

            @Override
            public Image build() {
                return new Image(this.contents.build());
            }
        }
    }
}
