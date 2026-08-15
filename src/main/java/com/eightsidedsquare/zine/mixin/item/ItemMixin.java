package com.eightsidedsquare.zine.mixin.item;

import com.eightsidedsquare.zine.client.trim.ArmorTrimRegistryImpl;
import com.eightsidedsquare.zine.common.item.ZineItem;
import com.eightsidedsquare.zine.common.item.tooltip.CompositeTooltip;
import com.eightsidedsquare.zine.common.item.tooltip.TooltipImage;
import com.eightsidedsquare.zine.core.ZineDataComponents;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.core.component.DataComponents;
import net.minecraft.resources.Identifier;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.item.equipment.ArmorType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Optional;

@Mixin(Item.class)
public abstract class ItemMixin implements ZineItem {

    @Shadow
    public abstract DataComponentMap components();

    @Override
    public boolean zine$modelEquals(Identifier modelId) {
        return modelId.equals(this.components().get(DataComponents.ITEM_MODEL));
    }

    @Inject(method = "<init>", at = @At("TAIL"))
    private void zine$init(Item.Properties properties, CallbackInfo ci) {
        ArmorType armorType = properties.zine$getArmorType();
        if(armorType != null) {
            ArmorTrimRegistryImpl.addArmorItem((Item) (Object) this, armorType);
        }
    }

    @SuppressWarnings("OptionalUsedAsFieldOrParameterType")
    @ModifyReturnValue(method = "getTooltipImage", at = @At("TAIL"))
    private Optional<TooltipComponent> zine$getTooltipImage(Optional<TooltipComponent> original, ItemStack itemStack) {
        TooltipImage tooltipImage = itemStack.get(ZineDataComponents.TOOLTIP_IMAGE);
        if (tooltipImage != null) {
            TooltipDisplay display = itemStack.getOrDefault(DataComponents.TOOLTIP_DISPLAY, TooltipDisplay.DEFAULT);
            if (display.shows(ZineDataComponents.TOOLTIP_IMAGE) && tooltipImage.canShow(display)) {
                TooltipComponent tooltip = tooltipImage.getTooltipImage(itemStack, display);
                if (tooltip != null) {
                    return original
                            .map(tooltipComponent -> (TooltipComponent) CompositeTooltip.builder()
                                    .with(tooltipComponent)
                                    .with(tooltip)
                                    .build())
                            .or(() -> Optional.of(tooltip));
                }
            }
        }
        return original;
    }
}
