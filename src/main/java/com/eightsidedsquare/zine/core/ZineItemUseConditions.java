package com.eightsidedsquare.zine.core;

import com.eightsidedsquare.zine.common.item.usecondition.*;

public final class ZineItemUseConditions {
    static void init() {
        ZineMod.REGISTRY.itemUseCondition("all_of", AllOfCondition.TYPE);
        ZineMod.REGISTRY.itemUseCondition("any_of", AnyOfCondition.TYPE);
        ZineMod.REGISTRY.itemUseCondition("block", BlockCondition.TYPE);
        ZineMod.REGISTRY.itemUseCondition("inverse", InverseUseCondition.TYPE);
        ZineMod.REGISTRY.itemUseCondition("item", ItemCondition.TYPE);
        ZineMod.REGISTRY.itemUseCondition("secondary_use", SecondaryUseCondition.TYPE);
    }

    private ZineItemUseConditions() {
    }
}
