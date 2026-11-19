package com.eightsidedsquare.zine.core;

import com.eightsidedsquare.zine.common.item.useduration.ComponentValueUseDuration;
import com.eightsidedsquare.zine.common.item.useduration.ConstantUseDuration;

public final class ZineItemUseDurations {
    static void init() {
        ZineMod.REGISTRY.itemUseDuration("component_value", ComponentValueUseDuration.TYPE);
        ZineMod.REGISTRY.itemUseDuration("constant", ConstantUseDuration.TYPE);
    }

    private ZineItemUseDurations() {
    }
}
