package com.eightsidedsquare.zine.mixin.client.font;

import net.minecraft.client.font.TextRenderer;
import net.minecraft.text.Style;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(TextRenderer.class)
public interface TextRendererAccessor {

    @Invoker("method_37297")
    boolean zine$invokeAcceptOutline(
            TextRenderer.Drawer drawer,
            float[] advance,
            int dX,
            float y,
            int dY,
            int outlineColor,
            int index,
            Style style,
            int codePoint
    );

}
