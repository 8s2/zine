package com.eightsidedsquare.zine.client.gui;

import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent;
import net.minecraft.world.inventory.tooltip.TooltipComponent;

public record TooltipComponentWrapper(ClientTooltipComponent component) implements TooltipComponent {
}
