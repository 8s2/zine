package com.eightsidedsquare.zine.mixin.client.gui;

import com.eightsidedsquare.zine.client.gui.ZineClientTooltipComponent;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(ClientTooltipComponent.class)
public interface ClientTooltipComponentMixin extends ZineClientTooltipComponent {
}
