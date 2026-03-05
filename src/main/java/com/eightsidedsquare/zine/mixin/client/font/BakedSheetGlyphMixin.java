package com.eightsidedsquare.zine.mixin.client.font;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.font.GlyphRenderTypes;
import net.minecraft.client.gui.font.glyphs.BakedSheetGlyph;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.network.chat.Style;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BakedSheetGlyph.class)
public abstract class BakedSheetGlyphMixin {

    @Mixin(BakedSheetGlyph.GlyphInstance.class)
    public static abstract class GlyphInstanceMixin {

        @Shadow
        @Final
        @Mutable
        private int shadowColor;
        @Shadow @Final
        private int color;
        @Unique
        @Nullable
        private Integer outlineColor;

        @Inject(method = "<init>", at = @At("TAIL"))
        private void zine$init(float x, float y, int color, int shadowColor, BakedSheetGlyph glyph, Style style, float boldOffset, float shadowOffset, CallbackInfo ci) {
            if(((this.outlineColor = style.zine$getOutlineColor()) & 0xff000000) != 0) {
                this.shadowColor = 0;
            }
        }

        @WrapOperation(method = "renderType", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/font/GlyphRenderTypes;select(Lnet/minecraft/client/gui/Font$DisplayMode;)Lnet/minecraft/client/renderer/rendertype/RenderType;"))
        private RenderType zine$changeOutlineLayer(GlyphRenderTypes instance, Font.DisplayMode layerType, Operation<RenderType> original) {
            if(layerType == Font.DisplayMode.POLYGON_OFFSET && this.outlineColor != null) {
                return original.call(instance, this.color != this.outlineColor ? Font.DisplayMode.POLYGON_OFFSET : Font.DisplayMode.NORMAL);
            }
            return original.call(instance, layerType);
        }
    }

}
