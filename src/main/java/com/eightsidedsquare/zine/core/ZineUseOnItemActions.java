package com.eightsidedsquare.zine.core;

import com.eightsidedsquare.zine.common.item.useonaction.ConstantUseOn;
import com.eightsidedsquare.zine.common.item.useonaction.SequenceUseOn;

public final class ZineUseOnItemActions {
    static void init() {
        ZineMod.REGISTRY.useOnItemAction("constant", ConstantUseOn.TYPE);
        ZineMod.REGISTRY.useOnItemAction("sequence", SequenceUseOn.TYPE);
    }

    private ZineUseOnItemActions() {
    }
}
