package com.eightsidedsquare.zine.mixin.item;

import com.eightsidedsquare.zine.core.ZineDataComponents;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.ChatFormatting;
import net.minecraft.core.component.DataComponentHolder;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ItemStack.class)
public abstract class ItemStackMixin implements DataComponentHolder {

    @WrapOperation(method = "getDisplayName", at = @At(value = "INVOKE", target = "Lnet/minecraft/network/chat/MutableComponent;withStyle(Lnet/minecraft/ChatFormatting;)Lnet/minecraft/network/chat/MutableComponent;", ordinal = 1))
    private MutableComponent zine$applyTextColorToHoverable(MutableComponent text, ChatFormatting formatting, Operation<MutableComponent> original) {
        return this.zine$applyTextColor(text, formatting, original);
    }

    @WrapOperation(method = "getStyledHoverName", at = @At(value = "INVOKE", target = "Lnet/minecraft/network/chat/MutableComponent;withStyle(Lnet/minecraft/ChatFormatting;)Lnet/minecraft/network/chat/MutableComponent;", ordinal = 0))
    private MutableComponent zine$applyTextColorToName(MutableComponent text, ChatFormatting formatting, Operation<MutableComponent> original) {
        return this.zine$applyTextColor(text, formatting, original);
    }

    @Unique
    private MutableComponent zine$applyTextColor(MutableComponent text, ChatFormatting formatting, Operation<MutableComponent> original) {
        if(this.has(ZineDataComponents.ITEM_NAME_COLOR)) {
            return text.withColor(this.getOrDefault(ZineDataComponents.ITEM_NAME_COLOR, -1));
        }else {
            return original.call(text, formatting);
        }
    }

}
