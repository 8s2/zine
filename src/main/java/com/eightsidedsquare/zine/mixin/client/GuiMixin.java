package com.eightsidedsquare.zine.mixin.client;

import com.eightsidedsquare.zine.core.ZineDataComponents;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.Gui;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(Gui.class)
public abstract class GuiMixin {

    @Shadow private ItemStack lastToolHighlight;

    @WrapOperation(method = "renderSelectedItemName", at = @At(value = "INVOKE", target = "Lnet/minecraft/network/chat/MutableComponent;withStyle(Lnet/minecraft/ChatFormatting;)Lnet/minecraft/network/chat/MutableComponent;", ordinal = 0))
    private MutableComponent zine$applyNameColor(MutableComponent text, ChatFormatting formatting, Operation<MutableComponent> original) {
        if(this.lastToolHighlight.has(ZineDataComponents.ITEM_NAME_COLOR)) {
            return text.withColor(this.lastToolHighlight.getOrDefault(ZineDataComponents.ITEM_NAME_COLOR, -1));
        }else {
            return original.call(text, formatting);
        }
    }

}
