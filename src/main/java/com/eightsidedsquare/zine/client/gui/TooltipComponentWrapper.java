package com.eightsidedsquare.zine.client.gui;

import net.minecraft.client.gui.tooltip.TooltipComponent;
import net.minecraft.item.tooltip.TooltipData;

public record TooltipComponentWrapper(TooltipComponent component) implements TooltipData {
}
