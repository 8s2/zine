package com.eightsidedsquare.zine.mixin.client.font;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.gui.Font;
import net.minecraft.network.chat.Style;
import net.minecraft.util.Util;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Font.class)
public abstract class FontMixin {
    @Unique
    private static final ThreadLocal<Boolean> CANCEL_OUTLINE = Util.make(() -> {
        ThreadLocal<Boolean> threadLocal = new ThreadLocal<>();
        threadLocal.set(false);
        return threadLocal;
    });

    @Inject(method = "drawInBatch8xOutline", at = @At("HEAD"))
    private void zine$prepareOutlineCancel(CallbackInfo ci) {
        CANCEL_OUTLINE.remove();
    }

    @Inject(method = "lambda$drawInBatch8xOutline$0", at = @At("HEAD"), cancellable = true)
    private void zine$cancelOutline(CallbackInfoReturnable<Boolean> cir, @Local(argsOnly = true) Style style) {
        Boolean cancel = CANCEL_OUTLINE.get();
        if(cancel == null) {
            if(style.zine$hasOutline()) {
                CANCEL_OUTLINE.set(true);
                cir.setReturnValue(false);
            }else {
                CANCEL_OUTLINE.set(false);
            }
        }else if(cancel) {
            cir.setReturnValue(false);
        }
    }

    @Inject(method = "drawInBatch8xOutline", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/Font$GlyphVisitor;forMultiBufferSource(Lnet/minecraft/client/renderer/MultiBufferSource;Lorg/joml/Matrix4fc;Lnet/minecraft/client/gui/Font$DisplayMode;I)Lnet/minecraft/client/gui/Font$GlyphVisitor;", ordinal = 0))
    private void zine$resetOutlineCancel(CallbackInfo ci) {
        CANCEL_OUTLINE.set(false);
    }

    @ModifyExpressionValue(method = "lambda$drawInBatch8xOutline$0", at = @At(value = "INVOKE", target = "Lnet/minecraft/network/chat/Style;withColor(I)Lnet/minecraft/network/chat/Style;"))
    private Style zine$modifyStyle(Style style) {
        if(style.zine$hasOutline() && (style.isUnderlined() || style.isStrikethrough())) {
            return style.withStrikethrough(false).withUnderlined(false);
        }
        return style;
    }
}
