package com.eightsidedsquare.zine.core;

import com.eightsidedsquare.zine.common.item.tooltip.*;

public interface ZineTooltipImages {
    static void init() {
        ZineMod.REGISTRY.tooltipImage("bundle", BundleTooltipImage.TYPE);
        ZineMod.REGISTRY.tooltipImage("composite", CompositeTooltip.Image.TYPE);
        ZineMod.REGISTRY.tooltipImage("condition", ConditionTooltipImage.TYPE);
        ZineMod.REGISTRY.tooltipImage("graphics", GraphicsTooltip.Image.TYPE);
        ZineMod.REGISTRY.tooltipImage("text", TextTooltip.Image.TYPE);
    }
}
