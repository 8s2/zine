package com.eightsidedsquare.zine.mixin.client.gui;

import com.eightsidedsquare.zine.client.gui.ZineTooltipComponent;
import net.minecraft.client.gui.tooltip.TooltipComponent;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(TooltipComponent.class)
public interface TooltipComponentMixin extends ZineTooltipComponent {
}
