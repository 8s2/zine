package com.eightsidedsquare.zine.mixin.client.gui;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.gui.tooltip.TooltipComponent;
import net.minecraft.client.search.SearchManager;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipData;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Mixin(SearchManager.class)
public abstract class SearchManagerMixin {

    @ModifyExpressionValue(method = "method_60365", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;getTooltip(Lnet/minecraft/item/Item$TooltipContext;Lnet/minecraft/entity/player/PlayerEntity;Lnet/minecraft/item/tooltip/TooltipType;)Ljava/util/List;"))
    private static List<Text> zine$addSearchableTexts(List<Text> texts, @Local(argsOnly = true) ItemStack stack) {
        Optional<TooltipData> data = stack.getTooltipData();
        if(data.isPresent()) {
            TooltipComponent component = TooltipComponent.of(data.get());
            texts = new ArrayList<>(texts);
            component.zine$appendSearchableText(texts);
        }
        return texts;
    }

}
