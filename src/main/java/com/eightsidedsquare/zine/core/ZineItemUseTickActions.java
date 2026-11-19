package com.eightsidedsquare.zine.core;

import com.eightsidedsquare.zine.common.item.usetickaction.ConditionUseTick;
import com.eightsidedsquare.zine.common.item.usetickaction.SequenceUseTick;

public final class ZineItemUseTickActions {
    static void init() {
        ZineMod.REGISTRY.itemUseTickAction("condition", ConditionUseTick.TYPE);
        ZineMod.REGISTRY.itemUseTickAction("sequence", SequenceUseTick.TYPE);
    }

    private ZineItemUseTickActions() {
    }
}
