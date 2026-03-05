package com.eightsidedsquare.zine.mixin.client.text;

import com.eightsidedsquare.zine.common.text.ZineComponent;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.util.FormattedCharSequence;
import org.spongepowered.asm.mixin.Mixin;

import java.util.List;

@Mixin(Component.class)
public interface ComponentMixin extends ZineComponent {
    @Override
    default List<Component> zine$wrap(int width) {
        return Minecraft.getInstance().font
                .split((Component) this, width)
                .stream()
                .map(FormattedCharSequence::zine$toText)
                .toList();
    }
}
