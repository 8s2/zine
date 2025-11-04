package com.eightsidedsquare.zine.mixin.client.font;

import com.eightsidedsquare.zine.core.ZineCustomStyleAttributes;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.text.Style;
import net.minecraft.util.math.ColorHelper;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(TextRenderer.Drawer.class)
public abstract class TextRendererDrawerMixin {

    @Shadow(aliases = "field_24240") @Final
    TextRenderer textRenderer;
    @Shadow
    float x;
    @Shadow
    float y;
    @Shadow @Final @Mutable
    private int color;

    @SuppressWarnings("ConstantValue")
    @Inject(method = "accept(ILnet/minecraft/text/Style;I)Z", at = @At("HEAD"))
    private void zine$acceptOutline(int index, Style style, int codePoint, CallbackInfoReturnable<Boolean> cir) {
        Integer outlineColor = style.zine$getCustomAttribute(ZineCustomStyleAttributes.OUTLINE);
        if(outlineColor != null) {
            float originalX = this.x;
            float originalY = this.y;
            int originalColor = this.color;
            float alpha = ColorHelper.getAlphaFloat(originalColor) * ColorHelper.getAlphaFloat(outlineColor);
            this.color = ColorHelper.withAlpha(alpha, originalColor);
            for (int x = -1; x <= 1; x++) {
                for (int y = -1; y <= 1; y++) {
                    if (x != 0 || y != 0) {
                        float[] advance = new float[]{originalX};
                        ((TextRendererAccessor) this.textRenderer).zine$invokeAcceptOutline(
                                (TextRenderer.Drawer) (Object) this,
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
        if(original != 0 && style.zine$containsCustomAttribute(ZineCustomStyleAttributes.OUTLINE)) {
            return 0;
        }
        return original;
    }
}
