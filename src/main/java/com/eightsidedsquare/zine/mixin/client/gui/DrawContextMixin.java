package com.eightsidedsquare.zine.mixin.client.gui;

import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.tooltip.TooltipComponent;
import net.minecraft.client.gui.tooltip.TooltipPositioner;
import net.minecraft.util.Identifier;
import org.jspecify.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(DrawContext.class)
public abstract class DrawContextMixin {

    @Inject(method = "drawTooltipImmediately", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/tooltip/TooltipComponent;getWidth(Lnet/minecraft/client/font/TextRenderer;)I"))
    private void zine$cacheTooltipDimensions(TextRenderer textRenderer, List<TooltipComponent> components, int x, int y, TooltipPositioner positioner, @Nullable Identifier texture, CallbackInfo ci, @Local TooltipComponent component) {
        component.zine$cacheDimensions(textRenderer);
    }

}
