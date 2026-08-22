package com.eightsidedsquare.zine.common.item.tooltip;

import com.eightsidedsquare.zine.common.util.codec.SyncedCodec;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.inventory.tooltip.BundleTooltip;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.BundleContents;
import net.minecraft.world.item.component.TooltipDisplay;
import org.jspecify.annotations.Nullable;

public final class BundleTooltipImage implements TooltipImage {
    public static final BundleTooltipImage INSTANCE = new BundleTooltipImage();
    public static final SyncedCodec<BundleTooltipImage> TYPE = new SyncedCodec<>(
            MapCodec.unit(INSTANCE),
            StreamCodec.unit(INSTANCE)
    );

    @Override
    public @Nullable TooltipComponent getTooltip(ItemStack itemStack, TooltipDisplay display) {
        BundleContents bundleContents = itemStack.get(DataComponents.BUNDLE_CONTENTS);
        return bundleContents == null ? null : new BundleTooltip(bundleContents);
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
        return () -> INSTANCE;
    }

    private BundleTooltipImage() {
    }
}
