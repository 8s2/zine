package com.eightsidedsquare.zine.mixin.client.text;

import com.eightsidedsquare.zine.common.text.ZineComponent;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.util.FormattedCharSequence;
import org.spongepowered.asm.mixin.Mixin;

import java.util.List;

@Mixin(Component.class)
public interface ComponentMixin extends ZineComponent {
    @Override
    default List<Component> zine$wrap(int width) {
        if(!RenderSystem.isOnRenderThread()) {
            return ZineComponent.super.zine$wrap(width);
        }
        return Minecraft.getInstance().font
                .split((Component) this, width)
                .stream()
                .map(FormattedCharSequence::zine$toComponent)
                .toList();
    }
}
