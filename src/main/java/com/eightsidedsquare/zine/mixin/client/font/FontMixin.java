package com.eightsidedsquare.zine.mixin.client.font;

import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.gui.Font;
import net.minecraft.network.chat.Style;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Font.class)
public abstract class FontMixin {
    @Inject(method = "lambda$prepare8xTextOutline$0", at = @At("HEAD"), cancellable = true)
    private void zine$overrideOutline(CallbackInfoReturnable<Boolean> cir, @Local(argsOnly = true) Style style) {
        if(style.zine$hasOutline()) {
            cir.setReturnValue(false);
        }
    }
}
