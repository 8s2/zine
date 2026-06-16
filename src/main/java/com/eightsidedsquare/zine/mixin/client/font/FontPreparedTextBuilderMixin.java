package com.eightsidedsquare.zine.mixin.client.font;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.font.TextRenderable;
import net.minecraft.client.gui.font.glyphs.BakedGlyph;
import net.minecraft.client.renderer.feature.TextFeatureRenderer;
import net.minecraft.network.chat.Style;
import net.minecraft.util.ARGB;
import org.jspecify.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.ArrayList;
import java.util.List;

@Mixin(Font.PreparedTextBuilder.class)
public abstract class FontPreparedTextBuilderMixin {
    @Shadow
    private float x;
    @Shadow
    private float y;
    @Unique @Nullable
    private List<TextRenderable.Styled> outlineGlyphs;

    @Inject(method = "accept(ILnet/minecraft/network/chat/Style;Lnet/minecraft/client/gui/font/glyphs/BakedGlyph;)Z", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/Font$PreparedTextBuilder;addGlyph(Lnet/minecraft/client/gui/font/TextRenderable$Styled;)V"))
    private void zine$outline(
            int position,
            Style style,
            BakedGlyph glyph,
            CallbackInfoReturnable<Boolean> cir,
            @Local(name = "textColor") int textColor,
            @Local(name = "shadowOffset") float shadowOffset,
            @Local(name = "boldOffset") float boldOffset
    ) {
        if (!style.zine$hasOutline()) {
            return;
        } else if (this.outlineGlyphs == null) {
            this.outlineGlyphs = new ArrayList<>();
        }

        int outline = style.zine$getOutlineColor();
        int color = ARGB.color(ARGB.alphaFloat(outline) * ARGB.alphaFloat(textColor), outline);
        Style outlineStyle = style.zine$withOutlineColor(0).withoutShadow();

        for (int xo = -1; xo <= 1; xo++) {
            for (int yo = -1; yo <= 1; yo++) {
                if (xo == 0 && yo == 0) {
                    continue;
                }
                float x = this.x + xo * shadowOffset;
                float y = this.y + yo * shadowOffset;
                TextRenderable.Styled outlineGlyph = glyph.createGlyph(x, y, color, 0, outlineStyle, boldOffset, shadowOffset);

                if (outlineGlyph != null) {
                    this.outlineGlyphs.add(outlineGlyph);
                }
            }
        }
    }

    @Inject(method = "visit", at = @At("HEAD"))
    private void zine$outlineDisplayMode(Font.GlyphVisitor visitor, CallbackInfo ci) {
        if (this.outlineGlyphs == null) {
            return;
        }
        if (visitor instanceof TextFeatureRenderer.GlyphRenderer glyphRenderer) {
            glyphRenderer.displayMode = Font.DisplayMode.NORMAL;

            for (TextRenderable.Styled glyph : this.outlineGlyphs) {
                glyphRenderer.acceptGlyph(glyph);
            }

            glyphRenderer.displayMode = Font.DisplayMode.POLYGON_OFFSET;
        } else {
            for (TextRenderable.Styled glyph : this.outlineGlyphs) {
                visitor.acceptGlyph(glyph);
            }
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
