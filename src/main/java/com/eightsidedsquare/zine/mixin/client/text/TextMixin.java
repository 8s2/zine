package com.eightsidedsquare.zine.mixin.client.text;

import com.eightsidedsquare.zine.common.text.ZineText;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.OrderedText;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;

import java.util.List;

@Mixin(Text.class)
public interface TextMixin extends ZineText {
    @Override
    default List<Text> zine$wrap(int width) {
        if(!RenderSystem.isOnRenderThread()) {
            return ZineText.super.zine$wrap(width);
        }
        return MinecraftClient.getInstance().textRenderer
                .wrapLines((Text) this, width)
                .stream()
                .map(OrderedText::zine$toText)
                .toList();
    }
}
