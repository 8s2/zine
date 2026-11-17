package com.eightsidedsquare.zine.core;

import com.eightsidedsquare.zine.common.item.useaction.ConstantUse;
import com.eightsidedsquare.zine.common.item.useaction.SequenceUse;

public final class ZineUseItemActions {
    static void init() {
        ZineMod.REGISTRY.useItemAction("constant", ConstantUse.TYPE);
        ZineMod.REGISTRY.useItemAction("sequence", SequenceUse.TYPE);
    }

    private ZineUseItemActions() {
    }
}
