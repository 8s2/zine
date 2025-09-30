package com.eightsidedsquare.zine.mixin.client.font;

import com.eightsidedsquare.zine.core.ZineCustomStyleAttributes;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.text.OrderedText;
import net.minecraft.text.Style;
import net.minecraft.util.Util;
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(TextRenderer.class)
public abstract class TextRendererMixin {
    @Unique
    private static final ThreadLocal<Boolean> CANCEL_OUTLINE = Util.make(() -> {
        ThreadLocal<Boolean> threadLocal = new ThreadLocal<>();
        threadLocal.set(false);
        return threadLocal;
    });

    @Inject(method = "drawWithOutline", at = @At("HEAD"))
    private void zine$prepareOutlineCancel(OrderedText text, float x, float y, int color, int outlineColor, Matrix4f matrix, VertexConsumerProvider vertexConsumers, int light, CallbackInfo ci) {
        CANCEL_OUTLINE.remove();
    }

    @Inject(method = "method_37297", at = @At("HEAD"), cancellable = true)
    private void zine$cancelOutline(TextRenderer.Drawer drawer, float[] fs, int i, float f, int j, int k, int index, Style style, int codePoint, CallbackInfoReturnable<Boolean> cir) {
        Boolean cancel = CANCEL_OUTLINE.get();
        if(cancel == null) {
            if(style.zine$containsCustomAttribute(ZineCustomStyleAttributes.OUTLINE)) {
                CANCEL_OUTLINE.set(true);
                cir.setReturnValue(false);
            }else {
                CANCEL_OUTLINE.set(false);
            }
        }else if(cancel) {
            cir.setReturnValue(false);
        }
    }

    @Inject(method = "drawWithOutline", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/font/TextRenderer$GlyphDrawer;drawing(Lnet/minecraft/client/render/VertexConsumerProvider;Lorg/joml/Matrix4f;Lnet/minecraft/client/font/TextRenderer$TextLayerType;I)Lnet/minecraft/client/font/TextRenderer$GlyphDrawer;", ordinal = 0))
    private void zine$resetOutlineCancel(OrderedText text, float x, float y, int color, int outlineColor, Matrix4f matrix, VertexConsumerProvider vertexConsumers, int light, CallbackInfo ci) {
        CANCEL_OUTLINE.set(false);
    }

    @ModifyExpressionValue(method = "method_37297", at = @At(value = "INVOKE", target = "Lnet/minecraft/text/Style;withColor(I)Lnet/minecraft/text/Style;"))
    private Style zine$modifyStyle(Style style) {
        if(style.zine$containsCustomAttribute(ZineCustomStyleAttributes.OUTLINE) && (style.isUnderlined() || style.isStrikethrough())) {
            return style.withStrikethrough(false).withUnderline(false);
        }
        return style;
    }
}
