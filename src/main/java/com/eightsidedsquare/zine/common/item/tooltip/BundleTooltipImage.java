package com.eightsidedsquare.zine.common.item.tooltip;

import com.eightsidedsquare.zine.common.util.codec.SyncedCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.inventory.tooltip.BundleTooltip;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.BundleContents;
import net.minecraft.world.item.component.TooltipDisplay;
import org.jspecify.annotations.Nullable;

import java.util.Optional;

public record BundleTooltipImage(Optional<BundleContents> contentsOverride) implements TooltipImage {
    public static final SyncedCodec<BundleTooltipImage> TYPE = new SyncedCodec<>(
            RecordCodecBuilder.mapCodec(i -> i.group(
                    BundleContents.CODEC.optionalFieldOf("contents_override").forGetter(BundleTooltipImage::contentsOverride)
            ).apply(i, BundleTooltipImage::new)),
            StreamCodec.composite(
                    BundleContents.STREAM_CODEC.apply(ByteBufCodecs::optional),
                    BundleTooltipImage::contentsOverride,
                    BundleTooltipImage::new
            )
    );

    @Override
    public TooltipComponent getTooltip(ItemStack itemStack, TooltipDisplay display) {
        return new BundleTooltip(
                this.contentsOverride.orElseGet(
                        () -> itemStack.getOrDefault(DataComponents.BUNDLE_CONTENTS, BundleContents.EMPTY)
                )
        );
    }

    @Override
    public boolean canShow(TooltipDisplay display) {
        return display.shows(DataComponents.BUNDLE_CONTENTS);
    }

    @Override
    public SyncedCodec<? extends TooltipImage> type() {
        return TYPE;
    }

    public static TooltipImage.Builder builder() {
        return new Builder();
    }

    public static class Builder implements TooltipImage.Builder {
        @Nullable
        private BundleContents contentsOverride;

        public Builder contentsOverride(BundleContents contentsOverride) {
            this.contentsOverride = contentsOverride;
            return this;
        }

        @Override
        public TooltipImage build() {
            return new BundleTooltipImage(Optional.ofNullable(this.contentsOverride));
        }
    }
}
