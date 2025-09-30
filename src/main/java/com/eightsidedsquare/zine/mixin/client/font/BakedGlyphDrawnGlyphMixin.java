package com.eightsidedsquare.zine.mixin.client.font;

import com.eightsidedsquare.zine.core.ZineCustomStyleAttributes;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.client.font.BakedGlyphImpl;
import net.minecraft.client.font.TextRenderLayerSet;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.text.Style;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BakedGlyphImpl.DrawnGlyph.class)
public abstract class BakedGlyphDrawnGlyphMixin {

    @Shadow @Final @Mutable
    private int shadowColor;
    @Shadow @Final
    private int color;
    @Unique @Nullable
    private Integer outlineColor;

    @Inject(method = "<init>", at = @At("TAIL"))
    private void zine$disableShadowIfOutlined(float x, float y, int color, int shadowColor, BakedGlyphImpl glyph, Style style, float boldOffset, float shadowOffset, CallbackInfo ci) {
        if((this.outlineColor = style.zine$getCustomAttribute(ZineCustomStyleAttributes.OUTLINE)) != null) {
            this.shadowColor = 0;
        }
    }

    @WrapOperation(method = "getRenderLayer", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/font/TextRenderLayerSet;getRenderLayer(Lnet/minecraft/client/font/TextRenderer$TextLayerType;)Lnet/minecraft/client/render/RenderLayer;"))
    private RenderLayer zine$changeOutlineLayer(TextRenderLayerSet instance, TextRenderer.TextLayerType layerType, Operation<RenderLayer> original) {
        if(layerType == TextRenderer.TextLayerType.POLYGON_OFFSET && this.outlineColor != null) {
            return original.call(instance, this.color != this.outlineColor ? TextRenderer.TextLayerType.POLYGON_OFFSET : TextRenderer.TextLayerType.NORMAL);
        }
        return original.call(instance, layerType);
    }
}
