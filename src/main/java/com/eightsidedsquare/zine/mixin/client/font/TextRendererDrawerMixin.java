package com.eightsidedsquare.zine.mixin.client.font;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.client.gui.Font;
import net.minecraft.network.chat.Style;
import net.minecraft.util.ARGB;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Font.PreparedTextBuilder.class)
public abstract class TextRendererDrawerMixin {

    @Shadow(aliases = "this$0") @Final
    private Font font;
    @Shadow
    private float x;
    @Shadow
    private float y;
    @Shadow @Final @Mutable
    private int color;

    @SuppressWarnings("ConstantValue")
    @Inject(method = "accept(ILnet/minecraft/network/chat/Style;I)Z", at = @At("HEAD"))
    private void zine$acceptOutline(int index, Style style, int codePoint, CallbackInfoReturnable<Boolean> cir) {
        if(style.zine$hasOutline()) {
            int outlineColor = style.zine$getOutlineColor();
            float originalX = this.x;
            float originalY = this.y;
            int originalColor = this.color;
            float alpha = ARGB.alphaFloat(originalColor) * ARGB.alphaFloat(outlineColor);
            this.color = ARGB.color(alpha, originalColor);
            for (int x = -1; x <= 1; x++) {
                for (int y = -1; y <= 1; y++) {
                    if (x != 0 || y != 0) {
                        float[] advance = new float[]{originalX};
                        ((FontAccessor) this.font).zine$invokeAcceptOutline(
                                (Font.PreparedTextBuilder) (Object) this,
                                advance,
                                x,
                                originalY,
                                y,
                                outlineColor,
                                index,
                                style,
                                codePoint
                        );
                    }
                }
            }
            this.x = originalX;
            this.y = originalY;
            this.color = originalColor;
        }
    }

    @ModifyReturnValue(method = "getShadowColor", at = @At("RETURN"))
    private int zine$getShadowColor(int original, Style style, int textColor) {
        if(original != 0 && style.zine$hasOutline()) {
            return 0;
        }
        return original;
    }
}
