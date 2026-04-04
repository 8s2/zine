package com.eightsidedsquare.zine.mixin.client.gui;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent;
import net.minecraft.client.multiplayer.SessionSearchTrees;
import net.minecraft.network.chat.Component;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Mixin(SessionSearchTrees.class)
public abstract class SessionSearchTreesMixin {

    @ModifyExpressionValue(method = "lambda$getTooltipLines$0", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;getTooltipLines(Lnet/minecraft/world/item/Item$TooltipContext;Lnet/minecraft/world/entity/player/Player;Lnet/minecraft/world/item/TooltipFlag;)Ljava/util/List;"))
    private static List<Component> zine$addSearchableTexts(List<Component> texts, @Local(argsOnly = true) ItemStack stack) {
        Optional<TooltipComponent> data = stack.getTooltipImage();
        if(data.isPresent()) {
            ClientTooltipComponent component = ClientTooltipComponent.create(data.get());
            texts = new ArrayList<>(texts);
            component.zine$appendSearchableText(texts);
        }
        return texts;
    }

}
