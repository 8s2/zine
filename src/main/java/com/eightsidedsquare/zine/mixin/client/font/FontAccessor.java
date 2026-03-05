package com.eightsidedsquare.zine.mixin.client.font;

import net.minecraft.client.gui.Font;
import net.minecraft.network.chat.Style;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(Font.class)
public interface FontAccessor {

    @Invoker("lambda$drawInBatch8xOutline$0")
    boolean zine$invokeAcceptOutline(
            Font.PreparedTextBuilder drawer,
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
