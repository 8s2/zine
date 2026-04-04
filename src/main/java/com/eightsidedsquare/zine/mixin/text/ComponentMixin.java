package com.eightsidedsquare.zine.mixin.text;

import com.eightsidedsquare.zine.common.text.ZineComponent;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(Component.class)
public interface ComponentMixin extends ZineComponent {
}
