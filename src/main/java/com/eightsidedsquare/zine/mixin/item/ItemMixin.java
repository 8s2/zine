package com.eightsidedsquare.zine.mixin.item;

import com.eightsidedsquare.zine.common.item.ZineItem;
import com.eightsidedsquare.zine.common.item.tooltip.CompositeTooltip;
import com.eightsidedsquare.zine.common.item.tooltip.TooltipImage;
import com.eightsidedsquare.zine.core.ZineDataComponents;
import com.google.common.collect.ImmutableList;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.TooltipDisplay;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import java.util.Optional;

@Mixin(Item.class)
public abstract class ItemMixin implements ZineItem {
    @SuppressWarnings("OptionalUsedAsFieldOrParameterType")
    @ModifyReturnValue(method = "getTooltipImage", at = @At("TAIL"))
    private Optional<TooltipComponent> zine$getTooltipImage(Optional<TooltipComponent> original, ItemStack itemStack) {
        TooltipImage tooltipImage = itemStack.get(ZineDataComponents.TOOLTIP_IMAGE);
        if (tooltipImage != null) {
            TooltipDisplay display = itemStack.getOrDefault(DataComponents.TOOLTIP_DISPLAY, TooltipDisplay.DEFAULT);
            if (display.shows(ZineDataComponents.TOOLTIP_IMAGE) && tooltipImage.canShow(display)) {
                TooltipComponent tooltip = tooltipImage.getTooltip(itemStack, display);
                if (tooltip != null) {
                    return original
                            .<TooltipComponent>map(tooltipComponent -> new CompositeTooltip(
                                            ImmutableList.<TooltipComponent>builder()
                                                    .add(tooltipComponent)
                                                    .add(tooltip)
                                                    .build()
                                    )
                            ).or(() -> Optional.of(tooltip));
                }
            }
        }
        return original;
    }
}
